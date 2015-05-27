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

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IJobInformation;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.IShowInformation;

/**
 * This holds the configuration information for Jenkins Remote.
 */
public class ConfigurationData implements IConfigurationManager
{
	/**
	 * The list of change listeners.
	 */
	private List<IPropertyChangeListener>	mListeners	= null;
	/**
	 * The list of servers to monitor.
	 */
	private List<IServerInformation>		mServers	= null;
	/**
	 * The configuration information for each job.
	 */
	private List<IJobInformation>			mJobs		= null;
	/**
	 * The list of items to know if they should be shown or hidden.
	 */
	private List<IShowInformation>			mShowInfo	= null;

	/**
	 * Constructs the configuration data.
	 */
	public ConfigurationData( )
	{
		// Nothing to do.
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#addPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
	 * @param listener The listener to add.
	 */
	public void addPropertyChangeListener( final IPropertyChangeListener listener )
	{
		getListeners( ).add( listener );
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#addServer(com.spextreme.jenkins.remote.model.IServerInformation)
	 * @param server The server to add.
	 */
	public void addServer( final IServerInformation server )
	{
		if( !getServers( ).contains( server ) )
		{
			getServers( ).add( server );
			firePropertyChange( server, PROPERTY_ADD_SERVER, null, server );
		}
	}

	/**
	 * Gets the List<IJobInformation> for this object.
	 * 
	 * @return Returns the list of job configuration items.
	 */
	public List<IJobInformation> getJobs( )
	{
		if( mJobs == null )
		{
			mJobs = new Vector<IJobInformation>( );
		}

		return mJobs;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#getJobShowState(java.lang.String)
	 * @param jobName The name of the project to get the show state for.
	 * @return <code>true</code> if the project should be shown or <code>false</code> otherwise.
	 */
	public boolean getJobShowState( final String jobName )
	{
		boolean result = true;

		for( final IJobInformation job : getJobs( ) )
		{
			if( job.getName( ).equals( jobName ) )
			{
				result = job.isVisible( );
				break;
			}
		}

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#getListeners()
	 * @return The list of listeners. This will never be <code>null</code>.
	 */
	public List<IPropertyChangeListener> getListeners( )
	{
		if( mListeners == null )
		{
			mListeners = new Vector<IPropertyChangeListener>( );
		}

		return mListeners;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#getPrimaryServer()
	 * @return The primary server instance or a Null Instance if not set.
	 */
	public IServerInformation getPrimaryServer( )
	{
		IServerInformation serverInformation = null;

		for( final IServerInformation item : getServers( ) )
		{
			if( item.isPrimary( ) )
			{
				serverInformation = item;
				break;
			}
		}

		if( ( serverInformation == null ) && ( getServers( ).size( ) > 0 ) )
		{
			serverInformation = getServers( ).get( 0 );
		}

		if( serverInformation == null )
		{
			serverInformation = new ServerInformation( "http://localhost/jenkins/", "", "", 60000, false, true );
			serverInformation.setPrimary( true );
		}

		return serverInformation;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#getServers()
	 * @return Returns the the {@link List} of {@link IServerInformation} items. This will never be
	 *         <code>null</code>.
	 */
	public List<IServerInformation> getServers( )
	{
		if( mServers == null )
		{
			mServers = new Vector<IServerInformation>( );
		}

		return mServers;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#getShowInfo()
	 * @return Returns the list of {@link IShowInformation} items. This will never be
	 *         <code>null</code>.
	 */
	public List<IShowInformation> getShowInfo( )
	{
		if( mShowInfo == null )
		{
			mShowInfo = new Vector<IShowInformation>( );
		}

		return mShowInfo;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#isVisible(java.lang.String)
	 * @param itemName The item name to get the visibility state of.
	 * @return <code>true</code> if the item should be show. <code>false</code> in all other cases.
	 */
	public boolean isVisible( final String itemName )
	{
		boolean result = true;

		for( final IShowInformation info : getShowInfo( ) )
		{
			if( info.getName( ).equals( itemName ) )
			{
				result = info.isVisible( );
				break;
			}
		}

		return result;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#removePropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
	 * @param listener The listener to remove.
	 */
	public void removePropertyChangeListener( final IPropertyChangeListener listener )
	{
		getListeners( ).remove( listener );
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IConfigurationManager#setItemShowState(java.lang.String,
	 *      boolean)
	 * @param name The name of the item to set the show state for.
	 * @param value The state to set the item to.
	 */
	public void setItemShowState( final String name, final boolean value )
	{
		for( final IShowInformation info : getShowInfo( ) )
		{
			if( info.getName( ).equals( name ) )
			{
				if( info.isVisible( ) != value )
				{
					info.setVisible( value );
					firePropertyChange( info, PROPERTY_SHOW, !value, value );
				}
				return;
			}
		}

		final IShowInformation showInfo = new ShowInformation( );
		showInfo.setName( name );
		showInfo.setVisible( value );

		getShowInfo( ).add( showInfo );
		firePropertyChange( showInfo, PROPERTY_SHOW, null, value );
	}

	/**
	 * Sets the job show state. If the job doesn't exist it will be added with the value passed in.
	 * 
	 * @param name The name of the job to set the show state for.
	 * @param value The value to set the show state for.
	 */
	public void setJobShowState( final String name, final boolean value )
	{
		for( final IJobInformation job : getJobs( ) )
		{
			final String jobName = job.getName( );
			if( jobName.equals( name ) )
			{
				final boolean previousState = job.isVisible( );
				if( previousState != value )
				{
					job.setVisible( value );
					firePropertyChange( job, PROPERTY_JOB, previousState, value );
				}

				return;
			}
		}

		final IJobInformation jobInfo = new JobInformation( );
		jobInfo.setName( name );
		jobInfo.setVisible( value );

		getJobs( ).add( jobInfo );
		firePropertyChange( jobInfo, PROPERTY_JOB, null, value );
	}

	/**
	 * Fires the change event out to all registered listeners.
	 * 
	 * @param object The object who's property changed.
	 * @param property The property that changed.
	 * @param oldValue The old value.
	 * @param newValue The new value.
	 */
	private synchronized void firePropertyChange( final Object object, final String property, final Object oldValue,
			final Object newValue )
	{
		for( final IPropertyChangeListener listener : getListeners( ) )
		{
			listener.propertyChange( new PropertyChangeEvent( object, property, oldValue, newValue ) );
		}
	}
}
