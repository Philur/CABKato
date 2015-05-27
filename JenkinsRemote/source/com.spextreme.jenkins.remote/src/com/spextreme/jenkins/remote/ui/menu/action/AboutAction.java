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

package com.spextreme.jenkins.remote.ui.menu.action;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.spextreme.jenkins.remote.model.ITrayNotification;

/**
 * This displays the about information for Jenkins Manager.
 */
public class AboutAction extends SelectionAdapter
{
	/**
	 * The tray this is attached to.
	 */
	private ITrayNotification	mTrayNotifier	= null;

	/**
	 * Constructs the about action.
	 * 
	 * @param notifier The tray notifier service.
	 */
	public AboutAction( final ITrayNotification notifier )
	{
		mTrayNotifier = notifier;
	}

	/**
	 * Opens the configuration dialog.
	 * 
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 * @param e The selection event.
	 */
	@Override
	public void widgetSelected( final SelectionEvent e )
	{
		mTrayNotifier
				.displayNotification( "About Jenkins Remote",
						"Jenkins Remote : 2.4.0\nCopyright 2009-2013 SP extreme\nLicensed Under Apache 2.0\nhttp://www.spextreme.com" );
	}
}
