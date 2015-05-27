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

import java.util.List;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.model.provider.ServerContentProvider;

/**
 * Tests the server content provider object.
 */
public class ServerContentProviderTest
{
	/**
	 * The object under test.
	 */
	private ServerContentProvider		mProvider	= null;
	/**
	 * Objects used for testing.
	 */
	private List<IServerInformation>	mList		= null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mProvider = new ServerContentProvider( );

		mList = new Vector<IServerInformation>( );
		mList.add( new ServerInformation( "http://localhost/jenkins/" ) );

		mProvider.inputChanged( null, null, mList );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mProvider.dispose( );
		mProvider = null;
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.ServerContentProvider#addServer(com.spextreme.jenkins.remote.model.IServerInformation)}
	 * .
	 */
	@Test
	public void testAddServer( )
	{
		assertEquals( 1, mProvider.getServers( ).size( ) );

		mProvider.addServer( new ServerInformation( "http://123.1.2.3/jenkins/" ) );
		assertEquals( 2, mProvider.getServers( ).size( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.ServerContentProvider#getElements(java.lang.Object)}
	 * .
	 */
	@Test
	public void testGetElements( )
	{
		assertEquals( 1, mProvider.getElements( null ).length );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.provider.ServerContentProvider#removeServer(com.spextreme.jenkins.remote.model.IServerInformation)}
	 * .
	 */
	@Test
	public void testRemoveServer( )
	{
		final IServerInformation server = mList.get( 0 );

		assertEquals( 1, mProvider.getServers( ).size( ) );

		mProvider.removeServer( server );

		assertEquals( 0, mProvider.getServers( ).size( ) );
	}
}
