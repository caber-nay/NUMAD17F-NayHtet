package edu.neu.madcourse.nayhtet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * Created by waffl35.
 */

public class LeaderBoardActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView username1, score1, bestWord1, time1;
    private TextView username2, score2, bestWord2, time2;
    private TextView username3, score3, bestWord3, time3;
    private TextView username4, score4, bestWord4, time4;
    private TextView username5, score5, bestWord5, time5;
    private TextView username6, score6, bestWord6, time6;
    private TextView username7, score7, bestWord7, time7;
    private TextView username8, score8, bestWord8, time8;
    private TextView username9, score9, bestWord9, time9;
    private TextView username10, score10, bestWord10, time10;

    private TextView[] userNameTextViews;
    private TextView[] scoresTextViews;
    private TextView[] bestWordTextViews;
    private TextView[] dateTextViews;
    private int index;
    private String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        initializeTextViews();
        TextView[] _userNameTextViews = {username1, username2, username3, username4, username5,
                username6, username7, username8, username9, username10};
        userNameTextViews = _userNameTextViews;
        TextView[] _scoresTextViews = {score1, score2, score3, score4, score5, score6,
                score7, score8, score9, score10};
        scoresTextViews = _scoresTextViews;
        TextView[] _bestWordTextViews = {bestWord1, bestWord2, bestWord3, bestWord4, bestWord5,
                bestWord6, bestWord7, bestWord8, bestWord9, bestWord10};
        bestWordTextViews = _bestWordTextViews;
        TextView[] _timeTextViews = {time1, time2, time3, time4, time5,
                time6, time7, time8, time9, time10};
        dateTextViews = _timeTextViews;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        index = 0;
        /*syncWithDatabase(userNameTextViews,scoresTextViews,bestWordTextViews,timeTextViews);*/
        mDatabase.child("leaders").addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);

                        userNameTextViews[index].setText(user.user_name);
                        scoresTextViews[index].setText(user.final_score);
                        bestWordTextViews[index].setText(user.best_word);
                        dateTextViews[index].setText(user.date);
                        index++;
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        User user = dataSnapshot.getValue(User.class);

                        /*if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                            score.setText(user.score);
                            userName.setText(user.username);
                        } else {
                            score2.setText(String.valueOf(user.score));
                            userName2.setText(user.username);
                        }
                        Log.v(TAG, "onChildChanged: "+dataSnapshot.getValue().toString());*/

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

    /*private void syncWithDatabase(TextView[] userNameTextViews,TextView[] scoresTextViews,TextView[] bestWordTextViews,TextView[] timeTextViews ) {
        String current = "leader1";
        int index = 0;
        while(mDatabase.child("leader")!= null) {
            String key = mDatabase.child("leaders").child(current).child("user_name").getKey();
            userNameTextViews[index].setText(mDatabase.child("leaders").child(current).child("user_name").getKey());
            scoresTextViews[index].setText(mDatabase.child("leaders").child(current).child("final_score").getKey());
            bestWordTextViews[index].setText(mDatabase.child("leaders").child(current).child("best_word").getKey());
            timeTextViews[index].setText(mDatabase.child("leaders").child(current).child("date").getKey());
            index = index + 1;
            current = "leader" + index + 1;
        }
    }*/
    private void initializeTextViews() {
        String name = "row1_username";
        username1 = (TextView) findViewById(R.id.row1_username);
        score1 = (TextView) findViewById(R.id.row1_score);
        bestWord1 = (TextView) findViewById(R.id.row1_bestWord);
        time1 = (TextView) findViewById(R.id.row1_time);
        username2 = (TextView) findViewById(R.id.row2_username);
        score2 = (TextView) findViewById(R.id.row2_score);
        bestWord2 = (TextView) findViewById(R.id.row2_bestWord);
        time2 = (TextView) findViewById(R.id.row2_time);
        username3 = (TextView) findViewById(R.id.row3_username);
        score3 = (TextView) findViewById(R.id.row3_score);
        bestWord3 = (TextView) findViewById(R.id.row3_bestWord);
        time3 = (TextView) findViewById(R.id.row3_time);
        username4 = (TextView) findViewById(R.id.row4_username);
        score4 = (TextView) findViewById(R.id.row4_score);
        bestWord4 = (TextView) findViewById(R.id.row4_bestWord);
        time4 = (TextView) findViewById(R.id.row4_time);
        username5 = (TextView) findViewById(R.id.row5_username);
        score5 = (TextView) findViewById(R.id.row5_score);
        bestWord5 = (TextView) findViewById(R.id.row5_bestWord);
        time5 = (TextView) findViewById(R.id.row5_time);
        username6 = (TextView) findViewById(R.id.row6_username);
        score6 = (TextView) findViewById(R.id.row6_score);
        bestWord6 = (TextView) findViewById(R.id.row6_bestWord);
        time6 = (TextView) findViewById(R.id.row6_time);
        username7 = (TextView) findViewById(R.id.row7_username);
        score7 = (TextView) findViewById(R.id.row7_score);
        bestWord7 = (TextView) findViewById(R.id.row7_bestWord);
        time7 = (TextView) findViewById(R.id.row7_time);
        username8 = (TextView) findViewById(R.id.row8_username);
        score8 = (TextView) findViewById(R.id.row8_score);
        bestWord8 = (TextView) findViewById(R.id.row8_bestWord);
        time8 = (TextView) findViewById(R.id.row8_time);
        username9 = (TextView) findViewById(R.id.row9_username);
        score9 = (TextView) findViewById(R.id.row9_score);
        bestWord9 = (TextView) findViewById(R.id.row9_bestWord);
        time9 = (TextView) findViewById(R.id.row9_time);
        username10 = (TextView) findViewById(R.id.row10_username);
        score10 = (TextView) findViewById(R.id.row10_score);
        bestWord10 = (TextView) findViewById(R.id.row10_bestWord);
        time10 = (TextView) findViewById(R.id.row10_time);
    }
}
