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

package com.spextreme.jenkins.remote.jenkins;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.jenkins.JenkinsCLI;

/**
 * Tests the jenkins CLI object. This test requires a valid jenkins URL. Change if jenkins server is
 * different.
 */
public class JenkinsCLITest
{
	/**
	 * The key used to load the Jenkins URL from the system properties.
	 */
	public static final String	JENKINS_URL_PROPERTY_KEY	= "jenkins.remote.test.url";
	/**
	 * Change this if testing else where.
	 */
	public static final String	JENKINS_URL				= System.getProperty( JENKINS_URL_PROPERTY_KEY );

	/**
	 * The jenkins CLI.
	 */
	private JenkinsCLI			mCli					= null;

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mCli = new JenkinsCLI( );
	}

	/**
	 * @throws java.lang.Exception Thrown if an error occurs.
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mCli = null;
	}

	/**
	 * Test open, is command supported, execute and close.
	 */
	@Test
	public void testFullPath( )
	{
		mCli.open( JENKINS_URL );
		assertTrue( mCli.isCommandSupported( "help" ) );
		assertFalse( mCli.isCommandSupported( "badcommand" ) );
		assertTrue( mCli.executeCommand( "help" ).length( ) > 0 );
		mCli.close( );
	}

	/**
	 * Test when a cli is already open and open is called again.
	 */
	@Test
	public void testOpenClosing( )
	{
		mCli.open( JENKINS_URL );
		mCli.open( JENKINS_URL );
	}

	/**
	 * Test when an invalid url is given.
	 */
	@Test
	public void testOpenWithInvalidURL( )
	{
		mCli.open( "notvalid" );
		assertNull( mCli.getCLI( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.jenkins.JenkinsCLI#simpleExecuteCommand(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testSimpleExecuteCommand( )
	{
		assertTrue( mCli.simpleExecuteCommand( JENKINS_URL, "help" ).length( ) > 0 );
	}
}
