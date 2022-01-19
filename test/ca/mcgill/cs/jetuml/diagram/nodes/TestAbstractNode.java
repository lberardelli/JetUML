package ca.mcgill.cs.jetuml.diagram.nodes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.ShallowClone;

public class TestAbstractNode {

    @Test
    @ShallowClone(factory = "new ClassNode()", uut = "clone()")
    public void clone_ReturnShallowCopy() {
        AbstractNode initial = new ClassNode();
        AbstractNode cloned = initial.clone();
        assertNotSame(initial, cloned);
    }
}
