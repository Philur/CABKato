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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import com.spextreme.jenkins.remote.Utilities;
import com.spextreme.jenkins.remote.jenkins.JenkinsCLI;
import com.spextreme.jenkins.remote.jenkins.internal.JenkinsJobMonitor;
import com.spextreme.jenkins.remote.model.IArtifact;
import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IHealthReport;
import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.ITestResult;
import com.spextreme.jenkins.remote.model.ITrayNotification;
import com.spextreme.jenkins.remote.model.IUpdatable;
import com.spextreme.jenkins.remote.ui.menu.action.AboutAction;
import com.spextreme.jenkins.remote.ui.menu.action.LaunchApplicationAction;
import com.spextreme.jenkins.remote.ui.menu.action.OpenConfigurationDialogAction;
import com.spextreme.jenkins.remote.ui.menu.action.QuitAction;
import com.spextreme.jenkins.remote.ui.menu.action.RefreshAction;
import com.spextreme.jenkins.remote.ui.menu.action.RestartServerAction;
import com.spextreme.jenkins.remote.xml.internal.ShowInformationConverter;

/**
 * The Jenkins Remote tray is what resided in the OS Tray to show the status of jenkins and provide
 * the menu items.
 */
public final class JenkinsRemoteTray implements IUpdatable
{
	/**
	 * Gets the image matching the key or <code>null</code> if unable to find the key or not yet
	 * initialized.
	 * 
	 * @param key The key to look up.
	 * @return The image or <code>null</code>.
	 */
	public static Image getImage( final String key )
	{
		Image image = null;

		if( mImageSet != null )
		{
			image = mImageSet.get( key );
		}

		return image;
	}

	/**
	 * The configuration manager instance.
	 */
	private IConfigurationManager			mConfigManager	= null;
	/**
	 * The jenkins CLI interface.
	 */
	private JenkinsCLI						mJenkinsCLI		= null;
	/**
	 * Holds the set of images.
	 */
	private static Hashtable<String, Image>	mImageSet		= null;
	/**
	 * The job manager instance.
	 */
	private IJobManager						mJobManager		= null;
	/**
	 * The shell this tray item is attached to.
	 */
	private Shell							mShell			= null;

	/**
	 * The system tray.
	 */
	private Tray							mTray			= null;
	/**
	 * The tray item instance.
	 */
	private TrayItem						mTrayItem		= null;

	/**
	 * The tray notifier service.
	 */
	private ITrayNotification				mTrayNotifier	= null;

	/**
	 * The list of job monitors.
	 */
	private List<JenkinsJobMonitor>			mMonitors		= null;

	/**
	 * The jenkins remote constructor.
	 * 
	 * @param shell The shell to use.
	 * @param tray The tray to use.
	 * @param configManager The configuration manager instance.
	 * @param jobManager The job manager to use. This can not be <code>null</code>.
	 */
	public JenkinsRemoteTray( final Shell shell, final Tray tray, final IConfigurationManager configManager,
			final IJobManager jobManager )
	{
		mTray = tray;
		mShell = shell;
		mConfigManager = configManager;
		mJenkinsCLI = new JenkinsCLI( );
		mJobManager = jobManager;

		mImageSet = new Hashtable<String, Image>( );
		mImageSet.put( "blue", new Image( Display.getCurrent( ), "images/16x16/blue.gif" ) );
		mImageSet.put( "red", new Image( Display.getCurrent( ), "images/16x16/red.gif" ) );
		mImageSet.put( "yellow", new Image( Display.getCurrent( ), "images/16x16/yellow.gif" ) );
		mImageSet.put( "disabled", new Image( Display.getCurrent( ), "images/16x16/gray.gif" ) );
		mImageSet.put( "gray", new Image( Display.getCurrent( ), "images/16x16/gray.gif" ) );
		mImageSet.put( "00", new Image( Display.getCurrent( ), "images/16x16/health-00to19.gif" ) );
		mImageSet.put( "20", new Image( Display.getCurrent( ), "images/16x16/health-20to39.gif" ) );
		mImageSet.put( "40", new Image( Display.getCurrent( ), "images/16x16/health-40to59.gif" ) );
		mImageSet.put( "60", new Image( Display.getCurrent( ), "images/16x16/health-60to79.gif" ) );
		mImageSet.put( "80", new Image( Display.getCurrent( ), "images/16x16/health-80plus.gif" ) );
		mImageSet.put( "build", new Image( Display.getCurrent( ), "images/16x16/clock.gif" ) );
		mImageSet.put( "clipboard", new Image( Display.getCurrent( ), "images/16x16/clipboard.gif" ) );
		mImageSet.put( "package", new Image( Display.getCurrent( ), "images/16x16/package.gif" ) );
		mImageSet.put( "hourglass", new Image( Display.getCurrent( ), "images/16x16/hourglass.gif" ) );
		mImageSet.put( "save", new Image( Display.getCurrent( ), "images/16x16/save.gif" ) );
		mImageSet.put( "warning", new Image( Display.getCurrent( ), "images/16x16/warning.gif" ) );
		mImageSet.put( "jenkins", new Image( Display.getCurrent( ), "images/16x16/jenkins.gif" ) );
		mImageSet.put( "jenkins.failure", new Image( Display.getCurrent( ), "images/16x16/jenkins.red.gif" ) );
		mImageSet.put( "checkbox.checked", new Image( Display.getCurrent( ), "images/16x16/checkbox.checked.gif" ) );
		mImageSet.put( "checkbox.unchecked", new Image( Display.getCurrent( ), "images/16x16/checkbox.unchecked.gif" ) );
	}

	/**
	 * Requests each job be stopped. There is no guarantee that they will however.
	 */
	public void cancelMonitors( )
	{
		for( final JenkinsJobMonitor monitor : getServerMonitors( ) )
		{
			monitor.cancel( );
		}
	}

	/**
	 * Constructs the tray.
	 * 
	 * @throws Exception Thrown if the tray can not be accessed.
	 */
	public void configureTray( ) throws Exception
	{
		if( !isTraySupported( ) )
		{
			throw new Exception( "Tray not supported" );
		}

		getTrayItem( ).setToolTipText( "Jenkins Remote" );
		mTrayNotifier = new TrayNotifier( mShell, getTrayItem( ), getConfigurationManager( ) );
		getJobManager( ).addListener( mTrayNotifier );
		getJobManager( ).addListener( this );

		final Menu menu = new Menu( mShell, SWT.POP_UP );

		getTrayItem( ).addListener( SWT.MenuDetect, new Listener( )
		{
			public void handleEvent( final Event event )
			{
				// Remove everything...
				for( final MenuItem item : menu.getItems( ) )
				{
					item.dispose( );
				}

				buildProjectMenu( menu );
				new MenuItem( menu, SWT.SEPARATOR );

				buildServersMenu( menu );
				new MenuItem( menu, SWT.SEPARATOR );

				buildJenkinsRemoteItems( menu, getTrayItem( ) );

				menu.setVisible( true );
			}
		} );

		getTrayItem( ).setImage( mImageSet.get( "jenkins" ) );
	}

	/**
	 * Disposes of any allocated resources.
	 */
	public void dispose( )
	{
		getJobManager( ).removeListener( getTrayNotifier( ) );
		getJobManager( ).removeListener( this );

		// Release the images now.
		for( final Image img : mImageSet.values( ) )
		{
			img.dispose( );
		}
	}

	/**
	 * Sets up the server monitoring instances. This should only be called at the beginning or after
	 * a {@link IServerInformation} instance updated since it will cancel all existing monitors and
	 * clear them out. Then it recreates them from what is defined in the configuration data.
	 */
	public void establishAllServers( )
	{
		// Clear out so any existing ones are removed.
		cancelMonitors( );
		getServerMonitors( ).clear( );

		for( final IServerInformation server : getConfigurationManager( ).getServers( ) )
		{
			getServerMonitors( ).add( new JenkinsJobMonitor( getJobManager( ), server ) );
		}
	}

	/**
	 * Gets the configuration manager.
	 * 
	 * @return The configuration manager instance.
	 */
	public IConfigurationManager getConfigurationManager( )
	{
		return mConfigManager;
	}

	/**
	 * Gets the job manager.
	 * 
	 * @return The {@link IJobManager}.
	 */
	public IJobManager getJobManager( )
	{
		return mJobManager;
	}

	/**
	 * Gets the jobs (threads) that perform the monitoring of the Jenkins Servers.
	 * 
	 * @return The list of {@link JenkinsJobMonitor} instances.
	 */
	public List<JenkinsJobMonitor> getServerMonitors( )
	{
		if( mMonitors == null )
		{
			mMonitors = new Vector<JenkinsJobMonitor>( );
		}

		return mMonitors;
	}

	/**
	 * Gets the tray item instance.
	 * 
	 * @return The tray item instance.
	 */
	public TrayItem getTrayItem( )
	{
		if( mTrayItem == null )
		{
			mTrayItem = new TrayItem( mTray, SWT.NONE );
		}

		return mTrayItem;
	}

	/**
	 * Gets the tray notifier service.
	 * 
	 * @return The tray notifier instance.
	 */
	public ITrayNotification getTrayNotifier( )
	{
		return mTrayNotifier;
	}

	/**
	 * Checks to see that the tray is not <code>null</code>. If it is <code>null</code> then it must
	 * not be supported.
	 * 
	 * @return <code>true</code> if the system tray is supported. Otherwise <code>false</code> will
	 *         be returned.
	 */
	public boolean isTraySupported( )
	{
		return mTray != null;
	}

	/**
	 * Cancels all the current job monitors. Then revalidates them to make sure they are still
	 * properly aligned with the configuration. They are then recreated and scheduled.
	 */
	public void restartMonitors( )
	{
		cancelMonitors( );

		establishAllServers( );

		scheduleMonitors( );
	}

	/**
	 * Schedules each job to begin executing.
	 */
	public void scheduleMonitors( )
	{
		for( final JenkinsJobMonitor monitor : getServerMonitors( ) )
		{
			monitor.schedule( );
		}
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IUpdatable#updatedData(java.lang.String,
	 *      java.util.List)
	 * @param type The type of update.
	 * @param jobs The list off jobs being updated.
	 */
	@Override
	public void updatedData( final String type, final List<IJob> jobs )
	{
		String result = "jenkins";

		for( final IJob job : jobs )
		{
			if( getConfigurationManager( ).getJobShowState( job.getName( ) ) && ( job.getLastCompletedBuild( ) != null )
					&& job.getLastCompletedBuild( ).getResult( ).equalsIgnoreCase( "failure" ) )
			{
				result = "jenkins.failure";
			}
		}

		final String key = result;
		Display.getDefault( ).asyncExec( new Runnable( )
		{

			@Override
			public void run( )
			{
				getTrayItem( ).setImage( mImageSet.get( key ) );
			}
		} );
	}

	/**
	 * Builds the menu that allows the archives to be downloaded.
	 * 
	 * @param job The job to use.
	 * @param menu The parent menu.
	 */
	private void buildDownloadMenu( final IJob job, final Menu menu )
	{
		if( getConfigurationManager( ).isVisible( ShowInformationConverter.ARCHIVES ) )
		{
			MenuItem menuItem = null;
			MenuItem childItem = null;

			if( job.getLastCompletedBuild( ).getArtifacts( ).size( ) == 1 )
			{
				childItem = new MenuItem( menu, SWT.PUSH );
				final IArtifact artifact = job.getLastCompletedBuild( ).getArtifacts( ).get( 0 );
				childItem.setImage( mImageSet.get( "package" ) );
				childItem.setText( artifact.getDisplayName( ) );
				childItem.addSelectionListener( new LaunchApplicationAction( job.getURL( ) + "lastSuccessfulBuild/artifact/"
						+ artifact.getRelativePath( ) ) );
			}
			else if( job.getLastCompletedBuild( ).getArtifacts( ).size( ) > 1 )
			{
				menuItem = new MenuItem( menu, SWT.CASCADE );
				menuItem.setText( "Archives" );
				menuItem.setImage( mImageSet.get( "package" ) );

				final Menu downloadmenu = new Menu( mShell, SWT.DROP_DOWN );
				menuItem.setMenu( downloadmenu );

				for( final IArtifact artifact : job.getLastCompletedBuild( ).getArtifacts( ) )
				{
					String name = null;
					if( artifact.getDisplayName( ) != null )
					{
						name = artifact.getDisplayName( );
					}
					else if( artifact.getRelativePath( ) != null )
					{
						final int index = artifact.getRelativePath( ).lastIndexOf( '/' );

						if( index > -1 )
						{
							name = artifact.getRelativePath( ).substring( index + 1 );
						}
					}
					if( name != null )
					{
						childItem = new MenuItem( downloadmenu, SWT.PUSH );
						childItem.setText( name );
						childItem.addSelectionListener( new LaunchApplicationAction( job.getURL( )
								+ "lastSuccessfulBuild/artifact/" + artifact.getRelativePath( ) ) );
					}
				}
			}
		}
	}

	/**
	 * Builds the duration menu.
	 * 
	 * @param job The job.
	 * @param submenu The parent menu.
	 */
	private void buildDurationMenu( final IJob job, final Menu submenu )
	{
		if( getConfigurationManager( ).isVisible( ShowInformationConverter.DURATION ) )
		{
			final MenuItem childItem = new MenuItem( submenu, SWT.PUSH );
			childItem.setText( "Build Duration: "
					+ Utilities.formatBuildDuration( job.getLastCompletedBuild( ).getDuration( ) ) );
			childItem.setImage( mImageSet.get( "hourglass" ) );
		}
	}

	/**
	 * Builds the menu that shows the health of a project.
	 * 
	 * @param job The job to use for building the menu.
	 * @param menu The menu to add to.
	 */
	private void buildHealthMenu( final IJob job, final Menu menu )
	{
		if( getConfigurationManager( ).isVisible( ShowInformationConverter.HEALTH_REPORTS ) )
		{
			MenuItem menuItem = null;
			MenuItem childItem = null;

			menuItem = new MenuItem( menu, SWT.CASCADE );
			menuItem.setText( "Build Health" );
			menuItem.setImage( getHealthImage( job.getHealthReports( ) ) );

			final Menu healthmenu = new Menu( mShell, SWT.DROP_DOWN );
			menuItem.setMenu( healthmenu );

			for( final IHealthReport healthReport : job.getHealthReports( ) )
			{
				childItem = new MenuItem( healthmenu, SWT.PUSH );
				childItem.setText( healthReport.getDescription( ) + "\t" + healthReport.getScore( ) );
				childItem.setImage( getHealthImage( healthReport.getScore( ) ) );
			}
		}
	}

	/**
	 * Builds the Jenkins Remote specific menu items.
	 * 
	 * @param menu The menu to add the items to.
	 * @param trayItem The tray item we are part of.
	 */
	private void buildJenkinsRemoteItems( final Menu menu, final TrayItem trayItem )
	{
		MenuItem menuItem = new MenuItem( menu, SWT.PUSH );
		menuItem.setText( "Configuration..." );
		menuItem.addSelectionListener( new OpenConfigurationDialogAction( mShell, this, getConfigurationManager( ),
				mJobManager ) );

		menuItem = new MenuItem( menu, SWT.PUSH );
		menuItem.setText( "About" );
		menuItem.addSelectionListener( new AboutAction( getTrayNotifier( ) ) );

		menuItem = new MenuItem( menu, SWT.PUSH );
		menuItem.setText( "Exit" );
		menuItem.addSelectionListener( new QuitAction( mShell ) );
	}

	/**
	 * Builds the menu item that holds the server commands or the submenu server if more the one
	 * server is setup.
	 * 
	 * @param menu The menu to add the items to.
	 */
	private void buildServersMenu( final Menu menu )
	{
		MenuItem menuItem = null;

		menuItem = new MenuItem( menu, SWT.PUSH );
		menuItem.setText( "Refresh" );
		menuItem.addSelectionListener( new RefreshAction( getServerMonitors( ), getJobManager( ) ) );

		if( getConfigurationManager( ).getServers( ).size( ) < 2 )
		{
			menuItem = new MenuItem( menu, SWT.PUSH );
			menuItem.setText( "Jenkins Server" );
			menuItem.addSelectionListener( new LaunchApplicationAction( getConfigurationManager( ).getPrimaryServer( )
					.getAddress( ) ) );

			if( getConfigurationManager( ).isVisible( ShowInformationConverter.ADMIN ) )
			{
				menuItem = new MenuItem( menu, SWT.PUSH );
				menuItem.setText( "Restart Server" );
				menuItem.addSelectionListener( new RestartServerAction( getConfigurationManager( ).getPrimaryServer( ),
						mJenkinsCLI ) );
			}
		}
		else
		// multiple servers exist
		{
			for( final IServerInformation server : getConfigurationManager( ).getServers( ) )
			{
				menuItem = new MenuItem( menu, SWT.CASCADE );
				menuItem.setText( server.getAddress( ) );

				final Menu submenu = new Menu( mShell, SWT.DROP_DOWN );
				menuItem.setMenu( submenu );

				final MenuItem childItem = new MenuItem( submenu, SWT.PUSH );
				childItem.setText( "Open Server" );
				childItem.addSelectionListener( new LaunchApplicationAction( server.getAddress( ) ) );

				if( getConfigurationManager( ).isVisible( ShowInformationConverter.ADMIN ) )
				{
					menuItem = new MenuItem( submenu, SWT.PUSH );
					menuItem.setText( "Restart Server" );
					menuItem.addSelectionListener( new RestartServerAction( server, mJenkinsCLI ) );
				}

			}
		}
	}

	/**
	 * Builds the test data menu item.
	 * 
	 * @param job The job.
	 * @param menu The parent menu.
	 */
	private void buildTestDataMenu( final IJob job, final Menu menu )
	{
		if( getConfigurationManager( ).isVisible( ShowInformationConverter.TESTS ) )
		{
			final ITestResult testResult = job.getLastCompletedBuild( ).getTestResults( );
			if( testResult != null )
			{
				final MenuItem childItem = new MenuItem( menu, SWT.PUSH );
				childItem.setText( "Tests(F/S/T): " + testResult.getFailedCount( ) + "/" + testResult.getSkipCount( ) + "/"
						+ testResult.getTotalCount( ) );
				childItem.setImage( mImageSet.get( "clipboard" ) );
			}
		}
	}

	/**
	 * Creates the menu when a build has not occurred for a job.
	 * 
	 * @param menuItem The menu item.
	 */
	private void createDisabledBuildJob( final MenuItem menuItem )
	{
		menuItem.setImage( mImageSet.get( "disabled" ) );

		final Menu submenu = new Menu( mShell, SWT.DROP_DOWN );
		menuItem.setMenu( submenu );

		// Build ID
		final MenuItem childItem = new MenuItem( submenu, SWT.PUSH );
		childItem.setText( "Job Never Built" );
		childItem.setImage( mImageSet.get( "disabled" ) );
		childItem.setEnabled( false );
	}

	/**
	 * Gets the proper health images based on the value.
	 * 
	 * @param value The value to get the health image for.
	 * @return The health image.
	 */
	private Image getHealthImage( final int value )
	{
		Image img = null;

		if( value < 20 )
		{
			img = mImageSet.get( "00" );
		}
		else if( value < 40 )
		{
			img = mImageSet.get( "20" );
		}
		else if( value < 60 )
		{
			img = mImageSet.get( "40" );
		}
		else if( value < 80 )
		{
			img = mImageSet.get( "60" );
		}
		else
		{
			img = mImageSet.get( "80" );
		}

		return img;
	}

	/**
	 * Gets the proper health images based on the collection. It returns the worst case.
	 * 
	 * @param healthReports The list of health reports to get the health image for.
	 * @return The health image.
	 */
	private Image getHealthImage( final List<IHealthReport> healthReports )
	{
		int imageChoice = 100;

		for( final IHealthReport healthReport : healthReports )
		{
			if( imageChoice > healthReport.getScore( ) )
			{
				imageChoice = healthReport.getScore( );
			}
		}

		return getHealthImage( imageChoice );
	}

	/**
	 * Builds the project listing menu.
	 * 
	 * @param menu The menu to add the items to.
	 */
	protected synchronized void buildProjectMenu( final Menu menu )
	{
		MenuItem menuItem = null;

		if( getJobManager( ).getJobs( ).size( ) < 1 )
		{
			menuItem = new MenuItem( menu, SWT.PUSH );
			menuItem.setText( "No Connection or No Jobs Found" );
			menuItem.setImage( mImageSet.get( "warning" ) );
			menuItem.setEnabled( false );
		}
		else
		{
			try
			{
				for( final IJob job : getJobManager( ).getJobs( ) )
				{
					if( getConfigurationManager( ).getJobShowState( job.getName( ) ) )
					{
						MenuItem childItem = null;

						menuItem = new MenuItem( menu, SWT.CASCADE );
						menuItem.setText( job.getName( ) );

						if( job.getLastCompletedBuild( ) != null )
						{
							menuItem.setImage( getHealthImage( job.getHealthReports( ) ) );

							final Menu submenu = new Menu( mShell, SWT.DROP_DOWN );
							menuItem.setMenu( submenu );

							// Build ID
							childItem = new MenuItem( submenu, SWT.PUSH );
							childItem.setText( "Build #"
									+ Integer.toString( job.getLastCompletedBuild( ).getNumber( ) )
									+ new SimpleDateFormat( " MMM d, yyyy h:mm:ss a" ).format( new Date( job
											.getLastCompletedBuild( ).getTimestamp( ) ) ) );
							childItem.setImage( mImageSet.get( job.getColor( ) ) );
							childItem.addSelectionListener( new LaunchApplicationAction( job.getLastCompletedBuild( )
									.getURL( ) ) );

							// Health/Status
							buildHealthMenu( job, submenu );

							// Build Duration
							buildDurationMenu( job, submenu );

							// Test Data
							buildTestDataMenu( job, submenu );

							// Download Archive
							buildDownloadMenu( job, submenu );

							// separator
							childItem = new MenuItem( submenu, SWT.SEPARATOR );

							if( getConfigurationManager( ).getServers( ).size( ) > 0 )
							{
								// Build Now
								childItem = new MenuItem( submenu, SWT.PUSH );
								childItem.setText( "Build Now" );
								childItem.addSelectionListener( new LaunchApplicationAction( job.getServer( ).getAddress( )
										+ "job/" + job.getName( ) + "/build?delay=0sec" ) );
								childItem.setImage( mImageSet.get( "build" ) );

								// Enable/Disable build
								// childItem = new MenuItem( submenu, SWT.PUSH );
								// childItem.setText( "Enable Build" );
								// // TODO This should change the text to know which it is...if not
								// possible
								// // have each.
								//
								// // Keep build
								// childItem = new MenuItem( submenu, SWT.PUSH );
								// childItem.setText( "Keep Build" );
								// childItem.setImage( mImageSet.get( "save" ) );
								// // TODO add support to keep build
							}
						}
						else
						{
							createDisabledBuildJob( menuItem );
						}
					}
				}
			}
			catch( final Exception e )
			{
				// Here to catch an concurrent modification exceptions.
				// We ignore them since the worst that will happen is a incomplete list.
				// One day we should probably fix this properly.
			}
		}
	}
}
