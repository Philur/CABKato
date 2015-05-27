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
 * This is the interface for items that allow something to be shown or hidden in the menu. Examples
 * include test data, archives, admin commands, etc...
 */
public interface IShowInformation
{
	/**
	 * Gets the name to use for this show item.
	 * 
	 * @return The name. This should never be <code>null</code>.
	 */
	public String getName( );

	/**
	 * Gets if this is to be shown.
	 * 
	 * @return <code>true</code> if the item should be shown. <code>false</code> otherwise.
	 */
	public boolean isVisible( );

	/**
	 * The name to use to know what can be shown or hidden.
	 * 
	 * @param name The name.
	 */
	public void setName( String name );

	/**
	 * Sets if this should be visible or not.
	 * 
	 * @param value <code>true</code> if it should be visible. <code>false</code> otherwise.
	 */
	public void setVisible( boolean value );
}
