package edu.neu.madcourse.nayhtet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Nay Myo Htet on 9/26/2017.
 */

public class DictionaryAcknowledgmentsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_acknowledgments);
        setTitle("Acknowledgments (Dictionary)");
        TextView textView = (TextView)findViewById(R.id.dictionaryAcknowledgmentsTextView);
        String acknowledgments = new String(
                "References:\n" +
                "developer.android.com/reference \n" +
                "   ArrayAdapter\n   TextWatcher\n   BufferedReader\n   InputStream\n" +
                "www.stackoverflow.com\n   Reading from resource file\n   How to make a beep in Android");
        textView.setText(acknowledgments);
    }

}
