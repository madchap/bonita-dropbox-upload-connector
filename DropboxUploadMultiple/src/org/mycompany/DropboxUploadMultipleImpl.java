/**
 * 
 */
package org.mycompany;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.bonitasoft.engine.connector.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxPath;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.util.IOUtil;

/**
 *The connector execution will follow the steps
 * 1 - setInputParameters() --> the connector receives input parameters values
 * 2 - validateInputParameters() --> the connector can validate input parameters values
 * 3 - connect() --> the connector can establish a connection to a remote server (if necessary)
 * 4 - executeBusinessLogic() --> execute the connector
 * 5 - getOutputParameters() --> output are retrieved from connector
 * 6 - disconnect() --> the connector can close connection to remote server (if any)
 */
public class DropboxUploadMultipleImpl extends
		AbstractDropboxUploadMultipleImpl {

	private String argAuthFile;
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	java.util.HashMap<String, String> docsToUpload = new HashMap<String,String>();
	
	
	@Override
	protected void executeBusinessLogic() throws ConnectorException{
		//Get access to the connector input parameters
		argAuthFile = getDropboxOauthFile();
		docsToUpload = (HashMap<String, String>) getDocsToProcess();
	
		// Read auth info file.
		DbxAuthInfo authInfo = null;
		try {
			authInfo = DbxAuthInfo.Reader.readFromFile(argAuthFile);
		}
		catch (JsonReader.FileLoadException ex) {
			logger.error("JSON exception reading auth file" + ex.getMessage());
		}
		
		Iterator itFiles = docsToUpload.entrySet().iterator();
		
		while (itFiles.hasNext()) {
			Map.Entry fileEntry = (Map.Entry)itFiles.next();
			String localFilePath = (String)fileEntry.getKey();
			String dropboxFilePath = (String)fileEntry.getValue();
			
			String pathError = DbxPath.findError(dropboxFilePath);
			if (pathError != null) {
				logger.error("Invalid <dropbox-path>: " + pathError);
			}

			// Create a DbxClient, which is what you use to make API calls.
			String userLocale = Locale.getDefault().toString();
			DbxRequestConfig requestConfig = new DbxRequestConfig("Bonita-multiple-upload/1.0", userLocale);
			DbxClient dbxClient = new DbxClient(requestConfig, authInfo.accessToken, authInfo.host);

			// Make the API call to upload the file.
			DbxEntry.File metadata = null;
			try {
				InputStream in = new FileInputStream(localFilePath);
				try {
					metadata = dbxClient.uploadFile(dropboxFilePath, DbxWriteMode.add(), -1, in);
				} catch (DbxException ex) {
					logger.error("Error uploading to Dropbox: " + ex.getMessage());
				} finally {
					IOUtil.closeInput(in);
				}
			}
			catch (IOException ex) {
				logger.error("Error reading from file \"" + localFilePath + "\": " + ex.getMessage());
			}
			//WARNING : Set the output of the connector execution. If outputs are not set, connector fails
			//setJson_return_output(metadata.toStringMultiline());
		}
	 }

	@Override
	public void connect() throws ConnectorException{
		//[Optional] Open a connection to remote server
	
	}

	@Override
	public void disconnect() throws ConnectorException{
		//[Optional] Close connection to remote server
	
	}

}
