/**
 * ****************************************************************************
 *  JetUML - A desktop application for fast UML diagramming.
 *
 *  Copyright (C) 2020, 2021 by McGill University.
 *
 *  See: https://github.com/prmr/JetUML
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses.
 * *****************************************************************************
 */
package ca.mcgill.cs.jetuml.persistence;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ca.mcgill.cs.jetuml.JavaFXLoader;
import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.AssertThrows;
import ca.mcgill.cs.jetuml.diagram.ControlFlow;
import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.DiagramElement;
import ca.mcgill.cs.jetuml.diagram.Edge;
import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.diagram.edges.NoteEdge;
import ca.mcgill.cs.jetuml.diagram.nodes.CallNode;
import ca.mcgill.cs.jetuml.diagram.nodes.NoteNode;
import ca.mcgill.cs.jetuml.diagram.nodes.PointNode;
import ca.mcgill.cs.jetuml.geom.Rectangle;
import ca.mcgill.cs.jetuml.viewers.edges.EdgeViewerRegistry;
import ca.mcgill.cs.jetuml.viewers.nodes.NodeViewerRegistry;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/*
 * This class tests that the PersistenceService works by loading a 
 * number of elaborate test diagrams, storing the bounds of each diagram
 * element, saving the diagram in a temporary files, loading it back in,
 * and checking that the bounds of each element still corresponds to the 
 * original bounds.
 * 
 * The implementation of this test requires a specialized mechanism to 
 * assign each object a "unique" code that is unaltered by serialization.
 * This code is generated on a best-effort basis, an can be helped by 
 * effective naming of diagram elements.
 */
public class TestPersistenceService {

    private static final Path PATH_TEST_FILES = Path.of("testdata");

    private static final Path PATH_TEMPORARY_FILE = PATH_TEST_FILES.resolve("tmp");

    @BeforeAll
    public static void setupClass() {
        JavaFXLoader.load();
    }

    @ParameterizedTest
    @ValueSource(strings = { "testPersistenceService.class.jet", "testPersistenceService2.class.jet", "testPersistenceService.sequence.jet", "testPersistenceService.state.jet", "testPersistenceService.object.jet", "testPersistenceService.usecase.jet" })
    public void test(String pFileName) throws Exception {
        Diagram diagram = PersistenceService.read(PATH_TEST_FILES.resolve(pFileName).toFile()).diagram();
        Map<String, Rectangle> bounds = new HashMap<>();
        // Create a list of all bounds, indexed by object hash
        PersistenceTestUtils.getAllNodes(diagram).forEach(node -> bounds.put(hash(node), NodeViewerRegistry.getBounds(node)));
        diagram.edges().forEach(edge -> bounds.put(hash(edge), EdgeViewerRegistry.getBounds(edge)));
        // Save the diagram in a new file, and re-load it
        File temporaryFile = PATH_TEMPORARY_FILE.toFile();
        PersistenceService.save(diagram, temporaryFile);
        diagram = PersistenceService.read(temporaryFile).diagram();
        temporaryFile.delete();
        // Check that all bounds match
        PersistenceTestUtils.getAllNodes(diagram).forEach(node -> assertEquals(bounds.get(hash(node)), NodeViewerRegistry.getBounds(node), hash(node)));
        diagram.edges().forEach(edge -> assertEquals(bounds.get(hash(edge)), EdgeViewerRegistry.getBounds(edge), hash(edge)));
    }

    /*
	 * @return A string that is intended to uniquely represent the diagram element within a diagram,
	 * in a way that is resilient to serialization.
	 */
    private static String hash(DiagramElement pElement) {
        StringJoiner result = new StringJoiner("|");
        // At a minimum, a hash includes the element's type and all its property values
        result.add(pElement.getClass().getSimpleName());
        pElement.properties().forEach(property -> result.add(property.get().toString()));
        // Because many edges don't have many properties, we add the properties of the
        // start and end nodes
        if (pElement instanceof Edge) {
            result.add(hash(((Edge) pElement).getStart()));
            result.add(hash(((Edge) pElement).getEnd()));
        }
        // Call nodes don't have any properties, so we add their order in the control flow
        // as a unique indicator.
        if (pElement instanceof CallNode) {
            result.add(Integer.toString(getNumberOfCallers((CallNode) pElement)));
        }
        // Point nodes don't have any properties, so we add the properties of the other node
        if (pElement instanceof PointNode) {
            Edge edge = ((PointNode) pElement).getDiagram().get().edgesConnectedTo(((Node) pElement)).iterator().next();
            Node note = edge.getStart();
            if (note == pElement) {
                note = edge.getEnd();
            }
            note.properties().forEach(property -> result.add(property.get().toString()));
        }
        // NoteEdges don't have any properties, so we add their node nodes properties
        if (pElement instanceof NoteEdge) {
            Node node = ((NoteEdge) pElement).getStart();
            if (!(node instanceof NoteNode)) {
                node = ((NoteEdge) pElement).getEnd();
            }
            result.add(hash(node));
        }
        return result.toString();
    }

    /*
	 * This method is located here instead of in ControlFlow because the only usage
	 * scenario is for testing, so we prefer not to pollute the classe's interface.
	 */
    private static int getNumberOfCallers(CallNode pNode) {
        assert pNode.getDiagram().isPresent();
        ControlFlow controlFlow = new ControlFlow(pNode.getDiagram().get());
        Optional<CallNode> caller = controlFlow.getCaller(pNode);
        int result = 0;
        while (caller.isPresent()) {
            result++;
            caller = controlFlow.getCaller(caller.get());
        }
        return result;
    }

    @Test
    @AssertThrows(exType = java.io.IOException.class, factory = "PersistenceService", state = "FileNotRead", params = "new File(\"foo\")", uut = "read(File)")
    public void read_WhenFileNotRead_ThrowIOException() {
        assertThrows(java.io.IOException.class, () -> PersistenceService.read(new File("foo")));
    }
}
