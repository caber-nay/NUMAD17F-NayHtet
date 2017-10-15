package edu.neu.madcourse.nayhtet;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.HashSet;
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
    private Tile.Owner mPlayer = Tile.Owner.X;
    private Set<Tile> mAvailable = new HashSet<>();
    private int mLastLarge;
    private int mLastSmall;

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
        setAvailableFromLastMove(mLastSmall);
    }

    private void initViews(View rootView) {

        mGameBoard.setView(rootView);
        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            mLargeTiles[large].setView(outer);
            for (int small = 0; small < 9; small++) {
                ImageButton inner = (ImageButton) outer.findViewById
                        (mSmallIds[small]);
                final int fLarge = large;
                final int fSmall = small;
                final Tile smallTile = mSmallTiles[large][small];
                smallTile.setView(inner);
                inner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isAvailable(smallTile)) {
                            makeMove(fLarge, fSmall);
                            switchTurns();
                        }
                    }
                });
            }
        }
    }
    private void makeMove(int large, int small) {
        mLastLarge = large;
        mLastSmall = small;
        Tile smallTile = mSmallTiles[large][small];
        Tile largeTile = mLargeTiles[large];
        smallTile.setOwner(mPlayer);
        setAvailableFromLastMove(small);
        Tile.Owner oldWinner = largeTile.getOwner();
        Tile.Owner winner = largeTile.findWinner();
        if (winner != oldWinner) {
            largeTile.setOwner(winner);
        }
        winner = mGameBoard.findWinner();
        mGameBoard.setOwner(winner);
        updateAllTiles();
        if (winner != Tile.Owner.NEITHER) {
            ((GameActivity)getActivity()).reportWinner(winner);
        }
    }

    private void switchTurns() {
        mPlayer = mPlayer == Tile.Owner.X ? Tile.Owner.O : Tile.Owner.X;
    }
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

    private void setAvailableFromLastMove(int small) {
        clearAvailable();
        // Make all the tiles at the destination available
        if (small != -1) {
            for (int dest = 0; dest < 9; dest++) {
                Tile tile = mSmallTiles[small][dest];
                if (tile.getOwner() == Tile.Owner.NEITHER)
                    addAvailable(tile);
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
        setAvailableFromLastMove(mLastSmall);
        updateAllTiles();
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
}
