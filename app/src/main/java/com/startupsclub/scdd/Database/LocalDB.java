package com.startupsclub.scdd.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.startupsclub.scdd.RowElements.Agenda;
import com.startupsclub.scdd.RowElements.CEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amartya on 12/13/2015.
 */
public class LocalDB extends SQLiteOpenHelper {

    private String tUser_data="user_data";
    private String tAgenda_data="agenda_data";
    private String tEvents_data="events_data";

    private static String DATABASE_NAME="local_data";
    private static int DATABASE_VERSION=1;

    private String CREATE_TABLEuser_data="create table " + tUser_data+ "( username varchar(20) primary key, email varchar(10) , first_name varchar(15) , last_name varchar(15) , mobile varchar(10) , com_name varchar(20) , designation varchar(30) ,address varchar(40) );";
    private String CREATE_TABLEevents_data="create table " + tEvents_data+ "( title varchar(40), place varchar(20) , year varchar(4) , date varchar(10) , weekday varchar(8) );";
    private String CREATE_TABLEagenda_data="create table " + tAgenda_data+ "( title varchar(20), time varchar(20) );";

    public LocalDB(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLEuser_data);
        Log.e("LocalDB.java", "user_data table created");

        db.execSQL(CREATE_TABLEevents_data);
        Log.e("LocalDB.java", "events_data table created");

        db.execSQL(CREATE_TABLEagenda_data);
        Log.e("LocalDB.java", "agenda_data table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void updateUserData(String username,String email,String f_name,String l_name,String mobile,String com_name,String designation,String address)
    {
        SQLiteDatabase writableDB=this.getWritableDatabase();
        writableDB.delete(tUser_data,null,null);

        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("first_name",f_name);
        cv.put("last_name",l_name);
        cv.put("mobile",mobile);
        cv.put("com_name",com_name);
        cv.put("designation",designation);
        cv.put("address",address);

        writableDB.insert(tUser_data,null,cv);

        writableDB.close();
    }
    public void updateEventsData(ArrayList<CEvents> alist)
    {
        SQLiteDatabase writableDB=this.getWritableDatabase();
        writableDB.delete(tEvents_data,null,null);

        for(int i=0;i<alist.size();i++) {
            ContentValues cv = new ContentValues();
            cv.put("title", alist.get(i).get_title());
            cv.put("place", alist.get(i).get_venue());
            cv.put("year", alist.get(i).get_year());
            cv.put("date", alist.get(i).get_date());
            cv.put("weekday", alist.get(i).get_day());

            writableDB.insert(tEvents_data, null, cv);
        }
        writableDB.close();
    }

    public void updateAgendaData(ArrayList<Agenda> alist)
    {
        SQLiteDatabase writableDB=this.getWritableDatabase();
        writableDB.delete(tAgenda_data,null,null);

        for(int i=0;i<alist.size();i++) {
            ContentValues cv=new ContentValues();
            cv.put("title",alist.get(i).event);
            cv.put("time",alist.get(i).time);

            writableDB.insert(tAgenda_data,null,cv);
        }

        writableDB.close();
    }

    public ArrayList<CEvents> getEventsData() {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        Cursor cursor = readableDB.query(tEvents_data, null, null, null, null, null, null, null);
        Log.e("size",cursor.getCount()+"");
        ArrayList<CEvents> al = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String place = cursor.getString(cursor.getColumnIndex("place"));
                    String year = cursor.getString(cursor.getColumnIndex("year"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String weekday = cursor.getString(cursor.getColumnIndex("weekday"));

                    CEvents ev = new CEvents( year,date,weekday,title,place);
                    al.add(ev);

                } while (cursor.moveToNext());
            }
        }

        return al;
    }

    public ArrayList<Agenda> getAgendaData() {
        SQLiteDatabase readableDB = this.getReadableDatabase();

        Cursor cursor = readableDB.query(tAgenda_data, null, null, null, null, null, null, null);

        ArrayList<Agenda> al = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));

                    Agenda ag = new Agenda(title,time);
                    al.add(ag);

                } while (cursor.moveToNext());
            }
        }

        return al;
    }
}
