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

import hudson.cli.CLI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Vector;

/**
 * Connects using the Jenkins CLI service to access data in the Jenkins server.
 */
public class JenkinsCLI
{
	/**
	 * The jenkins command line interface.
	 */
	private CLI	mCommandLineInterface	= null;

	/**
	 * Constructs the CLI for jenkins.
	 */
	public JenkinsCLI( )
	{
		// Do nothing.
	}

	/**
	 * Closes the open CLI.
	 */
	public void close( )
	{
		try
		{
			if( getCLI( ) != null )
			{
				getCLI( ).close( );
			}
		}
		catch( final Exception e )
		{
			// Do nothing.
		}

		mCommandLineInterface = null;
	}

	/**
	 * Attempts to execute the command.
	 * 
	 * @param command The command to run.
	 * @return The return data from the command.
	 */
	public String executeCommand( final String command )
	{
		final List<String> commands = new Vector<String>( );
		commands.add( command );

		final ByteArrayOutputStream out = new ByteArrayOutputStream( );
		final ByteArrayInputStream in = new ByteArrayInputStream( new byte[1000] );

		getCLI( ).execute( commands, in, out, out );

		final byte[] bytes = out.toByteArray( );
		final char[] theChars = new char[bytes.length];

		for( int i = 0; i < bytes.length; i++ )
		{
			theChars[i] = (char)( bytes[i] & 0xff );
		}

		return new String( theChars );
	}

	/**
	 * Gets the CLI for jenkins or <code>null</code> if not yet opened.
	 * 
	 * @return The {@link CLI} or <code>null</code>.
	 */
	public CLI getCLI( )
	{
		return mCommandLineInterface;
	}

	/**
	 * Checks to see if the command is supported. This will throw an exception if the CLI was not
	 * previously opened.
	 * 
	 * @param command The command to check.
	 * @return <code>true</code> if the Jenkins server supports the command. Otherwise
	 *         <code>false</code> is returned.
	 */
	public boolean isCommandSupported( final String command )
	{
		return getCLI( ).hasCommand( command );
	}

	/**
	 * Opens a CLI to the given URL.
	 * 
	 * @param jenkinsURL The jenkins URL to connect to.
	 */
	public void open( final String jenkinsURL )
	{
		if( mCommandLineInterface != null )
		{
			close( );
		}

		try
		{
			mCommandLineInterface = new CLI( new URL( jenkinsURL ) );
		}
		catch( final Exception e )
		{
			mCommandLineInterface = null;
		}
	}

	/**
	 * Executes the command on the given server. This will open a connection, check if the command
	 * is supported, then run the command, and close down the connection. If the command is not
	 * supported -1 will be returned.
	 * 
	 * @param jenkinsURL The jenkins URL to use.
	 * @param command The command to attempt to run.
	 * @return The data returned from the command.
	 */
	public String simpleExecuteCommand( final String jenkinsURL, final String command )
	{
		open( jenkinsURL );
		String result = "";

		if( isCommandSupported( command ) )
		{
			result = executeCommand( command );
		}

		return result;
	}
}
