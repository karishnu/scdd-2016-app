package com.startupsclub.scdd.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.startupsclub.scdd.R;

/**
 * Created by Amartya on 12/20/2015.
 */
public class VenueFrag extends Fragment implements OnMapReadyCallback {

    String city_name;
    Float latitude,longitude;
    private GoogleMap mMap;

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.venue_fragment, container, false);

        Bundle b=getArguments();
       city_name= b.getString("city_name");
        latitude=b.getFloat("latitude");
        longitude=b.getFloat("longitude");

        TextView tv=(TextView)rootView.findViewById(R.id.tv_map);
        tv.setText(city_name);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in city and move the camera
        LatLng cityLoc = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(cityLoc).title(city_name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cityLoc));
    }
}
