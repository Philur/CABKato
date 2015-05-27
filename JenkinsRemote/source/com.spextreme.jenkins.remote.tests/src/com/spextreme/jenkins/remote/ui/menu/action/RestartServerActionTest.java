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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.spextreme.jenkins.remote.jenkins.JenkinsCLI;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.ui.menu.action.RestartServerAction;

/**
 * Tests the Restart Server action.
 */
public class RestartServerActionTest
{
	/**
	 * Test when <code>null</code> is given for the server.
	 */
	@Test( expected = NullPointerException.class )
	public void testNullCLI( )
	{
		new RestartServerAction( new ServerInformation( "address" ), null );
	}

	/**
	 * Test when <code>null</code> is given for the server.
	 */
	@Test( expected = NullPointerException.class )
	public void testNullServer( )
	{
		new RestartServerAction( null, new JenkinsCLI( ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.ui.menu.action.RestartServerAction#widgetSelected(org.eclipse.swt.events.SelectionEvent)}
	 * .
	 */
	@Test
	public void testWidgetSelectedSelectionEvent( )
	{
		final JenkinsCLI cli = mock( JenkinsCLI.class );

		final RestartServerAction action = new RestartServerAction( new ServerInformation( "http://localhost/jenkins/" ), cli );

		action.widgetSelected( null );

		verify( cli, times( 1 ) ).simpleExecuteCommand( "http://localhost/jenkins/", "restart" );
	}
}
