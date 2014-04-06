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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Build;

public class RetrieveActivity extends Activity {

	public static final String PREF_NAME = "com.example.fingerscanner";
	public static final String STORE_NAME = "name";
	public static final String STORE_CARD = "card";
	public static final String STORE_DOB = "dob";
	public static final String STORE_ZIP = "zip";
	
	SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retrieve);
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		TextView name = new TextView(this);
		name.setText("Name:"+sharedpreferences.getString(STORE_NAME+sharedpreferences.getString("current_user", ""), ""));
		ll.addView(name);
		TextView creditcard = new TextView(this);
		creditcard.setText("Credit Card Number:"+sharedpreferences.getString(STORE_CARD+sharedpreferences.getString("current_user", ""), ""));
		ll.addView(creditcard);
		TextView dob = new TextView(this);
		dob.setText("Date of Birth:"+sharedpreferences.getString(STORE_DOB+sharedpreferences.getString("current_user", ""), ""));
		ll.addView(dob);
		TextView zip = new TextView(this);
		zip.setText("Zip Code:"+sharedpreferences.getString(STORE_ZIP+sharedpreferences.getString("current_user", ""), ""));
		ll.addView(zip);
		
	}


}
