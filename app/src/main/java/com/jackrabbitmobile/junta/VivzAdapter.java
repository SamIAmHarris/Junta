package com.jackrabbitmobile.junta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by SamMyxer on 1/13/15.
 */
public class VivzAdapter  extends RecyclerView.Adapter<VivzAdapter.MyViewHolder> {
    List<Information> data= Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    public VivzAdapter(Context context, List<Information> data){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent,false);

        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current=data.get(position);

        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title= (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);

        }


        @Override
        public void onClick(View v) {

        }
    }
}
