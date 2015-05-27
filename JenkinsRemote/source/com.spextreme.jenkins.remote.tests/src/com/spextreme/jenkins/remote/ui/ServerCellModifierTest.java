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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Vector;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.ui.ConfigurationDialog;
import com.spextreme.jenkins.remote.ui.ServerCellModifier;

/**
 * Tests the {@link ServerCellModifier} object.
 */
public class ServerCellModifierTest
{
	/**
	 * The object under test.
	 */
	private ServerCellModifier		mModifier	= null;
	/**
	 * Objects using in testing.
	 */
	private TableViewer				mViewer		= null;
	private IConfigurationManager	mConfig		= null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp( ) throws Exception
	{
		mViewer = mock( TableViewer.class );
		mConfig = mock( IConfigurationManager.class );

		mModifier = new ServerCellModifier( mViewer, mConfig );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown( ) throws Exception
	{
		mModifier = null;
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.ui.ServerCellModifier#canModify(java.lang.Object, java.lang.String)}
	 * .
	 */
	@Test
	public void testCanModify( )
	{
		assertTrue( mModifier.canModify( null, "" ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.ui.ServerCellModifier#getValue(java.lang.Object, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetValue( )
	{
		when( mViewer.getColumnProperties( ) ).thenReturn( ConfigurationDialog.COLUMN_NAMES );

		final IServerInformation server = new ServerInformation( "address", "user", "password", 2000, false, true );

		assertEquals( "address", mModifier.getValue( server, ConfigurationDialog.COLUMN_NAMES[0] ) );
		assertEquals( "user", mModifier.getValue( server, ConfigurationDialog.COLUMN_NAMES[1] ) );
		assertEquals( "password", mModifier.getValue( server, ConfigurationDialog.COLUMN_NAMES[2] ) );
		assertEquals( "2000", mModifier.getValue( server, ConfigurationDialog.COLUMN_NAMES[3] ) );
		assertEquals( new Boolean( false ), mModifier.getValue( server, ConfigurationDialog.COLUMN_NAMES[4] ) );
		assertEquals( new Boolean( true ), mModifier.getValue( server, ConfigurationDialog.COLUMN_NAMES[5] ) );
		assertEquals( "", mModifier.getValue( server, "BadName" ) );
	}

	/**
	 * Test method for
	 * {@link com.spextreme.jenkins.remote.ui.ServerCellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)}
	 * .
	 */
	@Test
	public void testModify( )
	{
		// This should do nothing.
		mModifier.modify( null, "", "" );
		// This should do nothing.
		mModifier.modify( new Object( ), "", "" );

		final TableItem item1 = mock( TableItem.class );
		final IServerInformation server = new ServerInformation( "http://address.com/jenkins/" );

		final List<IServerInformation> list = new Vector<IServerInformation>( );
		list.add( server );

		when( mConfig.getServers( ) ).thenReturn( new Vector<IServerInformation>( ) ).thenReturn( list );
		when( mViewer.getColumnProperties( ) ).thenReturn( ConfigurationDialog.COLUMN_NAMES );
		when( item1.getData( ) ).thenReturn( server );

		mModifier.modify( item1, ConfigurationDialog.COLUMN_NAMES[0], "http://localhost:8080/jenkins " );
		mModifier.modify( item1, ConfigurationDialog.COLUMN_NAMES[1], "user" );
		mModifier.modify( item1, ConfigurationDialog.COLUMN_NAMES[2], "password" );
		mModifier.modify( item1, ConfigurationDialog.COLUMN_NAMES[3], "10000" );
		mModifier.modify( item1, ConfigurationDialog.COLUMN_NAMES[4], "false" );
		mModifier.modify( item1, ConfigurationDialog.COLUMN_NAMES[5], "true" );

		assertEquals( "http://localhost:8080/jenkins/", server.getAddress( ) );
		assertEquals( "user", server.getUserName( ) );
		assertEquals( "password", server.getPassword( ) );
		assertEquals( 10000, server.getQueryPeriodic( ) );
		assertFalse( server.isAllowIncrease( ) );
		assertTrue( server.isPrimary( ) );

		verify( mViewer, times( 1 ) ).add( any( IConfigurationManager.class ) );
		verify( mViewer, times( 6 ) ).refresh( );
	}
}
