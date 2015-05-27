/*
 * JenkinsRemote - A tool that sits in the tray and provide Jenkins information/control.
 *
 * Copyright 2009 SP extreme (http://www.spextreme.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spextreme.jenkins.remote;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.spextreme.jenkins.remote.Utilities;

/**
 * Tests the utilities method.
 */
public class UtilitiesTest
{
	/**
	 * Tests build duration is properly formatted.
	 */
	@Test
	public void testFormatBuildDuration( )
	{
		assertEquals( "", Utilities.formatBuildDuration( 0 ) );
		assertEquals( "1 sec ", Utilities.formatBuildDuration( 1000 ) );
		assertEquals( "1 min 0 secs ", Utilities.formatBuildDuration( 60000 ) );
		assertEquals( "1 min 5 secs ", Utilities.formatBuildDuration( 65000 ) );
		assertEquals( "2 mins 5 secs ", Utilities.formatBuildDuration( 125000 ) );
		assertEquals( "1 hr 5 mins 2 secs ", Utilities.formatBuildDuration( 3902000 ) );
		assertEquals( "2 hrs 0 mins 0 secs ", Utilities.formatBuildDuration( 7200000 ) );
	}

	/**
	 * Test parsing the int.
	 */
	@Test
	public void testParseInt( )
	{
		assertEquals( 45, Utilities.parseInt( "45", 5 ) );
		assertEquals( 6, Utilities.parseInt( "NotANumber", 6 ) );
	}

	/**
	 * Test parsing the long.
	 */
	@Test
	public void testParseLong( )
	{
		assertEquals( 7193428371498L, Utilities.parseLong( "7193428371498", 3L ) );
		assertEquals( 6L, Utilities.parseLong( "NotANumber", 6L ) );
	}

	/**
	 * Tests the verify ending slash.
	 */
	@Test
	public void testVerifyEndingSlash( )
	{
		assertEquals( "http://192.168.1.1/jenkins/", Utilities.verifyEndingSlash( "http://192.168.1.1/jenkins" ) );
		assertEquals( "http://192.168.1.1/jenkins/", Utilities.verifyEndingSlash( "http://192.168.1.1/jenkins/" ) );
	}
}
