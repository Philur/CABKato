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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ConfigurationData;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;

/**
 * Tests the configuration data object.
 */
public class ConfigurationDataTest
{
	/**
	 * The object under test.
	 */
	public ConfigurationData	mConfigData	= null;

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mConfigData = new ConfigurationData( );
	}

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mConfigData = null;
	}

	/**
	 * Tests the get jobs method.
	 */
	@Test
	public void testGetJobs( )
	{
		assertNotNull( mConfigData.getJobs( ) );
		assertEquals( 0, mConfigData.getJobs( ).size( ) );

		mConfigData.setJobShowState( "JenkinsRemote", false );
		assertEquals( 1, mConfigData.getJobs( ).size( ) );
	}

	/**
	 * Tests the get job show state method.
	 */
	@Test
	public void testGetJobShowState( )
	{
		assertTrue( mConfigData.getJobShowState( "JenkinsRemote" ) );

		mConfigData.setJobShowState( "JenkinsRemote", false );
		assertFalse( mConfigData.getJobShowState( "JenkinsRemote" ) );
	}

	/**
	 * Tests the get primary server method.
	 */
	@Test
	public void testGetPrimaryServer( )
	{
		final IServerInformation server1 = mConfigData.getPrimaryServer( );
		assertNotNull( server1 );
		assertEquals( "http://localhost/jenkins/", server1.getAddress( ) );
		assertEquals( "", server1.getUserName( ) );
		assertEquals( "", server1.getPassword( ) );
		assertEquals( 60000, server1.getQueryPeriodic( ) );
		assertFalse( server1.isAllowIncrease( ) );
		assertTrue( server1.isPrimary( ) );
		assertNotNull( mConfigData.getServers( ) );
		assertEquals( 0, mConfigData.getServers( ).size( ) );

		final IServerInformation server2 = new ServerInformation( "http://123.1.2.3/jenkins/" );
		server2.setPrimary( false );
		mConfigData.addServer( server2 );
		final IServerInformation server3 = new ServerInformation( "http://123.1.2.4/jenkins/" );
		mConfigData.addServer( server3 );
		server3.setPrimary( true );

		assertEquals( server3, mConfigData.getPrimaryServer( ) );
		assertEquals( server2, mConfigData.getServers( ).get( 0 ) );
	}

	/**
	 * Tests the is visible method.
	 */
	@Test
	public void testIsVisible( )
	{
		assertTrue( mConfigData.isVisible( "JenkinsRemote" ) );

		mConfigData.setItemShowState( "JenkinsRemote", false );
		assertFalse( mConfigData.isVisible( "JenkinsRemote" ) );

		mConfigData.setItemShowState( "JenkinsRemote", true );
		assertTrue( mConfigData.isVisible( "JenkinsRemote" ) );

		mConfigData.setItemShowState( "JenkinsRemote", false );
		assertFalse( mConfigData.isVisible( "JenkinsRemote" ) );
	}

	/**
	 * Test the property change notification.
	 */
	@Test
	public void testPropertyChangeListener( )
	{
		final IPropertyChangeListener listener1 = mock( IPropertyChangeListener.class );
		final IPropertyChangeListener listener2 = mock( IPropertyChangeListener.class );

		mConfigData.addPropertyChangeListener( listener1 );
		mConfigData.addPropertyChangeListener( listener2 );

		mConfigData.setItemShowState( "key1", true );
		mConfigData.setJobShowState( "JenkinsRemote", true );

		mConfigData.removePropertyChangeListener( listener2 );

		mConfigData.setItemShowState( "key1", true ); // this will not trigger a change event.
		mConfigData.setJobShowState( "JenkinsRemote", false ); // This should fire an event.

		verify( listener1, times( 3 ) ).propertyChange( any( PropertyChangeEvent.class ) );
		verify( listener2, times( 2 ) ).propertyChange( any( PropertyChangeEvent.class ) );
	}
}
