package com.startupsclub.scdd.RowElements;

/**
 * Created by Amartya on 12/20/2015.
 */
public class VenueDetails {

    String city_name;
    String address;
    Float latitude,longitude;

    public VenueDetails(String city_name, String address,Float latitude,Float longitude) {
        this.city_name=city_name;
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getCityName(){ return  city_name; }
    public String getAddress(){ return  address; }

    public Float getLatitude(){ return  latitude; }
    public Float getLongitude(){ return  longitude; }

}
