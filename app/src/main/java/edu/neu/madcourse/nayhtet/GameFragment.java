package edu.neu.madcourse.nayhtet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nay Htet.
 */
public class GameFragment extends Fragment{

    static private int[] mLargeIds = {R.id.large1,R.id.large2,R.id.large3,R.id.large4,
            R.id.large5,R.id.large6,R.id.large7,R.id.large8,R.id.large9};
    static private int[] mSmallIds = {R.id.small1,R.id.small2,R.id.small3,R.id.small4,
            R.id.small5,R.id.small6,R.id.small7,R.id.small8,R.id.small9};

    private Tile mGameBoard = new Tile(this);
    private Tile[] mLargeTiles = new Tile[9];
    private Tile[][] mSmallTiles = new Tile[9][9];
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retains this fragment instance across configuration changes
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game,container,false);

        return rootView;
    }


}
