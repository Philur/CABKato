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
 * This is the interface that represents a Jenkins build.
 */
public interface IBuild
{
	/**
	 * Adds an artifact to this build.
	 * 
	 * @param artifact The artifact to add.
	 */
	public void addArtifacts( final IArtifact artifact );

	/**
	 * Gets the list of {@link IArtifact}s.
	 * 
	 * @return The list of artifacts.
	 */
	public List<IArtifact> getArtifacts( );

	/**
	 * Gets the display name. Typically this is the job name + the build number (i.e. JenkinsRemote
	 * #12)
	 * 
	 * @return The display name.
	 */
	public String getDisplayName( );

	/**
	 * Gets the duration of the build in milliseconds.
	 * 
	 * @return The duration of the build.
	 */
	public long getDuration( );

	/**
	 * @return The build identifier.
	 */
	public String getID( );

	/**
	 * Gets the build number.
	 * 
	 * @return The build number.
	 */
	public int getNumber( );

	/**
	 * Gets the result of this build.
	 * 
	 * @return The result of the build.
	 */
	public String getResult( );

	/**
	 * Gets the test results.
	 * 
	 * @return The test results or <code>null</code> if none exist.
	 */
	public ITestResult getTestResults( );

	/**
	 * Gets the timestamp in milliseconds since the epoch.
	 * 
	 * @return The timestamp.
	 */
	public long getTimestamp( );

	/**
	 * Gets the build URL. This is a direct link to the page that represents this build.
	 * 
	 * @return The last build URL.
	 */
	public String getURL( );

	/**
	 * Gets if this is currently building or not.
	 * 
	 * @return <code>true</code> if it is still building. Otherwise <code>false</code>.
	 */
	public boolean isBuilding( );

	/**
	 * Sets if this is mBuilding or not.
	 * 
	 * @param building <code>true</code> if this is currently building. <code>false</code> in all
	 *            other cases.
	 */
	public void setBuilding( final boolean building );

	/**
	 * Sets the display name of this build.
	 * 
	 * @param name The name of the build or an empty string. This should never be <code>null</code>.
	 */
	public void setDisplayName( final String name );

	/**
	 * Sets the mDuration. The duration is a valid greater then 0 and represents the total
	 * milliseconds the build took.
	 * 
	 * @param duration The duration of the build.
	 */
	public void setDuration( final long duration );

	/**
	 * Sets the build unique identifier.
	 * 
	 * @param id The identifier.
	 */
	public void setID( final String id );

	/**
	 * Sets the build number.
	 * 
	 * @param number The build number.
	 */
	public void setNumber( final int number );

	/**
	 * Sets the build result.
	 * 
	 * @param result The build result.
	 */
	public void setResult( final String result );

	/**
	 * Sets the test results.
	 * 
	 * @param testResults The test results.
	 */
	public void setTestResults( final ITestResult testResults );

	/**
	 * Sets the timestamp (Total number of milliseconds).
	 * 
	 * @param timestamp The time stamp.
	 */
	public void setTimestamp( final long timestamp );

	/**
	 * Sets the URL of the build details page.
	 * 
	 * @param url The build URL.
	 */
	public void setURL( final String url );
}
