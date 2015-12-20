package com.startupsclub.scdd.RowElements;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Amartya on 12/20/2015.
 */
public class CitiesGeoloc {

    Hashtable<String,Float> lat=new Hashtable<>();
   Hashtable<String,Float> lon=new Hashtable<>();

    public CitiesGeoloc(){
        lat.put("Chennai",(float)13.0827);
        lon.put("Chennai",(float)80.2707);

        lat.put("Banglore",(float)12.9667);
        lon.put("Banglore",(float)77.5667);

        lat.put("Hyderabad",(float)17.3700);
        lon.put("Hyderabad",(float)78.4800);

        lat.put("Coimbatore",(float)11.0183);
        lon.put("Coimbatore",(float)76.9725);

        lat.put("Mumbai",(float)18.9750);
        lon.put("Mumbai",(float)72.8258);

        lat.put("Delhi",(float)28.6139);
        lon.put("Delhi",(float)77.2090);

        lat.put("Pune",(float)18.5203);
        lon.put("Pune",(float)73.8567);

        lat.put("Kochi",(float)9.9700);
        lon.put("Kochi",(float)76.2800);

        lat.put("Ahmedabad",(float)23.0300);
        lon.put("Ahmedabad",(float)72.5800);

        lat.put("Visakhapatnam",(float)17.6883);
        lon.put("Visakhapatnam",(float)83.2186);
    }

    public float getLatitude(String name) {
        return lat.get(name);
    }

    public float getLongitude(String name) {
        return lon.get(name);
    }
}
