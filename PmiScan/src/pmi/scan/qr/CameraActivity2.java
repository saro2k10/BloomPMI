package pmi.scan.qr;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.R.layout;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import pmi.scan.qr.storage.FileStorage;
import pmi.scan.qr.storage.impl.ExternalStorageImpl;
import pmi.scan.qr.util.PmiGlobals;
import android.view.Display;
import android.content.res.Configuration;
import android.view.WindowManager;

public class CameraActivity2 extends Activity {
	private ExternalStorageImpl externalStorageImpl;
	private IntentIntegrator scanIntegrator;
	//private String msg = "@CameraActivity@";
	private String documentName = "Pmiscan.txt";
	private static String barcodeSaved = " QR Code Saved ";
	BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	PmiGlobals globals;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		externalStorageImpl = new ExternalStorageImpl();

		if (savedInstanceState == null) {

			globals = PmiGlobals.getInstance();
			externalStorageImpl.createFile(bluetoothAdapter.getName());

			globals.setDeviceName(bluetoothAdapter.getName() + bluetoothAdapter.getAddress());
			globals.setCameraId((Integer) getFrontCameraId());

			initiateScan();
		}
	}

	public void initiateScan() {
		scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.addExtra("SCAN_CAMERA_ID", globals.getCameraId());
		scanIntegrator.initiateScan(scanIntegrator.QR_CODE_TYPES);
	}

	private Object getFrontCameraId() {
		int cameraId = -1;
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				cameraId = i;
				return cameraId;
			}
		}
		return cameraId;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

		if (scanningResult.getContents() == null || scanningResult.getContents() == null || globals.getCourceId().trim().equals("Course ID")) {
			Intent callAdmin = new Intent(CameraActivity2.this, AdminActivity.class);
			startActivity(callAdmin);
		} else {

			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
			final int orientation = display.getOrientation();

			String space = "   |   ";
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			String deviceName = bluetoothAdapter.getName();
			String content = scanContent + space + globals.getCourceId() + space + getCurrentDateAndTime() + space + deviceName;
			externalStorageImpl.writeFile(content, deviceName);

			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
			if (orientation == 1) {
				infoToast(barcodeSaved);
			}
			initiateScan();
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
		Toast toast = Toast.makeText(getApplicationContext(), infoString, Toast.LENGTH_SHORT);
		View view = toast.getView();
		view.setBackgroundResource(R.drawable.toastborder3);
		// toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER, 0, 0);
		toast.setGravity(Gravity.NO_GRAVITY, 0, 0);
		toast.setView(view);
		toast.show();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		}
		return super.onKeyDown(keyCode, event);
	}

	public String getCurrentDateAndTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		String formattedDate = df.format(c.getTime());

		return formattedDate;
	}

}
