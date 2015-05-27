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

package com.spextreme.jenkins.remote.ui.menu.action;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.spextreme.jenkins.remote.jenkins.JenkinsCLI;
import com.spextreme.jenkins.remote.model.IServerInformation;

/**
 * This displays the about information for Jenkins Manager.
 */
public class RestartServerAction extends SelectionAdapter
{
	/**
	 * The Jenkins CLI to use for the command.
	 */
	private JenkinsCLI			mCLI	= null;
	/**
	 * The server instance.
	 */
	private IServerInformation	mServer	= null;

	/**
	 * Constructs the restart server action. Throws a {@link NullPointerException} if either value
	 * is <code>null</code>.
	 * 
	 * @param cli The Jenkins CLI to use.
	 * @param server The {@link IServerInformation} to use.
	 */
	public RestartServerAction( final IServerInformation server, final JenkinsCLI cli )
	{
		if( server == null )
		{
			throw new NullPointerException( "The server instance can not be null." );
		}

		if( cli == null )
		{
			throw new NullPointerException( "The command line interface can not be null." );
		}

		mServer = server;
		mCLI = cli;
	}

	/**
	 * Opens the configuration dialog.
	 * 
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 * @param e The selection event.
	 */
	@Override
	public void widgetSelected( final SelectionEvent e )
	{
		mCLI.simpleExecuteCommand( mServer.getAddress( ), "restart" );
	}
}
