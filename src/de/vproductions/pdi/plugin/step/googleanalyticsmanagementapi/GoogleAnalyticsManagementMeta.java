package de.vproductions.pdi.plugin.step.googleanalyticsmanagementapi;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.*;
import org.pentaho.di.core.database.DatabaseMeta; 
import org.pentaho.di.core.exception.*;
import org.pentaho.di.core.row.*;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.*;
import org.pentaho.di.shared.SharedObjectInterface;
import org.pentaho.di.trans.*;
import org.pentaho.di.trans.step.*;
import org.w3c.dom.Node;

public class GoogleAnalyticsManagementMeta extends BaseStepMeta implements StepMetaInterface {

	private static Class<?> PKG = GoogleAnalyticsManagementMeta.class; // for i18n purposes
	
	private String applicationName;
	private String accountId;
	private String webPropertyId;
	private String serviceAccountMail;
	private String p12File;
	
	private String outputField;
	
	
	/**
	 * @return Returns the procedure.
	 */
	public String getApplicationName() {
	  return applicationName;
	}

	/**
	 * @param procedure
	 *          The procedure to set.
	 */
	public void setApplicationName( String procedure ) {
		this.applicationName = procedure;
	}
	
	/**
	 * @return Returns the procedure.
	 */
	public String getAccountId() {
	  return accountId;
	}

	/**
	 * @param procedure
	 *          The procedure to set.
	 */
	public void setAccountId( String procedure ) {
		this.accountId = procedure;
	}
	
	/**
	 * @return Returns the procedure.
	 */
	public String getWebPropertyId() {
	  return webPropertyId;
	}

	/**
	 * @param procedure
	 *          The procedure to set.
	 */
	public void setWebPropertyId( String procedure ) {
		this.webPropertyId = procedure;
	}
	
	/**
	 * @return Returns the procedure.
	 */
	public String getServiceAccountMail() {
	  return serviceAccountMail;
	}

	/**
	 * @param procedure
	 *          The procedure to set.
	 */
	public void setServiceAccountMail( String procedure ) {
		this.serviceAccountMail = procedure;
	}
	
	/**
	 * @return Returns the procedure.
	 */
	public String getP12File() {
	  return p12File;
	}

	/**
	 * @param procedure
	 *          The procedure to set.
	 */
	public void setP12File( String procedure ) {
		this.p12File = procedure;
	}
	

	public GoogleAnalyticsManagementMeta() {
		super(); 
	}

	public String getOutputField() {
		return outputField;
	}

	public void setOutputField(String outputField) {
		this.outputField = outputField;
	}

	public String getXML() throws KettleValueException {
		StringBuffer retval = new StringBuffer();
		retval.append( "    " + XMLHandler.addTagValue( "applicationName", applicationName ) );
		retval.append( "    " + XMLHandler.addTagValue( "accountId", accountId ) );
		retval.append( "    " + XMLHandler.addTagValue( "webPropertyId", webPropertyId ) );
		retval.append( "    " + XMLHandler.addTagValue( "serviceAccountMail", serviceAccountMail ) );
		retval.append( "    " + XMLHandler.addTagValue( "p12File", p12File ) );
		retval.append( "    " + XMLHandler.addTagValue( "outputField", outputField ) );

		return retval.toString();
	}

	public void getFields(RowMetaInterface r, String origin, RowMetaInterface[] info, StepMeta nextStep, VariableSpace space) {

		ValueMetaInterface v = new ValueMeta();
		v.setName(space.environmentSubstitute(outputField)); // Spaltenname für den XML Output
		v.setType(ValueMeta.TYPE_STRING);
		v.setTrimType(ValueMeta.TRIM_TYPE_BOTH);
		v.setOrigin(origin);

		r.addValueMeta(v);
		
	}
	

	

	


	


	public Object clone() {
		Object retval = super.clone();
		return retval;
	}

	public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String, Counter> counters) throws KettleXMLException {
		readData( stepnode, databases );
		/*
		try {
			setOutputField(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "outputfield")));
		} catch (Exception e) {
			throw new KettleXMLException("Template Plugin Unable to read step info from XML node", e);
		}*/

	}

	public void setDefault() {
		outputField = "ga_xml";
		applicationName = "Pentaho Data Integration";
		accountId = "~all";
		webPropertyId = "~all";
		serviceAccountMail = "";
		p12File = "";
	}

	public void check(List<CheckResultInterface> remarks, TransMeta transmeta, StepMeta stepMeta, RowMetaInterface prev, String input[], String output[], RowMetaInterface info) {
		CheckResult cr;

		// See if we have input streams leading to this step!
		if (input.length > 0) {
			cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is receiving info from other steps.", stepMeta);
			remarks.add(cr);
		} else {
			cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, "No input received from other steps!", stepMeta);
			remarks.add(cr);
		}	
    	
	}

	public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name) {
		return new GoogleAnalyticsManagementDialog(shell, meta, transMeta, name);
	}

	public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta, Trans disp) {
		return new GoogleAnalyticsManagement(stepMeta, stepDataInterface, cnr, transMeta, disp);
	}

	public StepDataInterface getStepData() {
		return new GoogleAnalyticsManagementData();
	}
	
	private void readData( Node stepnode, List<? extends SharedObjectInterface> databases ) throws KettleXMLException {
	    try {
	    	applicationName = XMLHandler.getTagValue( stepnode, "applicationName" );
	    	accountId = XMLHandler.getTagValue( stepnode, "accountId" );
	    	webPropertyId = XMLHandler.getTagValue( stepnode, "webPropertyId" );
	    	serviceAccountMail = XMLHandler.getTagValue( stepnode, "serviceAccountMail" );
	    	p12File = XMLHandler.getTagValue( stepnode, "p12File" );
	    	outputField = XMLHandler.getTagValue( stepnode, "outputField" );

	    } catch ( Exception e ) {
	      throw new KettleXMLException( BaseMessages.getString( PKG, "RestMeta.Exception.UnableToReadStepInfo" ), e );
	    }
	  }

	public void readRep(Repository rep, ObjectId id_step, List<DatabaseMeta> databases, Map<String, Counter> counters) throws KettleException {
		try
		{
			applicationName = rep.getStepAttributeString( id_step, "applicationName" );
			accountId = rep.getStepAttributeString( id_step, "accountId" );
			webPropertyId = rep.getStepAttributeString( id_step, "webPropertyId" );
			serviceAccountMail = rep.getStepAttributeString( id_step, "serviceAccountMail" );
			p12File = rep.getStepAttributeString( id_step, "p12File" );
			
			outputField  = rep.getStepAttributeString(id_step, "outputField"); //$NON-NLS-1$
		}
		catch(Exception e)
		{
			throw new KettleException(BaseMessages.getString(PKG, "TemplateStep.Exception.UnexpectedErrorInReadingStepInfo"), e);
		}
	}

	public void saveRep(Repository rep, ObjectId id_transformation, ObjectId id_step) throws KettleException
	{
		try
		{
			rep.saveStepAttribute( id_transformation, id_step, "applicationName", applicationName );
			rep.saveStepAttribute( id_transformation, id_step, "accountId", accountId );
			rep.saveStepAttribute( id_transformation, id_step, "webPropertyId", webPropertyId );
			rep.saveStepAttribute( id_transformation, id_step, "serviceAccountMail", serviceAccountMail );
			rep.saveStepAttribute( id_transformation, id_step, "p12File", p12File );
			
			rep.saveStepAttribute(id_transformation, id_step, "outputField", outputField); //$NON-NLS-1$
		}
		catch(Exception e)
		{
			throw new KettleException(BaseMessages.getString(PKG, "TemplateStep.Exception.UnableToSaveStepInfoToRepository")+id_step, e); 
		}
	}
	

}
