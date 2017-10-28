package edu.neu.madcourse.nayhtet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This activity is for Scroggle button
 * Created by Nay Htet
 */
public class ScroggleActivity extends AppCompatActivity{

    static ArrayList<String> dict = new ArrayList<>();
    String read;
    InputStream inputStream;
    BufferedReader bufferedReader;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroggle);

        inputStream = getResources().openRawResource(R.raw.wordlist);
        bufferedReader =  new BufferedReader(new InputStreamReader(inputStream));
        try{
            read = bufferedReader.readLine();
            // reads the contents of wordlist.txt into a string array
            while(read != null) {
                dict.add(read);
                read = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
