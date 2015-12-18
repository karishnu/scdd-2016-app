package com.startupsclub.scdd.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.startupsclub.scdd.LoginActivity;
import com.startupsclub.scdd.R;
import com.startupsclub.scdd.web.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by Amartya on 12/18/2015.
 */
public class ForgotPass_Frag3 extends Fragment implements PostRequest.PostRequestResponseHandler{

    OnSuccess mCallback;
    String pass,re_pass;
    EditText ed,ed1;
    Button bt;
    ProgressBar pb;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forgotnewpass_fragment, container, false);

        Bundle b=getArguments();
       final String username=b.getString("username");

        ed=(EditText)rootView.findViewById(R.id.password_edtext);
         ed1=(EditText)rootView.findViewById(R.id.repassword_edtext);

        bt=(Button)rootView.findViewById(R.id.change_button);
        pb=(ProgressBar)rootView.findViewById(R.id.progressBar);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass=ed.getText().toString();
                re_pass=ed1.getText().toString();
                if(validate())
                {
                    progressbarvisiblity(true);
                    Hashtable<String,String> ht=new Hashtable<String, String>();
                    ht.put("username",username);
                    ht.put("password",pass);
                    new PostRequest(ht, "http://myappserver.netau.net/SCDD/update_password.php").setListener(ForgotPass_Frag3.this);
                }
            }
        });
        return rootView;
    }

    public  Boolean validate()
    {
        Boolean re=true;
        if (pass.length() == 0) {
            re = false;
            ed.setError("This field cannot be empty");
        }
        else if(pass.length()<5)
        {
            re = false;
            ed.setError("Must be at least 5 characters");
        }
        else if(!re_pass.equals(pass)){
            re = false;
            ed1.setError("Password Mismatch");
        }
        else re=true;

        return re;
    }

    public void progressbarvisiblity(Boolean visible){
        if (visible){
            pb.setVisibility(View.VISIBLE);
            bt.setVisibility(View.INVISIBLE);}
        else {
            pb.setVisibility(View.INVISIBLE);
            bt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void postRequestResponse(String jSONResponse) {
        String status = "";
        try {
            JSONObject jObject = new JSONObject(jSONResponse);
            Log.e("Json response", jObject.toString());
            JSONArray jArray = jObject.getJSONArray("reset_password");

            JSONObject jTemp = jArray.getJSONObject(0);
            status = jTemp.getString("status");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            status = "201";
            Log.e("Json response", e.toString());
        }
        progressbarvisiblity(false);
        if(status.equals("true"))
        {
            mCallback.finishActivity(true);
        }
        else
            mCallback.finishActivity(false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnSuccess) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement this interface");
        }
    }

    public interface OnSuccess {
        void finishActivity(Boolean status);
    }
}
