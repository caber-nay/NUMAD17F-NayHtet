package edu.neu.madcourse.nayhtet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.neu.madcourse.nayhtet";
    private static final int READ_PHONE_STATE_PERMISSION = 646;
    private boolean imeiPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView verCode = (TextView) findViewById(R.id.textView1);
        String text = Integer.toString(BuildConfig.VERSION_CODE);
        verCode.setText(text);

        TextView verName = (TextView) findViewById(R.id.textView2);
        verName.setText(BuildConfig.VERSION_NAME);
    }

    // method for Send button
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    // method for About button
    public void about(View view){
        String imei;
        Intent intent = new Intent(this, About.class);

        // checking if app has permission to access IMEI
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            // Asks for user's permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},
                    READ_PHONE_STATE_PERMISSION);
        }else{
            // Already has permission so get IMEI
            imei = getIMEI();
            intent.putExtra(EXTRA_MESSAGE, imei);
            startActivity(intent);
        }
    }

    public void dictionary(View view){
        Intent intent = new Intent(this, DictionaryActivity.class);
        startActivity(intent);
    }

    // Overriding onRequestPermissionsResult to prompt user for permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_PERMISSION: {
                /*if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                }*/
            }
        }
    }

    // Method to get IMEI
    public String getIMEI(){
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    // method for Generate Error button
    public void generateError(View view){
        Intent intent = null;
        intent.putExtra(EXTRA_MESSAGE, "Error"); //intent is null, which will cause error
    }
}
