package com.jackrabbitmobile.junta.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackrabbitmobile.junta.R;

/**
 * Created by SamMyxer on 1/29/15.
 */
public class LoginGroupFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.activity_create_team, container, false);


        return v;
    }
}
