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

import com.spextreme.jenkins.remote.model.IArtifact;

/**
 * This represents a downloadable artifact that Jenkins has published.
 */
public class Artifact implements IArtifact
{
	/**
	 * The name used in displaying this artifact.
	 */
	private String	mDisplayName	= "";
	/**
	 * The relative path.
	 */
	private String	mRelativePath	= "";

	/**
	 * Constructs the artifact.
	 * 
	 * @param name The name to use for the display name.
	 * @param relativePath The relative path.
	 */
	public Artifact( final String name, final String relativePath )
	{
		setDisplayName( name );
		setRelativePath( relativePath );
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
			if( obj instanceof Artifact )
			{
				result = getRelativePath( ).equals( ( (IArtifact)obj ).getRelativePath( ) );
			}
		}

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IArtifact#getDisplayName()
	 * @return The display name.
	 */
	public String getDisplayName( )
	{
		return mDisplayName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IArtifact#getRelativePath()
	 * @return The relative path.
	 */
	public String getRelativePath( )
	{
		return mRelativePath;
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
		result = prime * result + getRelativePath( ).hashCode( );
		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IArtifact#setDisplayName(java.lang.String)
	 * @param name The display name.
	 */
	public void setDisplayName( final String name )
	{
		mDisplayName = name;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IArtifact#setRelativePath(java.lang.String)
	 * @param path The relative path.
	 */
	public void setRelativePath( final String path )
	{
		mRelativePath = path;
	}
}
