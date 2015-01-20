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
import android.widget.Toast;

import com.jackrabbitmobile.junta.model.Team;
import com.jackrabbitmobile.junta.model.TeamActivity;
import com.jackrabbitmobile.junta.model.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SamMyxer on 1/14/15.
 */
public class CreateTeamActivity extends ActionBarActivity {

    EditText teamNameET;
    Button createTeamBT;

    EditText joinTeamNameET;
    Button joinTeamBT;
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
        joinTeamNameET = (EditText) findViewById(R.id.join_team_name_et);
        joinTeamBT = (Button) findViewById(R.id.join_team_bt);

        createTeamBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamName = teamNameET.getText().toString();
                ParseQuery<Team> query = ParseQuery.getQuery(Team.class);
                query.whereEqualTo("teamName", teamName);
                query.findInBackground(new FindCallback<Team>() {
                    @Override
                    public void done(List<Team> parseObjects, com.parse.ParseException e) {
                        if(parseObjects.size() > 0) {
                            Toast.makeText(mContext, "Team Name Already Exists, Try a new one!", Toast.LENGTH_SHORT).show();
                        } else {
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
                    }
                });
            }
        });

        joinTeamBT.setOnClickListener(new View.OnClickListener() {
            String teamName = teamNameET.getText().toString();
            @Override
            public void onClick(View v) {
                ParseQuery<Team> query = ParseQuery.getQuery(Team.class);
                query.whereEqualTo("teamName", teamName);
                query.findInBackground(new FindCallback<Team>() {
                    @Override
                    public void done(List<Team> parseObjects, com.parse.ParseException e) {
                        if(parseObjects.size() > 0) {
                            Toast.makeText(mContext, "Team Name Already Exists, Try a new one!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}
