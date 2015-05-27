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

package com.spextreme.jenkins.remote.ui;

import java.util.Arrays;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

import com.spextreme.jenkins.remote.Utilities;
import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IServerInformation;

/**
 * 
 */
public class ServerCellModifier implements ICellModifier
{
	/**
	 * The table viewer instance.
	 */
	private TableViewer				mViewer			= null;
	/**
	 * The configuration manager instance.
	 */
	private IConfigurationManager	mConfigManager	= null;

	/**
	 * Constructs the cell modifier.
	 * 
	 * @param viewer The viewer used.
	 * @param configManager The configuration manager instance.
	 */
	public ServerCellModifier( final TableViewer viewer, final IConfigurationManager configManager )
	{
		mViewer = viewer;
		mConfigManager = configManager;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 * @param element The element to edit.
	 * @param property The property to be edited.
	 * @return <code>true</code> in all cases.
	 */
	@Override
	public boolean canModify( final Object element, final String property )
	{
		return true;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 * @param element The element to get the value from.
	 * @param property The property to get the value of.
	 * @return The object for the get value of.
	 */
	@Override
	public Object getValue( final Object element, final String property )
	{
		final int columnIndex = Arrays.asList( mViewer.getColumnProperties( ) ).indexOf( property );

		Object result = null;
		final IServerInformation server = (IServerInformation)element;

		switch( columnIndex )
		{
			case 0 : // Address
				result = server.getAddress( );
				break;

			case 1 : // User Name
				result = server.getUserName( );
				break;

			case 2 : // Password
				result = server.getPassword( );
				break;

			case 3 : // Query Periodic
				result = Integer.toString( server.getQueryPeriodic( ) );
				break;

			case 4 : // Allow Increase
				result = new Boolean( server.isAllowIncrease( ) );
				break;

			case 5 : // Is Primary
				result = new Boolean( server.isPrimary( ) );
				break;

			default :
				result = "";
		}

		return result;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String,
	 *      java.lang.Object)
	 * @param element The element to set the value of.
	 * @param property The property of the item to set.
	 * @param value The value that this is being set to.
	 */
	@Override
	public void modify( final Object element, final String property, final Object value )
	{
		if( ( element == null ) || !( element instanceof TableItem ) )
		{
			return;
		}

		final int columnIndex = Arrays.asList( mViewer.getColumnProperties( ) ).indexOf( property );

		final TableItem item = (TableItem)element;
		final IServerInformation server = (IServerInformation)item.getData( );
		final String trimmedString = value.toString( ).trim( );

		switch( columnIndex )
		{
			case 0 : // Address
				server.setAddress( Utilities.verifyEndingSlash( trimmedString ) );
				break;

			case 1 : // User Name
				server.setUserName( trimmedString );
				break;

			case 2 : // Password
				server.setPassword( trimmedString );
				break;

			case 3 : // Query Periodic
				server.setQueryPeriodic( Utilities.parseInt( trimmedString, server.getQueryPeriodic( ) ) );
				break;

			case 4 : // Allow Increase
				server.setAllowIncrease( Boolean.parseBoolean( trimmedString ) );
				break;

			case 5 : // Is Primary
				server.setPrimary( Boolean.parseBoolean( trimmedString ) );
				break;

			default :
		}

		updateServer( server );
	}

	/**
	 * Updates the server instance.
	 * 
	 * @param server The server data after the update.
	 */
	public void updateServer( final IServerInformation server )
	{
		final int index = mConfigManager.getServers( ).indexOf( server );

		if( index < 0 )
		{
			mConfigManager.addServer( server );

			mViewer.add( mConfigManager );
		}
		else
		{
			final IServerInformation updateServer = mConfigManager.getServers( ).get( index );

			updateServer.setAddress( server.getAddress( ) );
			updateServer.setUserName( server.getUserName( ) );
			updateServer.setPassword( server.getPassword( ) );
			updateServer.setQueryPeriodic( server.getQueryPeriodic( ) );
			updateServer.setAllowIncrease( server.isAllowIncrease( ) );
			updateServer.setPrimary( server.isPrimary( ) );
		}

		mViewer.refresh( );
	}
}
