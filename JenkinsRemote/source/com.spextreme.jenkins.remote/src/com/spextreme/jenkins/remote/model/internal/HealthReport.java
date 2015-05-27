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

import com.spextreme.jenkins.remote.model.IHealthReport;

/**
 * This represents a Health Report. A health report may be the stability of a build, the test
 * results, any other types.
 */
public class HealthReport implements IHealthReport
{
	/**
	 * The description of the health report.
	 */
	private String	mDescription	= "";
	/**
	 * The icon name used to represent this health report.
	 */
	private String	mIconName		= "";
	/**
	 * The score (percent) represented by this health report.
	 */
	private int		mScore			= 0;

	/**
	 * Constructs the project.
	 * 
	 * @param description The description of this health report.
	 * @param score The score of this health report. Usually a percentage (0 - 100)
	 */
	public HealthReport( final String description, final int score )
	{
		setDescription( description );
		setScore( score );
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

		if( !result && ( obj != null ) )
		{
			if( obj instanceof HealthReport )
			{
				result = getDescription( ).equals( ( (IHealthReport)obj ).getDescription( ) )
						&& ( getScore( ) == ( (IHealthReport)obj ).getScore( ) );
			}
		}

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IHealthReport#getDescription()
	 * @return The description or an empty string.
	 */
	public String getDescription( )
	{
		return mDescription;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IHealthReport#getIconName()
	 * @return The icon name or an empty string.
	 */
	public String getIconName( )
	{
		return mIconName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IHealthReport#getScore()
	 * @return The score.
	 */
	public int getScore( )
	{
		return mScore;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * @return The hash code value.
	 */
	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + getDescription( ).hashCode( );
		result = prime * result + getScore( );
		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IHealthReport#setDescription(java.lang.String)
	 * @param description The description or an empty string. This should never be <code>null</code>
	 *            .
	 */
	public void setDescription( final String description )
	{
		mDescription = description;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IHealthReport#setIconName(java.lang.String)
	 * @param name The name of the icon.
	 */
	public void setIconName( final String name )
	{
		mIconName = name;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IHealthReport#setScore(int)
	 * @param score The score to use.
	 */
	public void setScore( final int score )
	{
		mScore = score;
	}
}
