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

/***
 * This is the interface of an artifact used for downloading the artifacts built by Jenkins.
 */
public interface IArtifact
{
	/**
	 * Gets the name used for the display.
	 * 
	 * @return The display name.
	 */
	public String getDisplayName( );

	/**
	 * Gets the relative path. This prepended with the Project URL and the workspace or latest build
	 * path will give access to a downloadable archive via HTTP.
	 * 
	 * @return The relative path.
	 */
	public String getRelativePath( );

	/**
	 * Sets the display name.
	 * 
	 * @param name The display name.
	 */
	public void setDisplayName( final String name );

	/**
	 * Sets the relative path value.
	 * 
	 * @param path The relative path.
	 */
	public void setRelativePath( final String path );
}
