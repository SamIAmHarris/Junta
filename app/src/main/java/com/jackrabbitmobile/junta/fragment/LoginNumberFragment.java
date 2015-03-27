package com.jackrabbitmobile.junta.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.activity.CreateTeamActivity;
import com.jackrabbitmobile.junta.model.User;

import me.philio.pinentry.PinEntryView;

/**
 * Created by SamMyxer on 1/29/15.
 */
public class LoginNumberFragment extends Fragment {


    PinEntryView pinEntryViewOne;
    PinEntryView pinEntryViewTwo;
    PinEntryView pinEntryViewThree;


    Button addPhoneNumberBT;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login_number, container, false);


        pinEntryViewOne = (PinEntryView) v.findViewById(R.id.pin_entry_one);
        pinEntryViewTwo = (PinEntryView) v.findViewById(R.id.pin_entry_two);
        pinEntryViewThree = (PinEntryView) v.findViewById(R.id.pin_entry_three);

        addPhoneNumberBT = (Button) v.findViewById(R.id.add_phone_button);
        addPhoneNumberBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User) User.getCurrentUser();
                if(user != null) {
                    String number = "";
                    number += pinEntryViewOne.getText().toString() + pinEntryViewTwo.getText().toString() + pinEntryViewThree.getText().toString();
                    Toast.makeText(getActivity(), number, Toast.LENGTH_SHORT).show();
                    user.setPhoneNumber(number);

                    Intent createJoinIntent = new Intent(getActivity(), CreateTeamActivity.class);
                    startActivity(createJoinIntent);
                } else {
                    Toast.makeText(getActivity(), "Please Login or Sign Up Please!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;

    }
}
