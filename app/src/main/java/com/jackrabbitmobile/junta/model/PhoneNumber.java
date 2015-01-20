package com.jackrabbitmobile.junta.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by SamMyxer on 1/14/15.
 */
@ParseClassName("PhoneNumber")
public class PhoneNumber extends ParseObject{

    public List getTeams() {
        return getList("teams");
    }

    public void addTeam(Team team) {
        addUnique("teams", team);
    }

    public void setTeam(Team team) {put("team", team);}

    public Team getTeam() {return (Team) get("team");}

    public void setPhoneNumber(String number) {put("phoneNumber", number);}

    public String getPhoneNumber() {return getString("phoneNumber");}

}
