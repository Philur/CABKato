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

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.spextreme.jenkins.remote.model.IJobManager;

/**
 * This is a job content provider. It uses the Job Manager to get a lit of the jobs.
 */
public class JobContentProvider implements IStructuredContentProvider
{
	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose( )
	{
		// Nothing to do.
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 * @param inputElement The job manager if all is correct.
	 * @return The list of jobs if the input is a job manager. Otherwise <code>null</code> is
	 *         returned.
	 */
	@Override
	public Object[] getElements( final Object inputElement )
	{
		if( inputElement instanceof IJobManager )
		{
			final IJobManager jobManager = (IJobManager)inputElement;

			return jobManager.getJobs( ).toArray( );
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 * @param viewer The viewer instance.
	 * @param oldInput The old input data.
	 * @param newInput The new input data.
	 */
	@Override
	public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput )
	{
		// Do nothing.
	}
}
