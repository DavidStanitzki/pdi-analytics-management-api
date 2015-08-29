 package de.vproductions.pdi.plugin.step.googleanalyticsmanagementapi;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;
import com.google.api.services.analytics.model.Profiles;

public class GoogleAnalyticsManagementData extends BaseStepData implements StepDataInterface {

	public RowMetaInterface outputRowMeta;
	
	private String applicationName;
	private String accountId;
	private String webPropertyId;
	private String serviceAccountMail;
	private String p12File;
	
	public Profiles profiles;
	
    public GoogleAnalyticsManagementData()
	{
		super();
		this.applicationName = null;
		this.accountId = null;
		this.webPropertyId = null;
		this.serviceAccountMail = null;
		this.p12File = null;
	}
}
	
