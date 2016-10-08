package pmi.scan.qr.service;

import com.google.zxing.integration.android.IntentIntegrator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import pmi.scan.qr.storage.impl.ExternalStorageImpl;



public class CameraService extends Service{
	private String msg="%%%%%%%%% CameraService %%%%%%%";
	private String documentName = "Pmiscan.txt";
	private ExternalStorageImpl externalStorageImpl;
	
	public CameraService() {
		Log.i(msg, "CameraService() CONSTRUCTOR---- ");
	}
	
	 /** indicates how to behave if the service is killed */
	   int mStartMode;
	   
	   /** interface for clients that bind */
	   IBinder mBinder;     
	   
	   /** indicates whether onRebind should be used */
	   boolean mAllowRebind;

	   /** Called when the service is being created. */
	   @Override
	   public void onCreate() {
		   
		   Log.i(msg, "onCreate() ---- ");
		   
		   externalStorageImpl = new ExternalStorageImpl();
			Log.i(msg, "External Storage mount status : " + externalStorageImpl.getExternalStorageStatus());
			Log.i(msg, "Document status : " + externalStorageImpl.getDocumentStatus(documentName));
			
	   }

	   /** The service is starting, due to a call to startService() */
	   @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
		   Log.i(msg, "onStartCommand() -------------> ");
	      return START_STICKY;
	   }

	   /** A client is binding to the service with bindService() */
	   @Override
	   public IBinder onBind(Intent intent) {
		   Log.i(msg, "onBind() ---- ");
	      return mBinder;
	   }

	   /** Called when all clients have unbound with unbindService() */
	   @Override
	   public boolean onUnbind(Intent intent) {
		   Log.i(msg, "onUnbind() ---- ");
	      return mAllowRebind;
	   }

	   /** Called when a client is binding to the service with bindService()*/
	   @Override
	   public void onRebind(Intent intent) {
		   Log.i(msg, "onRebind() ---- ");

	   }

	   /** Called when The service is no longer used and is being destroyed */
	   @Override
	   public void onDestroy() {
		   Log.i(msg, "onDestroy() ---- ");

	   }
	   
	

}
