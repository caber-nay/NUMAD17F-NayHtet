package edu.neu.madcourse.nayhtet;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nay Myo Htet.
 */
@IgnoreExtraProperties
public class User  {
    public String user_name;
    public String final_score;
    public String date;
    public String best_word;

    public User() {

    }
    public User(String username, String score, String bestWord, String datePlayed) {
        this.user_name = username;
        this.final_score = score;
        this.best_word = bestWord;
        this.date = datePlayed;
    }
    public String currentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }
}
