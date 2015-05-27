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

import java.util.List;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TrayItem;

import com.spextreme.jenkins.remote.model.IBuild;
import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.ITrayNotification;
import com.spextreme.jenkins.remote.model.internal.JobStatus;
import com.spextreme.jenkins.remote.xml.internal.ShowInformationConverter;

/**
 * The tray notifier is used to display pop-up messages. This may be used to alert the user when
 * things happen.
 */
public class TrayNotifier implements ITrayNotification
{
	/**
	 * The time value to poll at.
	 */
	public static final int			INCREASE_QUERY_TIME_VALUE	= 500;	// Milliseconds
	/**
	 * The shell.
	 */
	private Shell					mShell						= null;
	/**
	 * The tray item.
	 */
	private TrayItem				mTrayItem					= null;
	/**
	 * The configuration items.
	 */
	private IConfigurationManager	mConfigManager				= null;
	/**
	 * The list of job status.
	 */
	private List<JobStatus>			mStatus						= null;

	/**
	 * Creates a notifier for use to display popup notification for the jenkins tray item.
	 * 
	 * @param shell The shell to use.
	 * @param trayItem The Jenkins tray item.
	 * @param configManager Configuration manager instance.
	 */
	public TrayNotifier( final Shell shell, final TrayItem trayItem, final IConfigurationManager configManager )
	{
		mShell = shell;
		mTrayItem = trayItem;
		mConfigManager = configManager;

		mStatus = new Vector<JobStatus>( );
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.ITrayNotification#displayNotification(java.lang.String,
	 *      java.lang.String)
	 * @param title The title of the pop-up.
	 * @param text The text in the pop-up.
	 */
	public void displayNotification( final String title, final String text )
	{
		if( mConfigManager.isVisible( ShowInformationConverter.POPUPS ) )
		{
			final ToolTip tooltip = new ToolTip( mShell, SWT.BALLOON | SWT.ICON_INFORMATION );
			tooltip.setText( title );
			tooltip.setMessage( text );

			mTrayItem.setToolTip( tooltip );
			tooltip.setVisible( true );
		}
	}

	/**
	 * @see com.spextreme.jenkins.remote.model.IUpdatable#updatedData(java.lang.String,
	 *      java.util.List)
	 * @param type The type of update that is occurring (added, updated).
	 * @param jobs The list of jobs.
	 */
	@Override
	public void updatedData( final String type, final List<IJob> jobs )
	{
		for( final IJob job : jobs )
		{
			if( mConfigManager.getJobShowState( job.getName( ) ) )
			{
				JobStatus status = getJobStatus( job.getName( ) );
				if( job.getLastCompletedBuild( ) != null )
				{
					if( status == null )
					{
						status = new JobStatus( job.getName( ), job.getLastCompletedBuild( ).getNumber( ), job
								.getLastCompletedBuild( ).isBuilding( ), false );
						mStatus.add( status );
					}

					if( ( job.getCurrentBuild( ) != null ) && job.getCurrentBuild( ).isBuilding( )
							&& !status.isNotifiedBuilding( ) )
					{
						performNotification( job.getCurrentBuild( ) );
						status.setNotifiedBuilding( true );
					}
					else if( job.getLastCompletedBuild( ).getNumber( ) > status.getBuildNumber( ) )
					{
						performNotification( job.getLastCompletedBuild( ) );
						status.setBuildNumber( job.getLastCompletedBuild( ).getNumber( ) );
						status.setNotifiedBuilding( false );
					}
				}
			}
		}
	}

	/**
	 * This searches the job status queue and gets the job status for the given job name.
	 * 
	 * @param jobName The job name to get status for.
	 * @return The {@link JobStatus} instance or <code>null</code> if one does not yet exist.
	 */
	private JobStatus getJobStatus( final String jobName )
	{
		JobStatus jobStatus = null;

		for( final JobStatus status : mStatus )
		{
			if( status.getJobName( ).equals( jobName ) )
			{
				jobStatus = status;
				break;
			}
		}

		return jobStatus;
	}

	/**
	 * Performs the notification for the given build.
	 * 
	 * @param build The build to notify about.
	 */
	protected void performNotification( final IBuild build )
	{
		if( build != null )
		{
			final StringBuilder title = new StringBuilder( "Build " );
			final StringBuilder body = new StringBuilder( );

			if( build.isBuilding( ) )
			{
				title.append( "Started" );
				body.append( "Building " );
				body.append( build.getDisplayName( ) );
				// if( mConfigManager.getBooleanValue( IConfigurationManager.ALLOW_QUERY_INCREASE )
				// && ( mConfigManager.getQueryPeriod( ) > INCREASE_QUERY_TIME_VALUE ) )
				// {
				// getTimerController( ).restart( INCREASE_QUERY_TIME_VALUE );
				// }
			}
			else
			{
				title.append( "Complete" );
				body.append( build.getDisplayName( ) );
				body.append( " " );
				body.append( build.getResult( ) );

				// if( mConfigManager.getBooleanValue( IConfigurationManager.ALLOW_QUERY_INCREASE )
				// && ( mConfigManager.getQueryPeriod( ) > INCREASE_QUERY_TIME_VALUE ) )
				// {
				// getTimerController( ).restart( mConfigManager.getQueryPeriod( ) );
				// }
			}

			if( Display.getDefault( ) != null )
			{
				Display.getDefault( ).asyncExec( new Runnable( )
				{
					@Override
					public void run( )
					{
						displayNotification( title.toString( ), body.toString( ) );
					}
				} );
			}
		}
	}
}
