package com.startupsclub.scdd;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.startupsclub.scdd.Database.LocalDB;
import com.startupsclub.scdd.RowElements.CEvents;
import com.startupsclub.scdd.Adapters.EventsAdapter;

import java.util.ArrayList;


/**
 * Created by Amartya on 12/7/2015.
 */
public class EventsFragment extends Fragment {

Context context;
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_events, container, false);

        context = getActivity();

        RecyclerView recList = (RecyclerView)rootView.findViewById(R.id.eventsList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        LocalDB db=new LocalDB(context);
        ArrayList<CEvents> aList=new ArrayList<>();
        aList=db.getEventsData();

        EventsAdapter adapter=new EventsAdapter(aList);
        recList.setAdapter(adapter);

        return  rootView;
    }

}
