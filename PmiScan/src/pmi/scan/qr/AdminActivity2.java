package pmi.scan.qr;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import pmi.scan.qr.storage.impl.ExternalStorageImpl;
import pmi.scan.qr.util.PmiGlobals;

public class AdminActivity2 extends Activity implements OnClickListener {

	private Button scanBtn, updateCourseId;
	private EditText courseId;
	private String msg = "#AdminActivity#";

	private String documentName = "Pmiscan.txt";
	private ExternalStorageImpl externalStorageImpl;
	private IntentIntegrator scanIntegrator;
	private static String missingCourseid = "Course ID is required...";
	private static String updatedCourseid = "Course ID is updated...";
	PmiGlobals globals;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		externalStorageImpl = new ExternalStorageImpl();
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		globals = PmiGlobals.getInstance();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_activity);

		if (savedInstanceState == null) {
			externalStorageImpl.createFile(bluetoothAdapter.getName());
			globals = PmiGlobals.getInstance();
		}

		scanBtn = (Button) findViewById(R.id.scan_button);
		updateCourseId = (Button) findViewById(R.id.update_course_button);
		courseId = (EditText) findViewById(R.id.course_id);
		if (externalStorageImpl.isFileAvailable(bluetoothAdapter.getName().trim())) {
			String id = externalStorageImpl.readFile(bluetoothAdapter.getName()).trim();
			String globalId = globals.getCourceId().trim();

			if (!globalId.equals("Course ID")) {
				courseId.setText(globalId);
			} else if (!id.equals("Course ID") && !id.equals("")) {
				courseId.setText(id);
			} else {
				scanBtn.setEnabled(false);
			}
		}

		scanBtn.setOnClickListener(this);
		updateCourseId.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		if (v.getId() == R.id.update_course_button) {

			if (!courseId.getText().toString().trim().isEmpty() && !courseId.getText().toString().trim().equals("Course ID")) {
				globals.setCourceId(courseId.getText().toString());
				infoToast(updatedCourseid);
				scanBtn.setEnabled(true);
			} else {

				warningToast(missingCourseid);
				scanBtn.setEnabled(false);
			}
		}

		if (v.getId() == R.id.scan_button) {
			if (!courseId.getText().toString().trim().isEmpty() && !courseId.getText().toString().trim().equals("Course ID")) {
				globals.setCourceId(courseId.getText().toString());
				Intent callAdmin = new Intent(AdminActivity2.this, CameraActivity.class);
				startActivity(callAdmin);
			} else {
				warningToast(missingCourseid);
				scanBtn.setEnabled(false);
			}

		}

	}

	public void warningToast(String warningString) {
		Toast toast = Toast.makeText(getApplicationContext(), warningString, Toast.LENGTH_LONG);
		View vieew = toast.getView();
		vieew.setBackgroundResource(R.drawable.toastborder2);
		toast.setView(vieew);
		toast.show();
	}

	public void infoToast(String infoString) {
		Toast toast = Toast.makeText(getApplicationContext(), infoString, Toast.LENGTH_LONG);
		View vieew = toast.getView();
		vieew.setBackgroundResource(R.drawable.toastborder3);
		toast.setView(vieew);
		toast.show();
	}

	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart() {
		super.onStart();
	}

	/** Called when the activity has become visible. */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/** Called when another activity is taking focus. */
	@Override
	protected void onPause() {
		super.onPause();
	}

	/** Called just before the activity is destroyed. */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void onBackPress() {
		moveTaskToBack(true);
		AdminActivity2.this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent callQrActivity = new Intent(AdminActivity2.this, QrMainActivity.class);
			startActivity(callQrActivity);
		}
		return super.onKeyDown(keyCode, event);
	}

}
