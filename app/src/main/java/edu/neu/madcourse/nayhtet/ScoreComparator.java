package edu.neu.madcourse.nayhtet;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Nay Myo Htet.
 */
public class ScoreComparator implements Serializable,Comparator<User> {
    @Override
    public int compare(User user, User t1) {
        if (Integer.parseInt(user.final_score) > Integer.parseInt(t1.final_score)) {
            return 1;
        } else if (Integer.parseInt(user.final_score) < Integer.parseInt(t1.final_score)) {
            return -1;
        }
        return 0;
    }
}
