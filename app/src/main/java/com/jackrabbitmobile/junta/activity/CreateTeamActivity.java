package com.jackrabbitmobile.junta.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.model.PhoneNumber;
import com.jackrabbitmobile.junta.model.Team;
import com.jackrabbitmobile.junta.model.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SamMyxer on 1/14/15.
 */
public class CreateTeamActivity extends ActionBarActivity {

    EditText teamNameET;
    Button createTeamBT;

    TextView joinTeamNameTV;
    Button joinTeamBT;
    Context mContext;

    Team existingTeam;

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
        joinTeamNameTV = (TextView) findViewById(R.id.join_team_name_tv);
        joinTeamBT = (Button) findViewById(R.id.join_team_bt);

        checkIfUserIsOnATeam();

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
            @Override
            public void onClick(View v) {
                User user = (User) User.getCurrentUser();
                if (user != null) {
                    user.setTeam(existingTeam);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Intent navDrawerIntent = new Intent(mContext, NavDrawerActivity.class);
                            startActivity(navDrawerIntent);
                        }
                    });
                    ;
                }
            }
        });


    }

    public void checkIfUserIsOnATeam() {
        User user = (User) User.getCurrentUser();
        if(user != null) {
            String phoneNumber = user.getPhoneNumber();
            ParseQuery<PhoneNumber> query = ParseQuery.getQuery(PhoneNumber.class);
            query.whereEqualTo("phoneNumber", phoneNumber);
            query.findInBackground(new FindCallback<PhoneNumber>() {
                @Override
                public void done(List<PhoneNumber> parseObjects, com.parse.ParseException e) {
                    Log.i("Debugging", "Debugging");
                    if(parseObjects.size() > 0) {
                        for (PhoneNumber p : parseObjects) {
                            existingTeam = p.getTeam();
                            try {
                                existingTeam.fetchIfNeeded();
                            } catch (ParseException pe) {

                            }
                            joinTeamNameTV.setText(existingTeam.getTeamName());
                        }
                    }
                }
            });
        } else {
            //send to login screen
        }
    }
}
