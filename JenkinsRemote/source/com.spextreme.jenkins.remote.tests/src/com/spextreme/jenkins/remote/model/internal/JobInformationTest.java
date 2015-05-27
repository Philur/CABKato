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

import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.JobInformation;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;

/**
 * Tests the {@link JobInformation} object.
 */
public class JobInformationTest
{
	/**
	 * Test method for {@link com.spextreme.jenkins.remote.model.internal.JobInformation#getName()}.
	 */
	@Test
	public void testGetName( )
	{
		final JobInformation info = new JobInformation( );

		assertEquals( "", info.getName( ) );
		assertNull( info.getServerInstance( ) );
		assertEquals( "", info.getServerReference( ) );
		assertTrue( info.isVisible( ) );

		info.setName( "test" );
		assertEquals( "test", info.getName( ) );

		final IServerInformation server = new ServerInformation( "http:://localhost/jenkins/" );
		info.setServerInstance( server );
		assertNotNull( info.getServerInstance( ) );
		assertEquals( server, info.getServerInstance( ) );

		info.setServerReference( "http://localhost/jenkins/" );
		assertEquals( "http://localhost/jenkins/", info.getServerReference( ) );

		info.setVisible( false );
		assertFalse( info.isVisible( ) );
	}
}
