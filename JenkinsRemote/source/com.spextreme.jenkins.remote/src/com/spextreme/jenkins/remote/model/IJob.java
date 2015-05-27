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
 * Defines the interface for a job. A job is a project in Jenkins that can be built.
 */
public interface IJob
{
	/**
	 * Adds a health report.
	 * 
	 * @param healthReport The health report to add.
	 */
	public void addHealthReport( final IHealthReport healthReport );

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#getColor()
	 * @return The color.
	 */
	public String getColor( );

	/**
	 * Gets the current build if one is in progress.
	 * 
	 * @return The current build.
	 */
	public IBuild getCurrentBuild( );

	/**
	 * Gets the list of health reports. This will never be <code>null</code>.
	 * 
	 * @return The list of health reports.
	 */
	public List<IHealthReport> getHealthReports( );

	/**
	 * Gets the last completed build that was done.
	 * 
	 * @return The last completed build.
	 */
	public IBuild getLastCompletedBuild( );

	/**
	 * Gets the name of the job.
	 * 
	 * @return The name or an empty string.
	 */
	public String getName( );

	/**
	 * Gets the instance of the server that this job is a member of.
	 * 
	 * @return The {@link IServerInformation} instance. This should not be <code>null</code>.
	 */
	public IServerInformation getServer( );

	/**
	 * Gets the URL of the job page.
	 * 
	 * @return The URL String.
	 */
	public String getURL( );

	/**
	 * Gets if this job is enabled (mBuildable) or not.
	 * 
	 * @return <code>true</code> if this is buildable. Otherwise <code>false</code>.
	 */
	public boolean isBuildable( );

	/**
	 * Sets if this job can be built.
	 * 
	 * @param buildable <code>true</code> if this can be built. Otherwise <code>false</code>.
	 */
	public void setBuildable( final boolean buildable );

	/**
	 * Sets the color of the project. Typically blue, red, yellow and gray.
	 * 
	 * @param color The color.
	 */
	public void setColor( final String color );

	/**
	 * Sets the current build.
	 * 
	 * @param build The current build.
	 */
	public void setCurrentBuild( final IBuild build );

	/**
	 * Sets the last completed build.
	 * 
	 * @param build The last completed build.
	 */
	public void setLastCompletedBuild( final IBuild build );

	/**
	 * Sets the name of the job.
	 * 
	 * @param name The name of the job or an empty string. This should never be <code>null</code>.
	 */
	public void setName( final String name );

	/**
	 * Sets the URL for the project page.
	 * 
	 * @param url The URL string.
	 */
	public void setURL( final String url );
}
