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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.spextreme.jenkins.remote.model.IJobStatus;
import com.spextreme.jenkins.remote.model.internal.JobStatus;

/**
 * Tests the job status object.
 */
public class JobStatusTest
{
	/**
	 * Tests the get/set methods.
	 */
	@Test
	public void testGetSetMethods( )
	{
		final IJobStatus status = new JobStatus( "Test1", 1, true, false );

		assertEquals( "Test1", status.getJobName( ) );
		assertEquals( 1, status.getBuildNumber( ) );
		assertTrue( status.isBuilding( ) );
		assertFalse( status.isNotifiedBuilding( ) );

		status.setJobName( "NewJob" );
		assertEquals( "NewJob", status.getJobName( ) );

		status.setBuildNumber( 3 );
		assertEquals( 3, status.getBuildNumber( ) );

		status.setBuilding( false );
		assertFalse( status.isBuilding( ) );

		status.setNotifiedBuilding( true );
		assertTrue( status.isNotifiedBuilding( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.JobStatus#JobStatus(java.lang.String, int)}
	 * .
	 */
	@Test
	public void testJobStatusStringInt( )
	{
		assertNotNull( new JobStatus( "Test", 1 ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.JobStatus#JobStatus(java.lang.String, int, boolean, boolean)}
	 * .
	 */
	@Test
	public void testJobStatusStringIntBooleanBoolean( )
	{
		assertNotNull( new JobStatus( "Test", 1, false, false ) );
	}
}
