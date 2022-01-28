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
import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class TestDimension
{

	// Fields because immutable, no need for @Before method
	private static Dimension DIM_0 = new Dimension(0, 0);

	private static Dimension DIM_1 = new Dimension(10, 10);

	private static Dimension DIM_2 = new Dimension(5, 5);

	private static Dimension DIM_3 = new Dimension(10, 5);

	private static Dimension DIM_4 = new Dimension(5, 10);

	@Test
	void testEquals_Same()
	{
		assertTrue(DIM_0.equals(DIM_0));
	}

	@Test
	void testEquals_Equal()
	{
		assertTrue(DIM_0.equals(new Dimension(0, 0)));
		assertTrue(DIM_1.equals(new Dimension(10, 10)));
		assertTrue(DIM_2.equals(new Dimension(5, 5)));
	}

	@Test
	void testEquals_Null()
	{
		assertFalse(DIM_0.equals(null));
	}

	@Test
	void testEquals_NotSameClass()
	{
		assertFalse(DIM_0.equals((Object) "foo"));
	}

	@Test
	void testEquals_NotEqual()
	{
		assertFalse(DIM_0.equals(DIM_1));
		assertFalse(DIM_0.equals(DIM_2));
		assertFalse(DIM_0.equals(DIM_3));
		assertFalse(DIM_0.equals(DIM_4));
		assertFalse(DIM_1.equals(DIM_0));
		assertFalse(DIM_1.equals(DIM_2));
		assertFalse(DIM_1.equals(DIM_3));
		assertFalse(DIM_1.equals(DIM_4));
	}

	@Test
	void testInclude_Zero()
	{
		assertEquals(new Dimension(0, 0), DIM_0.include(0, 0));
		assertEquals(new Dimension(10, 10), DIM_1.include(0, 0));
		assertEquals(new Dimension(5, 5), DIM_2.include(0, 0));
	}

	@Test
	void testInclude_Grow()
	{
		assertEquals(new Dimension(10, 10), DIM_2.include(10, 10));
		assertEquals(new Dimension(5, 10), DIM_2.include(5, 10));
	}

	@Test
	void testToString()
	{
		assertEquals("[Dimension: w=5 x h=10]", DIM_4.toString());
	}

	@Test
	void testWidth()
	{
		assertEquals(10, DIM_1.width());
	}

	@Test
	void testHeight()
	{
		assertEquals(10, DIM_1.height());
	}

	@Test
	void testHashCode()
	{
		assertEquals(1281, DIM_1.hashCode());
		assertEquals(1121, DIM_2.hashCode());
		assertEquals(1126, DIM_3.hashCode());
		assertEquals(1276, DIM_4.hashCode());
	}

	@Nested
	@EqualsContract(factory1 = "new Dimension(1,1)", factory2 = "new Dimension(1,2)", factory3 = "new Dimension(1,1)", uut = "equals(Object)")
	public class EqualsContractTest
	{

		@Test
		public void whenDimensionsAreSame_ReturnTrue()
		{
			boolean actual = new Dimension(1, 1).equals(new Dimension(1, 1));
			assertTrue(actual);
		}

		@Test
		public void whenAppliedSymmetrically_ReturnSameResult()
		{
			boolean actual1 = new Dimension(1, 1).equals(new Dimension(1, 2));
			boolean actual2 = new Dimension(1, 2).equals(new Dimension(1, 1));
			assertEquals(actual1, actual2);
		}

		@Test
		public void whenDimensionsAreEqual_ReturnTrue()
		{
			boolean actual = new Dimension(1, 1).equals(new Dimension(1, 1));
			assertTrue(actual);
		}

		@Test
		public void whenDimensionsAreDifferent_ReturnFalse()
		{
			boolean actual = new Dimension(1, 1).equals(new Dimension(1, 2));
			assertFalse(actual);
		}

		@Test
		public void whenDimensionIsNull_ReturnFalse()
		{
			boolean actual = new Dimension(1, 1).equals(null);
			assertFalse(actual);
		}
	}

	@Test
	@ToString(factory = "new Dimension(2, 3)", target = "\"[Dimension: w=2 x h=3]\"", uut = "toString()")
	public void toString_ReturnsCorrectlyFormatted()
	{
		assertEquals("[Dimension: w=2 x h=3]", new Dimension(2, 3).toString());
	}

	@Test
	@AsFactory(factory = "new Dimension(1,1)", oracle = "1", verification_method = "width()", state = "foo", params = {
			"1", "1" }, uut = "include(int,int)")
	public void include_testAsFactoryfoo()
	{
		Dimension res = new Dimension(1, 1).include(1, 1);
		assertEquals(1, res.width());
	}
}
