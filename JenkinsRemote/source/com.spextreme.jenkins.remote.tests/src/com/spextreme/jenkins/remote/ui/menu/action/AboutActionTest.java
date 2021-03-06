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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.spextreme.jenkins.remote.model.ITrayNotification;
import com.spextreme.jenkins.remote.ui.menu.action.AboutAction;

/**
 * Tests the about action.
 */
public class AboutActionTest
{
	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.ui.menu.action.AboutAction#widgetSelected(org.eclipse.swt.events.SelectionEvent)}
	 * .
	 */
	@Test
	public void testWidgetSelectedSelectionEvent( )
	{
		final ITrayNotification notifier = mock( ITrayNotification.class );

		final AboutAction action = new AboutAction( notifier );
		action.widgetSelected( null );

		verify( notifier )
				.displayNotification( "About Jenkins Remote",
						"Jenkins Remote : 2.4.0\nCopyright 2009-2013 SP extreme\nLicensed Under Apache 2.0\nhttp://www.spextreme.com" );
	}
}
