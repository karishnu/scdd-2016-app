package com.startupsclub.scdd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.startupsclub.scdd.web.PostRequest;
import com.startupsclub.scdd.web.PostRequest.PostRequestResponseHandler;
import com.startupsclub.scdd.web.Sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;


public class LoginActivity extends AppCompatActivity implements PostRequestResponseHandler, Sync.SyncCompleteResponder {


    EditText ed1, ed2;
    ProgressBar pb;
    String username, password;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tv = (TextView) findViewById(R.id.forgot_passwd_tv);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView tv1 = (TextView) findViewById(R.id.newusr_tv);
        tv1.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        login_button = (Button) findViewById(R.id.login_button);
        ed1 = (EditText) findViewById(R.id.username_edtext);
        ed2 = (EditText) findViewById(R.id.passwd_edtext);
        username = ed1.getText().toString();
        password = ed2.getText().toString();
    }


    public void login(View view) {
        //Perform login task here.
        //finish();
        if (attempt_login()) {

            progressbarvisiblity(true);
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("username", ed1.getText().toString());
            ht.put("password", ed2.getText().toString());

            new PostRequest(ht, "http://myappserver.netau.net/SCDD/login.php").setListener(this);
        }

    }

    public Boolean attempt_login() {

        String email = ed1.getText().toString();
        String pass = ed2.getText().toString();
        if (!validateEmail(email)) {
            ed1.setError("Invalid email !");
            progressbarvisiblity(false);
            return false;
        }
        if (pass.length() < 5) {
            ed2.setError("Atleast 5 characters req");
            progressbarvisiblity(false);
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email) {
        if (email.contains("@"))
            return true;
        else
            return false;

    }

    public void forgot_password(View view) {
        // forgot password code here
    }

    public void signup(View view) {
        finish();
        startActivity(new Intent(this, SignupActivity.class));
    }

    @Override

    public void postRequestResponse(String jSONresponse) {

        String status = "";
        try {
            JSONObject jObject = new JSONObject(jSONresponse);
            Log.e("Json response", jObject.toString());
            JSONArray jArray = jObject.getJSONArray("login");


            JSONObject jTemp = jArray.getJSONObject(0);
            status = jTemp.getString("status");
            Log.e("status", status + "");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            status = "201";
            e.printStackTrace();
        }

        if (status.equals("0")){
            ed2.setError("Wrong Password");
        progressbarvisiblity(false);}
        if (status.equals("-1")){
            ed1.setError("This username doesn't exist");
        progressbarvisiblity(false);}
        if (status.equals("1")) {

            SharedPreferences.Editor pref = getSharedPreferences("login_data", MODE_PRIVATE).edit();
            pref.putString("username", username);
            pref.putString("password", password);

            pref.commit();
            finish();
            new Sync(username, this).setListener(this);
        }
    }

    public  void syncCompleteResponder(Boolean status)
    {
        if(status==false) {
            Toast.makeText(this, "Error while syncing", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(this, MainActivity.class));
    }
    public void progressbarvisiblity(Boolean visible){
        if (visible){
        pb.setVisibility(View.VISIBLE);
        login_button.setVisibility(View.INVISIBLE);}
        else {
            pb.setVisibility(View.INVISIBLE);
            login_button.setVisibility(View.VISIBLE);
        }
    }
}


