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

import com.spextreme.jenkins.remote.model.IJobStatus;

/**
 * This is used to track the job status. It is used to know if we have a new job is being build,
 * completed, etc...
 */
public class JobStatus implements IJobStatus
{
	/**
	 * A flag tagging if this is currently mBuilding.
	 */
	private boolean	mBuilding			= false;
	/**
	 * The current build number for the job.
	 */
	private int		mBuildNumber		= -1;
	/**
	 * The name of the job. This is the key to the status.
	 */
	private String	mJobName			= "";
	/**
	 * A flag used to know if a notification has been sent.
	 */
	private boolean	mNotifiedBuilding	= false;

	/**
	 * Constructs the job status. This sets the mBuilding and notified to <code>false</code>.
	 * 
	 * @param job The job to create.
	 * @param buildNumber The build number.
	 */
	public JobStatus( final String job, final int buildNumber )
	{
		this( job, buildNumber, false, false );
	}

	/**
	 * Constructs the job status.
	 * 
	 * @param jobName The job to create status for.
	 * @param buildNumber The build number.
	 * @param building The building flag. <code>true</code> for currently building.
	 *            <code>false</code> otherwise.
	 * @param notifiedBuilding A flag to know if a notification for building has gone out.
	 *            <code>true</code> if a notification was sent.
	 */
	public JobStatus( final String jobName, final int buildNumber, final boolean building, final boolean notifiedBuilding )
	{
		setBuilding( building );
		setBuildNumber( buildNumber );
		setJobName( jobName );
		setNotifiedBuilding( notifiedBuilding );
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobStatus#getBuildNumber()
	 * @return The build number.
	 */
	public int getBuildNumber( )
	{
		return mBuildNumber;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobStatus#getJobName()
	 * @return The job name.
	 */
	public String getJobName( )
	{
		return mJobName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobStatus#isBuilding()
	 * @return The building flag.
	 */
	public boolean isBuilding( )
	{
		return mBuilding;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobStatus#isNotifiedBuilding()
	 * @return <code>true</code> if a notification was previously sent. Otherwise <code>false</code>
	 *         .
	 */
	public boolean isNotifiedBuilding( )
	{
		return mNotifiedBuilding;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobStatus#setBuilding(boolean)
	 * @param building <code>true</code> if the job is building. Otherwise <code>false</code>.
	 */
	public void setBuilding( final boolean building )
	{
		mBuilding = building;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobStatus#setBuildNumber(int)
	 * @param buildNumber The build number.
	 */
	public void setBuildNumber( final int buildNumber )
	{
		mBuildNumber = buildNumber;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobStatus#setJobName(java.lang.String)
	 * @param jobName The job name.
	 */
	public void setJobName( final String jobName )
	{
		mJobName = jobName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobStatus#setNotifiedBuilding(boolean)
	 * @param notifiedBuilding <code>true</code> if a notification has been sent. Otherwise
	 *            <code>false</code>.
	 */
	public void setNotifiedBuilding( final boolean notifiedBuilding )
	{
		mNotifiedBuilding = notifiedBuilding;
	}
}
