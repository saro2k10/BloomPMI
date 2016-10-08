package pmi.scan.qr.storage.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import pmi.scan.qr.storage.ExternalStorage;

public class ExternalStorageImpl implements ExternalStorage {
	private String EXT_STORAGE_TAG = "ExternalStorage";

	public ExternalStorageImpl() {
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
		File root = new File(Environment.getExternalStorageDirectory(), "PMI");
		File pmiFile = new File(root, fileName + ".xlsx");

		if (pmiFile.exists())
			return true;

		return false;
	}

	public boolean createFile(String fileName) {

		try {
			File root = new File(Environment.getExternalStorageDirectory(), "PMI");
			File pmiFile = new File(root, fileName + ".xlsx");

			if (!root.exists()) {
				root.mkdirs();
				FileWriter writer = new FileWriter(pmiFile);
				pmiFile.createNewFile();

				String row1 = "*******************************************************************************************\n";
				String row2 = "**  MEMBER ID     COURSE ID     TIMESTAMP     DEVICE NAME      **\n";
				String row3 = "*******************************************************************************************\n";
				writer.append(row1 + row2 + row3);

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

				String row1 = "*******************************************************************************************\n";
				String row2 = "**  MEMBER ID     COURSE ID     TIMESTAMP     DEVICE NAME      **\n";
				String row3 = "*******************************************************************************************\n";

				writer.append(row1 + row2 + row3);
				writer.flush();
				writer.close();
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean writeFile(String content, String fileName) {

		try {
			File root = new File(Environment.getExternalStorageDirectory(), "PMI");
			File pmiFile = new File(root, fileName + ".xlsx");
			FileWriter writer = new FileWriter(pmiFile, true);

			writer.append(content + "\n\n");
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
			File pmiFile = new File(root, fileName + ".xlsx");

			BufferedReader input = new BufferedReader(new FileReader(pmiFile));
			List list = new ArrayList();

			for (String line; (line = input.readLine()) != null;) {
				if (!line.isEmpty() && !line.startsWith("*")) {
					// Log.i(EXT_STORAGE_TAG, " Count => " + count + " Line =>
					// "+line);
					list.add(line);
				}
				count++;
			}
			input.close();

			if (list.size() >= 1) {
				courseId = callTokenize(list.get(list.size() - 1).toString());
			}
			return courseId;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return courseId;
	}

	public String callTokenize(String lastEntry) {
		String courseToken = "empty";
		int count = 0;
		String space = "|";

		StringTokenizer st = new StringTokenizer(lastEntry, space);
		while (st.hasMoreElements()) {
			count++;
			st.nextElement();
			if (count == 1) {
				courseToken = st.nextElement().toString();
			}
		}

		return courseToken;
	}

	private boolean isPmiDirCreated() {

		try {
			File root = new File(Environment.getExternalStorageDirectory(), "PMI");
			if (!root.exists()) {
				root.mkdirs();
			}
			File gpxfile = new File(root, "saran.xlsx");
			if (gpxfile.exists()) {
			}
			FileWriter writer = new FileWriter(gpxfile);
			writer.flush();
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	public File getDocumentStatus(String document) {
		return getDocumentStorageDir(document);
	}

	private File getDocumentStorageDir(String document) {
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), document);
		return file;
	}

}
