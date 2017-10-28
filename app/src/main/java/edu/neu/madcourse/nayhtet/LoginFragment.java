package edu.neu.madcourse.nayhtet;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Nay Htet.
 */

public class LoginFragment extends Fragment {
    private AppCompatActivity mActivity;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Congratulations, " + usernameText.getText());
                builder.setMessage("Your score is submitted!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mActivity.finish();
                    }
                });
                mDialog = builder.show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScroggleActivity)mActivity).cancelLogin();
            }
        });

        return rootView;
    }

    public void setGameActivity(GameActivity mGameActivity) {
        this.mActivity = mGameActivity;
    }
}
