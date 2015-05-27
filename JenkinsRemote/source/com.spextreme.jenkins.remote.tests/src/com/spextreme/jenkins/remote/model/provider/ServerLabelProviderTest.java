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
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.model.provider.ServerLabelProvider;

/**
 * Tests the Server Label Provider object.
 */
public class ServerLabelProviderTest
{
	/**
	 * The object under test.
	 */
	private ServerLabelProvider	mProvider	= null;

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mProvider = new ServerLabelProvider( );
	}

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mProvider = null;
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.ServerLabelProvider#getColumnImage(java.lang.Object, int)}
	 * .
	 */
	@Test
	public void testGetColumnImage( )
	{
		final IServerInformation server = new ServerInformation( "address", "username", "password", 2000, true, false );

		// In the full up system this would not be null but for testing (since it's not loaded) they
		// will be null.
		assertNull( mProvider.getColumnImage( server, 4 ) );
		assertNull( mProvider.getColumnImage( server, 5 ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.ServerLabelProvider#getColumnImage(java.lang.Object, int)}
	 * .
	 */
	@Test
	public void testGetColumnImageNull( )
	{
		final IServerInformation server = new ServerInformation( "address", "username", "password", 2000, true, false );

		assertNull( mProvider.getColumnImage( server, 0 ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.ServerLabelProvider#getColumnText(java.lang.Object, int)}
	 * .
	 */
	@Test
	public void testGetColumnText( )
	{
		final IServerInformation server = new ServerInformation( "address", "username", "password", 2000, true, false );

		assertEquals( "address", mProvider.getColumnText( server, 0 ) );
		assertEquals( "username", mProvider.getColumnText( server, 1 ) );
		assertEquals( "password", mProvider.getColumnText( server, 2 ) );
		assertEquals( "2000", mProvider.getColumnText( server, 3 ) );
		assertEquals( "", mProvider.getColumnText( server, 4 ) );
		assertEquals( "", mProvider.getColumnText( server, 5 ) );
		assertEquals( "", mProvider.getColumnText( server, 6 ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.ServerLabelProvider#getColumnText(java.lang.Object, int)}
	 * .
	 */
	@Test
	public void testGetColumnTextNotISIObject( )
	{
		assertEquals( "", mProvider.getColumnText( new Object( ), 1 ) );
	}
}
