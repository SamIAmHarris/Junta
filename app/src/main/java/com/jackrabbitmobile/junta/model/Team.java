package com.jackrabbitmobile.junta.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SamMyxer on 1/10/15.
 */
@ParseClassName("Team")
public class Team extends ParseObject{

    public Team() {}

    public String getTeamName() {
        return getString("teamName");
    }

    public void setTeamName(String teamName) {
        put("teamName", teamName);
    }

    public void setCreatedBy(User user) {put("createdBy", user);}

    public List getUsers() {
        return getList("users");
    }

    public List getUserPhoneNumbers() {
        return getList("userPhoneNumbers");
    }

    public List<TeamActivity> getTeamActivities() {return getList("activites");}

}
