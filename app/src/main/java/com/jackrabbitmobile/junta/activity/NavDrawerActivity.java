package com.jackrabbitmobile.junta.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jackrabbitmobile.junta.fragment.LeftDrawerFragment;
import com.jackrabbitmobile.junta.adapter.MyAdapter;
import com.jackrabbitmobile.junta.fragment.NavigationDrawerFragment;
import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.model.Team;
import com.jackrabbitmobile.junta.model.TeamActivity;
import com.jackrabbitmobile.junta.model.User;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SamMyxer on 1/13/15.
 */
public class NavDrawerActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Handler mHandler;

    FloatingActionButton createActivityBT;
    Context mContext;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<TeamActivity> teamActivities;

    LeftDrawerFragment leftDrawerFragment;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mContext = getApplicationContext();
        mHandler = new Handler();

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Junta");
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        leftDrawerFragment = (LeftDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_left_drawer);
        leftDrawerFragment.setUp(R.id.fragment_left_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        teamActivities = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.activities_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(teamActivities);
        mRecyclerView.setAdapter(mAdapter);

        createActivityBT = (FloatingActionButton) findViewById(R.id.navDrawerFab);
        createActivityBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CreateActivity.class));
            }
        });

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
            leftDrawerFragment.openDrawerFromActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getTeamActivities() {
        User currentUser = (User) User.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Team myTeam = (Team) currentUser.getTeam();
            ParseQuery<TeamActivity> query = ParseQuery.getQuery(TeamActivity.class);
            query.whereEqualTo("team", myTeam);
            query.findInBackground(new FindCallback<TeamActivity>() {
                @Override
                public void done(List<TeamActivity> parseObjects, com.parse.ParseException e) {
                    Log.i("Debugging", "Debugging");
                    for (TeamActivity t : parseObjects) {
                        teamActivities.add(t);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            // show the signup or login screen
        }
    }
}
