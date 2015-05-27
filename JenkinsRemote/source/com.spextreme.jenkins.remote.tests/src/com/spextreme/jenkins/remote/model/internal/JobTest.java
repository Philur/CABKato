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

import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.Build;
import com.spextreme.jenkins.remote.model.internal.HealthReport;
import com.spextreme.jenkins.remote.model.internal.Job;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;

/**
 * Tests the job object.
 */
public class JobTest
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.Job#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject( )
	{
		final IServerInformation server1 = new ServerInformation( "http://192.168.1.21/jenkins", "", "", 1000, false, true );
		final IServerInformation server2 = new ServerInformation( "http://192.168.1.22/jenkins", "", "", 1000, false, true );

		final IJob j1 = new Job( server1, "Test1", "link", "blue" );
		final IJob j2 = new Job( server2, "Test2", "link", "blue" );
		final IJob j3 = new Job( server1, "Test1", "link", "blue" );
		final IJob j4 = new Job( server2, "Test1", "link", "blue" );

		assertTrue( j1.equals( j1 ) );
		assertFalse( j1.equals( j2 ) );
		assertTrue( j1.equals( j3 ) );
		assertFalse( j1.equals( j4 ) );
		assertFalse( j1.equals( null ) );
		assertFalse( j1.equals( new Object( ) ) );
	}

	/**
	 * Tests the get/set methods.
	 */
	@Test
	public void testGetSetMethods( )
	{
		final IServerInformation serverInformation = new ServerInformation( "http://192.168.1.21/jenkins", "", "", 1000,
				false, true );

		final IJob mJob = new Job( serverInformation, "Test1", "http://www.spextreme.com/jenkins/jobs/Test1", "blue" );

		assertEquals( "Test1", mJob.getName( ) );
		assertEquals( "http://www.spextreme.com/jenkins/jobs/Test1", mJob.getURL( ) );
		assertTrue( mJob.isBuildable( ) );
		assertNull( mJob.getLastCompletedBuild( ) );
		assertNull( mJob.getCurrentBuild( ) );
		assertEquals( "blue", mJob.getColor( ) );
		assertNotNull( mJob.getHealthReports( ) );
		assertEquals( 0, mJob.getHealthReports( ).size( ) );

		mJob.setName( "Test2" );
		assertEquals( "Test2", mJob.getName( ) );

		mJob.setURL( "http//a.fake.com/jenkins/jobs/Test2" );
		assertEquals( "http//a.fake.com/jenkins/jobs/Test2", mJob.getURL( ) );

		mJob.setBuildable( false );
		assertFalse( mJob.isBuildable( ) );

		mJob.setLastCompletedBuild( new Build( "2", "Test1", 2, false ) );
		assertNotNull( mJob.getLastCompletedBuild( ) );

		mJob.setCurrentBuild( new Build( "1", "Test1", 1, true ) );
		assertNotNull( mJob.getCurrentBuild( ) );

		mJob.setColor( "red" );
		assertEquals( "red", mJob.getColor( ) );

		mJob.addHealthReport( new HealthReport( "testResult", 1 ) );
		assertEquals( 1, mJob.getHealthReports( ).size( ) );
	}

	/**
	 * Test method for {@link com.spextreme.jenkins.remote.model.internal.Job#hashCode()}.
	 */
	@Test
	public void testHashCode( )
	{
		final IServerInformation server1 = new ServerInformation( "http://192.168.1.21/jenkins", "", "", 1000, false, true );
		final IServerInformation server2 = new ServerInformation( "http://192.168.1.22/jenkins", "", "", 1000, false, true );

		final IJob j1 = new Job( server1, "Test1", "url", "blue" );
		final IJob j2 = new Job( server1, "Test2", "link", "blue" );
		final IJob j3 = new Job( server1, "Test1", "link", "blue" );
		final IJob j4 = new Job( server2, "Test1", "link", "blue" );

		assertTrue( j1.hashCode( ) == j1.hashCode( ) );
		assertFalse( j1.hashCode( ) == j2.hashCode( ) );
		assertTrue( j1.hashCode( ) == j3.hashCode( ) );
		assertTrue( j1.hashCode( ) == j4.hashCode( ) );
		assertFalse( j1.hashCode( ) == "Test1".hashCode( ) );
	}

	/**
	 * Tests the set server method with a <code>null</code> server.
	 */
	@Test( expected = NullPointerException.class )
	public void testNullServer( )
	{
		new Job( null, "test", "link", "blue" );
	}
}
