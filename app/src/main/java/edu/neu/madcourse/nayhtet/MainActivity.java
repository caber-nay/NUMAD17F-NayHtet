package edu.neu.madcourse.nayhtet;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "edu.neu.madcourse.nayhtet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent intent = new Intent(this, About.class);
        //String imei = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String imei = UUID.randomUUID().toString();
        //TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //imei = telephonyManager.getDeviceId();

        intent.putExtra(EXTRA_MESSAGE, imei);
        startActivity(intent);
    }
    // method for Generate Error button
    public void generateError(View view){
        Intent intent = null;
        intent.putExtra(EXTRA_MESSAGE, "Error"); //intent is null, which will cause error
    }
}
