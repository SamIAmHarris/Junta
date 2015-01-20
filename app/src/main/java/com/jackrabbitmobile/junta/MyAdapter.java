package com.jackrabbitmobile.junta;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jackrabbitmobile.junta.model.TeamActivity;

import java.util.ArrayList;

/**
 * Created by SamMyxer on 1/15/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<TeamActivity> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mLocationTV;
        public TextView mTimeTV;
        public TextView mWhatTV;

        public ViewHolder(View v) {
            super(v);
            mLocationTV = (TextView) v.findViewById(R.id.location_tv_activity_card);
            mTimeTV = (TextView) v.findViewById(R.id.time_tv_activity_card);
            mWhatTV = (TextView) v.findViewById(R.id.what_tv_activity_card);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<TeamActivity> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        TeamActivity data = mDataset.get(position);
        holder.mLocationTV.setText(data.getLocationName());
        holder.mTimeTV.setText(data.getTime().toString());
        holder.mWhatTV.setText(data.getType());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
