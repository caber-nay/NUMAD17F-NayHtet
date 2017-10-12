package edu.neu.madcourse.nayhtet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Nay Htet.
 */

public class GameActivity extends AppCompatActivity {
    public static final String KEY_RESTORE = "key_restore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}
