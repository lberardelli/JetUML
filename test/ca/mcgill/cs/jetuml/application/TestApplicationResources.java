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
package ca.mcgill.cs.jetuml.application;

import static ca.mcgill.cs.jetuml.application.ApplicationResources.RESOURCES;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.cs.jetuml.annotations.DScribeAnnotations.AssertBool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class TestApplicationResources
{

	@Test
	public void testStringFound()
	{
		assertEquals("JetUML", RESOURCES.getString("application.name"));
	}

	@Test
	public void testStringNotFound()
	{
		assertEquals("[Resource cannot be found]", RESOURCES.getString("zzzzzzzzzzzzzz"));
	}

	@Test
	@AssertBool(bool = "False", factory = "ApplicationResources.RESOURCES", state = "NotResource", params = "\"foo\"", uut = "containsKey(String)")
	public void containsKey_WhenNotResource_Returnfalse()
	{
		boolean actual = ApplicationResources.RESOURCES.containsKey("foo");
		assertFalse(actual);
	}
}
