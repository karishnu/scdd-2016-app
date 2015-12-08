package com.startupsclub.scdd;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.startupsclub.scdd.Adapters.CityRVAdapter;
import com.startupsclub.scdd.RowElements.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/4/2015.
 */
public class Home extends Fragment {

    Context context;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_home, container, false);

        context = getActivity();

        ArrayList<City> cityList = new ArrayList<>();

        cityList.add(new City("Mumbai", "23rd November"));
        cityList.add(new City("Delhi", "24th November"));
        cityList.add(new City("Hyderabad", "25th November"));

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

        GridLayoutManager llm = new GridLayoutManager(context, 2);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        CityRVAdapter adapter = new CityRVAdapter(cityList,context);
        rv.setAdapter(adapter);

        return rootView;
    }
}
