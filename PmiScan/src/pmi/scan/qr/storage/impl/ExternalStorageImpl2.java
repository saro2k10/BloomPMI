package pmi.scan.qr.storage.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
/*import jxl.Cell;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.Orientation;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;*/
import pmi.scan.qr.storage.ExternalStorage;

public class ExternalStorageImpl2 implements ExternalStorage {
	//private String EXT_STORAGE_TAG = "ExternalStorage";
	File root = new File(Environment.getExternalStorageDirectory(), "PMI");
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
		     
	//CSV file header
	private static final String FILE_HEADER = "MEMBER ID,COURSE ID,TIMESTAMP,DEVICE NAME";

	public ExternalStorageImpl2() {
	}

	public boolean getExternalStorageStatus() {
		return isExternalStorageWritable();
	}

	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	private boolean isSDCardMountedAndAvialble() {
		File sdCardFiles = new File("/mnt/extSdCard");
		File listFiles[] = sdCardFiles.listFiles();

		try {
			if (sdCardFiles.listFiles().length > 0) {
				for (File sd : listFiles) {
					// Log.i(EXT_STORAGE_TAG, "files =============> " +
					// sd.getName() + " Path : " + sd.getAbsolutePath());
				}

				return true;
			}
		} catch (NullPointerException o) {
			return false;
		}
		return false;
	}

	public boolean isFileAvailable(String fileName) {
		File pmiFile = new File(root, fileName.trim() + ".csv");

		if (pmiFile.exists())
			return true;

		return false;
	}

	// for CSV
	public boolean createFile(String fileName) {

		try {
			
			File root = new File(Environment.getExternalStorageDirectory(), "PMI");
			File pmiFile = new File(root, fileName.trim() + ".csv");

			if (!root.exists()) {
				root.mkdirs();
				FileWriter writer = new FileWriter(pmiFile);
				pmiFile.createNewFile();

				writer.append(FILE_HEADER.toString());
				writer.append(NEW_LINE_SEPARATOR);

				writer.flush();
				writer.close();
			} else {
				// Log.i(EXT_STORAGE_TAG, "PMI FOLDER EXIST (:):):):) ");
			}

			if (pmiFile.exists()) {
				// Log.i(EXT_STORAGE_TAG, fileName + " File EXISTED ^^^^^^^^^^^^
				// ");

			} else {
				FileWriter writer = new FileWriter(pmiFile);
				pmiFile.createNewFile();

				writer.append(FILE_HEADER.toString());
				writer.append(NEW_LINE_SEPARATOR);

				writer.flush();
				writer.close();
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// for CSV
	public boolean writeFile(String content, String fileName) {

	try {
		File root = new File(Environment.getExternalStorageDirectory(), "PMI");
		File pmiFile = new File(root, fileName.trim() + ".csv");
		FileWriter writer = new FileWriter(pmiFile, true);

		writer.append(content + "\n");
		writer.flush();
		writer.close();

	} catch (IOException e) {
		e.printStackTrace();
	}

		return false;
	}
	
	
	public String readFile(String fileName) {
	String courseId = "Course ID";
	String last = "Last Line";
	int count = 0;
	try {
		File root = new File(Environment.getExternalStorageDirectory(), "PMI");
		File pmiFile = new File(root, fileName.trim() + ".csv");
		//Log.i(EXT_STORAGE_TAG, "+++++readFile ++++++++");
		BufferedReader input = new BufferedReader(new FileReader(pmiFile));
		List list = new ArrayList();

		for (String line; (line = input.readLine()) != null;) {
			if (!line.isEmpty() && !line.startsWith("*")) {
				// Log.i(EXT_STORAGE_TAG, " Count => " + count + " Line =>
				// "+line);
				//Log.i(EXT_STORAGE_TAG, "+++++readFile line++++++++"+line);
				list.add(line);
			}
			count++;
		}
		input.close();

		if (list.size() >= 2) {
			
			courseId = callTokenize(list.get(list.size() - 1).toString());
			//Log.i(EXT_STORAGE_TAG, "+++++readFile ++++++++: "+courseId);
		}
		//Log.i(EXT_STORAGE_TAG, "+++++readFile courseId++++++++"+courseId);
		return courseId;
	} catch (IOException e) {
		e.printStackTrace();
	}
	//Log.i(EXT_STORAGE_TAG, "+++++readFile courseId++++++++"+courseId);
	return courseId;
}


	public String callTokenize(String lastEntry) {
		String courseToken = "empty";
		int count = 0;
		String space = ",";

		StringTokenizer st = new StringTokenizer(lastEntry, space);
		//Log.i(EXT_STORAGE_TAG, "+++++callTokenize++++++++"+st);
		while (st.hasMoreElements()) {
			count++;
			st.nextElement();
			if (count == 1) {
				courseToken = st.nextElement().toString();
				//Log.i(EXT_STORAGE_TAG, "+++++courseToken++++++++"+courseToken);
			}
		}

		return courseToken;
	}

	public File getDocumentStatus(String document) {
		return getDocumentStorageDir(document);
	}

	private File getDocumentStorageDir(String document) {
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), document);
		return file;
	}

}
