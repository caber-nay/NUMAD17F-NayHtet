package edu.neu.madcourse.nayhtet;
/**
 * Created by Nay Htet.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ControlFragment extends Fragment {
    private View rootView;
    private View score;

    private View timer;
    private boolean phaseTwo = false;
    private boolean pauseTimer = false;
    private GameActivity mGameActivity;
    private MyTimer myTimer;
    private int index;
    private List<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_control, container, false);
        View main = rootView.findViewById(R.id.button_main);
        View restart = rootView.findViewById(R.id.button_restart);
        View confirm = rootView.findViewById(R.id.button_confirm);

        score = rootView.findViewById(R.id.text_score);
        View timer = rootView.findViewById(R.id.text_timer);
        ((TextView) timer).setText("Time: ");
        ((TextView) score).setText("0");
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((GameActivity) getActivity()).restartGame();
                Toast.makeText(getActivity(), "Currently Unavailable",
                        Toast.LENGTH_LONG).show();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity) getActivity()).confirmWord();
            }
        });

        return rootView;
    }

    public void startPhaseOneTimer() {
        myTimer = new MyTimer();
        myTimer.execute();
    }

    public void startPhaseTwoTimer() {
        phaseTwo = true;
        myTimer = new MyTimer();
        myTimer.execute();
    }

    private class MyTimer extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values[0] == 99) {
                timesUp();
                this.cancel(true);
            } else {
                TextView time = (TextView) rootView.findViewById(R.id.text_timer);
                time.setText("Time: " + Integer.toString(values[0]) + ":"
                        + Integer.toString(values[1]));
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            Integer finish = 99;
            int j = 0;
            for (int i = 1; i > -1; i--) {
                for (; j > -1; j--) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i, j);
                }
                j = 10;
            }
            publishProgress(finish);
            return null;
        }
    }
    public void stopTimer() {
        myTimer.cancel(true);
    }
    private void timesUp() {
        readDatabase();
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.times_up);
        if (phaseTwo) {
            builder.setMessage("Your score:" + ((TextView) score).getText() + "\n Submit Score?");
            builder.setCancelable(false);

            builder.setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mGameActivity.finish();
                }
            });
            builder.setPositiveButton(R.string.label_yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // add to score board
                            // check if made to leaderboard
                            checkIfScoreMakesLeaderboard();
                            mGameActivity.finish();
                        }
                    });
            dialog = builder.show();
        } else {
            builder.setMessage("Phase 1 is over. Click OK to start phase 2.");
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.label_ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mGameActivity.startPhaseTwo();
                        }
                    });
            dialog = builder.show();
        }
    }

    private void readDatabase() {
        users = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        index = 0;
        mDatabase.child("leaders").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        users.add(dataSnapshot.getValue(User.class));
                        System.out.println(users.get(index));
                        index++;
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }
    private void checkIfScoreMakesLeaderboard() {

    }

    public View getScore() {
        return score;
    }

    public void setPhaseTwo(boolean phaseTwo) {
        this.phaseTwo = phaseTwo;
    }

    public void setPauseTimer(boolean value) {
        pauseTimer = value;
    }

    public boolean isPaused() {
        return pauseTimer;
    }
    public void setGameActivity(GameActivity gameActivity) {
        mGameActivity = gameActivity;
    }
}