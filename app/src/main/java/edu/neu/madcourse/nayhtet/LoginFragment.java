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
import android.widget.EditText;

/**
 * Created by Nay Htet.
 */

public class LoginFragment extends Fragment {
    private GameActivity mGameActivity;
    private AlertDialog mDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        // Handle buttons here...
        View submitButton = rootView.findViewById(R.id.submit_button);
        View cancelButton = rootView.findViewById(R.id.cancel_button);
        final EditText usernameText = rootView.findViewById(R.id.username_editText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Congratulations, " + usernameText.getText());
                builder.setMessage("Your score is submitted!");
                builder.setCancelable(true);
                mDialog = builder.show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGameActivity.finish();
            }
        });

        return rootView;
    }

    public void setGameActivity(GameActivity mGameActivity) {
        this.mGameActivity = mGameActivity;
    }
}
