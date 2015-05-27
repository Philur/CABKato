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

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.spextreme.jenkins.remote.model.IJob;

/**
 * This is a job label provider. It provides the strings for the table when job is passed in.
 */
public class JobLabelProvider implements ITableLabelProvider
{
	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 * @param listener The listener to add.
	 */
	@Override
	public void addListener( final ILabelProviderListener listener )
	{
		// Do nothing.
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose( )
	{
		// Do nothing
	}

	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 * @param element The element to get the image for. Not used.
	 * @param columnIndex The column index. Not used.
	 * @return <code>null</code> in all cases.
	 */
	@Override
	public Image getColumnImage( final Object element, final int columnIndex )
	{
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 * @param element The element to get the string for. Should be an {@link IJob}.
	 * @param columnIndex The column index. Not used.
	 * @return The string to represent the job or <code>null</code> if not an {@link IJob} instance.
	 */
	@Override
	public String getColumnText( final Object element, final int columnIndex )
	{
		if( element instanceof IJob )
		{
			return ( (IJob)element ).getName( );
		}

		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
	 *      java.lang.String)
	 * @param element The element. Not used.
	 * @param property The property string. Not used.
	 * @return <code>false</code> in all cases.
	 */
	@Override
	public boolean isLabelProperty( final Object element, final String property )
	{
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 * @param listener The listener to remove.
	 */
	@Override
	public void removeListener( final ILabelProviderListener listener )
	{
		// do nothing.
	}
}
