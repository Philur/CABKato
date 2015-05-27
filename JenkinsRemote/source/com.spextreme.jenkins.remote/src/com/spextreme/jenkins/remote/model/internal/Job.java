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

import com.spextreme.jenkins.remote.model.IBuild;
import com.spextreme.jenkins.remote.model.IHealthReport;
import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IServerInformation;

/**
 * This represents a Jenkins project. A jenkins project will contain at a minimum, a name, state and a
 * link. It may include other details about the project including, last successful build last failed
 * build, revision number, and duration.
 */
public class Job implements IJob
{
	/**
	 * The server instance.
	 */
	private IServerInformation	mServer				= null;

	/**
	 * A flag denoting if this is mBuildable or not.
	 */
	private boolean				mBuildable			= true;

	/**
	 * The color to show the status. The value in this can be used to get the gif image.
	 */
	private String				mColor				= "";

	/**
	 * The current build.
	 */
	private IBuild				mCurrentBuild		= null;
	/**
	 * The health reports.
	 */
	private List<IHealthReport>	mHealthReports		= null;
	/**
	 * The last completed build.
	 */
	private IBuild				mLastCompletedBuild	= null;
	/**
	 * The name of the job.
	 */
	private String				mName				= "";
	/**
	 * The URL of the project.
	 */
	private String				mURL				= "";

	/**
	 * Constructs the project.
	 * 
	 * @param serverInformation The server instance. This can not be <code>null</code>.
	 * @param name The name of the project.
	 * @param url The project URL.
	 * @param color The status color.
	 */
	public Job( final IServerInformation serverInformation, final String name, final String url, final String color )
	{
		setServer( serverInformation );

		setName( name );
		setURL( url );
		setColor( color );
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#addHealthReport(com.spextreme.jenkins.remote.model.IHealthReport)
	 * @param healthReport The health report to add.
	 */
	public void addHealthReport( final IHealthReport healthReport )
	{
		getHealthReports( ).add( healthReport );
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
			if( obj instanceof IJob )
			{
				result = getName( ).equals( ( (IJob)obj ).getName( ) ) && getServer( ).equals( ( (IJob)obj ).getServer( ) );
			}
		}

		return result;
	}

	/**
	 * 
	 * @see com.spextreme.jenkins.remote.model.IJob#getColor()
	 * @return The color.
	 */
	public String getColor( )
	{
		return mColor;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#getCurrentBuild()
	 * @return The current build.
	 */
	public IBuild getCurrentBuild( )
	{
		return mCurrentBuild;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#getHealthReports()
	 * @return The list of health reports.
	 */
	public List<IHealthReport> getHealthReports( )
	{
		if( mHealthReports == null )
		{
			mHealthReports = new Vector<IHealthReport>( );
		}

		return mHealthReports;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#getLastCompletedBuild()
	 * @return The last completed build.
	 */
	@Override
	public IBuild getLastCompletedBuild( )
	{
		return mLastCompletedBuild;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#getName()
	 * @return The name or an empty string.
	 */
	public String getName( )
	{
		return mName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#getServer()
	 * @return The server instance.
	 */
	@Override
	public IServerInformation getServer( )
	{
		return mServer;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#getURL()
	 * @return The URL String.
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
		result = prime * result + getName( ).hashCode( );
		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#isBuildable()
	 * @return <code>true</code> if this is buildable. Otherwise <code>false</code>.
	 */
	public boolean isBuildable( )
	{
		return mBuildable;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#setBuildable(boolean)
	 * @param buildable <code>true</code> if this can be built. Otherwise <code>false</code>.
	 */
	public void setBuildable( final boolean buildable )
	{
		mBuildable = buildable;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#setColor(java.lang.String)
	 * @param color The color.
	 */
	public void setColor( final String color )
	{
		mColor = color;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#setCurrentBuild(com.spextreme.jenkins.remote.model.IBuild)
	 * @param build The current build.
	 */
	public void setCurrentBuild( final IBuild build )
	{
		mCurrentBuild = build;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#setLastCompletedBuild(com.spextreme.jenkins.remote.model.IBuild)
	 * @param build The last completed build.
	 */
	public void setLastCompletedBuild( final IBuild build )
	{
		mLastCompletedBuild = build;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#setName(java.lang.String)
	 * @param name The name of the job or an empty string. This should never be <code>null</code>.
	 */
	public void setName( final String name )
	{
		mName = name;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJob#setURL(java.lang.String)
	 * @param url The URL string.
	 */
	public void setURL( final String url )
	{
		mURL = url;
	}

	/**
	 * Sets the server instance.
	 * 
	 * @param serverInformation The server instance.
	 */
	private void setServer( final IServerInformation serverInformation )
	{
		if( serverInformation == null )
		{
			throw new NullPointerException( "The server can not be null." );
		}

		mServer = serverInformation;
	}
}
