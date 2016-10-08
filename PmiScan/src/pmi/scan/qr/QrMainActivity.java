package pmi.scan.qr;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QrMainActivity extends Activity implements OnClickListener {

	private Button signinBtn;
	private EditText userName, password;
	private static String loginFaild="Invalid Credential Passed - Please try again.";
	
	private static String loginPass="    Welcome to PMI   ";
	//String msg = "*QrMainActivity*";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		signinBtn = (Button) findViewById(R.id.signin_button);
		userName = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);

		signinBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

		if (v.getId() == R.id.signin_button) {
			if (userName.getText().toString().equals("admin") && password.getText().toString().equals("password")) {
				Intent callAdmin = new Intent(QrMainActivity.this, AdminActivity.class);
				infoToast(loginPass);
				startActivity(callAdmin);
			} else {
				warningToast(loginFaild);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent settingsIntent = new Intent(getApplicationContext(), QrMainActivity.class);
			startActivity(settingsIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void warningToast(String warningString){
		Toast toast = Toast.makeText(getApplicationContext(), warningString, Toast.LENGTH_LONG);
		View vieew = toast.getView();
		vieew.setBackgroundResource(R.drawable.toastborder2);
		toast.setView(vieew);
		toast.show();
	}
	
	public void infoToast(String infoString){
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

	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop() {
		super.onStop();
	}

	/** Called just before the activity is destroyed. */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void onBackPress() {
		QrMainActivity.this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			QrMainActivity.this.finishActivity(0);
			QrMainActivity.this.finishAffinity();

		}
		QrMainActivity.this.finishActivity(0);
		return super.onKeyDown(keyCode, event);
	}

}
