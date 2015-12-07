package com.startupsclub.scdd.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.startupsclub.scdd.R;
import com.startupsclub.scdd.RowElements.CEvents;

import java.util.ArrayList;


/**
 * Created by Amartya on 12/7/2015.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    ArrayList<CEvents> list;

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView year;
        TextView date;
        TextView day;
        TextView title;
        TextView venue;

        EventsViewHolder(View view) {
            super(view);
            cv = (CardView)view.findViewById(R.id.events_cv);
            year = (TextView)view.findViewById(R.id.event_year);
            date= (TextView)view.findViewById(R.id.event_date);
            day= (TextView)view.findViewById(R.id.event_wday);
            title= (TextView)view.findViewById(R.id.event_name);
            venue= (TextView)view.findViewById(R.id.event_loc);

        }
    }



    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item_events = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.events_row, viewGroup, false);

        return new EventsViewHolder(item_events);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder h,int i)
    {
        h.year.setText(list.get(i).get_year());
        h.date.setText(list.get(i).get_date());
        h.day.setText(list.get(i).get_day());
        h.title.setText(list.get(i).get_title());
        h.venue.setText(list.get(i).get_venue());
    }


    public EventsAdapter(ArrayList<CEvents> list)
    {
        this.list=list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
