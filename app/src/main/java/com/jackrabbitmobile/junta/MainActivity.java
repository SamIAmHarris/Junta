package com.jackrabbitmobile.junta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class MainActivity extends ActionBarActivity {

    public final static String FIRST_OPEN = "first open";
    public final static String LOGGED_IN = "logged in";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launchActivityDependingFirstOpen();
    }


    private void launchActivityDependingFirstOpen() {
        SharedPreferences prefs = getSharedPreferences(FIRST_OPEN, Context.MODE_PRIVATE);

        if(prefs.getBoolean(LOGGED_IN, false)) {
            Intent navIntent = new Intent(this, NavDrawerActivity.class);
            startActivity(navIntent);
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }
}
