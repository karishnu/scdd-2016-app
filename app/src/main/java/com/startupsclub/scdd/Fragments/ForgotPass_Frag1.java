package com.startupsclub.scdd.Fragments;

import android.content.Context;
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

import com.startupsclub.scdd.R;
import com.startupsclub.scdd.web.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by Amartya on 12/18/2015.
 */
public class ForgotPass_Frag1 extends Fragment  implements PostRequest.PostRequestResponseHandler {
    int code;
    EditText ed3;
    OnSuccess mCallback;
    ProgressBar pb;
    Button bt;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forgotpass_fragment, container, false);

        ed3=(EditText)rootView.findViewById(R.id.user_email);
        pb=(ProgressBar)rootView.findViewById(R.id.progressBar);
         bt=(Button)rootView.findViewById(R.id.submit_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=ed3.getText().toString()+"";
                if(s.length()==0)
                    ed3.setError("Please provide a valid e-mail or username");
                else {
                    progressbarvisiblity(true);
                    submit(s);
                }
            }
        });
        return rootView;
    }

    public void submit(String s) {
        int min=10001;
        int max=99999;

         code=10000+(int)(Math.random()*(max-min)+1);
        Hashtable<String,String> ht=new Hashtable<>();
        ht.put("user_email",s);
        ht.put("code",code+"");

        new PostRequest(ht, "http://myappserver.netau.net/SCDD/forgot_pass.php").setListener(this);
    }

    @Override

    public void postRequestResponse(String jSONresponse) {

        String status="",email_sent="",user_exists="",email_exists="",emailid="",username="";

        try {
            JSONObject jObject = new JSONObject(jSONresponse);
            Log.e("Json response", jObject.toString());
            JSONArray jArray = jObject.getJSONArray("reply");
            JSONObject jTemp = jArray.getJSONObject(0);
            status = jTemp.getString("success");
            user_exists=jTemp.getString("exists_username")+"";
            email_exists=jTemp.getString("exists_email")+"";
            emailid=jTemp.getString("email_id")+"";
            username=jTemp.getString("username")+"";


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            status = "201";

            e.printStackTrace();
        }
        progressbarvisiblity(false);
        if(!status.equals("true"))
            Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
        else
        {
            if(email_exists.equals("true")||user_exists.equals("true")) {

                mCallback.startFrag2(emailid,username,code+"");
            }
            else
                ed3.setError("This username/email doesn't exists");
        }



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
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnSuccess) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement this interface");
        }
    }


    public interface OnSuccess {
        void startFrag2(String email,String username,String code);
    }
    }

