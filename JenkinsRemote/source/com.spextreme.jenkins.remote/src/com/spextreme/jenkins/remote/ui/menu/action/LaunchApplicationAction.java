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
import org.eclipse.swt.program.Program;

/**
 * This action uses the SWT {@link Program} object to launch a process. If a URL is passed then the
 * default web browser should load the URL.
 */
public class LaunchApplicationAction extends SelectionAdapter
{
	/**
	 * The name of the process this will attempt to launch.
	 */
	private String	mProcessString	= "";

	/**
	 * Constructs the application action.
	 * 
	 * @param process The process string to launch.
	 */
	public LaunchApplicationAction( final String process )
	{
		mProcessString = process;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 * @param e The event.
	 */
	@Override
	public void widgetSelected( final SelectionEvent e )
	{
		if( ( mProcessString != null ) && ( mProcessString.length( ) > 0 ) )
		{
			Program.launch( mProcessString );
		}
	}
}
