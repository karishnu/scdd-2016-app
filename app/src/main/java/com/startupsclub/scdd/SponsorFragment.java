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
import android.widget.TextView;

import com.startupsclub.scdd.Adapters.SponsorsRVAdapter;
import com.startupsclub.scdd.RowElements.Sponsor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/7/2015.
 */
public class SponsorFragment extends Fragment {
    Context context;
    TextView city_name;

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sponsor_fragment, container, false);

        context = getActivity();
        RecyclerView sponsor_rv;
        List<Sponsor> sponsor_list;

        sponsor_list = new ArrayList<>();

        sponsor_rv = (RecyclerView) rootView.findViewById(R.id.sponsor_rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        sponsor_rv.setLayoutManager(llm);

        sponsor_list.add(new Sponsor("Sponsor","",R.drawable.sponsor));
        sponsor_list.add(new Sponsor("Web Partner","",R.drawable.web_partner));
        sponsor_list.add(new Sponsor("Eco-System Partner","",R.drawable.ecosystem_partner));
        sponsor_list.add(new Sponsor("Organising Partner","",R.drawable.organising_partner));
        sponsor_list.add(new Sponsor("Technology Partner","",R.drawable.technology_partner));
        sponsor_list.add(new Sponsor("Eco System Partner","",R.drawable.ecosystem_partner_2));
        sponsor_list.add(new Sponsor("Eco System Partner","",R.drawable.ecosystem_partner_3));
        sponsor_list.add(new Sponsor("Angel Partners","",R.drawable.angel_partner));
        sponsor_list.add(new Sponsor("Live Streaming Partner","",R.drawable.live_streaming_partner));
        sponsor_list.add(new Sponsor("Student Startup Partner","",R.drawable.student_startup_partner));

        SponsorsRVAdapter adapter = new SponsorsRVAdapter(sponsor_list);
        sponsor_rv.setAdapter(adapter);

        city_name = (TextView) rootView.findViewById(R.id.sponsor_cityname);
        city_name.setText(((MainActivity) getActivity()).city_name);

        return rootView;
    }
}
