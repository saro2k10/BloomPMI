package pmi.scan.qr.storage;

import java.io.File;
import java.io.IOException;

import android.util.Log;
import pmi.scan.qr.util.PmiGlobals;

public class FileStorage {

	private File pmiTextFile;
	private static String textFilePath;
	private static String fileName ="/storage/extSdCard/HELLO";
	private static FileStorage fileStorageInstance;
	private static String msg = "FileStorage";
	private String documentName = "Pmiscan.txt";

	public FileStorage() {
		// TODO Auto-generated constructor stub
	}

	public boolean fileExist() {
		pmiTextFile = new File(textFilePath);
		return pmiTextFile.exists();
	}

	public boolean createFile(String fileName) {
		try {
			pmiTextFile = new File(fileName);
			return pmiTextFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getTextFilePath() {
		return textFilePath;
	}

	public static void setTextFilePath(String textFilePath) {
		FileStorage.textFilePath = textFilePath;
	}

	public static FileStorage getInstance() {
		Log.i(msg, "FileStorage  getInstance() - Called");
		if (fileStorageInstance == null) {
			Log.i(msg, "FileStorage  getInstance() - Instance created");
			fileStorageInstance = new FileStorage();
		}
		return fileStorageInstance;
	}

}
