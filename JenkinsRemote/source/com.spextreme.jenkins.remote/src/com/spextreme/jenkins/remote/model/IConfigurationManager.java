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

import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * This is the interface used for the configuration manager. The configuration manager holds the
 * information used to configure Jenkins Remote.
 */
public interface IConfigurationManager
{
	/**
	 * The configuration file name (jenkinsremote.xml).
	 */
	public static final String	CONFIG_FILE				= "jenkinsremote.xml";
	/**
	 * The property name for a server added.
	 */
	public static final String	PROPERTY_ADD_SERVER		= "addServer";
	/**
	 * The property name for a server being removed.
	 */
	public static final String	PROPERTY_REMOVE_SERVER	= "removeServer";
	/**
	 * The property name for a server being updated.
	 */
	public static final String	PROPERTY_UPDATE_SERVER	= "updateServer";
	/**
	 * The property name for a show item updates.
	 */
	public static final String	PROPERTY_SHOW			= "show";
	/**
	 * The property name for a job visibility updates.
	 */
	public static final String	PROPERTY_JOB			= "job";

	/**
	 * Adds a listener to be notified on changes.
	 * 
	 * @param listener The listener to add.
	 */
	public void addPropertyChangeListener( final IPropertyChangeListener listener );

	/**
	 * Adds the server to the set if it isn't already on the list.
	 * 
	 * @param server The server to add.
	 */
	public void addServer( IServerInformation server );

	/**
	 * Gets the list of job information items.
	 * 
	 * @return Returns the {@link List} of {@link IJobInformation} items.
	 */
	public List<IJobInformation> getJobs( );

	/**
	 * Gets if the given job should be displayed. If the name is not found then the default value
	 * should be returned. See the implementation for details of what the default value is.
	 * Typically it is to be shown (<code>true</code>).
	 * 
	 * @param jobName The name of the job to get the show state of.
	 * @return <code>true</code> if the job should be visible. <code>false</code> otherwise.
	 */
	public boolean getJobShowState( final String jobName );

	/**
	 * Gets the set of listeners.
	 * 
	 * @return The list of listeners. This will never be <code>null</code>.
	 */
	public List<IPropertyChangeListener> getListeners( );

	/**
	 * Gets the primary server. This can not return <code>null</code> under any circumstance. It is
	 * recommend that if the server has not been setup yet, then a Null Object (see Null Object
	 * Design Pattern) be returned.
	 * 
	 * @return The primary server instance. This will never be <code>null</code>.
	 */
	public IServerInformation getPrimaryServer( );

	/**
	 * Gets the list of server information items.
	 * 
	 * @return Returns the the {@link List} of {@link IServerInformation} items. This will never be
	 *         <code>null</code>.
	 */
	public List<IServerInformation> getServers( );

	/**
	 * Gets the list of items to show.
	 * 
	 * @return Returns the list of {@link IShowInformation} items. This will never be
	 *         <code>null</code>.
	 */
	public List<IShowInformation> getShowInfo( );

	/**
	 * Gets the visibility state of a show item. If the item name is not found, <code>true</code>
	 * will be returned by default.
	 * 
	 * @param itemName The item name to get the visibility state of.
	 * @return <code>true</code> if the item should be show. <code>false</code> in all other cases.
	 */
	public boolean isVisible( final String itemName );

	/**
	 * Removes the property change listener.
	 * 
	 * @param listener The listener to remove.
	 */
	public void removePropertyChangeListener( final IPropertyChangeListener listener );

	/**
	 * Sets the show state for the named item.
	 * 
	 * @param name The name of the item to set the show state for.
	 * @param value The state to set the item to.
	 */
	public void setItemShowState( final String name, final boolean value );

	/**
	 * Sets the job show state. If the job doesn't exist it will be added with the value passed in.
	 * 
	 * @param name The name of the job to set the show state for.
	 * @param value The value to set the show state for.
	 */
	public void setJobShowState( final String name, final boolean value );
}
