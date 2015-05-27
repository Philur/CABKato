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

import java.util.List;
import java.util.Vector;

import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.model.IUpdatable;

/**
 * This maintains the Jobs retrieved from the Jenkins Server.
 */
public class JobManager implements IJobManager
{
	/**
	 * THe list of listeners to notify when a job change occurs.
	 */
	private List<IUpdatable>	mListeners	= null;
	/**
	 * Holds the list of jobs.
	 */
	private List<IJob>			mJobListing	= null;

	/**
	 * Constructs the job manager.
	 */
	public JobManager( )
	{
		// Do nothing.
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobManager#addJob(com.spextreme.jenkins.remote.model.IJob)
	 * @param job The job to add or update.
	 */
	public void addJob( final IJob job )
	{
		if( getJobs( ).contains( job ) )
		{
			getJobs( ).remove( job );
		}

		getJobs( ).add( job );
		fireEvent( "added", getJobs( ) );
	}

	/**
	 * Adds the listener to the set if it isn't already present.
	 * 
	 * @param listener The listener.
	 */
	public void addListener( final IUpdatable listener )
	{
		if( !getListeners( ).contains( listener ) )
		{
			getListeners( ).add( listener );
		}
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobManager#getJobs()
	 * @return The list of {@link IJob}s.
	 */
	public List<IJob> getJobs( )
	{
		if( mJobListing == null )
		{
			mJobListing = new Vector<IJob>( );
		}

		return mJobListing;
	}

	/**
	 * Gets the List<IUpdatable> for this object.
	 * 
	 * @return Returns the mListeners.
	 */
	public List<IUpdatable> getListeners( )
	{
		if( mListeners == null )
		{
			mListeners = new Vector<IUpdatable>( );
		}

		return mListeners;
	}

	/**
	 * Removes the listener so it will no longer be notified.
	 * 
	 * @param listener The listener to be removed.
	 */
	public void removeListener( final IUpdatable listener )
	{
		getListeners( ).remove( listener );
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobManager#reset()
	 */
	@Override
	public void reset( )
	{
		mJobListing.clear( );

		fireEvent( "cleared", getJobs( ) );
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobManager#updateJob(com.spextreme.jenkins.remote.model.IJob)
	 * @param job The job to be updated.
	 * @return <code>true</code> if the job was updated. <code>false</code> otherwise.
	 */
	public boolean updateJob( final IJob job )
	{
		boolean result = false;

		if( getJobs( ).contains( job ) )
		{
			final IJob foundJob = getJobs( ).get( getJobs( ).indexOf( job ) );
			foundJob.setColor( job.getColor( ) );
			foundJob.setCurrentBuild( job.getCurrentBuild( ) );
			foundJob.setLastCompletedBuild( job.getLastCompletedBuild( ) );
			foundJob.setURL( job.getURL( ) );

			fireEvent( "update", getJobs( ) );

			result = true;
		}
		else
		{
			addJob( job );
		}

		return result;
	}

	/**
	 * Fires the event to each registered listener.
	 * 
	 * @param type The string defining the update type.
	 * @param jobs The list of jobs.
	 */
	protected void fireEvent( final String type, final List<IJob> jobs )
	{
		for( final IUpdatable listener : getListeners( ) )
		{
			listener.updatedData( type, jobs );
		}
	}
}
