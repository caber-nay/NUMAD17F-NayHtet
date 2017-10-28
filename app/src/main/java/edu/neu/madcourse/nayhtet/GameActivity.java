package edu.neu.madcourse.nayhtet;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ResourceBundle;

/**
 * Created by Nay Htet.
 */

public class GameActivity extends AppCompatActivity {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private GameFragment mGameFragment;
    private ControlFragment mControlFragment;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // Restore game here...
        mGameFragment = (GameFragment) getFragmentManager().findFragmentById(R.id.fragment_game);
        mControlFragment = (ControlFragment) getFragmentManager().findFragmentById(R.id.fragment_game_controls);

        mGameFragment.setScoreView(mControlFragment.getScore());
        mControlFragment.setGameActivity(this);

        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, true);
        /*if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }*/
        if (mControlFragment.isPaused()) {
            mControlFragment.setPauseTimer(false);
        } else {
            mControlFragment.startPhaseOneTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mControlFragment.startPhaseOneTimer();
    }

    public void startPhaseTwo() {
        mControlFragment.startPhaseTwoTimer();
        mGameFragment.enterPhaseTwo();
    }

    public void restartGame() {
        mGameFragment.restartGame();
    }

    public void reportWinner(final Tile.Owner winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.declare_winner, winner));
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        final Dialog dialog = builder.create();
        dialog.show();
        // Reset the board to the initial position
        mGameFragment.initGame();
    }

    public void confirmWord() {
        mGameFragment.confirmWord();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mControlFragment.stopTimer();
    }
}
