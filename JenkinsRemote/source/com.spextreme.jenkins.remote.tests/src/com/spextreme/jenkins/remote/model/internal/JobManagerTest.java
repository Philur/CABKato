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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.IUpdatable;
import com.spextreme.jenkins.remote.model.internal.Build;
import com.spextreme.jenkins.remote.model.internal.Job;
import com.spextreme.jenkins.remote.model.internal.JobManager;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;

/**
 * Tests the Job Manager object.
 */
public class JobManagerTest
{
	/**
	 * The object under test.
	 */
	private IJobManager	mJobManager	= null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mJobManager = new JobManager( );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mJobManager = null;
	}

	/**
	 * Tests the add job, get job and add/remove listener methods.
	 */
	@Test
	public void testAddJobAndListeners( )
	{
		final IServerInformation server1 = new ServerInformation( "http://192.168.1.21/jenkins", "", "", 1000, false, true );
		final IJob job1 = new Job( server1, "Test1", "link", "blue" );
		final IJob job2 = new Job( server1, "Test2", "link", "red" );

		final IUpdatable listener1 = mock( IUpdatable.class );

		assertNotNull( mJobManager.getJobs( ) );
		assertEquals( 0, mJobManager.getJobs( ).size( ) );

		mJobManager.addListener( listener1 );
		mJobManager.addJob( job1 );
		// removes it then adds again.
		mJobManager.addJob( job1 );
		assertEquals( 1, mJobManager.getJobs( ).size( ) );

		mJobManager.reset( );
		assertEquals( 0, mJobManager.getJobs( ).size( ) );

		mJobManager.removeListener( listener1 );
		mJobManager.addJob( job2 );

		assertEquals( 1, mJobManager.getJobs( ).size( ) );

		verify( listener1, times( 2 ) ).updatedData( "added", mJobManager.getJobs( ) );
		verify( listener1, times( 1 ) ).updatedData( "cleared", mJobManager.getJobs( ) );
	}

	/**
	 * Tests the add job, get job and add/remove listener methods.
	 */
	@Test
	public void testUpdateJob( )
	{
		final IServerInformation server1 = new ServerInformation( "http://192.168.1.21/jenkins", "", "", 1000, false, true );
		final IJob job1 = new Job( server1, "Test1", "link", "blue" );
		final IJob job2 = new Job( server1, "Test2", "link", "red" );

		final IUpdatable listener1 = mock( IUpdatable.class );

		mJobManager.updateJob( job1 ); // This does an add.
		mJobManager.updateJob( job2 ); // this does an add.
		assertEquals( 2, mJobManager.getJobs( ).size( ) );

		mJobManager.addListener( listener1 );

		assertTrue( job2.isBuildable( ) );
		assertEquals( "red", job2.getColor( ) );
		assertNull( job2.getCurrentBuild( ) );
		assertEquals( 0, job2.getHealthReports( ).size( ) );
		assertNull( job2.getLastCompletedBuild( ) );
		assertEquals( "link", job2.getURL( ) );

		job2.setBuildable( false );
		job2.setColor( "blue" );
		job2.setCurrentBuild( new Build( "2", "2", 2, true ) );
		job2.setURL( "abc" );
		job2.setLastCompletedBuild( new Build( "1", "1", 1, false ) );
		mJobManager.updateJob( job2 );

		assertFalse( job2.isBuildable( ) );
		assertEquals( "blue", job2.getColor( ) );
		assertNotNull( job2.getCurrentBuild( ) );
		assertNotNull( job2.getLastCompletedBuild( ) );
		assertEquals( "abc", job2.getURL( ) );

		mJobManager.removeListener( listener1 );
		mJobManager.addJob( job2 );

		assertEquals( 2, mJobManager.getJobs( ).size( ) );

		verify( listener1, times( 1 ) ).updatedData( "update", mJobManager.getJobs( ) );
	}
}
