package com.jackrabbitmobile.junta.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.model.User;
import com.parse.ParseException;
import com.parse.SignUpCallback;

/**
 * Created by SamMyxer on 1/29/15.
 */
public class LoginNameFragment extends Fragment {



    EditText emailAddressET;
    EditText passwordET;
    EditText phoneNumberET;
    Context context;
    Activity hostActivity;

    Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v =inflater.inflate(R.layout.activity_login, container, false);

        hostActivity = getActivity();

        emailAddressET = (EditText) v.findViewById(R.id.email_address_et_login);
        passwordET = (EditText) v.findViewById(R.id.password_et_login);
        phoneNumberET = (EditText) v.findViewById(R.id.phone_number_et_login);

        loginButton = (Button) v.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        return v;
    }


    private void loginUser() {
        User user = new User();
        user.setUsername(emailAddressET.getText().toString());
        user.setPassword(passwordET.getText().toString());
        user.setEmail(emailAddressET.getText().toString());


        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();

                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
