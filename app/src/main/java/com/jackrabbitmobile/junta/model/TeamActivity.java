package com.jackrabbitmobile.junta.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by SamMyxer on 1/10/15.
 */
@ParseClassName("TeamActivity")
public class TeamActivity extends ParseObject{

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public void setCreatedBy(User user) {
        put("createdBy", user);
    }

    public ParseObject getTeam() {
        return getParseObject("team");
    }

    public void setTeam(Team team) {
        put("team", team);
    }

    public String getLocationName(){
        return getString("locationName");
    }

    public void setLocationName(String locationName) {
        put("locationName", locationName);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type", type);
    }

    public Date getTime() {
        return getDate("time");
    }

    public void setTime(Date date) {
        put("time", date);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("location");
    }

}
