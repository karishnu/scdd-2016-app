package com.startupsclub.scdd.web;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.startupsclub.scdd.Database.LocalDB;
import com.startupsclub.scdd.RowElements.Agenda;
import com.startupsclub.scdd.RowElements.CEvents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Amartya on 12/14/2015.
 */
public class Sync implements PostRequest.PostRequestResponseHandler {

    Context context;
    private SyncCompleteResponder resposeHandler;
    public  Sync(String username,Context context){
        this.context=context;

		Hashtable<String,String> ht=new Hashtable<>();
        ht.put("username",username);

        new PostRequest(ht,"http://myappserver.netau.net/SCDD/sync.php").setListener(this);
    }

    public void setListener(SyncCompleteResponder handler) {
        resposeHandler = handler;
    }

    @Override
    public void postRequestResponse(String jSONResponse) {

        		try {
			JSONObject jObject=new JSONObject(jSONResponse);
			Log.e("Json response",jObject.toString());
			JSONArray jArray = jObject.getJSONArray("sync");

				JSONObject jUserdata = jArray.getJSONObject(0);
			JSONObject jEventsdata = jArray.getJSONObject(1);
			JSONObject jAgendadata = jArray.getJSONObject(2);


			JSONArray jTempArray;
			JSONObject jTemp;

			//Retrieving user data

			jTempArray=jUserdata.getJSONArray("user_data");
			jTemp= jTempArray.getJSONObject(0);
			if(jTemp.getString("success").equals("true")){

				String username=jTemp.getString("username");
				String email=jTemp.getString("email");
				String fn=jTemp.getString("first_name");
				String ln=jTemp.getString("last_name");
				String phone=jTemp.getString("phone");
				String com_name=jTemp.getString("company_name");
				String designation=jTemp.getString("designation");
				String address=jTemp.getString("address");

				//Update local database for user data here.

				LocalDB db=new LocalDB(context);
				db.updateUserData(username,email,fn,ln,phone,com_name,designation,address);

			}

			//Retrieving events data

			jTempArray=jEventsdata.getJSONArray("events_data");
					ArrayList<CEvents> al=new ArrayList<>();

			for(int i=0;i<jTempArray.length();i++){

				jTemp=jTempArray.getJSONObject(i);

				if(jTemp.getString("success").equals("true")){

					String title=jTemp.getString("title");
					String place=jTemp.getString("place");
					String year=jTemp.getString("year");
					String date=jTemp.getString("date");
					String weekday=jTemp.getString("weekday");

					  //Update local database for events here for each row retrieved
				CEvents ce=	new CEvents(year,date,weekday,title,place);
					al.add(ce);
					LocalDB db=new LocalDB(context);
					db.updateEventsData(al);
				}

			}

			//Retrieving agenda data

			jTempArray=jAgendadata.getJSONArray("agenda_data");
				ArrayList<Agenda>	al1=new ArrayList<>();

			for(int i=0;i<jTempArray.length();i++){

				jTemp=jTempArray.getJSONObject(i);

				if(jTemp.getString("success").equals("true")){

					String title=jTemp.getString("title");
					String time=jTemp.getString("time");

					//Update local database for agenda here for each row retrieved
					Agenda ag=new Agenda(time,title);
					al1.add(ag);
					LocalDB db=new LocalDB(context);
					db.updateAgendaData(al1);
				}

			}
                    resposeHandler.syncCompleteResponder(true);

			}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            resposeHandler.syncCompleteResponder(false);
		}
    }

    public  interface SyncCompleteResponder{
        void syncCompleteResponder(Boolean status);
    }
}
