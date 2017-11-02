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
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ControlFragment extends Fragment {
    private View rootView;
    private View score;
    private boolean phaseTwo = false;
    private boolean pauseTimer = false;
    private GameActivity mGameActivity;
    private MyTimer myTimer;
    private int index;
    private List<User> users;
    static int finalScore;
    static String bestWord = "";
    private static final String SERVER_KEY = "key=AAAASHYvjtc:APA91bHd00SOURgQswbcA_nERc-" +
            "7x079lTJNpfYcbeVSvZiGDcIZ-QUgIPbnph0A0vsy0Di3d8-U9it1clatHeXWiNaEAKrXLWdHLL1VR" +
            "_rYnGfYQ_uWB0Wy2TB_Xey8rlm3vEWAoxAG";
    DatabaseReference mDatabase;
    private boolean isMute = false;
    MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_control, container, false);
        View main = rootView.findViewById(R.id.button_main);
        View mute = rootView.findViewById(R.id.button_restart);
        View confirm = rootView.findViewById(R.id.button_confirm);

        score = rootView.findViewById(R.id.text_score);
        View timer = rootView.findViewById(R.id.text_timer);
        ((TextView) timer).setText("Time: ");
        ((TextView) score).setText("0");
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGameActivity.finish();
            }
        });
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMute) {
                    mGameActivity.getMediaPlayer().start();
                    isMute = false;
                } else {
                    mGameActivity.getMediaPlayer().pause();
                    isMute = true;
                }
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
        private String zero = "";

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values[0] == 99) {
                timesUp();
                this.cancel(true);
            } else {
                if (values[1] < 10) {
                    zero = "0";
                } else {
                    zero = "";
                }
                TextView time = (TextView) rootView.findViewById(R.id.text_timer);
                time.setText("Time: " + Integer.toString(values[0]) + ":" + zero
                        + Integer.toString(values[1]));
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            Integer finish = 99;
            int j = 30;
            for (int i = 1; i > -1; i--) {
                for (; j > -1; j--) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i, j);
                }
                j = 59;
            }
            publishProgress(finish);
            return null;
        }
    }

    public void stopTimer() {
        myTimer.cancel(true);
    }

    private void timesUp() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.times_up);
        if (phaseTwo) {
            readDatabase();
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
                            if (ScroggleActivity.loggedIn) { // if user is logged in
                                checkIfScoreMakesLeaderboard();
                                mGameActivity.finish();
                                Intent intent = new Intent(mGameActivity.getApplicationContext(),
                                        LeaderBoardActivity.class);
                                startActivity(intent);
                            } else {
                                /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                LoginFragment loginFragment = new LoginFragment();
                                loginFragment.setActivity(mGameActivity);
                                fragmentTransaction.replace(android.R.id.content, loginFragment);
                                fragmentTransaction.commit();*/
                                mGameActivity.finish();
                            }
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
                            mGameActivity.getMediaPlayer().stop();
                            mGameActivity.startPhaseTwo();
                            mGameActivity.playPhase2Music();
                        }
                    });
            dialog = builder.show();
        }
    }

    private void readDatabase() {
        users = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        Collections.sort(users, new ScoreComparator());
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        // adds user to leaderboard
        if (users.size() != 10) {
            mDatabase = firebase.getReference("leaders");
            User newUser = new User(ScroggleActivity.username,
                    String.valueOf(finalScore), bestWord, User.currentDate());
            mDatabase.child(ScroggleActivity.username).setValue(newUser);
        } else if (finalScore > Integer.parseInt(users.get(0).final_score)) {
            // replaces the lowest score on the leaderboard
            mDatabase = firebase.getReference("leaders");
            mDatabase.child(users.get(0).user_name).removeValue();
            User newUser = new User(ScroggleActivity.username,
                    String.valueOf(finalScore), bestWord, User.currentDate());
            mDatabase.child(ScroggleActivity.username).setValue(newUser);
            if (finalScore > Integer.parseInt(users.get(users.size() - 1).final_score)) {
                sendMessageToScroggle();
            }
        }
        // Add to scoreboard for current user
        mDatabase = firebase.getReference("users").child(ScroggleActivity.username);
        final User newScore = new User(ScroggleActivity.username,
                String.valueOf(finalScore), bestWord, User.currentDate());
        mDatabase.setValue(newScore.date);
        mDatabase.child(newScore.date).setValue(newScore);
    }

    public void sendMessageToScroggle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessage();
            }
        }).start();
    }

    private void sendMessage() {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {
            jNotification.put("message", "New Leader");
            jNotification.put("body", ScroggleActivity.username
                    + " is now leading the leaderboard!");
            jNotification.put("sound", "default");
            jNotification.put("badge", 1);
            jNotification.put("click_action", "OPEN_ACTIVITY_1");

            jPayload.put("to", "/topics/scroggle");
            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

           /* Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ControlFragment.this,resp,Toast.LENGTH_LONG).show();
                }
            });*/

        } catch (JSONException | IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Helper function
     */
    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
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