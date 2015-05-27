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

package com.spextreme.jenkins.remote.model.provider;

import java.util.List;
import java.util.Vector;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.spextreme.jenkins.remote.model.IServerInformation;

/**
 * This is the content provider for the server table in the configuration dialog.
 */
public class ServerContentProvider implements IStructuredContentProvider
{
	/**
	 * The list of servers.
	 */
	private List<IServerInformation>	mServers	= null;

	/**
	 * Adds a server to the set.
	 * 
	 * @param server The server to be added. Duplicates are allowed.
	 */
	public void addServer( final IServerInformation server )
	{
		getServers( ).add( server );
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose( )
	{
		// nothing to cleanup
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 * @param inputElement The input. Expects a list of IServerInformation items.
	 * @return The list of {@link IServerInformation} as an array.
	 */
	@Override
	public Object[] getElements( final Object inputElement )
	{
		return getServers( ).toArray( );
	}

	/**
	 * Gets the List of {@link IServerInformation}.
	 * 
	 * @return Returns the server list.
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
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 * @param viewer The viewer (not used).
	 * @param oldInput The input before the update (not used).
	 * @param newInput The new input to use (not used).
	 */
	@Override
	public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput )
	{
		if( newInput instanceof List<?> )
		{
			for( final Object obj : (List<?>)newInput )
			{
				if( obj instanceof IServerInformation )
				{
					try
					{
						addServer( (IServerInformation)( (IServerInformation)obj ).clone( ) );
					}
					catch( final Exception e )
					{
						// This will never happen but needed for completeness.
					}
				}
			}
		}
	}

	/**
	 * Removes the server instance.
	 * 
	 * @param server The server to remove.
	 */
	public void removeServer( final IServerInformation server )
	{
		getServers( ).remove( server );
	}
}
