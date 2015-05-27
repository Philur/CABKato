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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.ui.JenkinsRemoteTray;

/**
 * This is the server label provider.
 */
public class ServerLabelProvider extends LabelProvider implements ITableLabelProvider
{
	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 * @param element The element to get the image for.
	 * @param columnIndex The column index the image will be placed.
	 * @return The image or <code>null</code> if no image.
	 */
	@Override
	public Image getColumnImage( final Object element, final int columnIndex )
	{
		Image image = null;

		if( element instanceof IServerInformation )
		{
			switch( columnIndex )
			{
				case 4 :
					image = getCheckboxImage( ( (IServerInformation)element ).isAllowIncrease( ) );
					break;

				case 5 :
					image = getCheckboxImage( ( (IServerInformation)element ).isPrimary( ) );
					break;

				default :
					break;
			}
		}

		return image;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 * @param element The {@link IServerInformation} item to get the text for.
	 * @param columnIndex The index of the column to get text.
	 * @return The text for the column or an empty string if non should be displayed.
	 */
	@Override
	public String getColumnText( final Object element, final int columnIndex )
	{
		String text = "";

		if( element instanceof IServerInformation )
		{
			final IServerInformation server = (IServerInformation)element;

			switch( columnIndex )
			{
				case 0 : // Address
					text = server.getAddress( );
					break;
				case 1 : // User Name
					text = server.getUserName( );
					break;
				case 2 : // Password
					text = server.getPassword( );
					break;
				case 3 : // Query Rate
					text = Integer.toString( server.getQueryPeriodic( ) );
					break;
				case 4 : // Allow Increase
					// text = server.isAllowIncrease( );
					break;
				case 5 : // Is Primary
					// text = server.isPrimary( );
					break;

				default :
					break;
			}
		}

		return text;
	}

	/**
	 * Gets the checkbox image for the given boolean.
	 * 
	 * @param value The value to use.
	 * @return The image matching the boolean value.
	 */
	private Image getCheckboxImage( final boolean value )
	{
		if( value )
		{
			return JenkinsRemoteTray.getImage( "checkbox.checked" );
		}

		return JenkinsRemoteTray.getImage( "checkbox.unchecked" );
	}
}
