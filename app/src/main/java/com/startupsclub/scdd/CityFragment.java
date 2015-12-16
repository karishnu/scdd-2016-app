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

import com.startupsclub.scdd.Adapters.AgendaRVAdapter;
import com.startupsclub.scdd.Database.LocalDB;
import com.startupsclub.scdd.RowElements.Agenda;

import java.util.ArrayList;

/**
 * Created by admin on 12/7/2015.
 */
public class CityFragment extends Fragment {
    Context context;

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.city_fragment, container, false);

        context = getActivity();
        ((MainActivity) getActivity()).city_name = getArguments().getString("city");

        return rootView;
    }

}
