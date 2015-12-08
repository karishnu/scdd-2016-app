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

import com.startupsclub.scdd.RowElements.Agenda;
import com.startupsclub.scdd.Adapters.AgendaRVAdapter;

import java.util.ArrayList;

/**
 * Created by admin on 12/7/2015.
 */
public class AgendaFragment extends Fragment {
    ArrayList<Agenda> agenda_list;
    RecyclerView agenda_rv;
    Context context;

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_agenda, container, false);

        context = getActivity();

        agenda_list = new ArrayList<>();

        agenda_list.add(new Agenda("9:30am - 10am","Speech"));
        agenda_list.add(new Agenda("9:30am - 10am","Speech"));
        agenda_list.add(new Agenda("9:30am - 10am","Speech"));
        agenda_list.add(new Agenda("9:30am - 10am","Speech"));

        agenda_rv = (RecyclerView) rootView.findViewById(R.id.agenda_rv);

        agenda_rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        agenda_rv.setLayoutManager(llm);

        AgendaRVAdapter rvAdapter = new AgendaRVAdapter(agenda_list);

        agenda_rv.setAdapter(rvAdapter);

        return rootView;
    }

}
