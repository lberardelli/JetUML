package ca.mcgill.cs.jetuml.application;

import org.junit.jupiter.api.Test;
import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

/**
 * ****************************************************************************
 *  JetUML - A desktop application for fast UML diagramming.
 *
 *  Copyright (C) 2020 by McGill University.
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
public class TestVersion {

    private static final Version VERSION1 = Version.create(2, 5);

    private static final Version VERSION2 = Version.create(0, 6, 1);

    @Test
    public void testToString_Patch() {
        assertEquals("2.5", VERSION1.toString());
    }

    @Test
    public void testToString_NoPatch() {
        assertEquals("0.6.1", VERSION2.toString());
    }

    @Test
    public void testParse_Patch() {
        assertEquals(VERSION2, Version.parse("0.6.1"));
    }

    @Test
    public void testParse_NoPatch() {
        assertEquals(VERSION1, Version.parse("2.5"));
    }

    @Test
    public void testParse_TooShort() {
        assertThrows(IllegalArgumentException.class, () -> Version.parse("0"));
    }

    @Test
    public void testParse_TooLong() {
        assertThrows(IllegalArgumentException.class, () -> Version.parse("0.1.2.3"));
    }

    @Test
    public void testParse_NumberFormat() {
        assertThrows(IllegalArgumentException.class, () -> Version.parse("a.1.2"));
        assertThrows(IllegalArgumentException.class, () -> Version.parse("0.a.2"));
        assertThrows(IllegalArgumentException.class, () -> Version.parse("1.2.a"));
    }

    @Test
    public void testEquals_Same() {
        assertTrue(VERSION1.equals(VERSION1));
    }

    @Test
    public void testEquals_Null() {
        assertFalse(VERSION1.equals(null));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEquals_NotSameClass() {
        assertFalse(VERSION1.equals("2.5.0"));
    }

    @Test
    public void testEquals_DifferentMajor() {
        assertFalse(VERSION1.equals(Version.create(3, 5, 0)));
    }

    @Test
    public void testEquals_DifferentMinor() {
        assertFalse(VERSION1.equals(Version.create(2, 6, 0)));
    }

    @Test
    public void testEquals_DifferentPatch() {
        assertFalse(VERSION1.equals(Version.create(2, 5, 1)));
    }

    @Test
    public void testEquals_True() {
        assertTrue(VERSION1.equals(Version.create(2, 5, 0)));
    }

    @Test
    public void testCompareTo_Equals() {
        assertTrue(VERSION1.compareTo(VERSION1) == 0);
        assertTrue(VERSION2.compareTo(VERSION2) == 0);
    }

    @Test
    public void testCompareTo_Lower() {
        assertTrue(VERSION2.compareTo(VERSION1) < 0);
    }

    @Test
    public void testCompareTo_Higher() {
        assertTrue(VERSION1.compareTo(VERSION2) > 0);
    }

    @Test
    public void compatibleWith_Same() {
        assertTrue(VERSION1.compatibleWith(VERSION1));
        assertTrue(VERSION2.compatibleWith(VERSION2));
    }

    @Test
    public void compatibleWith_False() {
        assertFalse(Version.create(2, 1).compatibleWith(Version.create(3, 0)));
        assertFalse(Version.create(2, 1, 1).compatibleWith(Version.create(3, 0, 1)));
    }

    @Test
    public void compatibleWith_True() {
        assertTrue(Version.create(2, 1).compatibleWith(Version.create(2, 2)));
        assertTrue(Version.create(3, 0).compatibleWith(Version.create(3, 1)));
    }

    @Test
    public void whenAppliedSymmetrically_ReturnSameResult() {
        boolean actual1 = Version.create(1, 1, 1).equals(Version.create(0, 0));
        boolean actual2 = Version.create(0, 0).equals(Version.create(1, 1, 1));
        assertEquals(actual1, actual2);
    }

    @Test
    public void whenVersionsAreEqual_ReturnTrue() {
        boolean actual = Version.create(1, 1, 1).equals(Version.create(1, 1, 1));
        assertTrue(actual);
    }

    @Test
    public void whenVersionsAreDifferent_ReturnFalse() {
        boolean actual = Version.create(1, 1, 1).equals(Version.create(0, 0));
        assertFalse(actual);
    }

    @Test
    public void whenVersionIsNull_ReturnFalse() {
        boolean actual = Version.create(1, 1, 1).equals(null);
        assertFalse(actual);
    }

    @EqualsContract(factory1 = "Version.create(1,1,1)", factory2 = "Version.create(0,0)", factory3 = "Version.create(1,1,1)", uut = "equals(Object)")
    public class EqualsContractTest {

        @Test
        public void whenVersionsAreSame_ReturnTrue() {
            boolean actual = Version.create(1, 1, 1).equals(Version.create(1, 1, 1));
            assertTrue(actual);
        }

        @Test
        public void whenAppliedSymmetrically_ReturnSameResult() {
            boolean actual1 = Version.create(1, 1, 1).equals(Version.create(0, 0));
            boolean actual2 = Version.create(0, 0).equals(Version.create(1, 1, 1));
            assertEquals(actual1, actual2);
        }

        @Test
        public void whenVersionsAreEqual_ReturnTrue() {
            boolean actual = Version.create(1, 1, 1).equals(Version.create(1, 1, 1));
            assertTrue(actual);
        }

        @Test
        public void whenVersionsAreDifferent_ReturnFalse() {
            boolean actual = Version.create(1, 1, 1).equals(Version.create(0, 0));
            assertFalse(actual);
        }

        @Test
        public void whenVersionIsNull_ReturnFalse() {
            boolean actual = Version.create(1, 1, 1).equals(null);
            assertFalse(actual);
        }
    }

    /*
	 * $method$ returns a correctly formatted string
	 */
    @Test
    @ToString(factory = "Version.create(1,1,1)", target = "\"1.1.1\"", uut = "toString()")
    public void toString_ReturnsCorrectlyFormatted() {
        assertEquals("1.1.1", Version.create(1, 1, 1).toString());
    }

    @Test
    @AssertThrows(exType = java.lang.IllegalArgumentException.class, factory = "Version", state = "notIntegers", params = "\"a.a.a\"", uut = "parse(String)")
    public void parse_WhennotIntegers_ThrowIllegalArgumentException() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> Version.parse("a.a.a"));
    }

    @Test
    @AssertThrows(exType = java.lang.IllegalArgumentException.class, factory = "Version", state = "tooManyTokens", params = "\"i.vi.va.va.va\"", uut = "parse(String)")
    public void parse_WhentooManyTokens_ThrowIllegalArgumentException() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> Version.parse("i.vi.va.va.va"));
    }

    @Test
    @AssertThrows(state = "Invalid", exType = java.lang.IllegalArgumentException.class, params = { "\"ivica\"" }, factory = "Version", uut = "parse(String)")
    public void parse_WhenInvalid_ThrowIllegalArgumentException() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> Version.parse("ivica"));
    }
}
