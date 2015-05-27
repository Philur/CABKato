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
 * This is the interface for the model that holds the Jenkins Server information. A server is a
 * single instance of Jenkins.
 */
public interface IServerInformation extends Cloneable
{
	/**
	 * @see java.lang.Object#clone()
	 * @return A copy of the {@link IServerInformation} object.
	 * @throws CloneNotSupportedException Thrown if it's not support. This should never happen.
	 */
	public Object clone( ) throws CloneNotSupportedException;

	/**
	 * Gets the String for this object.
	 * 
	 * @return Returns the address of the Jenkins server.
	 */
	public String getAddress( );

	/**
	 * Gets the String for this object.
	 * 
	 * @return Returns the password.
	 */
	public String getPassword( );

	/**
	 * Gets the int for this object.
	 * 
	 * @return Returns the query periodic.
	 */
	public int getQueryPeriodic( );

	/**
	 * Gets the String for this object.
	 * 
	 * @return Returns the username.
	 */
	public String getUserName( );

	/**
	 * Gets the boolean for this object.
	 * 
	 * @return <code>true</code> if increase of polling is allowed.
	 */
	public boolean isAllowIncrease( );

	/**
	 * The primary flag. The primary is the one that will be consulted for basic information.
	 * 
	 * @return <code>true</code> if this is the primary instance. <code>false</code> otherwise.
	 */
	public boolean isPrimary( );

	/**
	 * Sets the address to use.
	 * 
	 * @param address The address. This can never be <code>null</code>.
	 */
	public void setAddress( String address );

	/**
	 * Use to know if this can increase the query periodic when builds are detected.
	 * 
	 * @param value The value to use.
	 */
	public void setAllowIncrease( boolean value );

	/**
	 * Sets the password to use.
	 * 
	 * @param password The password to use. An empty string or <code>null</code> if not needed.
	 */
	public void setPassword( String password );

	/**
	 * Sets the primary flag.
	 * 
	 * @param value <code>true</code> if this is the primary server. <code>false</code> in all other
	 *            cases.
	 */
	public void setPrimary( final boolean value );

	/**
	 * Sets the total number of milliseconds to wait between query requests.
	 * 
	 * @param value The value to use.
	 */
	public void setQueryPeriodic( int value );

	/**
	 * The user name to use or an empty string.
	 * 
	 * @param name The user name to use or an empty string. <code>null</code> is also valid but will
	 *            be converted to an empty string. When this is an empty string no authentication is
	 *            performed.
	 */
	public void setUserName( String name );
}
