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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.spextreme.jenkins.remote.model.IBuild;
import com.spextreme.jenkins.remote.model.ITestResult;
import com.spextreme.jenkins.remote.model.internal.Artifact;
import com.spextreme.jenkins.remote.model.internal.Build;
import com.spextreme.jenkins.remote.model.internal.TestResult;

/**
 * Tests the build object.
 */
public class BuildTest
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.Build#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject( )
	{
		final IBuild b1 = new Build( "1", "Build1", 1, false );
		final IBuild b2 = new Build( "2", "Build2", 1, false );
		final IBuild b3 = new Build( "1", "Build1", 1, true );

		assertTrue( b1.equals( b1 ) );
		assertFalse( b1.equals( b2 ) );
		assertTrue( b1.equals( b3 ) );
		assertFalse( b1.equals( null ) );
		assertFalse( b1.equals( new Object( ) ) );
	}

	/**
	 * Tests the get/set methods.
	 */
	@Test
	public void testGetSetMethods( )
	{
		final IBuild build = new Build( "1", "JenkinsRemote", 1, false );

		assertNotNull( build.getArtifacts( ) );
		assertEquals( 0, build.getArtifacts( ).size( ) );
		assertEquals( "JenkinsRemote", build.getDisplayName( ) );
		assertEquals( 0, build.getDuration( ) );
		assertEquals( "1", build.getID( ) );
		assertEquals( 1, build.getNumber( ) );
		assertEquals( "", build.getResult( ) );
		assertNull( build.getTestResults( ) );
		assertEquals( 0L, build.getTimestamp( ) );
		assertEquals( "", build.getURL( ) );
		assertFalse( build.isBuilding( ) );

		build.addArtifacts( new Artifact( "Test", "/path/to/test.zip" ) );
		assertEquals( 1, build.getArtifacts( ).size( ) );

		build.setDisplayName( "NewProduct" );
		assertEquals( "NewProduct", build.getDisplayName( ) );

		build.setDuration( 213132L );
		assertEquals( 213132L, build.getDuration( ) );

		build.setID( "abc123" );
		assertEquals( "abc123", build.getID( ) );

		build.setNumber( 2 );
		assertEquals( 2, build.getNumber( ) );

		build.setResult( "SUCCESS" );
		assertEquals( "SUCCESS", build.getResult( ) );

		final ITestResult r1 = new TestResult( 0, 0, 20 );
		build.setTestResults( r1 );
		assertNotNull( build.getTestResults( ) );
		assertEquals( r1, build.getTestResults( ) );

		build.setTimestamp( 63296129361212321L );
		assertEquals( 63296129361212321L, build.getTimestamp( ) );

		build.setURL( "test.url" );
		assertEquals( "test.url", build.getURL( ) );

		build.setBuilding( true );
		assertTrue( build.isBuilding( ) );
	}

	/**
	 * Test method for {@link com.spextreme.jenkins.remote.model.internal.Build#hashCode()}.
	 */
	@Test
	public void testHashCode( )
	{
		final IBuild b1 = new Build( "1", "Build1", 1, false );
		final IBuild b2 = new Build( "2", "Build2", 1, false );
		final IBuild b3 = new Build( "1", "Build1", 1, true );

		assertTrue( b1.hashCode( ) == b1.hashCode( ) );
		assertFalse( b1.hashCode( ) == b2.hashCode( ) );
		assertTrue( b1.hashCode( ) == b3.hashCode( ) );
		assertFalse( b1.hashCode( ) == new Object( ).hashCode( ) );
	}
}
