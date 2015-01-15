package com.jackrabbitmobile.junta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jackrabbitmobile.junta.model.Team;
import com.jackrabbitmobile.junta.model.User;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;

/**
 * Created by SamMyxer on 1/14/15.
 */
public class CreateTeamActivity extends ActionBarActivity {

    EditText teamNameET;
    Button createTeamBT;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primaryYellow));
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();

        teamNameET = (EditText) findViewById(R.id.team_name_et);
        createTeamBT = (Button) findViewById(R.id.create_team_bt);

        createTeamBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = (User) User.getCurrentUser();
                if (currentUser != null) {
                    // do stuff with the user
                    Team team = new Team();
                    team.setTeamName(teamNameET.getText().toString());
                    team.setCreatedBy(currentUser);
                    team.addAllUnique("users", Arrays.asList(currentUser));
                    team.addAllUnique("userPhoneNumbers", Arrays.asList(currentUser.getPhoneNumber()));
                    team.saveInBackground();

                    currentUser.setTeam(team);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Intent navDrawerIntent = new Intent(mContext, NavDrawerActivity.class);
                            startActivity(navDrawerIntent);
                        }
                    });
                } else {
                    // show the signup or login screen
                }
            }
        });

    }
}
