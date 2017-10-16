package edu.neu.madcourse.nayhtet;

import android.view.View;
import android.widget.Button;

/**
 * Created by Nay Htet.
 */

public class Tile {

    public enum Owner{
        FIRST, SECOND, NEITHER, SELECTED, OUT
    }

    private static final int LEVEL_BLANK = 0;
    private static final int LEVEL_AVAILABLE = 1;
    private static final int LEVEL_FIRST = 2;
    private static final int LEVEL_SECOND =3;
    private static final int LEVEL_SELECTED = 4;
    private static final int top = 1;
    private static final int bottom = 3;

    private final GameFragment mGame;
    private View mView;
    private Owner mOwner = Owner.NEITHER;
    private char letter;
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
    public Owner getOwner(){
        return mOwner;
    }
    public void setOwner(Owner owner){
        mOwner = owner;
    }
    public void setLetter(char letter){
        this.letter = letter;
    }
    public char getLetter(){
        return letter;
    }

    public void updateDrawableState() {
        if (mView == null) return;
        int level = getLevel();
        if (mView.getBackground() != null) {
            mView.getBackground().setLevel(level);
        }
        if (mView instanceof Button) {
            ((Button)mView).setText(String.valueOf(letter));
            /*Drawable drawable = ((Button) mView).getCompoundDrawables()[top];
            drawable.setLevel(level);*/
        }
    }

    private int getLevel() {
        int level = LEVEL_BLANK;
        switch (mOwner) {
            case FIRST:
                level = LEVEL_FIRST;
                break;
            /*case SECOND: // letter O
                level = LEVEL_O;
                break;*/
            case SELECTED:
                level = LEVEL_SELECTED;
                break;
            case NEITHER:
                level = mGame.isAvailable(this) ? LEVEL_AVAILABLE : LEVEL_BLANK;
                break;
            case OUT:
                level = LEVEL_BLANK;
                break;
        }
        return level;
    }

    /*public Owner findWinner() {
        // If owner already calculated, return it
        if (getOwner() != Owner.NEITHER)
            return getOwner();
        int totalX[] = new int[4];
        int totalO[] = new int[4];
        countCaptures(totalX, totalO);
        if (totalX[3] > 0) return Owner.X;
        if (totalO[3] > 0) return Owner.O;

        // Check for a draw
        int total = 0;
        for (int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                Owner owner = subTiles[3 * row + col].getOwner();
                if (owner != Owner.NEITHER) total++;
            }
            if (total == 9) return Owner.BOTH;
        }

        // Neither player has won this tile
        return Owner.NEITHER;
    }
    private void countCaptures(int totalX[], int totalO[]) {

        int capturedX, capturedO;
        // Check the horizontal
        for (int row = 0; row < 3; row++) {
            capturedX = capturedO = 0;
            for(int col = 0; col < 3; col++) {
                Owner owner = subTiles[3 * row + col].getOwner();
                if (owner == Owner.X || owner == Owner.BOTH) capturedX++;
                if (owner == Owner.O || owner == Owner.BOTH) capturedO++;
            }
            totalX[capturedX]++;
            totalO[capturedO]++;
        }

        // Check the vertical
        for (int col = 0; col < 3; col++) {
            capturedX = capturedO = 0;
            for (int row = 0; row < 3; row++) {
                Owner owner = subTiles[3 * row + col].getOwner();
                if (owner == Owner.X || owner == Owner.BOTH) capturedX++;
                if (owner == Owner.O || owner == Owner.BOTH) capturedO++;
            }
            totalX[capturedX]++;
            totalO[capturedO]++;
        }

        // Check the diagonals
        capturedX = capturedO = 0;
        for (int diag = 0; diag < 3; diag++) {
            Owner owner = subTiles[3 * diag + diag].getOwner();
            if (owner == Owner.X || owner == Owner.BOTH) capturedX++;
            if (owner == Owner.O || owner == Owner.BOTH) capturedO++;
        }
        totalX[capturedX]++;
        totalO[capturedO]++;
        capturedX = capturedO = 0;
        for (int diag = 0; diag < 3; diag++) {
            Owner owner = subTiles[3 * diag + (2 - diag)].getOwner();
            if (owner == Owner.X || owner == Owner.BOTH) capturedX++;
            if (owner == Owner.O || owner == Owner.BOTH) capturedO++;
        }
        totalX[capturedX]++;
        totalO[capturedO]++;
    }*/
}
