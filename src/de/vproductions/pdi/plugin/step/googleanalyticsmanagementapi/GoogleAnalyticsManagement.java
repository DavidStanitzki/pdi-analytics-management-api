package de.vproductions.pdi.plugin.step.googleanalyticsmanagementapi;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;
import org.pentaho.di.i18n.BaseMessages;

import com.google.api.services.analytics.Analytics;
//import com.google.api.services.analytics.model.Accounts;
//import com.google.api.services.analytics.model.Profile;
import com.google.api.services.analytics.model.Profiles;
//import com.google.api.services.analytics.model.Webproperties;


public class GoogleAnalyticsManagement extends BaseStep implements StepInterface {

	private static Class<?> PKG = GoogleAnalyticsManagementMeta.class; // for i18n purposes
	
	private GoogleAnalyticsManagementData data;
	private GoogleAnalyticsManagementMeta meta;
	
	private Analytics analytics;


	
	public GoogleAnalyticsManagement(StepMeta s, StepDataInterface stepDataInterface, int c, TransMeta t, Trans dis) {
		super(s, stepDataInterface, c, t, dis);
	}

	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
		meta = (GoogleAnalyticsManagementMeta) smi;
		data = (GoogleAnalyticsManagementData) sdi;

		Object[] r = getRow(); // get row, blocks when needed!
		if (r == null) // no more input to be expected...
		{
			setOutputDone();
			return false;
		}

		if (first) {
			first = false;

			data.outputRowMeta = (RowMetaInterface) getInputRowMeta().clone();
			meta.getFields(data.outputRowMeta, getStepname(), null, null, this);

			logBasic("template step initialized successfully");

		}

		Object[] outputRow = callGoogleAnalyticsManagement(r);

		putRow(data.outputRowMeta, outputRow); // copy row to possible alternate rowset(s)

		
		
		if (checkFeedback(getLinesRead())) {
			logBasic("Linenr " + getLinesRead()); // Some basic logging
		}

		return true;
	}
	
	private Object[] callGoogleAnalyticsManagement( Object[] rowData ) throws KettleException {
		
		Object[] newRow = null;
	    if ( rowData != null ) {
	      newRow = rowData.clone();
	    }

	    newRow = RowDataUtil.addValueData( newRow, data.outputRowMeta.size() - 1, data.profiles );	    
	    
		return newRow;
	}

	public boolean init(StepMetaInterface smi, StepDataInterface sdi) {
		meta = (GoogleAnalyticsManagementMeta) smi;
		data = (GoogleAnalyticsManagementData) sdi;
		
		if ( !super.init( smi, sdi ) ) {
		      return false;
		}

	    String appName = environmentSubstitute( meta.getApplicationName() );
	    String serviceAccount = environmentSubstitute( meta.getServiceAccountMail() );
	    String OAuthKeyFile = environmentSubstitute( meta.getP12File() );

	    if ( log.isDetailed() ) {
	      logDetailed( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.GeneralTab.SettingsGroup.ApplicationName.Label" ) + ": " + appName );
	      logDetailed( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.AuthTab.APIAuthGroup.ServiceAccountMail.Label" ) + ": " + serviceAccount );
	      logDetailed( BaseMessages.getString( PKG, "GoogleAnalyticsManagementDialog.AuthTab.APIAuthGroup.P12File.Label" ) + ": " + OAuthKeyFile );
	    }
		
		try {
		      // Create an Analytics object, and fetch what we can for later (account name, e.g.)
		      analytics = GoogleAnalyticsManagementApiFramework.createFor(
		        environmentSubstitute( meta.getApplicationName() ),
		        environmentSubstitute( meta.getServiceAccountMail() ),
		        environmentSubstitute( meta.getP12File() )
		      ).getAnalytics();
		      // There is necessarily an account name associated with this, so any NPEs or other exceptions mean bail out
		      //accountName = analytics.management().accounts().list().execute().getItems().iterator().next().getName();
//		      Accounts accounts = analytics.management().accounts().list().execute();
//		      Webproperties properties = analytics.management().webproperties().list("~all").execute();
		      
//    		  if (properties.getItems().isEmpty()) {
//		        System.err.println("No Webproperties found");
//		      } else {
//		        String firstWebpropertyId = properties.getItems().get(0).getId();		    		  

		      
		      
		      //logError("all profiles: " + analytics.management().profiles().list("~all", "~all").execute());
		      // 1: accountId
		      // 2: webPropertyId
		      
		      Profiles profiles = analytics.management().profiles().list("~all", "~all").execute();
		      data.profiles = profiles;
		      
//		      for (Profile profile : profiles.getItems()) {
//		    	  logError("View (Profile) ID: " + profile.getId());
//		    	  logError("View (Profile) Name: " + profile.getName());
//		      }
		      



		} catch ( Exception e ) {
			logError( e.toString() );
			return false;
		}
		return true;


	}

	public void dispose(StepMetaInterface smi, StepDataInterface sdi) {
		meta = (GoogleAnalyticsManagementMeta) smi;
		data = (GoogleAnalyticsManagementData) sdi;

		super.dispose(smi, sdi);
	}

	//
	// Run is were the action happens!
	public void run() {
		logBasic("Starting to run...");
		try {
			while (processRow(meta, data) && !isStopped())
				;
		} catch (Exception e) {
			logError("Unexpected error : " + e.toString());
			logError(Const.getStackTracker(e));
			setErrors(1);
			stopAll();
		} finally {
			dispose(meta, data);
			logBasic("Finished, processing " + getLinesRead() + " rows");
			markStop();
		}
	}

}
