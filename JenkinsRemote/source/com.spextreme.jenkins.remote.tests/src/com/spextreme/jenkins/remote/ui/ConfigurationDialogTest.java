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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.provider.ServerContentProvider;
import com.spextreme.jenkins.remote.ui.ConfigurationDialog;

/**
 * Test the configuration dialog (missing UI based test however).
 */
public class ConfigurationDialogTest
{
	/**
	 * Tests the get methods.
	 */
	@Test
	public void testGetMethods( )
	{
		final ConfigurationDialog dialog = new ConfigurationDialog( new Shell( ), null, null );

		final IConfigurationManager manager = dialog.getConfigurationData( );
		final ServerContentProvider provider = dialog.getContentProvider( );

		assertNotNull( manager );
		assertNotNull( provider );

		assertTrue( manager == dialog.getConfigurationData( ) );
		assertTrue( provider == dialog.getContentProvider( ) );
	}
}
