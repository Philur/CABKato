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

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.spextreme.jenkins.remote.JenkinsRemote;
import com.spextreme.jenkins.remote.jenkins.internal.JenkinsRemoteXMLProcessing;
import com.spextreme.jenkins.remote.model.IConfigurationManager;
import com.spextreme.jenkins.remote.model.IJob;
import com.spextreme.jenkins.remote.model.IJobManager;
import com.spextreme.jenkins.remote.model.IServerInformation;
import com.spextreme.jenkins.remote.model.internal.ConfigurationData;
import com.spextreme.jenkins.remote.model.internal.JobManager;
import com.spextreme.jenkins.remote.model.internal.ServerInformation;
import com.spextreme.jenkins.remote.model.provider.JobContentProvider;
import com.spextreme.jenkins.remote.model.provider.JobLabelProvider;
import com.spextreme.jenkins.remote.model.provider.ServerContentProvider;
import com.spextreme.jenkins.remote.model.provider.ServerLabelProvider;
import com.spextreme.jenkins.remote.xml.internal.ShowInformationConverter;

/**
 * Constructs the configuration mDialog.
 */
public final class ConfigurationDialog extends TitleAreaDialog
{
	/**
	 * The list of columns for the table.
	 */
	public static final String[]	COLUMN_NAMES				= new String[] { "Address",
			"User Name",
			"Password",
			"Query Rate",
			"Allow Increase",
			"Is Primary"										};

	/**
	 * The hide artifacts checkbox.
	 */
	private Button					mCheckboxShowArtifacts		= null;
	/**
	 * The hide duration checkbox.
	 */
	private Button					mCheckboxShowDuration		= null;
	/**
	 * The hide admin checkbox.
	 */
	private Button					mCheckboxShowAdmin			= null;
	/**
	 * The hide/show pop-ups checkbox.
	 */
	private Button					mCheckboxShowPopups			= null;
	/**
	 * The hide health reports checkbox.
	 */
	private Button					mCheckboxShowHealthReports	= null;
	/**
	 * The hide test data checkbox.
	 */
	private Button					mCheckboxShowTestData		= null;
	/**
	 * The configuration data.
	 */
	private IConfigurationManager	mConfigData					= null;
	/**
	 * The job manager instance.
	 */
	private IJobManager				mJobManager					= null;
	/**
	 * The table mServerTableViewer instance for the list of jobs.
	 */
	private TableViewer				mJobsTableViewer			= null;
	/**
	 * The table mServerTableViewer instance for the list of servers.
	 */
	private TableViewer				mServerTableViewer			= null;
	/**
	 * The content provider for the servers.
	 */
	private ServerContentProvider	mContentProvider			= null;

	/**
	 * Add image.
	 */
	private Image					mAddImage					= null;
	/**
	 * Remove image.
	 */
	private Image					mRemoveImage				= null;

	/**
	 * Constructs the mDialog.
	 * 
	 * @param shell The shell this mDialog is part of.
	 * @param configData The configuration data for this dialog.
	 * @param jobManager The job manager instance.
	 */
	public ConfigurationDialog( final Shell shell, final IConfigurationManager configData, final IJobManager jobManager )
	{
		super( shell );

		mConfigData = configData;
		mJobManager = jobManager;
	}

	/**
	 * @see org.eclipse.jface.dialogs.TrayDialog#close()
	 * @return The result of the close.
	 */
	@Override
	public boolean close( )
	{
		final boolean result = super.close( );

		if( mAddImage != null )
		{
			mAddImage.dispose( );
			mAddImage = null;
		}

		if( mRemoveImage != null )
		{
			mRemoveImage.dispose( );
			mRemoveImage = null;
		}

		return result;
	}

	/**
	 * Gets the configuration data item. This will never be <code>null</code>.
	 * 
	 * @return The {@link ConfigurationData} item.
	 */
	public IConfigurationManager getConfigurationData( )
	{
		if( mConfigData == null )
		{
			mConfigData = new ConfigurationData( );
		}

		return mConfigData;
	}

	/**
	 * Gets the content provider for the servers.
	 * 
	 * @return The {@link ServerContentProvider} instance.
	 */
	public ServerContentProvider getContentProvider( )
	{
		if( mContentProvider == null )
		{
			mContentProvider = new ServerContentProvider( );
		}

		return mContentProvider;
	}

	/**
	 * Establishes the cell editors for the table viewer.
	 * 
	 * @param viewer The viewer.
	 */
	private void createCellEditor( final TableViewer viewer )
	{
		// Create the cell editors
		final CellEditor[] editors = new CellEditor[COLUMN_NAMES.length];

		// Address
		editors[0] = new TextCellEditor( viewer.getTable( ) );
		// User Name
		editors[1] = new TextCellEditor( viewer.getTable( ) );
		// Password
		editors[2] = new TextCellEditor( viewer.getTable( ) );
		// Query Periodic
		editors[3] = new TextCellEditor( viewer.getTable( ) );
		( (Text)editors[3].getControl( ) ).addVerifyListener( new VerifyListener( )
		{
			public void verifyText( final VerifyEvent e )
			{
				e.doit = e.text.matches( "[\\-0-9]*" );
			}
		} );
		// Allow Increase
		editors[4] = new CheckboxCellEditor( viewer.getTable( ) );
		// Is Primary
		editors[5] = new CheckboxCellEditor( viewer.getTable( ) );

		viewer.setCellEditors( editors );
		viewer.setCellModifier( new ServerCellModifier( viewer, getConfigurationData( ) ) );
	}

	/**
	 * Creates the admin area.
	 * 
	 * @param parent The parent to draw onto.
	 */
	private void createCompositeAdmin( final Composite parent )
	{
		final GridData gridData4 = new GridData( );
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.grabExcessHorizontalSpace = false;
		gridData4.grabExcessVerticalSpace = true;
		gridData4.verticalAlignment = GridData.FILL;

		final Composite compositeAdmin = new Composite( parent, SWT.NONE );
		compositeAdmin.setLayout( new GridLayout( ) );
		compositeAdmin.setLayoutData( gridData4 );

		final Label labelVisibiltiy = new Label( compositeAdmin, SWT.NONE );
		labelVisibiltiy.setText( "Item Visibility" );

		mCheckboxShowTestData = new Button( compositeAdmin, SWT.CHECK );
		mCheckboxShowTestData.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_BOTH ) );
		mCheckboxShowTestData.setText( "Test Data" );
		mCheckboxShowTestData
				.setToolTipText( "When checked, this will prevent the test data from being displayed in the job menus." );
		mCheckboxShowTestData.setSelection( getConfigurationData( ).isVisible( ShowInformationConverter.TESTS ) );

		mCheckboxShowHealthReports = new Button( compositeAdmin, SWT.CHECK );
		mCheckboxShowHealthReports.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_BOTH ) );
		mCheckboxShowHealthReports.setText( "Health Reports" );
		mCheckboxShowHealthReports
				.setSelection( getConfigurationData( ).isVisible( ShowInformationConverter.HEALTH_REPORTS ) );
		mCheckboxShowHealthReports
				.setToolTipText( "When checked, this will prevent the health reports submenu from being displayed." );

		mCheckboxShowArtifacts = new Button( compositeAdmin, SWT.CHECK );
		mCheckboxShowArtifacts.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_BOTH ) );
		mCheckboxShowArtifacts
				.setToolTipText( "When checked, this will prevent the artifacts from being displayed in the job menus." );
		mCheckboxShowArtifacts.setText( "Artifacts" );
		mCheckboxShowArtifacts.setSelection( getConfigurationData( ).isVisible( ShowInformationConverter.ARCHIVES ) );

		mCheckboxShowDuration = new Button( compositeAdmin, SWT.CHECK );
		mCheckboxShowDuration.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_BOTH ) );
		mCheckboxShowDuration
				.setToolTipText( "When checked, this will prevent the duration from being displayed in the job menus." );
		mCheckboxShowDuration.setText( "Duration" );
		mCheckboxShowDuration.setSelection( getConfigurationData( ).isVisible( ShowInformationConverter.DURATION ) );

		mCheckboxShowAdmin = new Button( compositeAdmin, SWT.CHECK );
		mCheckboxShowAdmin.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_BOTH ) );
		mCheckboxShowAdmin.setToolTipText( "When checked, this will show server administrative commands like Reboot" );
		mCheckboxShowAdmin.setText( "Admin" );
		mCheckboxShowAdmin.setSelection( getConfigurationData( ).isVisible( ShowInformationConverter.ADMIN ) );

		mCheckboxShowPopups = new Button( compositeAdmin, SWT.CHECK );
		mCheckboxShowPopups.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.FILL_BOTH ) );
		mCheckboxShowPopups.setToolTipText( "When checked, this will show popups whenever a build begins and ends." );
		mCheckboxShowPopups.setText( "Popups" );
		mCheckboxShowPopups.setSelection( getConfigurationData( ).isVisible( ShowInformationConverter.POPUPS ) );

	}

	/**
	 * Creates the add/remove button bar for the top server table.
	 * 
	 * @param parent The composite to draw onto.
	 */
	private void createCompositeTopButtons( final Composite parent )
	{
		final GridData gridData7 = new GridData( );
		gridData7.grabExcessHorizontalSpace = true;
		gridData7.verticalAlignment = GridData.CENTER;
		gridData7.horizontalAlignment = GridData.END;
		final GridData gridData3 = new GridData( );
		gridData3.horizontalAlignment = GridData.END;
		gridData3.verticalAlignment = GridData.CENTER;
		final GridLayout gridLayout2 = new GridLayout( );
		gridLayout2.numColumns = 2;
		final GridData gridData2 = new GridData( );
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.FILL;
		final Composite compositeTopButtons = new Composite( parent, SWT.NONE );
		compositeTopButtons.setLayoutData( gridData2 );
		compositeTopButtons.setLayout( gridLayout2 );

		final Button buttonAdd = new Button( compositeTopButtons, SWT.NONE );
		buttonAdd.setImage( mAddImage );
		buttonAdd.setToolTipText( "Addes a new row to the table" );
		buttonAdd.setLayoutData( gridData7 );
		buttonAdd.addSelectionListener( new SelectionAdapter( )
		{
			@Override
			public void widgetSelected( final SelectionEvent e )
			{
				final IServerInformation newServer = new ServerInformation( "http://" );
				getContentProvider( ).addServer( newServer );
				mServerTableViewer.refresh( );
			}
		} );

		final Button buttonRemove = new Button( compositeTopButtons, SWT.NONE );
		buttonRemove.setImage( mRemoveImage );
		buttonRemove.setToolTipText( "Removes the selected row" );
		buttonRemove.setEnabled( false );
		buttonRemove.setLayoutData( gridData3 );
		buttonRemove.addSelectionListener( new SelectionAdapter( )
		{
			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * @param e The selection event.
			 */
			@Override
			public void widgetSelected( final SelectionEvent e )
			{
				if( !mServerTableViewer.getSelection( ).isEmpty( ) )
				{
					final IStructuredSelection selection = (IStructuredSelection)mServerTableViewer.getSelection( );

					final Iterator<?> intr = selection.iterator( );

					while( intr.hasNext( ) )
					{
						final Object obj = intr.next( );
						if( obj instanceof IServerInformation )
						{
							final IServerInformation server = (IServerInformation)obj;

							if( getContentProvider( ).getServers( ).size( ) <= 1 )
							{
								MessageDialog.openError( parent.getShell( ), "Unable to Delete",
										"At least one item must remain.  Unable to delete the last item." );
								buttonRemove.setEnabled( false );
								break;
							}
							else
							{
								getContentProvider( ).getServers( ).remove( server );
							}
						}
					}

					mServerTableViewer.refresh( );
				}

			}
		} );

		mServerTableViewer.addSelectionChangedListener( new ISelectionChangedListener( )
		{
			@Override
			public void selectionChanged( final SelectionChangedEvent event )
			{
				if( event.getSelection( ).isEmpty( ) || ( getContentProvider( ).getServers( ).size( ) <= 1 ) )
				{
					buttonRemove.setEnabled( false );
				}
				else
				{
					buttonRemove.setEnabled( true );
				}
			}
		} );

	}

	/**
	 * Creates the section allows the configuration of what items to show and hide.
	 * 
	 * @param parent The parent composite.
	 */
	private void createHideShowSection( final Composite parent )
	{
		final GridData gridData5 = new GridData( );
		gridData5.horizontalAlignment = GridData.FILL;
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.grabExcessVerticalSpace = true;
		gridData5.verticalAlignment = GridData.FILL;
		final GridLayout gridLayout1 = new GridLayout( );
		gridLayout1.numColumns = 3;

		final Composite compositeBottom = new Composite( parent, SWT.NONE );
		createCompositeAdmin( compositeBottom );
		compositeBottom.setLayout( gridLayout1 );

		mJobsTableViewer = new TableViewer( compositeBottom, SWT.CHECK | SWT.BORDER );
		mJobsTableViewer.getControl( ).setLayoutData( gridData5 );
		mJobsTableViewer.setContentProvider( new JobContentProvider( ) );
		mJobsTableViewer.setLabelProvider( new JobLabelProvider( ) );
		mJobsTableViewer.setSorter( new ViewerSorter( ) );
		mJobsTableViewer.setInput( mJobManager );

		for( final TableItem item : mJobsTableViewer.getTable( ).getItems( ) )
		{
			item.setChecked( getConfigurationData( ).getJobShowState( item.getText( ) ) );
		}

		createTableViewerButtonSet( compositeBottom );
	}

	/**
	 * Creates the section that defines items used to access and control jenkins.
	 * 
	 * @param parent The parent composite.
	 */
	private void createJenkinsSection( final Composite parent )
	{
		final GridLayout gridLayout = new GridLayout( );
		gridLayout.numColumns = 1;
		final GridData gridData1 = new GridData( );
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;
		gridData1.verticalAlignment = GridData.FILL;
		gridData1.grabExcessVerticalSpace = true;

		final Composite compositeTop = new Composite( parent, SWT.NONE );
		compositeTop.setLayout( gridLayout );

		mServerTableViewer = new TableViewer( compositeTop, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER );
		mServerTableViewer.setLabelProvider( new ServerLabelProvider( ) );
		mServerTableViewer.setContentProvider( getContentProvider( ) );
		mServerTableViewer.setSorter( new ViewerSorter( ) );
		mServerTableViewer.getControl( ).setLayoutData( gridData1 );
		createTableColumns( mServerTableViewer );
		createCellEditor( mServerTableViewer );

		mServerTableViewer.setInput( mConfigData.getServers( ) );
		mServerTableViewer.getTable( ).pack( );

		final TableViewerFocusCellManager focusManager = new TableViewerFocusCellManager( mServerTableViewer,
				new FocusCellOwnerDrawHighlighter( mServerTableViewer ) );

		final ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy( mServerTableViewer )
		{
			@Override
			protected boolean isEditorActivationEvent( final ColumnViewerEditorActivationEvent event )
			{
				final boolean result = ( event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL )
						|| ( event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION )
						|| ( event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION )
						|| ( ( event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED ) && ( event.keyCode == SWT.CR ) )
						|| ( event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC );

				return result;
			}
		};
		TableViewerEditor.create( mServerTableViewer, focusManager, actSupport, ColumnViewerEditor.TABBING_HORIZONTAL
				| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR | ColumnViewerEditor.TABBING_VERTICAL
				| ColumnViewerEditor.KEYBOARD_ACTIVATION );

		createCompositeTopButtons( compositeTop );
	}

	/**
	 * This method initializes sashForm.
	 * 
	 * @param parent The composite to draw onto.
	 */
	private void createSashForm( final Composite parent )
	{
		final GridData gridData = new GridData( );
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;

		final SashForm sashForm = new SashForm( parent, SWT.NONE );
		sashForm.setOrientation( SWT.VERTICAL );
		sashForm.setLayoutData( gridData );

		createJenkinsSection( sashForm );
		createHideShowSection( sashForm );
	}

	/**
	 * Sets up the columns for the table.
	 * 
	 * @param viewer The table viewer instance.
	 */
	private void createTableColumns( final TableViewer viewer )
	{
		final TableColumn[] columns = new TableColumn[COLUMN_NAMES.length];

		viewer.setColumnProperties( COLUMN_NAMES );
		viewer.setUseHashlookup( true );
		viewer.getTable( ).setHeaderVisible( true );
		viewer.getTable( ).setLinesVisible( true );
		final TableLayout layout = new TableLayout( );

		layout.addColumnData( new ColumnWeightData( 9, 150, true ) ); // Address
		layout.addColumnData( new ColumnWeightData( 7, 90, true ) ); // Username
		layout.addColumnData( new ColumnWeightData( 7, 90, true ) ); // Password
		layout.addColumnData( new ColumnWeightData( 5, 60, true ) ); // Query Rate
		layout.addColumnData( new ColumnWeightData( 2, 40, true ) ); // Allow Increase
		layout.addColumnData( new ColumnWeightData( 2, 40, true ) ); // Primary?

		viewer.getTable( ).setLayout( layout );
		viewer.getTable( ).setLayoutData(
				new GridData( GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL ) );

		for( int i = 0; i < COLUMN_NAMES.length; i++ )
		{
			columns[i] = new TableColumn( viewer.getTable( ), SWT.LEFT, i );
			columns[i].setText( COLUMN_NAMES[i] );
			columns[i].setResizable( true );

			if( ( i == 4 ) || ( i == 5 ) )
			{
				columns[i].setAlignment( SWT.CENTER );
			}
		}
	}

	/**
	 * Creates the button bar for the selection options of the job set.
	 * 
	 * @param parent The parent to draw onto.
	 */
	private void createTableViewerButtonSet( final Composite parent )
	{
		final GridData gridData6 = new GridData( );
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.grabExcessHorizontalSpace = false;
		gridData6.grabExcessVerticalSpace = true;
		gridData6.verticalAlignment = GridData.FILL;
		final Composite compositeButtons = new Composite( parent, SWT.NONE );
		compositeButtons.setLayout( new GridLayout( ) );
		compositeButtons.setLayoutData( gridData6 );

		final Button selectAllButton = new Button( compositeButtons, SWT.NONE );
		selectAllButton.setText( "Select All" );
		selectAllButton.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
		selectAllButton.addSelectionListener( new SelectionAdapter( )
		{
			@Override
			public void widgetSelected( final SelectionEvent e )
			{
				for( final TableItem item : mJobsTableViewer.getTable( ).getItems( ) )
				{
					item.setChecked( true );
				}
			}
		} );

		final Button deselectAllButton = new Button( compositeButtons, SWT.NONE );
		deselectAllButton.setText( "Deselect All" );
		deselectAllButton.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
		deselectAllButton.addSelectionListener( new SelectionAdapter( )
		{
			@Override
			public void widgetSelected( final SelectionEvent e )
			{
				for( final TableItem item : mJobsTableViewer.getTable( ).getItems( ) )
				{
					item.setChecked( false );
				}
			}
		} );

		final Button refreshButton = new Button( compositeButtons, SWT.NONE );
		refreshButton.setLayoutData( new GridData( GridData.HORIZONTAL_ALIGN_BEGINNING ) );
		refreshButton.setText( "Refresh" );
		refreshButton.addSelectionListener( new SelectionAdapter( )
		{
			@Override
			public void widgetSelected( final SelectionEvent e )
			{
				final IJobManager tempManager = new JobManager( );

				for( final IServerInformation server : getContentProvider( ).getServers( ) )
				{
					final List<IJob> jobs = JenkinsRemoteXMLProcessing.loadDataFromJenkinsServer( server );
					for( final IJob job : jobs )
					{
						tempManager.addJob( job );
					}
				}

				mJobsTableViewer.setInput( tempManager );
			}
		} );
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 * @param parent The parent composite.
	 */
	@Override
	protected void createButtonsForButtonBar( final Composite parent )
	{
		createButton( parent, IDialogConstants.OK_ID, "Save", true );
		createButton( parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false );
	}

	/**
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 * @param parent The parent composite.
	 * @return The dialog composite area.
	 */
	@Override
	protected Control createDialogArea( final Composite parent )
	{
		mAddImage = new Image( getShell( ).getDisplay( ), "images/16x16/add.gif" );
		mRemoveImage = new Image( getShell( ).getDisplay( ), "images/16x16/remove.gif" );

		final Composite composite = (Composite)super.createDialogArea( parent );

		final GridLayout topLayout = new GridLayout( 1, false );
		topLayout.marginHeight = 0;
		topLayout.marginWidth = 0;
		topLayout.horizontalSpacing = 0;
		topLayout.verticalSpacing = 0;
		composite.setLayout( topLayout );

		createSashForm( composite );

		setTitle( "Configure Jenkins Remote" );
		setBlockOnOpen( true );
		setDialogHelpAvailable( false );
		setMessage( "This allows you to customize Jenkins Remote to your personal setttings." );

		return composite;
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed( )
	{
		// Sync up the servers
		getConfigurationData( ).getServers( ).clear( );
		for( final IServerInformation server : getContentProvider( ).getServers( ) )
		{
			getConfigurationData( ).addServer( server );
		}

		// Show items.
		getConfigurationData( ).setItemShowState( ShowInformationConverter.ARCHIVES, mCheckboxShowArtifacts.getSelection( ) );
		getConfigurationData( ).setItemShowState( ShowInformationConverter.DURATION, mCheckboxShowDuration.getSelection( ) );
		getConfigurationData( ).setItemShowState( ShowInformationConverter.HEALTH_REPORTS,
				mCheckboxShowHealthReports.getSelection( ) );
		getConfigurationData( ).setItemShowState( ShowInformationConverter.TESTS, mCheckboxShowTestData.getSelection( ) );
		getConfigurationData( ).setItemShowState( ShowInformationConverter.ADMIN, mCheckboxShowAdmin.getSelection( ) );
		getConfigurationData( ).setItemShowState( ShowInformationConverter.POPUPS, mCheckboxShowPopups.getSelection( ) );

		// Jobs to show.
		for( final TableItem tableItem : mJobsTableViewer.getTable( ).getItems( ) )
		{
			getConfigurationData( ).setJobShowState( tableItem.getText( ), tableItem.getChecked( ) );
		}

		try
		{
			JenkinsRemote.saveConfigurationData( getConfigurationData( ), IConfigurationManager.CONFIG_FILE );
		}
		catch( final Exception e1 )
		{
			// TODO Report this error somehow.
		}

		super.okPressed( );
	}

	/**
	 * @see org.eclipse.jface.window.Window#setShellStyle(int)
	 * @param newShellStyle The new shell style.
	 */
	@Override
	protected void setShellStyle( final int newShellStyle )
	{
		super.setShellStyle( newShellStyle | SWT.RESIZE );
	}
}
