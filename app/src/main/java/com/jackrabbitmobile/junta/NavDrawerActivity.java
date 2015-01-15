package com.jackrabbitmobile.junta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jackrabbitmobile.junta.model.Team;
import com.jackrabbitmobile.junta.model.TeamActivity;
import com.jackrabbitmobile.junta.model.User;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SamMyxer on 1/13/15.
 */
public class NavDrawerActivity extends ActionBarActivity {

    private Toolbar toolbar;
    TextView testView;
    static String testString = "";
    private Handler mHandler;

    FloatingActionButton createActivityBT;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mContext = getApplicationContext();
        mHandler = new Handler();


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        LeftDrawerFragment leftDrawerFragment = (LeftDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_left_drawer);
        leftDrawerFragment.setUp(R.id.fragment_left_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        createActivityBT = (FloatingActionButton) findViewById(R.id.navDrawerFab);
        createActivityBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CreateActivity.class));
            }
        });

        testView = (TextView) findViewById(R.id.test_tv);
        getTeamActivities();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hey you just hit " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.add_friends) {
        }

        return super.onOptionsItemSelected(item);
    }

    public void getTeamActivities() {
        User currentUser = (User) User.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Team myTeam = (Team) currentUser.getTeam();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TeamActivity");
            query.whereEqualTo("team", myTeam);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                    Log.i("Debugging", "Debugging");
                    for (ParseObject p : parseObjects) {
                        TeamActivity t = (TeamActivity) p;
                        testString += t.getLocationName();
                    }
                }
            });
            testView.setText(testString);
            testView.invalidate();
        } else {
            // show the signup or login screen
        }
    }
}
