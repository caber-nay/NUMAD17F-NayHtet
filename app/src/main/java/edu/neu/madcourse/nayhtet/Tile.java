package edu.neu.madcourse.nayhtet;

import android.view.View;

/**
 * Created by Nay Htet.
 */

public class Tile {

    private final GameFragment mGame;
    private View mView;
    private Character letter;
    private Tile[] subTiles;

    public Tile(GameFragment game) {
        this.mGame = game;
    }



    public void setView(View view){
        mView = view;
    }
    public View getView(){
        return mView;
    }
    public void setSubTiles(Tile[] subTiles){
        this.subTiles = subTiles;
    }
    public Tile[] getSubTiles(){
        return subTiles;
    }
}
