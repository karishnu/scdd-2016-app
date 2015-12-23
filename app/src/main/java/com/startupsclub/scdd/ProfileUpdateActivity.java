package com.startupsclub.scdd;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.startupsclub.scdd.web.PostRequest;
import com.startupsclub.scdd.web.Sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class ProfileUpdateActivity extends AppCompatActivity implements PostRequest.PostRequestResponseHandler {


    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    Button bt;
    String fn,ln,em,mob,com_name,des,add;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pb=(ProgressBar)findViewById(R.id.progressBar);

        TextView tv=(TextView)findViewById(R.id.tv_username);
        tv.setText(getSharedPreferences("login_data",0).getString("username",""));

        tv=(TextView)findViewById(R.id.change);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref=getSharedPreferences("user_data",0);

        ed1=(EditText)findViewById(R.id.first_name); ed1.setText(pref.getString("first_name",""));
        ed2=(EditText)findViewById(R.id.last_name); if(!pref.getString("last_name","").equals("null") )ed2.setText(pref.getString("last_name",""));
        ed3=(EditText)findViewById(R.id.email);  ed3.setText(pref.getString("email",""));

        ed4=(EditText)findViewById(R.id.mobile);        ed4.setText(pref.getString("phone",""));
        ed5=(EditText)findViewById(R.id.com_name); if(!pref.getString("company_name","").equals("null") )    ed5.setText(pref.getString("company_name",""));
        ed6=(EditText)findViewById(R.id.designation); if(!pref.getString("designation","").equals("null"))     ed6.setText(pref.getString("designation",""));
        ed7=(EditText)findViewById(R.id.address);     if(!pref.getString("address","").equals("null"))  ed7.setText(pref.getString("address",""));

        bt=(Button)findViewById(R.id.update_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fn = ed1.getText().toString() + "";
                ln = ed2.getText().toString() + "";
                em = ed3.getText().toString() + "";
                mob = ed4.getText().toString() + "";
                com_name = ed5.getText().toString() + "";
                des = ed6.getText().toString() + "";
                add = ed7.getText().toString() + "";

                if (ed3.getText().toString().contains("@")) {
                    progressbarvisiblity(true);
                    Hashtable<String, String> ht = new Hashtable<>();
                    ht.put("first_name", fn);
                    ht.put("last_name", ln);
                    ht.put("email", em);
                    ht.put("mobile", mob);
                    ht.put("company_name", com_name);
                    ht.put("designation", des);
                    ht.put("address", add);

                    ht.put("username",getSharedPreferences("login_data",0).getString("username","abc"));

                    new PostRequest(ht, "http://myappserver.netau.net/SCDD/update_profile.php").setListener(ProfileUpdateActivity.this);
                }
                else
                    ed3.setError("Invalid email");
            }
        });
    }


    public Boolean validate(){
        return  true;
    }
    @Override

    public void postRequestResponse(String jSONresponse) {

        String status = "";
        try {
            JSONObject jObject = new JSONObject(jSONresponse);
            Log.e("Json response", jObject.toString());
            JSONArray jArray = jObject.getJSONArray("update_profile");

            JSONObject jTemp = jArray.getJSONObject(0);
            status = jTemp.getString("status");
            Log.e("status", status + "");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            status = "201";
            e.printStackTrace();
        }

        progressbarvisiblity(false);

        if (status.equals("false")){
            progressbarvisiblity(false);
            displayDialog("Update Failed","Please check your internet connection");
        }

        else if (status.equals("true")) {


            SharedPreferences.Editor preferences=getSharedPreferences("user_data",0).edit();
            preferences.putString("email",em);
            preferences.putString("first_name",fn);
            preferences.putString("last_name",ln);
            preferences.putString("phone",mob);
            preferences.putString("company_name",com_name);
            preferences.putString("designation",des);
            preferences.putString("address",add);
            preferences.commit();
            Toast.makeText(this,"Profile Updated",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));

        }
        else
        {
           displayDialog("Update Failed","Please check your internet connection");
        }
    }

    public void displayDialog(String head,String msg)
    {
        AlertDialog ad=new AlertDialog.Builder(this).create();
        ad.setTitle(head);
        ad.setMessage(msg);
        ad.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        ad.show();
    }

    public void progressbarvisiblity(Boolean visible){
        if (visible){
            pb.setVisibility(View.VISIBLE);
            bt.setVisibility(View.INVISIBLE);}
        else {
            pb.setVisibility(View.GONE);
            bt.setVisibility(View.VISIBLE);
        }
    }
}
