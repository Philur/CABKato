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

package com.spextreme.jenkins.remote.model;

import java.util.List;

/**
 * This manages the set of jobs. This is the interface used by all job managers.
 */
public interface IJobManager
{
	/**
	 * This will add the job if it isn't already on the list. Otherwise it updates the job details.
	 * 
	 * @param job The job to add or update.
	 */
	public void addJob( final IJob job );

	/**
	 * Adds the listener to the set if it isn't already present.
	 * 
	 * @param listener The listener.
	 */
	public void addListener( final IUpdatable listener );

	/**
	 * Gets the list of jobs. If no jobs have been added then an empty list will be returned.
	 * 
	 * @return The list of {@link IJob}s.
	 */
	public List<IJob> getJobs( );

	/**
	 * Removes the listener so it will no longer be notified.
	 * 
	 * @param listener The listener to be removed.
	 */
	public void removeListener( final IUpdatable listener );

	/**
	 * Causes the underlying list to be cleared so the next time polling occurs all items will be
	 * new.
	 */
	public void reset( );

	/**
	 * Will attempt to update the job if it is found in the set. Otherwise the job will be added to
	 * the set.
	 * 
	 * @param job The job to be updated.
	 * @return <code>true</code> if the job was updated. <code>false</code> otherwise.
	 */
	public boolean updateJob( final IJob job );
}
