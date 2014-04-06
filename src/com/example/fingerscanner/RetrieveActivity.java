package com.example.fingerscanner;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Build;

public class RetrieveActivity extends Activity implements OnClickListener{

	Button backButton;
	Button submitButton;
	EditText userId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retrieve);
		backButton = (Button) findViewById(R.id.retrieveback);
		backButton.setOnClickListener(this);
		submitButton = (Button) findViewById(R.id.retrievesubmit);
		submitButton.setOnClickListener(this);
		userId = (EditText) findViewById(R.id.idnumber);
	}
	public void onClick(View v){
		int id = v.getId();

		if (id==backButton.getId()){
		Intent retrieveToHome = new Intent(RetrieveActivity.this,
		MainActivity.class);
		RetrieveActivity.this.startActivity(retrieveToHome);
		}

		else if (id==submitButton.getId()){
		String number = userId.getText().toString();
		int ct = Integer.parseInt(number);
		Intent retrieveToDisplay = new Intent(RetrieveActivity.this,InfoDisplayActivity.class);
		retrieveToDisplay.putExtra("ct", ct);
		RetrieveActivity.this.startActivity(retrieveToDisplay);

		}
	}


}
