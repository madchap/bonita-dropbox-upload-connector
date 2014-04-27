package org.mycompany;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

public abstract class AbstractDropboxUploadImpl extends AbstractConnector {

	protected final static String OAUTH2_JSON_FILE_INPUT_PARAMETER = "oauth2_json_file";
	protected final static String LOCAL_FILE_PATH_INPUT_PARAMETER = "local_file_path";
	protected final static String DROPBOX_FILE_PATH_INPUT_PARAMETER = "dropbox_file_path";
	protected final String JSON_RETURN_OUTPUT_OUTPUT_PARAMETER = "json_return_output";

	protected final java.lang.String getOauth2_json_file() {
		return (java.lang.String) getInputParameter(OAUTH2_JSON_FILE_INPUT_PARAMETER);
	}

	protected final java.lang.String getLocal_file_path() {
		return (java.lang.String) getInputParameter(LOCAL_FILE_PATH_INPUT_PARAMETER);
	}

	protected final java.lang.String getDropbox_file_path() {
		return (java.lang.String) getInputParameter(DROPBOX_FILE_PATH_INPUT_PARAMETER);
	}

	protected final void setJson_return_output(
			java.lang.String json_return_output) {
		setOutputParameter(JSON_RETURN_OUTPUT_OUTPUT_PARAMETER,
				json_return_output);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getOauth2_json_file();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"oauth2_json_file type is invalid");
		}
		try {
			getLocal_file_path();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"local_file_path type is invalid");
		}
		try {
			getDropbox_file_path();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException(
					"dropbox_file_path type is invalid");
		}

	}

}
