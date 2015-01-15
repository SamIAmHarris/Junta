package com.jackrabbitmobile.junta.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by SamMyxer on 1/10/15.
 */
@ParseClassName("_User")
public class User extends ParseUser{

    public User() {}

    public String getPhoneNumber() {
        return getString("phone");
    }

    public void setPhoneNumber(String phoneNumber) {
        put("phone", phoneNumber);
    }

    public ParseObject getTeam() {
        return getParseObject("team");
    }

    public void setTeam(Team team) {
        put("team",team);
    }

    public void setName(String name) {put("name", name);}

    public String getName() {return  getString("name");}


}
