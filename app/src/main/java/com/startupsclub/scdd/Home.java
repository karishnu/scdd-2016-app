package com.startupsclub.scdd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.startupsclub.scdd.Adapters.CityRVAdapter;
import com.startupsclub.scdd.Database.LocalDB;
import com.startupsclub.scdd.RowElements.CEvents;
import com.startupsclub.scdd.RowElements.City;
import com.startupsclub.scdd.RowElements.VenueDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 12/4/2015.
 */
public class Home extends Fragment {

    Context context;
    TextView nav_head_email;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_home, container, false);

        context = getActivity();

        ArrayList<City> cityList = new ArrayList<>();

        LocalDB db=new LocalDB(context);
        ArrayList<VenueDetails> aList=new ArrayList<>();
        ArrayList<CEvents> cList=new ArrayList<>();

        aList=db.getVenueDetails();
        cList=db.getEventsData();

        for(int i=0;i<aList.size();i++){
            cityList.add(new City(aList.get(i).getCityName(),cList.get(i).get_date()));
        }
//        cityList.add(new City("Banglore", "16th January"));
//        cityList.add(new City("Chennai", "20th February"));
//        cityList.add(new City("Hyderabad", "19th March"));
//        cityList.add(new City("Coimbatore", "16th April"));
//        cityList.add(new City("Mumbai", "21st May"));
//        cityList.add(new City("Delhi", "18th June"));
//        cityList.add(new City("Pune", "16th July"));
//        cityList.add(new City("Kochi", "20th August"));
//        cityList.add(new City("Ahmedabad", "17th September"));
//        cityList.add(new City("Visakhapatnam", "15th October"));
//        lat.put("Visakhapatnam",(float)17.6883);
//        lon.put("Visakhapatnam",(float)83.2186);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

        GridLayoutManager llm = new GridLayoutManager(context, 2);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        CityRVAdapter adapter = new CityRVAdapter(cityList,context);
        rv.setAdapter(adapter);

        return rootView;
    }
}
