package com.startupsclub.scdd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.startupsclub.scdd.web.PostRequest;
import com.startupsclub.scdd.web.Sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class FeedbackActivity extends AppCompatActivity implements PostRequest.PostRequestResponseHandler {
    ProgressBar pb;
    EditText ed;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }
    public void feedback(View view){
         pb= (ProgressBar) findViewById(R.id.fb_progressBar);
         ed= (EditText) findViewById(R.id.ed_feedback);
         bt= (Button) findViewById(R.id.feedback_button);


        //feedback backend code goes here
        // ed is input space


        SharedPreferences prefs = this.getSharedPreferences("login_data", Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);

        if(ed.getText().toString().length()>=5) {
            pb.setVisibility(View.VISIBLE);
            bt.setVisibility(View.INVISIBLE);
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("username", username);
            ht.put("review", ed.getText().toString() + "");

            new PostRequest(ht, "http://myappserver.netau.net/SCDD/submit_review.php").setListener(this);
        }
        else
            ed.setError("Must be at least 5 characters");

    }


    @Override

    public void postRequestResponse(String jSONresponse) {

        String status = "";
        try {
            JSONObject jObject = new JSONObject(jSONresponse);
            Log.e("Json response", jObject.toString());
            JSONArray jArray = jObject.getJSONArray("review");


            JSONObject jTemp = jArray.getJSONObject(0);
            status = jTemp.getString("status");
            Log.e("status", status + "");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            status = "201";
            e.printStackTrace();
        }
        pb.setVisibility(View.INVISIBLE);
        bt.setVisibility(View.VISIBLE);
        if (status.equals("1")) {

            Toast.makeText(this,"Feedback submitted",Toast.LENGTH_LONG).show();
            finish();

        }
        else
        {
            AlertDialog ad=new AlertDialog.Builder(this).create();
            ad.setTitle("Error while submitting feedback");
            ad.setMessage("Please check you network connection");
            ad.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            ad.show();
        }
    }
}
