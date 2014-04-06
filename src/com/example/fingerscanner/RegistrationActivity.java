package com.example.fingerscanner;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class RegistrationActivity extends Activity implements OnClickListener{

	Button backButton;
	Button submitButton;
	public static final String PREF_NAME = "data";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		backButton = (Button) findViewById(R.id.registrationback);
		backButton.setOnClickListener(this);
		submitButton = (Button) findViewById(R.id.registrationsubmit);
		submitButton.setOnClickListener(this);
		
	}
	
	public void onClick(View v){
		int id= v.getId();
		if (id==backButton.getId()){
			Intent registrationToHome = new Intent(RegistrationActivity.this,MainActivity.class);
			RegistrationActivity.this.startActivity(registrationToHome);
		}
		
		else if(id==submitButton.getId()){
			SharedPreferences prefs = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
			Editor editor = prefs.edit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.registration,
					container, false);
			return rootView;
		}
	}

}
