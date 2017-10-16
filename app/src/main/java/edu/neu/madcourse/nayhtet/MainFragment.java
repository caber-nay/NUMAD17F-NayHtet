package edu.neu.madcourse.nayhtet;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Nay Htet
 */
public class MainFragment extends Fragment {
    private AlertDialog mDialog;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Handle buttons here...
        View newButton = rootView.findViewById(R.id.new_button);
        View continueButton = rootView.findViewById(R.id.continue_button);
        ((Button)continueButton).setText("Tutorial");
        View acknowledgementButton = rootView.findViewById(R.id.acknowledgements_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            /*@Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra(GameActivity.KEY_RESTORE, true);
                getActivity().startActivity(intent);
            }*/
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Tutorial");
                builder.setMessage("Scroggle is a lot like Boggle \n" +
                        "There are 2 Phases in Scroggle.\n" +
                        "In Phase 1, you play each small board like Boggle.\n"+
                        "In Phase 2, you play the entire board and you can reuse a small board," +
                        " but you cannot use it back to back.\n" +
                        "Now go have fun!");
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.label_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });
        acknowledgementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.button_acknowledgments);
                builder.setMessage("Hello Android, 4th Edition \n" +
                        "Part 2 Let's Play A Game - Chapter 3, 4\n" +
                        "developer.android.com \n"+
                        "Mobile Application Development Course");
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.label_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });
        return rootView;
    }
    @Override
    public void onPause() {
        super.onPause();
        // Get rid of Acknowledgement box when app is paused
        if (mDialog != null)
            mDialog.dismiss();
    }
}
