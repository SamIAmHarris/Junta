package com.jackrabbitmobile.junta.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.fragment.TimePickerFragment;
import com.jackrabbitmobile.junta.model.Team;
import com.jackrabbitmobile.junta.model.TeamActivity;
import com.jackrabbitmobile.junta.model.User;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SamMyxer on 1/13/15.
 */
public class CreateActivity extends ActionBarActivity implements TimePickerFragment.TimePickerDialogListener {

    TextView textClock;
    Spinner choiceSpinnner;
    EditText locationET;
    Date mDate;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primaryYellow));
        setSupportActionBar(toolbar);

        ImageButton okBT = (ImageButton) toolbar.findViewById(R.id.ok_button_create_toolbar);
        ImageButton cancelBT = (ImageButton) toolbar.findViewById(R.id.cancel_button_create_toolbar);

        okBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = (User) User.getCurrentUser();
                if (currentUser != null) {
                    // do stuff with the user
                    String choice = (String) choiceSpinnner.getSelectedItem();
                    String location = locationET.getText().toString();

                    Team team = (Team) currentUser.getTeam();
                    TeamActivity teamActivity = new TeamActivity();
                    teamActivity.setType(choice);
                    teamActivity.setCreatedBy(currentUser);
                    teamActivity.setLocationName(location);
                    teamActivity.setTime(mDate);
                    teamActivity.setTeam((Team)currentUser.getTeam());
                    teamActivity.saveInBackground();
                    team.addAllUnique("activities", Arrays.asList(teamActivity));
                    team.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Intent intent = new Intent(mContext, NavDrawerActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    // show the signup or login screen
                }
            }
        });

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mContext = getApplicationContext();

        textClock = (TextView) findViewById(R.id.text_clock_create_activity);
        choiceSpinnner = (Spinner) findViewById(R.id.spinner_create_activity);
        locationET = (EditText) findViewById(R.id.location_et_create_activity);

        textClock = (TextView) findViewById(R.id.text_clock_create_activity);
        textClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void showTimePickerDialog(View v) {
        android.app.DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onFinishEditDialog(int hours, int minutes) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        mDate = cal.getTime();

        String ampm;
        if(hours == 0) {
            hours = 12;
            ampm = "AM";
        } else if (hours > 12) {
            hours = hours - 12;
            ampm = "PM";
        } else if (hours == 12) {
            ampm = "PM";
        } else {
            ampm = "AM";
        }

        if(minutes < 10) {
            textClock.setText(hours + ":0" + minutes + " " + ampm);
        } else {
            textClock.setText(hours + ":" + minutes + " " + ampm);
        }
    }

}
