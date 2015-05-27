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

import com.spextreme.jenkins.remote.model.IShowInformation;

/**
 * 
 */
public class ShowInformation implements IShowInformation
{
	/**
	 * The name of the item.
	 */
	private String	mName		= "";
	/**
	 * The visibility flag.
	 */
	private boolean	mVisible	= false;

	/**
	 * @see com.spextreme.jenkins.remote.model.IShowInformation#getName()
	 * @return The name of this item.
	 */
	@Override
	public String getName( )
	{
		return mName;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IShowInformation#isVisible()
	 * @return <code>true</code> if this should be show. <code>false</code> otherwise.
	 */
	@Override
	public boolean isVisible( )
	{
		return mVisible;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IShowInformation#setName(java.lang.String)
	 * @param name The name to use.
	 */
	@Override
	public void setName( final String name )
	{
		mName = name;
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IShowInformation#setVisible(boolean)
	 * @param value <code>true</code> if this should be shown. <code>false</code> otherwise.
	 */
	@Override
	public void setVisible( final boolean value )
	{
		mVisible = value;
	}
}
