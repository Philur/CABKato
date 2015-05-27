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

import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;

/**
 * Tests the server information.
 */
public class ServerInformationTest
{
	/**
	 * Tests the clone method.
	 * 
	 * @throws Exception Thrown if an error occurs.
	 */
	@Test
	public void testClone( ) throws Exception
	{
		final ServerInformation server = new ServerInformation( "address", "user", "password", 10000, true, true );
		final IServerInformation serverClon = (IServerInformation)server.clone( );

		assertTrue( server != serverClon );

		assertEquals( server.getAddress( ), serverClon.getAddress( ) );
		assertEquals( server.getPassword( ), serverClon.getPassword( ) );
		assertEquals( server.getQueryPeriodic( ), serverClon.getQueryPeriodic( ) );
		assertEquals( server.getUserName( ), serverClon.getUserName( ) );
		assertEquals( server.isAllowIncrease( ), serverClon.isAllowIncrease( ) );
		assertEquals( server.isPrimary( ), serverClon.isPrimary( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.ServerInformation#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject( )
	{
		final IServerInformation server1 = new ServerInformation( "http://localhost/jenkins/", "", "", 1, true, false );
		final IServerInformation server2 = new ServerInformation( "http://123.123.123.123/jenkins", "", "", 2, true, false );
		final IServerInformation server3 = new ServerInformation( "http://localhost/jenkins/", null, null, 3, false, false );
		final IServerInformation server4 = new ServerInformation( "http://123.1.2.3/jenkins/", "", "", 4, true, false );

		assertTrue( server1.equals( server1 ) );
		assertFalse( server1.equals( server2 ) );
		assertTrue( server1.equals( server3 ) );
		assertFalse( server1.equals( server4 ) );
		assertFalse( server1.equals( new Object( ) ) );
		assertFalse( server1.equals( null ) );
	}

	/**
	 * Tests the get/set methods.
	 */
	@Test
	public void testGetSetMethods( )
	{
		final ServerInformation info = new ServerInformation( "http://localhost/jenkins/" );

		assertEquals( "http://localhost/jenkins/", info.getAddress( ) );
		assertEquals( "", info.getPassword( ) );
		assertEquals( "", info.getUserName( ) );
		assertEquals( 5000, info.getQueryPeriodic( ) );
		assertTrue( info.isAllowIncrease( ) );
		assertFalse( info.isPrimary( ) );

		info.setAddress( "http://123.1.2.3/jenkins/" );
		assertEquals( "http://123.1.2.3/jenkins/", info.getAddress( ) );

		info.setPassword( "password" );
		assertEquals( "password", info.getPassword( ) );

		info.setUserName( "username" );
		assertEquals( "username", info.getUserName( ) );

		info.setQueryPeriodic( 2000 );
		assertEquals( 2000, info.getQueryPeriodic( ) );

		info.setAllowIncrease( true );
		assertTrue( info.isAllowIncrease( ) );

		info.setPrimary( true );
		assertTrue( info.isPrimary( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.model.internal.ServerInformation#hashCode()}.
	 */
	@Test
	public void testHashCode( )
	{
		final IServerInformation server1 = new ServerInformation( "http://123.123.123.123:8080/jenkins", "", "", 1, true,
				false );
		final IServerInformation server2 = new ServerInformation( "http://123.123.123.123/jenkins", "", "", 2, true, false );
		final IServerInformation server3 = new ServerInformation( "http://123.123.123.123:8080/jenkins", null, null, 3,
				false, false );
		final IServerInformation server4 = new ServerInformation( "http://192.168.1.20/jenkins", "", "", 4, true, false );

		assertTrue( server1.hashCode( ) == server1.hashCode( ) );
		assertFalse( server1.hashCode( ) == server2.hashCode( ) );
		assertTrue( server1.hashCode( ) == server3.hashCode( ) );
		assertFalse( server1.hashCode( ) == server4.hashCode( ) );
		assertFalse( server1.hashCode( ) == new Object( ).hashCode( ) );
	}

	/**
	 * Test when the address is <code>null</code>.
	 */
	@Test( expected = NullPointerException.class )
	public void testNullAddress( )
	{
		new ServerInformation( null, "", "", 1, true, true );
	}
}
