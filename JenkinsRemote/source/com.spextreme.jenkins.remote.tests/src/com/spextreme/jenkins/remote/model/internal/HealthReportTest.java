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

package com.spextreme.jenkins.remote.model.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.spextreme.jenkins.remote.model.IHealthReport;
import com.spextreme.jenkins.remote.model.internal.HealthReport;

/**
 * Tests the health report object.
 */
public class HealthReportTest
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.HealthReport#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject( )
	{
		final IHealthReport report1 = new HealthReport( "description 1", 100 );
		final IHealthReport report2 = new HealthReport( "description 2", 100 );
		final IHealthReport report3 = new HealthReport( "description 1", 100 );
		final IHealthReport report4 = new HealthReport( "description 1", 33 );

		assertTrue( report1.equals( report1 ) );
		assertFalse( report1.equals( report2 ) );
		assertTrue( report1.equals( report3 ) );
		assertFalse( report1.equals( report4 ) );
		assertFalse( report1.equals( null ) );
		assertFalse( report1.equals( new Object( ) ) );
	}

	/**
	 * Tests the get/set methods.
	 */
	@Test
	public void testGetSetMethods( )
	{
		final IHealthReport report = new HealthReport( "description", 100 );

		assertEquals( "description", report.getDescription( ) );
		assertEquals( 100, report.getScore( ) );
		assertEquals( "", report.getIconName( ) );

		report.setDescription( "New Description" );
		assertEquals( "New Description", report.getDescription( ) );

		report.setScore( 33 );
		assertEquals( 33, report.getScore( ) );

		report.setIconName( "icon.health.gif" );
		assertEquals( "icon.health.gif", report.getIconName( ) );
	}

	/**
	 * Test method for {@link com.spextreme.jenkins.remote.model.internal.HealthReport#hashCode()}.
	 */
	@Test
	public void testHashCode( )
	{
		final IHealthReport report1 = new HealthReport( "description 1", 100 );
		final IHealthReport report2 = new HealthReport( "description 2", 100 );
		final IHealthReport report3 = new HealthReport( "description 1", 100 );
		final IHealthReport report4 = new HealthReport( "description 1", 33 );

		assertTrue( report1.hashCode( ) == report1.hashCode( ) );
		assertFalse( report1.hashCode( ) == report2.hashCode( ) );
		assertTrue( report1.hashCode( ) == report3.hashCode( ) );
		assertFalse( report1.hashCode( ) == report4.hashCode( ) );
		assertFalse( report1.hashCode( ) == new Object( ).hashCode( ) );
	}
}
