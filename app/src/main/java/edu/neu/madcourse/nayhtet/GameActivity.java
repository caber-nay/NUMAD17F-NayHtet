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
        /*mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }
        Log.d("UT3", "restore = " + restore);*/
    }
}
