package com.jackrabbitmobile.junta.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jackrabbitmobile.junta.adapter.MembersAdapter;
import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.model.PhoneNumber;
import com.jackrabbitmobile.junta.model.Team;
import com.jackrabbitmobile.junta.model.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SamMyxer on 1/13/15.
 */
public class LeftDrawerFragment extends android.support.v4.app.Fragment {

    private static final int PICK_CONTACT = 5490;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageButton addContactBT;
    TextView groupNameTV;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mFromSavedInstanceState;
    private View containerView;
    private boolean isDrawerOpened=false;
    private Uri uriContact;
    private String contactID;

    private static final String[] PHONE_REGEX = {"/", "+", "(", ")", "-"};


    ArrayList<String> mMemberList;

    public LeftDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }

        getMemberList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_left_sliding_drawer, container, false);

        mMemberList = new ArrayList<String>();
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.left_sliding_users_rv);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MembersAdapter(mMemberList);
        mRecyclerView.setAdapter(mAdapter);

        groupNameTV = (TextView) layout.findViewById(R.id.left_sliding_group_name_tv);
        setGroupName();

        addContactBT = (ImageButton) layout.findViewById(R.id.left_sliding_add_contact_bt);
        addContactBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });



        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    uriContact = data.getData();
                    retrieveContactNumber();
                }
        }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(100);

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }


    public void getMemberList() {
        final User currentUser = (User) User.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Team myTeam = (Team) currentUser.getTeam();
            ParseQuery<User> query = ParseQuery.getQuery(User.class);
            query.whereEqualTo("team", myTeam);
            query.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> parseObjects, com.parse.ParseException e) {
                    Log.i("Debugging", "Debugging");
                    for (User u : parseObjects) {
                        mMemberList.add(u.getUsername());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            // show the signup or login screen
        }
    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getActivity().getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {
            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }
        cursorID.close();
        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactNumber = contactNumber.replaceAll("[^0-9.]", "");
            if(contactNumber.length() > 10) {
                contactNumber = contactNumber.substring(1);
            }
            showContactAlertDialog(contactNumber);
        }

        cursorPhone.close();
    }

    public void showContactAlertDialog(final String number) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Add Contact");
        alert.setMessage("Would you like to add " + number + " to your group?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                User user = (User) User.getCurrentUser();
                if(user != null) {
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber.setPhoneNumber(number);
                    phoneNumber.setTeam((Team) user.getTeam());
                    phoneNumber.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(getActivity(), number + " has been added to your group!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    //Show login/signup screen
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void openDrawerFromActivity() {
        mDrawerLayout.openDrawer(containerView);
    }

    public void setGroupName() {
        User user = (User) User.getCurrentUser();
        if (user != null) {
            Team team = (Team) user.getTeam();
            try {
                team.fetchIfNeeded();
                groupNameTV.setText(team.getTeamName());
            } catch (ParseException pe) {
                groupNameTV.setText("Could not find team name!");
            }
        } else {
            //send the user to the login screen
        }
    }


}
