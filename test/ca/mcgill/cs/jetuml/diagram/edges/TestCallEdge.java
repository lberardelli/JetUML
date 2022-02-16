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
package ca.mcgill.cs.jetuml.diagram.edges;

import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.*;
import ca.mcgill.cs.jetuml.diagram.PropertyName;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class TestCallEdge {

    @Test
    public void testGetWithProperty() {
        CallEdge edge = new CallEdge();
        assertFalse(edge.isSignal());
        edge.setSignal(true);
        assertTrue(edge.isSignal());
        assertTrue((boolean) edge.properties().get(PropertyName.SIGNAL).get());
        edge.properties().get(PropertyName.SIGNAL).set(false);
        assertFalse((boolean) edge.properties().get(PropertyName.SIGNAL).get());
        assertFalse(edge.isSignal());
        edge.properties().get(PropertyName.MIDDLE_LABEL).set("Foo");
        assertEquals("Foo", edge.getMiddleLabel());
    }

    @Test
    public void testGetWithPropertyAndClone() {
        CallEdge edge = new CallEdge();
        CallEdge clone = (CallEdge) edge.clone();
        edge.properties().get(PropertyName.MIDDLE_LABEL).set("Foo");
        assertEquals("Foo", edge.properties().get(PropertyName.MIDDLE_LABEL).get());
        assertEquals("", clone.properties().get(PropertyName.MIDDLE_LABEL).get());
    }

    @Test
    @AssertBoolWithMods(bool = "True", factory = "new CallEdge()", params = {}, state = "HasSignal", statement = "target.setSignal(true)", uut = "isSignal()")
    public void isSignal_WhenHasSignal_ReturnTrue() {
        CallEdge target = new CallEdge();
        target.setSignal(true);
        boolean oracle = target.isSignal();
        assertTrue(oracle);
    }

    @Test
    @AssertBool(bool = "False", factory = "new CallEdge()", state = "NewCallEdge", params = {}, uut = "isSignal()")
    public void isSignal_WhenNewCallEdge_ReturnFalse() {
        boolean actual = new CallEdge().isSignal();
        assertFalse(actual);
    }
}
