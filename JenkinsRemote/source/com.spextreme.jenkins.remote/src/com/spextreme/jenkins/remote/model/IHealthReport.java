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
 * This is the interface for a health report.
 */
public interface IHealthReport
{
	/**
	 * Gets the description of this. This should never be <code>null</code>.
	 * 
	 * @return The description or an empty string.
	 */
	public String getDescription( );

	/**
	 * Gets the name of the icon used on the Jenkins server. This should never be <code>null</code>.
	 * 
	 * @return The icon name or an empty string.
	 */
	public String getIconName( );

	/**
	 * Gets the score.
	 * 
	 * @return The score.
	 */
	public int getScore( );

	/**
	 * Sets the description of this health report.
	 * 
	 * @param description The description or an empty string. This should never be <code>null</code>
	 *            .
	 */
	public void setDescription( final String description );

	/**
	 * Sets the name of the icon used on the Jenkins server.
	 * 
	 * @param name The name of the icon.
	 */
	public void setIconName( final String name );

	/**
	 * Sets the score of this health report.
	 * 
	 * @param score The score to use.
	 */
	public void setScore( final int score );
}
