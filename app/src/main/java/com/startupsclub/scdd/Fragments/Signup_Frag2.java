package com.startupsclub.scdd.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class Signup_Frag2 extends Fragment implements PostRequest.PostRequestResponseHandler{


    EditText ed5,ed6,ed7;
    String user,pass,re_pass;
    ProgressBar pb;
    Button bt;

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signup_frag2, container, false);

        Bundle b=getArguments();
       final String fn=b.getString("fn");
       final String ln=b.getString("ln");
       final String email=b.getString("email");
       final String ph=b.getString("phone");
        ed5 = (EditText) rootView.findViewById(R.id.username_edtext);
        ed6 = (EditText)rootView.findViewById(R.id.password_edtext);
        ed7 = (EditText)rootView.findViewById(R.id.repassword_edtext);
        pb = (ProgressBar)rootView.findViewById(R.id.progressBar);

         bt=(Button)rootView.findViewById(R.id.register_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                user = ed5.getText().toString() + "";
                pass = ed6.getText().toString() + "";
                re_pass=ed7.getText().toString()+"";
             register(user,pass,email,ph,fn,ln);

            }
        });
        return rootView;
    }



    public void register(String user,String pass,String email,String ph,String fn,String ln) {
        if (attempt_register()) {
            showProgress(true);
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put("username", user);
            ht.put("password", pass);
            ht.put("email", email);
            ht.put("mobile", ph);
            ht.put("fn", fn);
            ht.put("ln", ln);

            new PostRequest(ht, "http://myappserver.netau.net/SCDD/register.php").setListener(this);

        }
    }
    public boolean attempt_register()
    {
        Boolean re;

        if (user.length() == 0) {
            re = false;
            ed5.setError("This field cannot be empty");
        }

        else if(user.length()<3)
        {
            re = false;
            ed5.setError("Must be at least 3 characters");
        }
        else
            re=true;

        if (pass.length() == 0) {
            re = false;
            ed6.setError("This field cannot be empty");
        }
        else if(pass.length()<5)
        {
            re = false;
            ed6.setError("Must be at least 5 characters");
        }
        else if(!re_pass.equals(pass)){
            re = false;
            ed7.setError("Password Mismatch");
        }
        else re=true;

            return re;
    }


    @Override
    public void postRequestResponse(String jSONResponse) {
        String status="";
        try {
            JSONObject jObject = new JSONObject(jSONResponse);
            Log.e("Json response", jObject.toString());
            JSONArray jArray = jObject.getJSONArray("register");

            JSONObject jTemp = jArray.getJSONObject(0);
            status = jTemp.getString("status");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            status="201";
            Log.e("Json response", e.toString());
        }
        showProgress(false);
        if (status.equals("1"))
            startActivity(new Intent(getContext(), LoginActivity.class));
        else if (status.equals("2"))
            ed5.setError("This username already exists");
        else
            Toast.makeText(getContext(), "Please check your connection", Toast.LENGTH_LONG).show();
        pb.setVisibility(View.INVISIBLE);
    }

    public  void showProgress(Boolean show)
    {
        if(show) {
            bt.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.VISIBLE);
        }
        else
        {
            bt.setVisibility(View.VISIBLE);
            pb.setVisibility(View.INVISIBLE);
        }
    }

//
//    @Override
//    public  void onAttach(Context context)
//    {
//        super.onAttach(context);
//
//        try{
//            mCallback=(OnCLickRegisterButton)context;
//
//        }
//        catch (ClassCastException e)
//        {
//            throw new ClassCastException(context.toString()+" must implement this interface");
//        }
//    }
//
//
//    public interface OnCLickRegisterButton
//    {
//        void register(String user,password);
//    }
}
