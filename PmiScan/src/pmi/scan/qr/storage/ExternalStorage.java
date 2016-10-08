/**
 * 
 */
package pmi.scan.qr.storage;

import java.io.File;

public interface ExternalStorage {
	public boolean getExternalStorageStatus ();
	public File getDocumentStatus(String docuemnt);
}
