package com.startupsclub.scdd.web;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Amartya on 12/8/2015.
 */
public class PostRequest {

    private PostRequestResponseHandler resposeHandler;
    Context context;
    Hashtable hashtable;
    String url;

    public PostRequest(Hashtable<String, String> ht, String url) {
        hashtable = ht;
        this.url = url + "";
        Log.e("url", this.url);
        new AsyncPerformPostRequest().execute();
    }

    public void setListener(PostRequestResponseHandler handler) {
        resposeHandler = handler;
    }

    public class AsyncPerformPostRequest extends AsyncTask<Void, Void, String> {
        String urlParams;

        @Override
        protected void onPreExecute() {
            urlParams = getUrlParams(hashtable);
            Log.e("UrlParameters:", urlParams + "");
        }

        @Override
        protected String doInBackground(Void... params) {

            String response = "";
            response = excutePost(url, urlParams);
            Log.e("rerf", response + "....");
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            String status = "";

            try {
                JSONObject jObject = new JSONObject(response);
                JSONArray jArray = jObject.getJSONArray("login");
                JSONObject jTemp = jArray.getJSONObject(0);
                status = jTemp.getString("status");
                Log.e("status", status + "");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                status = "--1--";
                e.printStackTrace();
            }
            resposeHandler.postRequestResponse(status);
        }

        public String excutePost(String targetURL, String urlParameters) {
            URL url;
            HttpURLConnection connection = null;
            try {
                //Create connection
                url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length", "" +
                        Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }

                rd.close();

                return response.toString();

            } catch (Exception e) {

                e.printStackTrace();
                return e.toString();

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }

    public String getUrlParams(Hashtable<String, String> params) {
        if (params.size() == 0)
            return "";

        StringBuffer buf = new StringBuffer();
        Enumeration<String> keys = params.keys();
        while (keys.hasMoreElements()) {
            buf.append(buf.length() == 0 ? "" : "&");
            String key = keys.nextElement();
            buf.append(key).append("=").append(params.get(key));

        }
        return buf.toString();
    }


    public void displayDialog(String title, String message) {
        AlertDialog ad = new AlertDialog.Builder(context).create();
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        ad.show();
    }


    public interface PostRequestResponseHandler {
        public void postRequestResponse(String st);
    }
}
