package com.example.fingerscanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Build;

public class InfoDisplayActivity extends Activity {
	
	public static final String PREF_NAME = "com.example.fingerscanner";
	public static final String STORE_NAME = "name";
	public static final String STORE_CARD = "card";
	public static final String STORE_DOB = "dob";
	public static final String STORE_ZIP = "zip";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		SharedPreferences prefs = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
		
		Intent it = getIntent();
		int ct = it.getIntExtra("ct", -1);
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.infolayout);
		ll.setOrientation(LinearLayout.VERTICAL);
		TextView name = new TextView(this);
		name.setText("Name:"+prefs.getString(STORE_NAME+ct, ""));
		ll.addView(name);
		String filename=Environment.getExternalStorageDirectory().toString()+"/fingerprintscanner/photo_"+ct+".png";
		Bitmap bitmap = BitmapFactory.decodeFile(filename);
		ImageView image= new ImageView(this);
		image.setImageBitmap(bitmap);
		ll.addView(image);
		
		
		
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
			View rootView = inflater.inflate(R.layout.info,
					container, false);
			return rootView;
		}
	}

}
