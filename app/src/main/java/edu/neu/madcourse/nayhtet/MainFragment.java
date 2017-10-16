package edu.neu.madcourse.nayhtet;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        //View continueButton = rootView.findViewById(R.id.continue_button);
        View acknowledgementButton = rootView.findViewById(R.id.acknowledgements_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
            }
        });
        /*continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra(GameActivity.KEY_RESTORE, true);
                getActivity().startActivity(intent);
            }
        });*/
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
