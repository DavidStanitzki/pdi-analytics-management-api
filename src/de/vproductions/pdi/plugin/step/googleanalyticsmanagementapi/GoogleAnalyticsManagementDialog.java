package de.vproductions.pdi.plugin.step.googleanalyticsmanagementapi;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.ui.core.PropsUI;
import org.pentaho.di.ui.core.dialog.ErrorDialog;
import org.pentaho.di.ui.core.widget.ColumnInfo;
import org.pentaho.di.ui.core.widget.ComboVar;
import org.pentaho.di.ui.core.widget.TableView;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class GoogleAnalyticsManagementDialog extends BaseStepDialog implements StepDialogInterface {
 private static Class<?> PKG = GoogleAnalyticsManagementMeta.class; // for i18n purposes, needed by Translator2!!


 private Label wlApplicationName;
 private TextVar wApplicationName;
 private FormData fdlApplicationName, fdApplicationName;
 
 private Label wlAccountId;
 private TextVar wAccountId;
 private FormData fdlAccountId, fdAccountId;

 private Label wlResult;
 private TextVar wResult;
 private FormData fdlResult, fdResult;

 private Label wlResultCode;
 private TextVar wResultCode;
 private FormData fdlResultCode, fdResultCode;
 
 private Label wlAccountIdInField;
 private Button wAccountIdInField;
 private FormData fdlAccountIdInField, fdAccountIdInField;

 private Label wlAccountIdField;
 private ComboVar wAccountIdField;
 private FormData fdlAccountIdField, fdAccountIdField;

 private Label wlWebPropertyId;
 private TextVar wWebPropertyId;
 private FormData fdlWebPropertyId, fdWebPropertyId;
 
 private Label wlWebPropertyIdInField;
 private Button wWebPropertyIdInField;
 private FormData fdlWebPropertyIdInField, fdWebPropertyIdInField;
 
 private Label wlWebPropertyIdField;
 private ComboVar wWebPropertyIdField;
 private FormData fdlWebPropertyIdField, fdWebPropertyIdField;

 private GoogleAnalyticsManagementMeta input;

 private Map<String, Integer> inputFields;

 private ColumnInfo[] colinf, colinfoparams;

 private String[] fieldNames;
 
 private Label wlServiceAccountMail;
 private TextVar wServiceAccountMail;

 private CTabFolder wTabFolder;

 private CTabItem wGeneralTab, wAuthTab;
 private FormData fdTabFolder;

 private Composite wGeneralComp;
 private FormData fdGeneralComp;


 private Composite wAuthComp;
 private FormData fdAuthComp;



 private Label wlResponseTime;
 private TextVar wResponseTime;
 private FormData fdlResponseTime, fdResponseTime;

 private Label wlP12File;
 private TextVar wP12File;
 private Button wbP12File;
 private FormData fdbP12File;
 private FormData fdlP12File, fdP12File;
 

 private boolean gotPreviousFields = false;

 public GoogleAnalyticsManagementDialog( Shell parent, Object in, TransMeta transMeta, String sname ) {
   super( parent, (BaseStepMeta) in, transMeta, sname );
   input = (GoogleAnalyticsManagementMeta) in;
   inputFields = new HashMap<String, Integer>();
 }

 public String open() {
   Shell parent = getParent();
   Display display = parent.getDisplay();

   shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX | SWT.MIN );
   props.setLook( shell );
   setShellImage( shell, input );

   ModifyListener lsMod = new ModifyListener() {
     public void modifyText( ModifyEvent e ) {
       input.setChanged();
     }
   };

   changed = input.hasChanged();

   FormLayout formLayout = new FormLayout();
   formLayout.marginWidth = Const.FORM_MARGIN;
   formLayout.marginHeight = Const.FORM_MARGIN;

   shell.setLayout( formLayout );
   shell.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.Shell.Title" ) );

   int middle = props.getMiddlePct();
   int margin = Const.MARGIN;

   // Stepname line
   wlStepname = new Label( shell, SWT.RIGHT );
   wlStepname.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.Stepname.Label" ) );
   props.setLook( wlStepname );
   fdlStepname = new FormData();
   fdlStepname.left = new FormAttachment( 0, 0 );
   fdlStepname.right = new FormAttachment( middle, -margin );
   fdlStepname.top = new FormAttachment( 0, margin );
   wlStepname.setLayoutData( fdlStepname );
   wStepname = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   wStepname.setText( stepname );
   props.setLook( wStepname );
   wStepname.addModifyListener( lsMod );
   fdStepname = new FormData();
   fdStepname.left = new FormAttachment( middle, 0 );
   fdStepname.top = new FormAttachment( 0, margin );
   fdStepname.right = new FormAttachment( 100, 0 );
   wStepname.setLayoutData( fdStepname );

   wTabFolder = new CTabFolder( shell, SWT.BORDER );
   props.setLook( wTabFolder, PropsUI.WIDGET_STYLE_TAB );

   // ////////////////////////
   // START OF GENERAL TAB ///
   // ////////////////////////
   wGeneralTab = new CTabItem( wTabFolder, SWT.NONE );
   wGeneralTab.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.Title" ) );

   wGeneralComp = new Composite( wTabFolder, SWT.NONE );
   props.setLook( wGeneralComp );

   FormLayout fileLayout = new FormLayout();
   fileLayout.marginWidth = 3;
   fileLayout.marginHeight = 3;
   wGeneralComp.setLayout( fileLayout );

   // ////////////////////////
   // START Settings GROUP

   Group gSettings = new Group( wGeneralComp, SWT.SHADOW_ETCHED_IN );
   gSettings.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.Label" ) );
   FormLayout SettingsLayout = new FormLayout();
   SettingsLayout.marginWidth = 3;
   SettingsLayout.marginHeight = 3;
   gSettings.setLayout( SettingsLayout );
   props.setLook( gSettings );

   //ApplicationName Line
   wlApplicationName = new Label( gSettings, SWT.RIGHT );
   wlApplicationName.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.ApplicationName.Label" ) );
   props.setLook( wlApplicationName );
   fdlApplicationName = new FormData();
   fdlApplicationName.left = new FormAttachment( 0, 0 );
   fdlApplicationName.right = new FormAttachment( middle, -margin );
   fdlApplicationName.top = new FormAttachment( wGeneralComp, margin * 2 );
   wlApplicationName.setLayoutData( fdlApplicationName );

   wApplicationName = new TextVar( transMeta, gSettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   props.setLook( wApplicationName );
   wApplicationName.addModifyListener( lsMod );
   fdApplicationName = new FormData();
   fdApplicationName.left = new FormAttachment( middle, 0 );
   fdApplicationName.top = new FormAttachment( wGeneralComp, margin * 2 );
   fdApplicationName.right = new FormAttachment( 100, 0 );
   wApplicationName.setLayoutData( fdApplicationName );
   
   // AccountId Line
   wlAccountId = new Label( gSettings, SWT.RIGHT );
   wlAccountId.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.AccountId.Label" ) );
   props.setLook( wlAccountId );
   fdlAccountId = new FormData();
   fdlAccountId.left = new FormAttachment( 0, 0 );
   fdlAccountId.right = new FormAttachment( middle, -margin );
   fdlAccountId.top = new FormAttachment( wApplicationName, margin * 2 );
   wlAccountId.setLayoutData( fdlAccountId );

   wAccountId = new TextVar( transMeta, gSettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   props.setLook( wAccountId );
   wAccountId.addModifyListener( lsMod );
   fdAccountId = new FormData();
   fdAccountId.left = new FormAttachment( middle, 0 );
   fdAccountId.top = new FormAttachment( wApplicationName, margin * 2 );
   fdAccountId.right = new FormAttachment( 100, 0 );
   wAccountId.setLayoutData( fdAccountId );

   // AccountIdInField line
   wlAccountIdInField = new Label( gSettings, SWT.RIGHT );
   wlAccountIdInField.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.AccountIdInField.Label" ) );
   props.setLook( wlAccountIdInField );
   fdlAccountIdInField = new FormData();
   fdlAccountIdInField.left = new FormAttachment( 0, 0 );
   fdlAccountIdInField.top = new FormAttachment( wAccountId, margin );
   fdlAccountIdInField.right = new FormAttachment( middle, -margin );
   wlAccountIdInField.setLayoutData( fdlAccountIdInField );
   wAccountIdInField = new Button( gSettings, SWT.CHECK );
   props.setLook( wAccountIdInField );
   fdAccountIdInField = new FormData();
   fdAccountIdInField.left = new FormAttachment( middle, 0 );
   fdAccountIdInField.top = new FormAttachment( wAccountId, margin );
   fdAccountIdInField.right = new FormAttachment( 100, 0 );
   wAccountIdInField.setLayoutData( fdAccountIdInField );
   wAccountIdInField.addSelectionListener( new SelectionAdapter() {
     public void widgetSelected( SelectionEvent e ) {
       input.setChanged();
       activeAccountIdInfield();
     }
   } );

   // AccountIdField Line
   wlAccountIdField = new Label( gSettings, SWT.RIGHT );
   wlAccountIdField.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.AccountIdField.Label" ) );
   props.setLook( wlAccountIdField );
   fdlAccountIdField = new FormData();
   fdlAccountIdField.left = new FormAttachment( 0, 0 );
   fdlAccountIdField.right = new FormAttachment( middle, -margin );
   fdlAccountIdField.top = new FormAttachment( wAccountIdInField, margin );
   wlAccountIdField.setLayoutData( fdlAccountIdField );

   wAccountIdField = new ComboVar( transMeta, gSettings, SWT.BORDER | SWT.READ_ONLY );
   wAccountIdField.setEditable( true );
   props.setLook( wAccountIdField );
   wAccountIdField.addModifyListener( lsMod );
   fdAccountIdField = new FormData();
   fdAccountIdField.left = new FormAttachment( middle, 0 );
   fdAccountIdField.top = new FormAttachment( wAccountIdInField, margin );
   fdAccountIdField.right = new FormAttachment( 100, -margin );
   wAccountIdField.setLayoutData( fdAccountIdField );
   wAccountIdField.addFocusListener( new FocusListener() {
     public void focusLost( org.eclipse.swt.events.FocusEvent e ) {
     }

     public void focusGained( org.eclipse.swt.events.FocusEvent e ) {
       Cursor busy = new Cursor( shell.getDisplay(), SWT.CURSOR_WAIT );
       shell.setCursor( busy );
       setStreamFields();
       shell.setCursor( null );
       busy.dispose();
     }
   } );
   
   //WebPropertyId Line
   wlWebPropertyId = new Label( gSettings, SWT.RIGHT );
   wlWebPropertyId.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.WebPropertyId.Label" ) );
   props.setLook( wlWebPropertyId );
   fdlWebPropertyId = new FormData();
   fdlWebPropertyId.left = new FormAttachment( 0, 0 );
   fdlWebPropertyId.right = new FormAttachment( middle, -margin );
   fdlWebPropertyId.top = new FormAttachment( wAccountIdField, margin * 2 );
   wlWebPropertyId.setLayoutData( fdlWebPropertyId );

   wWebPropertyId = new TextVar( transMeta, gSettings, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   props.setLook( wWebPropertyId );
   wWebPropertyId.addModifyListener( lsMod );
   fdWebPropertyId = new FormData();
   fdWebPropertyId.left = new FormAttachment( middle, 0 );
   fdWebPropertyId.top = new FormAttachment( wAccountIdField, margin * 2 );
   fdWebPropertyId.right = new FormAttachment( 100, 0 );
   wWebPropertyId.setLayoutData( fdWebPropertyId );



   // WebPropertyIdInField line
   wlWebPropertyIdInField = new Label( gSettings, SWT.RIGHT );
   wlWebPropertyIdInField.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.WebPropertyIdInField.Label" ) );
   props.setLook( wlWebPropertyIdInField );
   fdlWebPropertyIdInField = new FormData();
   fdlWebPropertyIdInField.left = new FormAttachment( 0, 0 );
   fdlWebPropertyIdInField.top = new FormAttachment( wWebPropertyId, margin );
   fdlWebPropertyIdInField.right = new FormAttachment( middle, -margin );
   wlWebPropertyIdInField.setLayoutData( fdlWebPropertyIdInField );
   wWebPropertyIdInField = new Button( gSettings, SWT.CHECK );
   props.setLook( wWebPropertyIdInField );
   fdWebPropertyIdInField = new FormData();
   fdWebPropertyIdInField.left = new FormAttachment( middle, 0 );
   fdWebPropertyIdInField.top = new FormAttachment( wWebPropertyId, margin );
   fdWebPropertyIdInField.right = new FormAttachment( 100, 0 );
   wWebPropertyIdInField.setLayoutData( fdWebPropertyIdInField );
   wWebPropertyIdInField.addSelectionListener( new SelectionAdapter() {
     public void widgetSelected( SelectionEvent e ) {
       input.setChanged();
       activeWebPropertyIdInfield();
     }
   } );

   // WebPropertyIdField Line
   wlWebPropertyIdField = new Label( gSettings, SWT.RIGHT );
   wlWebPropertyIdField.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.WebPropertyIdField.Label" ) );
   props.setLook( wlWebPropertyIdField );
   fdlWebPropertyIdField = new FormData();
   fdlWebPropertyIdField.left = new FormAttachment( 0, 0 );
   fdlWebPropertyIdField.right = new FormAttachment( middle, -margin );
   fdlWebPropertyIdField.top = new FormAttachment( wWebPropertyIdInField, margin );
   wlWebPropertyIdField.setLayoutData( fdlWebPropertyIdField );

   wWebPropertyIdField = new ComboVar( transMeta, gSettings, SWT.BORDER | SWT.READ_ONLY );
   wWebPropertyIdField.setEditable( true );
   props.setLook( wWebPropertyIdField );
   wWebPropertyIdField.addModifyListener( lsMod );
   fdWebPropertyIdField = new FormData();
   fdWebPropertyIdField.left = new FormAttachment( middle, 0 );
   fdWebPropertyIdField.top = new FormAttachment( wWebPropertyIdInField, margin );
   fdWebPropertyIdField.right = new FormAttachment( 100, -margin );
   wWebPropertyIdField.setLayoutData( fdWebPropertyIdField );
   wWebPropertyIdField.addFocusListener( new FocusListener() {
     public void focusLost( org.eclipse.swt.events.FocusEvent e ) {
     }

     public void focusGained( org.eclipse.swt.events.FocusEvent e ) {
       Cursor busy = new Cursor( shell.getDisplay(), SWT.CURSOR_WAIT );
       shell.setCursor( busy );
       setStreamFields();
       shell.setCursor( null );
       busy.dispose();
     }
   } );




   FormData fdSettings = new FormData();
   fdSettings.left = new FormAttachment( 0, 0 );
   fdSettings.right = new FormAttachment( 100, 0 );
   fdSettings.top = new FormAttachment( wStepname, margin );
   gSettings.setLayoutData( fdSettings );

   // END Output Settings GROUP
   // ////////////////////////

   // ////////////////////////
   // START Output Fields GROUP

   Group gOutputFields = new Group( wGeneralComp, SWT.SHADOW_ETCHED_IN );
   gOutputFields.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.OutputFieldsGroup.Label" ) );
   FormLayout OutputFieldsLayout = new FormLayout();
   OutputFieldsLayout.marginWidth = 3;
   OutputFieldsLayout.marginHeight = 3;
   gOutputFields.setLayout( OutputFieldsLayout );
   props.setLook( gOutputFields );

   // Result line...
   wlResult = new Label( gOutputFields, SWT.RIGHT );
   wlResult.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.OutputFieldsGroup.Result.Label" ) );
   props.setLook( wlResult );
   fdlResult = new FormData();
   fdlResult.left = new FormAttachment( 0, 0 );
   fdlResult.right = new FormAttachment( middle, -margin );
   fdlResult.top = new FormAttachment( gSettings, margin );
   wlResult.setLayoutData( fdlResult );
   wResult = new TextVar( transMeta, gOutputFields, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   props.setLook( wResult );
   wResult.addModifyListener( lsMod );
   fdResult = new FormData();
   fdResult.left = new FormAttachment( middle, 0 );
   fdResult.top = new FormAttachment( gSettings, margin * 2 );
   fdResult.right = new FormAttachment( 100, -margin );
   wResult.setLayoutData( fdResult );

   FormData fdOutputFields = new FormData();
   fdOutputFields.left = new FormAttachment( 0, 0 );
   fdOutputFields.right = new FormAttachment( 100, 0 );
   fdOutputFields.top = new FormAttachment( gSettings, margin );
   gOutputFields.setLayoutData( fdOutputFields );

   // END Output Fields GROUP
   // ////////////////////////

   fdGeneralComp = new FormData();
   fdGeneralComp.left = new FormAttachment( 0, 0 );
   fdGeneralComp.top = new FormAttachment( wStepname, margin );
   fdGeneralComp.right = new FormAttachment( 100, 0 );
   fdGeneralComp.bottom = new FormAttachment( 100, 0 );
   wGeneralComp.setLayoutData( fdGeneralComp );

   wGeneralComp.layout();
   wGeneralTab.setControl( wGeneralComp );

   // ///////////////////////////////////////////////////////////
   // / END OF GENERAL TAB
   // ///////////////////////////////////////////////////////////

   // Auth tab...
   //
   wAuthTab = new CTabItem( wTabFolder, SWT.NONE );
   wAuthTab.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.AuthTab.Title" ) );

   FormLayout alayout = new FormLayout();
   alayout.marginWidth = Const.FORM_MARGIN;
   alayout.marginHeight = Const.FORM_MARGIN;

   wAuthComp = new Composite( wTabFolder, SWT.NONE );
   wAuthComp.setLayout( alayout );
   props.setLook( wAuthComp );

   // ////////////////////////
   // START API AUTH GROUP

   Group gAPIAuth = new Group( wAuthComp, SWT.SHADOW_ETCHED_IN );
   gAPIAuth.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.AuthTab.APIAuthGroup.Label" ) );
   FormLayout httpAuthLayout = new FormLayout();
   httpAuthLayout.marginWidth = 3;
   httpAuthLayout.marginHeight = 3;
   gAPIAuth.setLayout( httpAuthLayout );
   props.setLook( gAPIAuth );

   // API Login
   wlServiceAccountMail = new Label( gAPIAuth, SWT.RIGHT );
   wlServiceAccountMail.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.AuthTab.APIAuthGroup.ServiceAccountMail.Label" ) );
   props.setLook( wlServiceAccountMail );
   FormData fdlServiceAccountMail = new FormData();
   fdlServiceAccountMail.top = new FormAttachment( 0, margin );
   fdlServiceAccountMail.left = new FormAttachment( 0, 0 );
   fdlServiceAccountMail.right = new FormAttachment( middle, -margin );
   wlServiceAccountMail.setLayoutData( fdlServiceAccountMail );
   wServiceAccountMail = new TextVar( transMeta, gAPIAuth, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   wServiceAccountMail.addModifyListener( lsMod );
   wServiceAccountMail.setToolTipText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.AuthTab.APIAuthGroup.ServiceAccountMail.Tooltip" ) );
   props.setLook( wServiceAccountMail );
   FormData fdServiceAccountMail = new FormData();
   fdServiceAccountMail.top = new FormAttachment( 0, margin );
   fdServiceAccountMail.left = new FormAttachment( middle, 0 );
   fdServiceAccountMail.right = new FormAttachment( 100, 0 );
   wServiceAccountMail.setLayoutData( fdServiceAccountMail );

   // P12File line
   wlP12File = new Label( gAPIAuth, SWT.RIGHT );
   wlP12File.setText( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.AuthTab.APIAuthGroup.P12File.Label" ) );
   props.setLook( wlP12File );
   fdlP12File = new FormData();
   fdlP12File.left = new FormAttachment( 0, 0 );
   fdlP12File.top = new FormAttachment( wServiceAccountMail, margin );
   fdlP12File.right = new FormAttachment( middle, -margin );
   wlP12File.setLayoutData( fdlP12File );

   wbP12File = new Button( gAPIAuth, SWT.PUSH | SWT.CENTER );
   props.setLook( wbP12File );
   wbP12File.setText( BaseMessages.getString( PKG, "System.Button.Browse" ) );
   fdbP12File = new FormData();
   fdbP12File.right = new FormAttachment( 100, 0 );
   fdbP12File.top = new FormAttachment( wServiceAccountMail, 0 );
   wbP12File.setLayoutData( fdbP12File );

   wbP12File.addSelectionListener( new SelectionAdapter() {
     public void widgetSelected( SelectionEvent e ) {
       FileDialog dialog = new FileDialog( shell, SWT.SAVE );
       dialog.setFilterExtensions( new String[] { "*.*" } );
       if ( wP12File.getText() != null ) {
         dialog.setFileName( transMeta.environmentSubstitute( wP12File.getText() ) );
       }
       dialog.setFilterNames( new String[] { BaseMessages.getString( PKG, "System.FileType.AllFiles" ) } );
       if ( dialog.open() != null ) {
         wP12File.setText( dialog.getFilterPath()
           + System.getProperty( "file.separator" ) + dialog.getFileName() );
       }
     }
   } );

   wP12File = new TextVar( transMeta, gAPIAuth, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   props.setLook( wP12File );
   wP12File.addModifyListener( lsMod );
   fdP12File = new FormData();
   fdP12File.left = new FormAttachment( middle, 0 );
   fdP12File.top = new FormAttachment( wServiceAccountMail, margin );
   fdP12File.right = new FormAttachment( wbP12File, -margin );
   wP12File.setLayoutData( fdP12File );


   FormData fdAPIAuth = new FormData();
   fdAPIAuth.left = new FormAttachment( 0, 0 );
   fdAPIAuth.right = new FormAttachment( 100, 0 );
   fdAPIAuth.top = new FormAttachment( gOutputFields, margin );
   gAPIAuth.setLayoutData( fdAPIAuth );

   // END API AUTH GROUP
   // ////////////////////////


   fdAuthComp = new FormData();
   fdAuthComp.left = new FormAttachment( 0, 0 );
   fdAuthComp.top = new FormAttachment( wStepname, margin );
   fdAuthComp.right = new FormAttachment( 100, 0 );
   fdAuthComp.bottom = new FormAttachment( 100, 0 );
   wAuthComp.setLayoutData( fdAuthComp );

   wAuthComp.layout();
   wAuthTab.setControl( wAuthComp );
   // ////// END of Auth Tab





   fdTabFolder = new FormData();
   fdTabFolder.left = new FormAttachment( 0, 0 );
   fdTabFolder.top = new FormAttachment( wStepname, margin );
   fdTabFolder.right = new FormAttachment( 100, 0 );
   fdTabFolder.bottom = new FormAttachment( 100, -50 );
   wTabFolder.setLayoutData( fdTabFolder );

   //
   // Search the fields in the background
   //

   final Runnable runnable = new Runnable() {
     public void run() {
       StepMeta stepMeta = transMeta.findStep( stepname );
       if ( stepMeta != null ) {
         try {
           RowMetaInterface row = transMeta.getPrevStepFields( stepMeta );

           // Remember these fields...
           for ( int i = 0; i < row.size(); i++ ) {
             inputFields.put( row.getValueMeta( i ).getName(), Integer.valueOf( i ) );
           }

           setComboBoxes();
         } catch ( KettleException e ) {
           log.logError( toString(), BaseMessages.getString( PKG, "System.Dialog.GetFieldsFailed.Message" ) );
         }
       }
     }
   };
   new Thread( runnable ).start();

   // THE BUTTONS
   wOK = new Button( shell, SWT.PUSH );
   wOK.setText( BaseMessages.getString( PKG, "System.Button.OK" ) );
   wCancel = new Button( shell, SWT.PUSH );
   wCancel.setText( BaseMessages.getString( PKG, "System.Button.Cancel" ) );

   setButtonPositions( new Button[] { wOK, wCancel }, margin, wTabFolder );

   // Add listeners
   lsOK = new Listener() {
     public void handleEvent( Event e ) {
       ok();
     }
   };
   lsCancel = new Listener() {
     public void handleEvent( Event e ) {
       cancel();
     }
   };



   wOK.addListener( SWT.Selection, lsOK );

   wCancel.addListener( SWT.Selection, lsCancel );

   lsDef = new SelectionAdapter() {
     public void widgetDefaultSelected( SelectionEvent e ) {
       ok();
     }
   };

   wStepname.addSelectionListener( lsDef );
   wAccountId.addSelectionListener( lsDef );
   wResult.addSelectionListener( lsDef );


   // Detect X or ALT-F4 or something that kills this window...
   shell.addShellListener( new ShellAdapter() {
     public void shellClosed( ShellEvent e ) {
       cancel();
     }
   } );


   // Set the shell size, based upon previous time...
   setSize();
   wTabFolder.setSelection( 0 );
   getData();
   activeAccountIdInfield();
   activeWebPropertyIdInfield();
   setMethod();
   input.setChanged( changed );

   shell.open();
   while ( !shell.isDisposed() ) {
     if ( !display.readAndDispatch() ) {
       display.sleep();
     }
   }
   return stepname;
 }

 private void setMethod() {


 }

 protected void setComboBoxes() {

   final Map<String, Integer> fields = new HashMap<String, Integer>();

   // Add the currentMeta fields...
   fields.putAll( inputFields );

   Set<String> keySet = fields.keySet();
   List<String> entries = new ArrayList<String>( keySet );

   fieldNames = entries.toArray( new String[entries.size()] );

   Const.sortStrings( fieldNames );
   colinfoparams[0].setComboValues( fieldNames );
   colinf[0].setComboValues( fieldNames );
 }

 private void setStreamFields() {
   if ( !gotPreviousFields ) {
     String accountid = wAccountIdField.getText();

     String webpropertyid = wWebPropertyIdField.getText();

     wAccountIdField.removeAll();

     wWebPropertyIdField.removeAll();

     try {
       if ( fieldNames != null ) {
         wAccountIdField.setItems( fieldNames );

         wWebPropertyIdField.setItems( fieldNames );
       }
     } finally {
       if ( accountid != null ) {
         wAccountIdField.setText( accountid );
       }
       if ( webpropertyid != null ) {
         wWebPropertyIdField.setText( webpropertyid );
       }
     }
     gotPreviousFields = true;
   }
 }

 private void activeAccountIdInfield() {
   wlAccountIdField.setEnabled( wAccountIdInField.getSelection() );
   wAccountIdField.setEnabled( wAccountIdInField.getSelection() );
   wlAccountId.setEnabled( !wAccountIdInField.getSelection() );
   wAccountId.setEnabled( !wAccountIdInField.getSelection() );
 }

 /**
  * Copy information from the meta-data input to the dialog fields.
  */
 public void getData() {
   if ( isDebug() ) {
     logDebug( BaseMessages.getString( PKG, "RestDialog.Log.GettingKeyInfo" ) );
   }



   if ( input.getApplicationName() != null ) {
	     wApplicationName.setText( input.getApplicationName() );
   }
   if ( input.getAccountId() != null ) {
	     wAccountId.setText( input.getAccountId() );
   }
   if ( input.getWebPropertyId() != null ) {
	     wWebPropertyId.setText( input.getWebPropertyId() );
   }
   if ( input.getServiceAccountMail() != null ) {
	     wServiceAccountMail.setText( input.getServiceAccountMail() );
   }
   if ( input.getP12File() != null ) {
	     wP12File.setText( input.getP12File() );
   }
   if ( input.getOutputField() != null ) {
	     wResult.setText( input.getOutputField() );
   }



   wStepname.selectAll();
   wStepname.setFocus();
 }

 private void cancel() {
   stepname = null;
   input.setChanged( changed );
   dispose();
 }

 private void ok() {
   if ( Const.isEmpty( wStepname.getText() ) ) {
     return;
   }



   
   input.setApplicationName( wApplicationName.getText() );
   input.setAccountId( wAccountId.getText() );
   input.setWebPropertyId( wWebPropertyId.getText() );
   input.setServiceAccountMail( wServiceAccountMail.getText() );
   input.setP12File( wP12File.getText() );
   input.setOutputField( wResult.getText() );
   
   stepname = wStepname.getText(); // return value

   dispose();
 }

 



 private void activeWebPropertyIdInfield() {
   wlWebPropertyId.setEnabled( !wWebPropertyIdInField.getSelection() );
   wWebPropertyId.setEnabled( !wWebPropertyIdInField.getSelection() );
   wlWebPropertyIdField.setEnabled( wWebPropertyIdInField.getSelection() );
   wWebPropertyIdField.setEnabled( wWebPropertyIdInField.getSelection() );

 }
}
