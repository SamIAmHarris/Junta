package com.jackrabbitmobile.junta;

import android.app.Application;

import com.jackrabbitmobile.junta.model.PhoneNumber;
import com.jackrabbitmobile.junta.model.Team;
import com.jackrabbitmobile.junta.model.TeamActivity;
import com.jackrabbitmobile.junta.model.User;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by SamMyxer on 1/10/15.
 */
public class App extends Application {

    public final static String PARSE_APP_ID = "mrm2ycDGKh3eiUH3OK1eBZR15JozoT5W9Y3hr0vI";
    public final static String PARSE_CLIENT_KEY = "l75K21FSnwwn4qtyAnt97gmcV81UqvvaX4kCX2j5";


    @Override
    public void onCreate() {
        super.onCreate();
        initParse();
    }

    private void initParse() {
        Parse.initialize(this, PARSE_APP_ID, PARSE_CLIENT_KEY);

        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Team.class);
        ParseObject.registerSubclass(TeamActivity.class);
        ParseObject.registerSubclass(PhoneNumber.class);
    }

}
