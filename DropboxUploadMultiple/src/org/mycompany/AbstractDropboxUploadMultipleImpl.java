package org.mycompany;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

public abstract class AbstractDropboxUploadMultipleImpl extends
		AbstractConnector {

	protected final static String DROPBOXOAUTHFILE_INPUT_PARAMETER = "dropboxOauthFile";
	protected final static String DOCSTOPROCESS_INPUT_PARAMETER = "docsToProcess";

	protected final java.lang.String getDropboxOauthFile() {
		return (java.lang.String) getInputParameter(DROPBOXOAUTHFILE_INPUT_PARAMETER);
	}

	protected final java.util.Map getDocsToProcess() {
		return (java.util.Map) getInputParameter(DOCSTOPROCESS_INPUT_PARAMETER);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getDropboxOauthFile();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"dropboxOauthFile type is invalid");
		}
		try {
			getDocsToProcess();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"docsToProcess type is invalid");
		}

	}

}
