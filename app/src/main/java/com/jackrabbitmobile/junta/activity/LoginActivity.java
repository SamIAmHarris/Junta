package com.jackrabbitmobile.junta.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.model.User;
import com.parse.ParseException;
import com.parse.SignUpCallback;

/**
 * Created by SamMyxer on 1/12/15.
 */
public class LoginActivity extends ActionBarActivity {

    EditText emailAddressET;
    EditText passwordET;
    EditText phoneNumberET;
    EditText usernameET;
    Button loginBT;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        usernameET = (EditText) findViewById(R.id.username_et_login);
        emailAddressET = (EditText) findViewById(R.id.email_address_et_login);
        passwordET = (EditText) findViewById(R.id.password_et_login);
        phoneNumberET = (EditText) findViewById(R.id.phone_number_et_login);

        loginBT = (Button) findViewById(R.id.login_button);
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        context = this;
    }

    private void loginUser() {
        User user = new User();
        user.setUsername(usernameET.getText().toString());
        user.setEmail(emailAddressET.getText().toString());
        user.setPassword(passwordET.getText().toString());

        // other fields can be set just like with ParseObject
        user.setPhoneNumber(phoneNumberET.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    loginSuccessful();
                } else {
                    Log.i("LOGIN", "LOGIN_FAILED");
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }


    public void loginSuccessful() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.FIRST_OPEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean(MainActivity.LOGGED_IN, true);
        ed.apply();

        Intent navDrawerIntent = new Intent(context, CreateTeamActivity.class);
        startActivity(navDrawerIntent);

    }

}
