package com.jackrabbitmobile.junta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.model.TeamActivity;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by SamMyxer on 1/15/15.
 */
public class TeamActivityCardAdapter extends RecyclerView.Adapter<TeamActivityCardAdapter.ViewHolder> {
    private ArrayList<TeamActivity> mDataset;
    Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mLocationTV;
        public TextView mTimeTV;
        public LinearLayout mUsersLayout;
        public CheckBox mCheckbox;

        public ViewHolder(View v) {
            super(v);
            mLocationTV = (TextView) v.findViewById(R.id.location_tv_activity_card);
            mTimeTV = (TextView) v.findViewById(R.id.time_tv_activity_card);
            mUsersLayout = (LinearLayout) v.findViewById(R.id.user_array_layout);
            mCheckbox = (CheckBox) v.findViewById(R.id.checkbox_activity_card);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TeamActivityCardAdapter(ArrayList<TeamActivity> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TeamActivityCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_team_activity, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final TeamActivity data = mDataset.get(position);

        final List<ParseUser> users = data.getList("users");

        Date date = data.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String time = sdf.format(date);
        int hours = Integer.valueOf(time.substring(0,2));
        int minutes = Integer.valueOf(time.substring(3));
        String ampm;

        if(hours == 0) {
            hours = 12;
            ampm = "A";
        } else if (hours > 12) {
            hours = hours - 12;
            ampm = "P";
        } else if (hours == 12) {
            ampm = "P";
        } else {
            ampm = "A";
        }

        holder.mLocationTV.setText(data.getLocationName());

        if(minutes == 0) {
            holder.mTimeTV.setText(hours + ampm + " @ ");
        } else {
            holder.mTimeTV.setText(hours + ":" + minutes + ampm + " @ ");
        }

        addUserCircles(users, holder.mUsersLayout);
        holder.mCheckbox.setChecked(checkIfAdded(users));
        holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if(isChecked) {
                    //add the user to the team activity
                    //holder.mUsersLayout.removeAllViews();
                    addUser(currentUser, data);
                    addUserCircles(users, holder.mUsersLayout);
                } else {
                    //remove the user from the team activity
                    holder.mUsersLayout.removeAllViews();
                    removeUser(currentUser, users, data);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addUserCircles(List<ParseUser> users, LinearLayout userLayout) {

        if(users != null && users.size() != 0) {
            for (ParseUser user: users) {
                try {
                    String name = user.fetchIfNeeded().getString("username");
                    final TextView textView = new TextView(mContext);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
                    layoutParams.setMargins(8, 0, 8, 0);
                    textView.setLayoutParams(layoutParams);
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_image_view_blue_circle));
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(18);
                    textView.setText(getInitials(name));
                    userLayout.addView(textView);
                } catch (ParseException p) {

                }
            }
        }

    }
    public static String getInitials(String name) {
        StringBuilder sbInitials = new StringBuilder();
        String[] nameParts = name.split("\\s+");
        int nameLength;

        if (nameParts.length > 2) {
            nameLength = 2;
        } else {
            nameLength = nameParts.length;
        }

        for (int i = 0; i < nameLength; i++) {
            String part = nameParts[i];
            if (part.length() > 0) {
                sbInitials.append(part.charAt(0));
            }
        }
        String initials = sbInitials.toString().toUpperCase();
        return initials;
    }

    public boolean checkIfAdded(List<ParseUser> users) {

        ParseUser currentUser = ParseUser.getCurrentUser();
        for(ParseUser user: users) {
            if(currentUser.getObjectId().equals(user.getObjectId())) {
                return true;
            }

        }
        return false;
    }

    public void addUser(ParseUser user, TeamActivity teamActivity) {
        teamActivity.addUnique("users", user);
        teamActivity.saveInBackground();
    }

    public void removeUser(ParseUser user, final List<ParseUser> users, TeamActivity teamActivity) {
        teamActivity.removeAll("users", Arrays.asList(user));
        teamActivity.saveInBackground();
    }
}
