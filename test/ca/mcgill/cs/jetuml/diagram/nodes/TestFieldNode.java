package ca.mcgill.cs.jetuml.diagram.nodes;

import static org.junit.jupiter.api.Assertions.*;
import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.AssertBool;
import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.AssertBoolWithMods;

import org.junit.jupiter.api.Test;

public class TestFieldNode {

    @Test
    @AssertBoolWithMods(bool = "True", factory = "new FieldNode()", params = {}, state = "HasDefiningObject", statement = "target.link(new ObjectNode())", uut = "hasParent()")
    public void hasParent_WhenHasDefiningObject_ReturnTrue() {
        FieldNode target = new FieldNode();
        target.link(new ObjectNode());
        boolean oracle = target.hasParent();
        assertTrue(oracle);
    }

    @Test
    @AssertBool(bool = "False", factory = "new FieldNode()", state = "NoObject", params = {}, uut = "hasParent()")
    public void hasParent_WhenNoObject_ReturnFalse() {
        boolean actual = new FieldNode().hasParent();
        assertFalse(actual);
    }
}
