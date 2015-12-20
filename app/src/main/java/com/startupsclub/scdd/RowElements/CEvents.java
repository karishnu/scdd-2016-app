package com.startupsclub.scdd.RowElements;

/**
 * Created by Amartya on 12/7/2015.
 */
public class CEvents {

    String year;
    String date;
    String day;
    String title;
    String venue;
    String loc;

   public  CEvents(String year,String date,String day,String title,String venue,String loc) {
        this.year = year;
        this.date =date;
        this.day=day;
        this.title=title;
        this.venue=venue;
        this.loc=loc;
    }
    public String get_year(){
        return year;
    }
    public String get_date(){
        return date;
    }

    public String get_day(){
        return day;
    }
    public String get_title(){
        return title;
    }
    public String get_venue(){
        return venue;
    }
    public String get_venue_location(){ return loc; }


}
