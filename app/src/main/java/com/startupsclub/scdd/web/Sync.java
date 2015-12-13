package com.startupsclub.scdd.web;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

				//Update local database here.
			}

			//Retrieving events data

			jTempArray=jEventsdata.getJSONArray("events_data");
			for(int i=0;i<jTempArray.length();i++){

				jTemp=jTempArray.getJSONObject(i);

				if(jTemp.getString("success").equals("true")){

					String title=jTemp.getString("title");
					String place=jTemp.getString("place");
					String year=jTemp.getString("year");
					String date=jTemp.getString("date");
					String weekday=jTemp.getString("weekday");

					  //Update local database for events here for each row retrieved

				}

			}

			//Retrieving agenda data

			jTempArray=jAgendadata.getJSONArray("agenda_data");
			for(int i=0;i<jTempArray.length();i++){

				jTemp=jTempArray.getJSONObject(i);

				if(jTemp.getString("success").equals("true")){

					String title=jTemp.getString("title");
					String start_time=jTemp.getString("start_time");
					String end_time=jTemp.getString("end_time");

					//Update local database for agenda here for each row retrieved
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
