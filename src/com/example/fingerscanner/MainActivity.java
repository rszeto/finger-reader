package com.example.fingerscanner;

import SecuGen.FDxSDKPro.JSGFPLib;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	Button regButton;
	Button retrieveButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regButton = (Button) findViewById(R.id.register);
        regButton.setOnClickListener(this);
        retrieveButton = (Button) findViewById(R.id.retrieve);
        retrieveButton.setOnClickListener(this);
    }

    public void onClick(View v){
    	int id = v.getId();
    	
    	if(id==regButton.getId()){
    		Intent homeToRegistration = new Intent(MainActivity.this,RegistrationActivity.class);
    		MainActivity.this.startActivity(homeToRegistration);
    	}
    	
    	else if (id==retrieveButton.getId()){
    		Intent homeToRegistration = new Intent(MainActivity.this,RegistrationActivity.class);
    		MainActivity.this.startActivity(homeToRegistration);
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
