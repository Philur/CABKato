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

import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.spextreme.jenkins.remote.jenkins.internal.JenkinsJobMonitor;
import com.spextreme.jenkins.remote.model.IJobManager;

/**
 * This get the list of servers and forces all to be updated.
 */
public class RefreshAction extends SelectionAdapter
{
	/**
	 * The list of jobs.
	 */
	private List<JenkinsJobMonitor>	mJobs		= null;
	/**
	 * The job manager instance.
	 */
	private IJobManager				mJobManager	= null;

	/**
	 * Constructs the action.
	 * 
	 * @param jobs The list of jenkins server monitoring jobs.
	 * @param jobManager The job manager.
	 */
	public RefreshAction( final List<JenkinsJobMonitor> jobs, final IJobManager jobManager )
	{
		mJobs = jobs;
		mJobManager = jobManager;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 * @param e The selection event.
	 */
	@Override
	public void widgetSelected( final SelectionEvent e )
	{
		// Clear the current job list.
		mJobManager.reset( );

		// Reschedule all so they populate the cleared list.
		for( final Job j : mJobs )
		{
			j.schedule( );
		}
	}
}
