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
 * This represents the configuration file's Job item. A job items is used to control the visibility
 * of jobs returned from the Jenkins Server.
 */
public interface IJobInformation
{
	/**
	 * The name of this job.
	 * 
	 * @return The name.
	 */
	public String getName( );

	/**
	 * Gets the server instance. This may be <code>null</code> if it has not yet been synchronized.
	 * 
	 * @return The server instnace or <code>null</code> if not yet set.
	 */
	public IServerInformation getServerInstance( );

	/**
	 * Gets the server reference.
	 * 
	 * @return The server reference name.
	 */
	public String getServerReference( );

	/**
	 * Gets if this should be visible.
	 * 
	 * @return <code>true</code> if visible. Otherwise <code>false</code>.
	 */
	public boolean isVisible( );

	/**
	 * Sets the name of the job.
	 * 
	 * @param name The name of the job.
	 */
	public void setName( String name );

	/**
	 * Sets the server instance.
	 * 
	 * @param server The server instance.
	 */
	public void setServerInstance( IServerInformation server );

	/**
	 * Sets the server reference name. This should match the address of the server instance.
	 * 
	 * @param serverRefName The server instance name.
	 */
	public void setServerReference( String serverRefName );

	/**
	 * Sets the visibility state.
	 * 
	 * @param visible The visibility state to use.
	 */
	public void setVisible( boolean visible );
}
