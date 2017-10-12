package edu.neu.madcourse.nayhtet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Console;

public class About extends AppCompatActivity {
    private static final int READ_PHONE_STATE_PERMISSION = 646;
    String imei = "Permission required to display IMEI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Intent intent = getIntent();
        // checking if app has permission to access IMEI
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            // Asks for user's permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},
                    READ_PHONE_STATE_PERMISSION);
        }else {
            // Already has permission so get IMEI
            imei = getIMEI();
        }
        TextView textView = (TextView) findViewById(R.id.aboutTextView4);
        textView.setText("IMEI: "+imei);
    }

    // Overriding onRequestPermissionsResult to prompt user for permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                }
            }
        }
    }

    // Method to get IMEI
    public String getIMEI(){
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
