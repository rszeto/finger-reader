package com.example.fingerscanner;

import SecuGen.FDxSDKPro.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "SecuGen USB";
	
	Button regButton;
	Button retrieveButton;

	private PendingIntent mPermissionIntent;

	private JSGFPLib sgfplib;

	private boolean mLed;

	private int mImageWidth;

	private int mImageHeight;

	private int[] mMaxTemplateSize;

	//private byte[] mRegisterTemplate;

	//private byte[] mVerifyTemplate;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regButton = (Button) findViewById(R.id.register);
        regButton.setOnClickListener(this);
        retrieveButton = (Button) findViewById(R.id.retrieve);
        retrieveButton.setOnClickListener(this);
        
        mMaxTemplateSize = new int[1];
        
        //USB Permissions
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
       	IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
       	registerReceiver(mUsbReceiver, filter);
        sgfplib = new JSGFPLib((UsbManager)getSystemService(Context.USB_SERVICE));
		debugMessage("jnisgfplib version: " + sgfplib.Version() + "\n");
		mLed = false;		
    }
    
    @Override
    public void onResume() {
        super.onResume();
        long error = sgfplib.Init( SGFDxDeviceName.SG_DEV_AUTO);
        if (error != SGFDxErrorCode.SGFDX_ERROR_NONE){
        	AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        	if (error == SGFDxErrorCode.SGFDX_ERROR_DEVICE_NOT_FOUND)
        		dlgAlert.setMessage("The attached fingerprint device is not supported on Android");
        	else
        		dlgAlert.setMessage("Fingerprint device initialization failed!");
        	dlgAlert.setTitle("SecuGen Fingerprint SDK");
        	dlgAlert.setPositiveButton("OK",
        			new DialogInterface.OnClickListener() {
        		      public void onClick(DialogInterface dialog,int whichButton){
        		        	finish();
        		        	return;        		    	  
        		      }        			
        			}
        	);
        	dlgAlert.setCancelable(false);
        	dlgAlert.create().show();        	
        }
        else {
	        UsbDevice usbDevice = sgfplib.GetUsbDevice();
	        if (usbDevice == null){
	        	AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
	        	dlgAlert.setMessage("SDU04P or SDU03P fingerprint sensor not found!");
	        	dlgAlert.setTitle("SecuGen Fingerprint SDK");
	        	dlgAlert.setPositiveButton("OK",
	        			new DialogInterface.OnClickListener() {
	        		      public void onClick(DialogInterface dialog,int whichButton){
	        		        	finish();
	        		        	return;        		    	  
	        		      }        			
	        			}
	        	);
	        	dlgAlert.setCancelable(false);
	        	dlgAlert.create().show();
	        }
	        else {
		        sgfplib.GetUsbManager().requestPermission(usbDevice, mPermissionIntent);
		        error = sgfplib.OpenDevice(0);
				debugMessage("OpenDevice() ret: " + error + "\n");
		        SecuGen.FDxSDKPro.SGDeviceInfoParam deviceInfo = new SecuGen.FDxSDKPro.SGDeviceInfoParam();
		        error = sgfplib.GetDeviceInfo(deviceInfo);
				debugMessage("GetDeviceInfo() ret: " + error + "\n");
		    	mImageWidth = deviceInfo.imageWidth;
		    	mImageHeight= deviceInfo.imageHeight;
		        sgfplib.SetTemplateFormat(SGFDxTemplateFormat.TEMPLATE_FORMAT_SG400);
				sgfplib.GetMaxTemplateSize(mMaxTemplateSize);
				debugMessage("TEMPLATE_FORMAT_SG400 SIZE: " + mMaxTemplateSize[0] + "\n");
//		        boolean smartCaptureEnabled = this.mCheckBoxSCEnabled.isChecked();
//		        if (smartCaptureEnabled)
		        	sgfplib.writeData((byte)5, (byte)1);
//		        else
//		        	sgfplib.writeData((byte)5, (byte)0);
	        }
        }
    }
    
    @Override
    public void onPause() {
    	sgfplib.CloseDevice();
        super.onPause();
    }
    
    @Override
    public void onDestroy() {
    	sgfplib.CloseDevice();
    	sgfplib.Close();
        super.onDestroy();
    }

    public void onClick(View v){
    	int id = v.getId();
    	
    	if(id==regButton.getId()){
    		Intent homeToRegistration = new Intent(MainActivity.this,RegistrationActivity.class);
    		MainActivity.this.startActivity(homeToRegistration);
    	}
    	
    	else if (id==retrieveButton.getId()){
    		
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void debugMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
  //This broadcast receiver is necessary to get user permissions to access the attached USB device
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
    	public void onReceive(Context context, Intent intent) {
    		String action = intent.getAction();
    		//DEBUG Log.d(TAG,"Enter mUsbReceiver.onReceive()");
    		if (ACTION_USB_PERMISSION.equals(action)) {
    			synchronized (this) {
    				UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
    				if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
    					if(device != null){
    						//DEBUG Log.d(TAG, "Vendor ID : " + device.getVendorId() + "\n");
    						//DEBUG Log.d(TAG, "Product ID: " + device.getProductId() + "\n");
    						debugMessage("Vendor ID : " + device.getVendorId() + "\n");
    						debugMessage("Product ID: " + device.getProductId() + "\n");
    					}
    					else
        					Log.e(TAG, "mUsbReceiver.onReceive() Device is null");    						
    				} 
    				else
    					Log.e(TAG, "mUsbReceiver.onReceive() permission denied for device " + device);    				
    			}
    		}
    	}
    };  
}
