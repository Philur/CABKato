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

package com.spextreme.jenkins.remote.jenkins.internal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.jenkins.JenkinsCLITest;
import com.spextreme.jenkins.remote.jenkins.internal.JenkinsJobMonitor;
import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.Build;
import com.spextreme.jenkins.remote.model.internal.Job;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;

/**
 * Tests the Jenkins job mMonitor object.
 */
public class JenkinsJobMonitorTest
{
	/**
	 * The object under test.
	 */
	private JenkinsJobMonitor	mMonitor	= null;
	/**
	 * OBjects used in testing.
	 */
	private IJobManager			mJobManager	= null;
	private List<IJob>			mJobs		= null;
	private IJob				mNewJob		= null;

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@Before
	public void setUp( ) throws Exception
	{
		final IServerInformation serverInformation = new ServerInformation(
				System.getProperty( JenkinsCLITest.JENKINS_URL_PROPERTY_KEY ), "", "", 100, false, true );

		mJobs = new Vector<IJob>( );
		mJobs.add( new Job( serverInformation, "JenkinsRemote", System.getProperty( JenkinsCLITest.JENKINS_URL_PROPERTY_KEY ),
				"blue" ) );
		mJobs.add( new Job( serverInformation, "JenkinsRemote2", System.getProperty( JenkinsCLITest.JENKINS_URL_PROPERTY_KEY ),
				"red" ) );

		mNewJob = new Job( serverInformation, "NewJob", System.getProperty( JenkinsCLITest.JENKINS_URL_PROPERTY_KEY ), "blue" );

		mJobManager = mock( IJobManager.class );
		mMonitor = new JenkinsJobMonitor( mJobManager, serverInformation );
	}

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mMonitor = null;
	}

	/**
	 * Tests the process jobs method.
	 */
	@Test
	public void testProcessJobs( )
	{
		when( mJobManager.getJobs( ) ).thenReturn( mJobs );

		final IJob oldJob = mJobs.get( 1 );
		final IJob updatedJob = new Job( oldJob.getServer( ), oldJob.getName( ), oldJob.getURL( ), "red" );
		updatedJob.setBuildable( true );
		updatedJob.setCurrentBuild( new Build( "id", "1", 1, false ) );

		final List<IJob> newJobs = new Vector<IJob>( );
		newJobs.add( mJobs.get( 0 ) );
		newJobs.add( updatedJob );
		newJobs.add( mNewJob );

		mMonitor.processJobs( newJobs );
	}

	/**
	 * Tests the process jobs method.
	 */
	@Test
	public void testRun( )
	{
		assertEquals( Status.OK_STATUS, mMonitor.run( null ) );
	}
}
