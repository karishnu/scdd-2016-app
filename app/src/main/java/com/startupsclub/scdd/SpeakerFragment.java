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

import com.startupsclub.scdd.Adapters.SpeakersRVAdapter;
import com.startupsclub.scdd.RowElements.Speaker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/7/2015.
 */
public class SpeakerFragment extends Fragment {
    Context context;
    TextView city_name;

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.speaker_fragment, container, false);

        context = getActivity();
        RecyclerView speaker_rv;
        List<Speaker> speaker_list;

        speaker_list = new ArrayList<>();

        speaker_list.add(new Speaker("Mahesh Bhalla","President, Qwickcilver",R.drawable.speaker9));
        speaker_list.add(new Speaker("Balachandran","Manager, VIT - TBI",R.drawable.speaker4));
        speaker_list.add(new Speaker("Nagaraja Prakasam","Angel Investor, Mentor in Chief at NSRCEL, IIMB",R.drawable.speaker6));
        speaker_list.add(new Speaker("Sharda Balaji","Founder, NovoJuris",R.drawable.speaker8));
        speaker_list.add(new Speaker("Vivek Srinivasan","Managing Director at Prudence Advisors",R.drawable.speakerex2));
        speaker_list.add(new Speaker("Salma Moosa","Core Designated Partner",R.drawable.speakerex));
        speaker_list.add(new Speaker("Pavan Kumar","CEO & Co-Founder at White Lotus Lifescapes Pvt. Ltd.",R.drawable.speaker10));
        speaker_list.add(new Speaker("Khuzema Siamwala","Founder, Siam Computing",R.drawable.speaker3));

        speaker_rv = (RecyclerView) rootView.findViewById(R.id.speaker_rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        speaker_rv.setLayoutManager(llm);

        SpeakersRVAdapter adapter = new SpeakersRVAdapter(speaker_list);

        speaker_rv.setAdapter(adapter);

        city_name = (TextView) rootView.findViewById(R.id.speaker_cityname);
        city_name.setText(((MainActivity) getActivity()).city_name);

        return rootView;
    }
}
