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

import com.spextreme.jenkins.remote.model.IJobInformation;
import com.spextreme.jenkins.remote.model.IServerInformation;

/**
 * This is a job information item which represents each job in the configuration file and it's query
 * state.
 */
public class JobInformation implements IJobInformation
{
	/**
	 * The name of the job.
	 */
	private String				mName			= "";
	/**
	 * The visibility flag.
	 */
	private boolean				mVisible		= true;
	/**
	 * The server name reference.
	 */
	private String				mServer			= "";
	/**
	 * The server instance.
	 */
	private IServerInformation	mServerInstance	= null;

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobInformation#getName()
	 * @return The name of this job. Must match what ZJenkins returns to be an exact lookup.
	 */
	@Override
	public String getName( )
	{
		return mName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobInformation#getServerInstance()
	 * @return Gets the server instance.
	 */
	@Override
	public IServerInformation getServerInstance( )
	{
		return mServerInstance;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobInformation#getServerReference()
	 * @return Gets the server reference name for use in lookups.
	 */
	@Override
	public String getServerReference( )
	{
		return mServer;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobInformation#isVisible()
	 * @return <code>true</code> if this is visible. <code>false</code> otherwise.
	 */
	@Override
	public boolean isVisible( )
	{
		return mVisible;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobInformation#setName(java.lang.String)
	 * @param name The name of the job.
	 */
	@Override
	public void setName( final String name )
	{
		mName = name;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobInformation#setServerInstance(com.spextreme.jenkins.remote.model.IServerInformation)
	 * @param server The server information instance.
	 */
	@Override
	public void setServerInstance( final IServerInformation server )
	{
		mServerInstance = server;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobInformation#setServerReference(java.lang.String)
	 * @param serverRefName The server name used in looking up the server instance.
	 */
	@Override
	public void setServerReference( final String serverRefName )
	{
		mServer = serverRefName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IJobInformation#setVisible(boolean)
	 * @param visible <code>true</code> if this job should be displayed. <code>false</code>
	 *            otherwise.
	 */
	@Override
	public void setVisible( final boolean visible )
	{
		mVisible = visible;
	}

}
