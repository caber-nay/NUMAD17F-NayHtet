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
    static String username;
    static ArrayList<String> dict = new ArrayList<>();
    private String read;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private LoginFragment loginFragment;
    static boolean loginCancelled = false;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroggle);

        if(!loginCancelled) {
            bringUpLoginFragment();
        }
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

    public void bringUpLoginFragment() {
        fragmentTransaction = getFragmentManager().beginTransaction();
        loginFragment = new LoginFragment();
        loginFragment.setActivity(this);
        fragmentTransaction.replace(android.R.id.content, loginFragment);
        fragmentTransaction.commit();
    }

    public void cancelLogin() {
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(loginFragment).commit();
        loginCancelled = true;
    }
}
