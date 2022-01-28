/**
 * **************************************************************************** JetUML - A desktop application for fast
 * UML diagramming.
 *
 * Copyright (C) 2020 by McGill University.
 *
 * See: https://github.com/prmr/JetUML
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * http://www.gnu.org/licenses. *****************************************************************************
 */
package ca.mcgill.cs.jetuml.geom;

import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.ToString;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class TestLine
{

	private static final Point ZERO = new Point(0, 0);

	private static final Point ONE = new Point(1, 1);

	private static final Line ZERO_TO_ONE = new Line(ZERO, ONE);

	@Test
	public void testToString()
	{
		assertEquals("[(x=0,y=0), (x=1,y=1)]", ZERO_TO_ONE.toString());
	}

	// @Test
	// public void testHashCode()
	// {
	// assertEquals(31745, ZERO_TO_ONE.hashCode());
	// }
	// @Test
	// public void testClone()
	// {
	// Line clone = ZERO_TO_ONE.clone();
	// assertFalse( clone == ZERO_TO_ONE);
	// assertTrue( ZERO_TO_ONE.getPoint1() == clone.getPoint1() );
	// assertTrue( ZERO_TO_ONE.getPoint2() == clone.getPoint2() );
	// }
	// @SuppressWarnings("unlikely-arg-type")
	// @Test
	// public void testEquals()
	// {
	// assertTrue(ZERO_TO_ONE.equals(ZERO_TO_ONE));
	// // assertTrue(ZERO_TO_ONE.equals(ZERO_TO_ONE.clone()));
	// assertFalse(ZERO_TO_ONE.equals(new Line(ONE, ZERO)));
	// assertFalse(ZERO_TO_ONE.equals(null));
	// assertFalse(ZERO_TO_ONE.equals("Foo"));
	// assertFalse(ZERO_TO_ONE.equals(new Line(ZERO, ZERO)));
	// assertFalse(ZERO_TO_ONE.equals(new Line(ONE, ONE)));
	// }
	@Test
	public void testPositions()
	{
		assertEquals(0, ZERO_TO_ONE.getX1());
		assertEquals(0, ZERO_TO_ONE.getY1());
		assertEquals(1, ZERO_TO_ONE.getX2());
		assertEquals(1, ZERO_TO_ONE.getY2());
	}

	@Test
	@ToString(factory = "new Line(new Point(1,1), new Point(3,3))", target = "\"[(x=1,y=1), (x=3,y=3)]\"", uut = "toString()")
	public void toString_ReturnsCorrectlyFormatted()
	{
		assertEquals("[(x=1,y=1), (x=3,y=3)]", new Line(new Point(1, 1), new Point(3, 3)).toString());
	}

	@Test
	public void test_RectangleFactory()
	{
		Rectangle ret = ZERO_TO_ONE.spanning();
		assertEquals(0, ret.getX());
		assertEquals(0, ret.getY());
		assertEquals(1, ret.getHeight());
		assertEquals(1, ret.getWidth());
	}
}
