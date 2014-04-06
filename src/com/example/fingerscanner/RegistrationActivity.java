package com.example.fingerscanner;

import java.io.FileOutputStream;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegistrationActivity extends Activity implements OnClickListener {

	Button photoSelect;
	Button backButton;
	Button submitButton;
	EditText nameInput;
	EditText cardInput;
	EditText zipcode;
	EditText dateOfBirth;
	ImageView photo;

	public static final String PREF_NAME = "com.example.fingerscanner";
	private static final int SELECT_PHOTO = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		photo = (ImageView) findViewById(R.id.photo);
		photoSelect = (Button) findViewById(R.id.choosephoto);
		photoSelect.setOnClickListener(this);

		backButton = (Button) findViewById(R.id.registrationback);
		backButton.setOnClickListener(this);

		submitButton = (Button) findViewById(R.id.registrationsubmit);
		submitButton.setOnClickListener(this);

		nameInput = (EditText) findViewById(R.id.nameinput);
		cardInput = (EditText) findViewById(R.id.cardinput);
		zipcode = (EditText) findViewById(R.id.zipcodeinput);
		dateOfBirth = (EditText) findViewById(R.id.dobinput);

	}

	public void onClick(View v) {
		int id = v.getId();
		if (id == backButton.getId()) {
			Intent registrationToHome = new Intent(RegistrationActivity.this,
					MainActivity.class);
			RegistrationActivity.this.startActivity(registrationToHome);
		}

		else if (id == submitButton.getId()) {

			// retrieve info from registration screen
			String name = nameInput.getText().toString();
			String card = cardInput.getText().toString();
			String zip = zipcode.getText().toString();
			String birth = dateOfBirth.getText().toString();

			SharedPreferences prefs = getSharedPreferences(PREF_NAME,
					Context.MODE_PRIVATE);
			Editor editor = prefs.edit();

			// get counter, return 0 if there is no counter
			int ct = prefs.getInt("counter", 0);

			// add info to database
			editor.putString("name" + ct, name);
			editor.putString("card" + ct, card);
			editor.putString("zip" + ct, zip);
			editor.putString("dob" + ct, birth);

			// increment counter
			ct++;
			editor.putInt("counter", ct);

			editor.commit();

			Intent backToHome = new Intent(RegistrationActivity.this,
					MainActivity.class);
			RegistrationActivity.this.startActivity(backToHome);
		}

		else if (id == photoSelect.getId()) {
			Intent photoPickerIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(photoPickerIntent, SELECT_PHOTO);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
		SharedPreferences prefs = getSharedPreferences(PREF_NAME, 0);
		int ct = prefs.getInt("counter", -1);
		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				Bundle extras = imageReturnedIntent.getExtras();
				Bitmap imageBitmap = (Bitmap) extras.get("data");
				photo.setImageBitmap(imageBitmap);

				FileOutputStream out;
				try {
					out = new FileOutputStream("photo_" + ct);
					imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
			View rootView = inflater.inflate(R.layout.registration, container,
					false);
			return rootView;
		}
	}

}
