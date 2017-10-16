package edu.neu.madcourse.nayhtet;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
    private Tile.Owner mPlayer = Tile.Owner.FIRST;
    private Set<Tile> mAvailable = new HashSet<>();
    private int mLastLarge;
    private int mLastSmall;
    private ArrayList<String> word_list = ScroggleActivity.dict;
    private ArrayList<Tile> currentWord = new ArrayList<>();

    Random random = new Random();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retains this fragment instance across configuration changes
        initGame();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game,container,false);
        initViews(rootView);
        updateAllTiles();
        return rootView;
    }

    public void initGame() {

        Log.d("UT3", "init game");
        mGameBoard = new Tile(this);

        // Create all the tiles
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large] = new Tile(this);
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small] = new Tile(this);
            }
            mLargeTiles[large].setSubTiles(mSmallTiles[large]);
        }
        mGameBoard.setSubTiles(mLargeTiles);
        // If the player moves first, set which spots are available
        mLastSmall = -1;
        mLastLarge = -1;
        setAvailableFromLastMove(mLastLarge, mLastSmall);
    }

    private void initViews(View rootView) {
        String word;
        char temp;
        mGameBoard.setView(rootView);
        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            word = getRandomWord();
            mLargeTiles[large].setView(outer);
            for (int small = 0; small < 9; small++) {
                Button inner = (Button) outer.findViewById
                        (mSmallIds[small]);
                temp = word.charAt(small);
                inner.setText(String.valueOf(temp));
                final int fLarge = large;
                final int fSmall = small;
                final Tile smallTile = mSmallTiles[large][small];
                smallTile.setLetter(temp);
                smallTile.setView(inner);
                inner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isAvailable(smallTile) || currentWord.contains(smallTile)) {
                            makeMove(fLarge, fSmall);
                            /*switchTurns();*/
                        }
                    }
                });
            }
        }
    }
    private String getRandomWord(){
        String word = null;
        int index = 0;
        while(word == null){
            index = random.nextInt(word_list.size());
            if(word_list.get(index).length() == 9){
                word = word_list.get(index);
            }
        }
        return word;
    }
    private void makeMove(int large, int small) {
        mLastLarge = large;
        mLastSmall = small;
        Tile smallTile = mSmallTiles[large][small];
        Tile largeTile = mLargeTiles[large];
        boolean cancel = false;
        if(currentWord.contains(smallTile)){
            // remove letters that come after this tile
            cancel = removeFollowingLetters(smallTile);
        }
        if(!cancel) {
            smallTile.setOwner(mPlayer);
            setAvailableFromLastMove(large, small);
        }else {
            if(currentWord.size() == 1) {
                currentWord.remove(0).setOwner(Tile.Owner.NEITHER);
                setAllAvailable();
            }else {
                currentWord.remove(currentWord.size()-1).setOwner(Tile.Owner.NEITHER);
                smallTile = currentWord.get(currentWord.size()-1);
                smallTile.setOwner(mPlayer);
                for(int i = 0; i < 9; i++){
                    if(mSmallTiles[large][i] == smallTile){
                        setAvailableFromLastMove(large,i);
                    }
                }
            }
        }
        updateAllTiles();
    }
    public boolean removeFollowingLetters(Tile currentTile){
        int index = currentWord.size() - 1;
        if(currentWord.get(index) == currentTile) {
            return true;
        }else {
            Tile removed;
            while (currentWord.get(index) != currentTile) {
                removed = currentWord.remove(index);
                removed.setOwner(Tile.Owner.NEITHER);
                index--;
            }
            return false;
        }
    }
    public void confirmWord() {
        if (currentWord != null) {
            String guess = "";
           for(int i = 0; i < currentWord.size(); i++) {
                guess = guess + currentWord.get(i).getLetter();
            }
            if(word_list.contains(guess)){
                removeRemainingLetters();
                currentWord.clear();
                clearAvailable();
                setAllAvailable();
                updateAllTiles();
            }else{
                // penalize
            }
        }
    }
    public void removeRemainingLetters(){
        Tile smallTile;
        for(int i = 0; i < 9; i ++) {
            smallTile = mSmallTiles[mLastLarge][i];
            if(!currentWord.contains(smallTile)){
                smallTile.setLetter(' ');
                smallTile.setOwner(Tile.Owner.FIRST);
            }
        }
    }
   /* private void switchTurns() {
        mPlayer = mPlayer == Tile.Owner.X ? Tile.Owner.O : Tile.Owner.X;
    }*/
    public void restartGame() {
        initGame();
        initViews(getView());
        updateAllTiles();
    }

    private void clearAvailable() {
        mAvailable.clear();
    }

    private void addAvailable(Tile tile) {
        mAvailable.add(tile);
    }

    public boolean isAvailable(Tile tile) {
        return mAvailable.contains(tile);
    }

    private void setAvailableFromLastMove(int large, int small) {
        clearAvailable();
        if (large != -1) {
            switch(small){
                case 0:
                    addAvailable(mSmallTiles[large][1]);
                    addAvailable(mSmallTiles[large][3]);
                    addAvailable(mSmallTiles[large][4]);
                    break;
                case 1:
                    addAvailable(mSmallTiles[large][0]);
                    addAvailable(mSmallTiles[large][2]);
                    addAvailable(mSmallTiles[large][3]);
                    addAvailable(mSmallTiles[large][4]);
                    addAvailable(mSmallTiles[large][5]);
                    break;
                case 2:
                    addAvailable(mSmallTiles[large][1]);
                    addAvailable(mSmallTiles[large][4]);
                    addAvailable(mSmallTiles[large][5]);
                    break;
                case 3:
                    addAvailable(mSmallTiles[large][0]);
                    addAvailable(mSmallTiles[large][1]);
                    addAvailable(mSmallTiles[large][4]);
                    addAvailable(mSmallTiles[large][6]);
                    addAvailable(mSmallTiles[large][7]);
                    break;
                case 4:
                    addAvailable(mSmallTiles[large][0]);
                    addAvailable(mSmallTiles[large][1]);
                    addAvailable(mSmallTiles[large][2]);
                    addAvailable(mSmallTiles[large][3]);
                    addAvailable(mSmallTiles[large][5]);
                    addAvailable(mSmallTiles[large][6]);
                    addAvailable(mSmallTiles[large][7]);
                    addAvailable(mSmallTiles[large][8]);
                    break;
                case 5:
                    addAvailable(mSmallTiles[large][1]);
                    addAvailable(mSmallTiles[large][2]);
                    addAvailable(mSmallTiles[large][4]);
                    addAvailable(mSmallTiles[large][7]);
                    addAvailable(mSmallTiles[large][8]);
                    break;
                case 6:
                    addAvailable(mSmallTiles[large][3]);
                    addAvailable(mSmallTiles[large][4]);
                    addAvailable(mSmallTiles[large][7]);
                    break;
                case 7:
                    addAvailable(mSmallTiles[large][3]);
                    addAvailable(mSmallTiles[large][4]);
                    addAvailable(mSmallTiles[large][5]);
                    addAvailable(mSmallTiles[large][6]);
                    addAvailable(mSmallTiles[large][8]);
                    break;
                case 8:
                    addAvailable(mSmallTiles[large][4]);
                    addAvailable(mSmallTiles[large][5]);
                    addAvailable(mSmallTiles[large][7]);
                    break;
            }
            if(!currentWord.contains(mSmallTiles[large][small])) {
                currentWord.add(mSmallTiles[large][small]);
            }
        }
        // If there were none available, make all squares available
        if (mAvailable.isEmpty()) {
            setAllAvailable();
        }
    }

    private void setAllAvailable() {
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile tile = mSmallTiles[large][small];
                if (tile.getOwner() == Tile.Owner.NEITHER)
                    addAvailable(tile);
            }
        }
    }

    private void updateAllTiles() {
        mGameBoard.updateDrawableState();
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large].updateDrawableState();
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small].updateDrawableState();
            }
        }
    }
    /**
     * Create a string containing the state of the game
     * @return a string that is the serialized form of the game state
     */
    public String getState() {
        StringBuilder builder = new StringBuilder();
        builder.append(mLastLarge);
        builder.append(',');
        builder.append(mLastSmall);
        builder.append(',');
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                builder.append(mSmallTiles[large][small].getOwner().name());
                builder.append(',');
            }
        }
        return builder.toString();
    }

    /**
     * Restore the state of the game from the serialized string parameter
     * @param gameData serialized form of the state of the game
     */
    public void putState(String gameData) {
        String[] fields = gameData.split(",");
        int index = 0;
        mLastLarge = Integer.parseInt(fields[index++]);
        mLastSmall = Integer.parseInt(fields[index++]);
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile.Owner owner = Tile.Owner.valueOf(fields[index++]);
                mSmallTiles[large][small].setOwner(owner);
            }
        }
        setAvailableFromLastMove(mLastLarge,mLastSmall);    // might be a bug
        updateAllTiles();
    }
}
