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

/**
 * This is the interface of the Job Status.
 */
public interface IJobStatus
{
	/**
	 * Gets the build number.
	 * 
	 * @return The build number.
	 */
	public int getBuildNumber( );

	/**
	 * Gets the job name.
	 * 
	 * @return The job name.
	 */
	public String getJobName( );

	/**
	 * Gets if this is currently building the job.
	 * 
	 * @return The building flag.
	 */
	public boolean isBuilding( );

	/**
	 * Gets if a notification was sent out about building.
	 * 
	 * @return <code>true</code> if a notification was previously sent. Otherwise <code>false</code>
	 *         .
	 */
	public boolean isNotifiedBuilding( );

	/**
	 * Sets the building flag.
	 * 
	 * @param building <code>true</code> if the job is building. Otherwise <code>false</code>.
	 */
	public void setBuilding( final boolean building );

	/**
	 * Sets the current build number for the job.
	 * 
	 * @param buildNumber The build number.
	 */
	public void setBuildNumber( final int buildNumber );

	/**
	 * Sets the job name.
	 * 
	 * @param jobName The job name.
	 */
	public void setJobName( final String jobName );

	/**
	 * Sets if a notification has been sent out.
	 * 
	 * @param notifiedBuilding <code>true</code> if a notification has been sent. Otherwise
	 *            <code>false</code>.
	 */
	public void setNotifiedBuilding( final boolean notifiedBuilding );
}
