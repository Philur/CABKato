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

package com.spextreme.jenkins.remote;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.model.internal.ConfigurationData;
import com.spextreme.jenkins.remote.model.internal.JobManager;
import com.spextreme.jenkins.remote.ui.JenkinsRemoteTray;
import com.spextreme.jenkins.remote.xml.internal.ConfigurationFileConverter;
import com.spextreme.jenkins.remote.xml.internal.ShowInformationConverter;

/**
 * The Jenkins Remote application will connect to a Jenkins server and report details about the state
 * of the project within Jenkins. The following features are supported
 * <ul>
 * <li>System tray icon</li>
 * <li>Menu showing the overview of each projects state</li>
 * <li>Links to each projects page to get details</li>
 * <li>The sunny/cloudy icons to show the state</li>
 * <li>Configuration of the server address</li>
 * <li>Trigger builds</li>
 * <li>Hide/Show projects</li>
 * <li>Job statistics (Tests, Coverage, etc..)</li>
 * </ul>
 */
public final class JenkinsRemote
{
	/**
	 * The main application of jenkins remote.
	 * 
	 * @param args Command line arguments. None currently supported.
	 */
	public static void main( final String[] args )
	{
		final Display d = new Display( );
		final Shell shell = new Shell( d );

		final IConfigurationManager configManager = loadConfigurationData( );
		final IJobManager jobManager = new JobManager( );
		final JenkinsRemoteTray tray = new JenkinsRemoteTray( shell, d.getSystemTray( ), configManager, jobManager );

		boolean beenRunning = false;

		try
		{
			tray.configureTray( );
			tray.establishAllServers( );

			shell.setBounds( 0, 0, 1, 1 );
			shell.open( );
			shell.setVisible( false );

			tray.scheduleMonitors( );

			beenRunning = true;

			while( !shell.isDisposed( ) )
			{
				if( !d.readAndDispatch( ) )
				{
					d.sleep( );
				}
			}
		}
		catch( final Exception e )
		{
			if( beenRunning )
			{
				MessageDialog.openError( Display.getCurrent( ).getActiveShell( ), "Jenkins Remote Fatal Error",
						"An unexpected error occurred and Jenkins Remote must shutdown.  Sorry for the issue." );
			}
			else
			{
				MessageDialog
						.openError( Display.getCurrent( ).getActiveShell( ), "Jenkins Remote Fatal Error",
								"The System Tray is not supported or couldn't be accessed.  Jenkins Remote will not function in this environment." );
			}
		}
		finally
		{
			// Do nothing.
		}

		tray.dispose( );
		d.dispose( );
	}

	/**
	 * Attempts to save the configuration data to the file system.
	 * 
	 * @param config The {@link IConfigurationManager} item.
	 * @param filename The name of the file to save to.
	 */
	public static void saveConfigurationData( final IConfigurationManager config, final String filename )
	{
		final File configFile = new File( filename );

		final List<ShowInformationConverter> showConverters = new Vector<ShowInformationConverter>( );
		for( final String type : ShowInformationConverter.SHOW_TYPES )
		{
			showConverters.add( new ShowInformationConverter( type ) );
		}

		final ConfigurationFileConverter converter = new ConfigurationFileConverter( showConverters );
		final Element element = converter.toElement( config );

		if( element != null )
		{
			final Document document = new Document( element );

			try
			{
				final XMLOutputter out = new XMLOutputter( Format.getPrettyFormat( ) );
				final FileWriter writer = new FileWriter( configFile );
				out.output( document, writer );
				writer.flush( );
				writer.close( );
			}
			catch( final Exception e )
			{
				// TODO Do something to report this.
			}
		}
	}

	/**
	 * Attempts to load the configuration data from the file system.
	 * 
	 * @return The {@link IConfigurationManager} item.
	 */
	protected static IConfigurationManager loadConfigurationData( )
	{
		IConfigurationManager configData = null;
		final File configFile = new File( IConfigurationManager.CONFIG_FILE );

		final List<ShowInformationConverter> showConverters = new Vector<ShowInformationConverter>( );
		for( final String type : ShowInformationConverter.SHOW_TYPES )
		{
			showConverters.add( new ShowInformationConverter( type ) );
		}

		final ConfigurationFileConverter converter = new ConfigurationFileConverter( showConverters );

		try
		{
			final Document dom = new SAXBuilder( ).build( configFile );
			configData = (IConfigurationManager)converter.fromElement( dom.getRootElement( ) );
		}
		catch( final Exception e )
		{
			// do nothing.
		}

		if( configData == null )
		{
			configData = new ConfigurationData( );
		}

		return configData;
	}

	/**
	 * The constructor. This is hidden to prevent anyone from creating one.
	 */
	private JenkinsRemote( )
	{
		// Do nothing.
	}
}
