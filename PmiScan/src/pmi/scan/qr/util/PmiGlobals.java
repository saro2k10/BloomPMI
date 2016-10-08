package pmi.scan.qr.util;

import android.util.Log;

public class PmiGlobals {

	private static PmiGlobals instance;
	private static String deviceName;
	private static int cameraId;
	//private static String msg = "PmiGlobals";
	private static String courceId="Course ID";
	

	public PmiGlobals() {
	}

	public static String getDeviceName() {
		return PmiGlobals.deviceName;
	}

	public static void setDeviceName(String deviceName) {
		PmiGlobals.deviceName = deviceName;
	}

	public static int getCameraId() {
		return PmiGlobals.cameraId;
	}

	public static void setCameraId(int cameraId) {
		PmiGlobals.cameraId = cameraId;
	}
	
	public static String getCourceId() {
		//Log.i(msg, "PmiGlobals  getCourceId() - Called : " +courceId);
		return courceId;
	}

	public static void setCourceId(String courceId) {
		//Log.i(msg, "PmiGlobals  setCourceId() - Called : " +courceId);
		PmiGlobals.courceId = courceId;
	}

	public static PmiGlobals getInstance() {
		//Log.i(msg, "PmiGlobals  getInstance() - Called");
		if (instance == null) {
			//Log.i(msg, "PmiGlobals  getInstance() - Instance created");
			instance = new PmiGlobals();
		}
		return instance;
	}

}
