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

import com.spextreme.jenkins.remote.model.IArtifact;
import com.spextreme.jenkins.remote.model.IBuild;
import com.spextreme.jenkins.remote.model.ITestResult;

/**
 * This represents a Jenkins Build within a job. A build could be any individual build, including the
 * last build, last stable, etc...
 */
public class Build implements IBuild
{
	/**
	 * The mArtifacts attached to this build.
	 */
	private List<IArtifact>	mArtifacts		= null;
	/**
	 * A flag to know if this is currently mBuilding.
	 */
	private boolean			mBuilding		= false;
	/**
	 * The build number.
	 */
	private int				mBuildNumber	= 0;
	/**
	 * The displayable name of the build. Usually is the Job Name + the Build number.
	 */
	private String			mDisplayName	= "";
	/**
	 * The mDuration of this build.
	 */
	private long			mDuration		= 0L;
	/**
	 * The identifier of this build. Usually includes the date and time of the build.
	 */
	private String			mID				= "";
	/**
	 * The results (SUCCESS, FAILURE, etc...) of the build.
	 */
	private String			mResult			= "";
	/**
	 * The test results of this build.
	 */
	private ITestResult		mTestResults	= null;
	/**
	 * The time stamp of the build since the epoch (milliseconds).
	 */
	private long			mTimeStamp		= 0L;
	/**
	 * The URL of the builds.
	 */
	private String			mURL			= "";

	/**
	 * Constructs the build.
	 * 
	 * @param id The ID of the build.
	 * @param name The displayable name of the build.
	 * @param buildNumber The build number.
	 * @param building The flag to know if this is mBuilding or not.
	 */
	public Build( final String id, final String name, final int buildNumber, final boolean building )
	{
		setID( id );
		setDisplayName( name );
		setNumber( buildNumber );
		setBuilding( building );
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#addArtifacts(com.spextreme.jenkins.remote.model.IArtifact)
	 * @param artifact The artifact to add.
	 */
	public void addArtifacts( final IArtifact artifact )
	{
		getArtifacts( ).add( artifact );
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param obj The object to compare to.
	 * @return <code>true</code> if the two are equal. Otherwise <code>false</code>.
	 */
	@Override
	public boolean equals( final Object obj )
	{
		boolean result = this == obj;

		if( ( obj != null ) && !result )
		{
			if( obj instanceof Build )
			{
				result = getID( ).equals( ( (IBuild)obj ).getID( ) );
			}
		}

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getArtifacts()
	 * @return The list of artifacts.
	 */
	public List<IArtifact> getArtifacts( )
	{
		if( mArtifacts == null )
		{
			mArtifacts = new Vector<IArtifact>( );
		}

		return mArtifacts;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getDisplayName()
	 * @return The display name.
	 */
	public String getDisplayName( )
	{
		return mDisplayName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getDuration()
	 * @return The duration of the build.
	 */
	public long getDuration( )
	{
		return mDuration;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getID()
	 * @return The build identifier.
	 */
	public String getID( )
	{
		return mID;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getNumber()
	 * @return The build number.
	 */
	public int getNumber( )
	{
		return mBuildNumber;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getResult()
	 * @return The result of the build.
	 */
	public String getResult( )
	{
		return mResult;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getTestResults()
	 * @return The test results or <code>null</code> if none exist.
	 */
	public ITestResult getTestResults( )
	{
		return mTestResults;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getTimestamp()
	 * @return The timestamp.
	 */
	public long getTimestamp( )
	{
		return mTimeStamp;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#getURL()
	 * @return The last build URL.
	 */
	public String getURL( )
	{
		return mURL;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * @return The hash code value based on the name.
	 */
	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + getID( ).hashCode( );
		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#isBuilding()
	 * @return <code>true</code> if it is still building. Otherwise <code>false</code>.
	 */
	public boolean isBuilding( )
	{
		return mBuilding;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setBuilding(boolean)
	 * @param building <code>true</code> if this is currently building. <code>false</code> in all
	 *            other cases.
	 */
	public void setBuilding( final boolean building )
	{
		mBuilding = building;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setDisplayName(java.lang.String)
	 * @param name The name of the build or an empty string. This should never be <code>null</code>.
	 */
	public void setDisplayName( final String name )
	{
		mDisplayName = name;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setDuration(long)
	 * @param duration The duration of the build.
	 */
	public void setDuration( final long duration )
	{
		mDuration = duration;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setID(java.lang.String)
	 * @param id The identifier.
	 */
	public void setID( final String id )
	{
		mID = id;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setNumber(int)
	 * @param number The build number.
	 */
	public void setNumber( final int number )
	{
		mBuildNumber = number;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setResult(java.lang.String)
	 * @param result The build result.
	 */
	public void setResult( final String result )
	{
		mResult = result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setTestResults(com.spextreme.jenkins.remote.model.ITestResult)
	 * @param testResults The test results.
	 */
	public void setTestResults( final ITestResult testResults )
	{
		mTestResults = testResults;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setTimestamp(long)
	 * @param timestamp The time stamp.
	 */
	public void setTimestamp( final long timestamp )
	{
		mTimeStamp = timestamp;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IBuild#setURL(java.lang.String)
	 * @param url The build URL.
	 */
	public void setURL( final String url )
	{
		mURL = url;
	}
}
