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

package com.spextreme.jenkins.remote.jenkins.internal;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;

import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.model.IServerInformation;

/**
 * This run and uses the RSS Processor to pull information from the jenkins server. This then
 * notifies registered parties about the updates set of information.
 */
public class JenkinsJobMonitor extends Job
{
	/**
	 * The server information including the query periodic and server address.
	 */
	private IServerInformation	mServerInformation	= null;
	/**
	 * The job manager.
	 */
	private IJobManager			mJobManager			= null;
	/**
	 * The lock for managing updates.
	 */
	private ILock				mLock				= null;

	/**
	 * This constructs the monitor instance.
	 * 
	 * @param jobManager The job manager.
	 * @param serverInfo The server information.
	 */
	public JenkinsJobMonitor( final IJobManager jobManager, final IServerInformation serverInfo )
	{
		super( "Jenkins Job Monitor" );

		mJobManager = jobManager;
		setServerInformation( serverInfo );
	}

	/**
	 * Gets the lock to use for updates.
	 * 
	 * @return The lock instance. This will never be <code>null</code>.
	 */
	public ILock getLock( )
	{
		if( mLock == null )
		{
			mLock = Job.getJobManager( ).newLock( );
		}

		return mLock;
	}

	/**
	 * Gets the server information.
	 * 
	 * @return Returns the server information.
	 */
	public IServerInformation getServerInformation( )
	{
		return mServerInformation;
	}

	/**
	 * Sets the server information. This can not be <code>null</code>.
	 * 
	 * @param serverInfo The server information.
	 */
	public void setServerInformation( final IServerInformation serverInfo )
	{
		mServerInformation = serverInfo;
	}

	/**
	 * Processes the job list and performs all updates.
	 * 
	 * @param jobs The list of jobs to update.
	 */
	protected void processJobs( final List<IJob> jobs )
	{
		getLock( ).acquire( );
		try
		{
			for( final IJob job : jobs )
			{
				mJobManager.updateJob( job );
			}
		}
		finally
		{
			getLock( ).release( );
		}
	}

	/**
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 * @param monitor The progress monitor. Not used.
	 * @return The status of the run. Always OK.
	 */
	@Override
	protected IStatus run( final IProgressMonitor monitor )
	{
		final List<IJob> jobs = JenkinsRemoteXMLProcessing.loadDataFromJenkinsServer( getServerInformation( ) );

		processJobs( jobs );

		// Reschedule again.
		this.schedule( getServerInformation( ).getQueryPeriodic( ) );

		return Status.OK_STATUS;
	}
}
