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
import static org.junit.jupiter.api.Assertions.*;
import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class TestRectangle
{

	private static final Rectangle RECTANGLE_1 = new Rectangle(0, 0, 60, 40);

	private static final Rectangle RECTANGLE_2 = new Rectangle(100, 20, 1, 1);

	@Test
	public void testToString()
	{
		assertEquals("[x=0, y=0, w=60, h=40]", RECTANGLE_1.toString());
	}

	@Test
	public void testMaxXY()
	{
		assertEquals(60, RECTANGLE_1.getMaxX());
		assertEquals(40, RECTANGLE_1.getMaxY());
		assertEquals(101, RECTANGLE_2.getMaxX());
		assertEquals(21, RECTANGLE_2.getMaxY());
	}

	@Test
	public void testHashCode()
	{
		assertEquals(2172821, RECTANGLE_1.hashCode());
		assertEquals(957393, RECTANGLE_2.hashCode());
	}

	@Test
	public void tesEquals()
	{
		assertTrue(RECTANGLE_1.equals(RECTANGLE_1));
		assertFalse(RECTANGLE_1.equals(null));
		assertTrue(RECTANGLE_1.equals(new Rectangle(0, 0, 60, 40)));
		assertFalse(RECTANGLE_1.equals(RECTANGLE_2));
	}

	@Test
	public void testTranslated()
	{
		assertEquals(new Rectangle(10, 20, 60, 40), RECTANGLE_1.translated(10, 20));
		assertEquals(new Rectangle(-10, -20, 60, 40), RECTANGLE_1.translated(-10, -20));
	}

	@Test
	public void testContainsPoint()
	{
		assertTrue(RECTANGLE_1.contains(new Point(10, 20)));
		assertTrue(RECTANGLE_1.contains(new Point(0, 0)));
		assertTrue(RECTANGLE_1.contains(new Point(60, 0)));
		assertTrue(RECTANGLE_1.contains(new Point(0, 40)));
		assertFalse(RECTANGLE_1.contains(new Point(0, 41)));
	}

	@Test
	public void testGetCenter()
	{
		Point center = RECTANGLE_1.getCenter();
		assertEquals(30, center.getX());
		assertEquals(20, center.getY());
		center = RECTANGLE_1.translated(10, 10).getCenter();
		assertEquals(40, center.getX());
		assertEquals(30, center.getY());
		center = RECTANGLE_2.getCenter();
		assertEquals(100, center.getX());
		assertEquals(20, center.getY());
	}

	@Test
	public void testAddPoint()
	{
		Rectangle rectangle = new Rectangle(10, 10, 0, 0);
		rectangle = rectangle.add(new Point(20, 30));
		assertEquals(new Rectangle(10, 10, 10, 20), rectangle);
		rectangle = rectangle.add(new Point(15, 15));
		assertEquals(new Rectangle(10, 10, 10, 20), rectangle);
		rectangle = rectangle.add(new Point(100, 100));
		assertEquals(new Rectangle(10, 10, 90, 90), rectangle);
		rectangle = rectangle.add(new Point(0, 0));
		assertEquals(new Rectangle(0, 0, 100, 100), rectangle);
	}

	@Test
	public void testAddRectangle()
	{
		Rectangle rectangle = new Rectangle(10, 10, 0, 0);
		rectangle = rectangle.add(new Rectangle(0, 0, 20, 20));
		assertEquals(new Rectangle(0, 0, 20, 20), rectangle);
	}

	@EqualsContract(factory1 = "new Rectangle(0,0,1,1)", factory2 = "new Rectangle(0,0,1,3)", factory3 = "new Rectangle(0,0,1,1)", uut = "equals(Object)")
	@Nested
	public class EqualsContractTest
	{

		@Test
		public void whenRectanglesAreSame_ReturnTrue()
		{
			boolean actual = new Rectangle(0, 0, 1, 1).equals(new Rectangle(0, 0, 1, 1));
			assertTrue(actual);
		}

		@Test
		public void whenAppliedSymmetrically_ReturnSameResult()
		{
			boolean actual1 = new Rectangle(0, 0, 1, 1).equals(new Rectangle(0, 0, 1, 3));
			boolean actual2 = new Rectangle(0, 0, 1, 3).equals(new Rectangle(0, 0, 1, 1));
			assertEquals(actual1, actual2);
		}

		@Test
		public void whenRectanglesAreEqual_ReturnTrue()
		{
			boolean actual = new Rectangle(0, 0, 1, 1).equals(new Rectangle(0, 0, 1, 1));
			assertTrue(actual);
		}

		@Test
		public void whenRectanglesAreDifferent_ReturnFalse()
		{
			boolean actual = new Rectangle(0, 0, 1, 1).equals(new Rectangle(0, 0, 1, 3));
			assertFalse(actual);
		}

		@Test
		public void whenRectangleIsNull_ReturnFalse()
		{
			boolean actual = new Rectangle(0, 0, 1, 1).equals(null);
			assertFalse(actual);
		}
	}

	@Test
	@AssertBools(factory = "new Rectangle(0,0,1,1)", falseParams = "new Point(2,2)", falseState = "PointNotInscribed", trueParams = "new Point(0,0)", trueState = "PointIsInscribed", uut = "contains(Point)")
	public void contains_ReturnTest()
	{
		boolean actual = new Rectangle(0, 0, 1, 1).contains(new Point(0, 0));
		boolean fOracle = new Rectangle(0, 0, 1, 1).contains(new Point(2, 2));
		assertTrue(actual);
		assertFalse(fOracle);
	}

	@Test
	@AssertBools(factory = "new Rectangle(0,0,10,10)", falseParams = "new Rectangle(0,0,20,20)", falseState = "NotInscribed", trueParams = "new Rectangle(0,0,1,1)", trueState = "Inscribed", uut = "contains(Rectangle)")
	public void contains_ReturnRectTest()
	{
		boolean actual = new Rectangle(0, 0, 10, 10).contains(new Rectangle(0, 0, 1, 1));
		boolean fOracle = new Rectangle(0, 0, 10, 10).contains(new Rectangle(0, 0, 20, 20));
		assertTrue(actual);
		assertFalse(fOracle);
	}

	@Test
	@ToString(factory = "new Rectangle(0,0,1,1)", target = "\"[x=0, y=0, w=1, h=1]\"", uut = "toString()")
	public void toString_Test()
	{
		assertEquals("[x=0, y=0, w=1, h=1]", new Rectangle(0, 0, 1, 1).toString());
	}
}
