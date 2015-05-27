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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.ui.ConfigurationDialog;
import com.spextreme.jenkins.remote.ui.JenkinsRemoteTray;

/**
 * This opens the configuration dialog.
 */
public class OpenConfigurationDialogAction extends SelectionAdapter
{
	/**
	 * The configuration manager instance.
	 */
	private IConfigurationManager	mConfigData	= null;
	/**
	 * The job manager instance.
	 */
	private IJobManager				mJobManager	= null;
	/**
	 * The shell.
	 */
	private Shell					mShell		= null;

	/**
	 * The tray instance.
	 */
	private JenkinsRemoteTray		mTray		= null;

	/**
	 * Constructs the open configuration dialog.
	 * 
	 * @param shell The shell.
	 * @param tray The Jenkins remote tray.
	 * @param configManager The {@link IConfigurationManager} instance.
	 * @param jobManager The job manager instance.
	 */
	public OpenConfigurationDialogAction( final Shell shell, final JenkinsRemoteTray tray,
			final IConfigurationManager configManager, final IJobManager jobManager )
	{
		mShell = shell;
		mTray = tray;
		mConfigData = configManager;
		mJobManager = jobManager;
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
		final ConfigurationDialog configDialog = new ConfigurationDialog( mShell, mConfigData, mJobManager );
		if( IStatus.OK == configDialog.open( ) )
		{
			mTray.restartMonitors( );
		}
	}
}
