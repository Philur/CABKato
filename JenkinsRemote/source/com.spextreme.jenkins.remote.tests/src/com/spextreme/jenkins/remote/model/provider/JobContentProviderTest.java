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

package com.spextreme.jenkins.remote.model.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.Job;
import com.spextreme.jenkins.remote.model.internal.JobManager;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.model.provider.JobContentProvider;

/**
 * Test the job content provider object.
 */
public class JobContentProviderTest
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.JobContentProvider#getElements(java.lang.Object)}
	 * .
	 */
	@Test
	public void testGetElements( )
	{
		final IServerInformation serverInformation = new ServerInformation( "address", "", "", 1000, false, false );

		final JobContentProvider provider = new JobContentProvider( );

		provider.inputChanged( null, null, null );

		assertNull( provider.getElements( new Object( ) ) );

		final IJobManager manager = new JobManager( );
		final IJob job1 = new Job( serverInformation, "Test1", "", "blue" );
		final IJob job2 = new Job( serverInformation, "Test2", "", "red" );

		manager.addJob( job1 );
		manager.addJob( job2 );

		final Object[] data = provider.getElements( manager );

		assertNotNull( data );
		assertEquals( 2, data.length );
		assertEquals( job1, data[0] );
		assertEquals( job2, data[1] );

		provider.dispose( );
	}
}
