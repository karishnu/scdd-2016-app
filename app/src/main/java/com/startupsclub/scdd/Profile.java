package com.startupsclub.scdd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 12/4/2015.
 */
public class Profile extends Fragment {
    Context context;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_update, container, false);

            this.rootView=rootView;
        context=getActivity();
        SharedPreferences pref=context.getSharedPreferences("user_data",0);
        tv1=(TextView)rootView.findViewById(R.id.first_name);   tv1.setText(pref.getString("first_name",""));
        tv2=(TextView)rootView.findViewById(R.id.last_name); if(!pref.getString("last_name","").equals("null") )tv2.setText(pref.getString("last_name",""));
        tv3=(TextView)rootView.findViewById(R.id.email);    tv3.setText(pref.getString("email",""));

        tv4=(TextView)rootView.findViewById(R.id.mobile);   tv4.setText(pref.getString("phone",""));
        tv5=(TextView)rootView.findViewById(R.id.com_name); if(!pref.getString("company_name","").equals("null") )    tv5.setText(pref.getString("company_name",""));
        tv6=(TextView)rootView.findViewById(R.id.designation);   if(!pref.getString("designation","").equals("null")) tv6.setText(pref.getString("designation",""));
        tv7=(TextView)rootView.findViewById(R.id.address);   if(!pref.getString("address","").equals("null"))  tv7.setText(pref.getString("address",""));

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(context,ProfileUpdateActivity.class));
            }
        });
        return rootView;
    }
        @Override
    public  void onResume()
    {

        SharedPreferences pref=context.getSharedPreferences("user_data",0);
        tv1=(TextView)rootView.findViewById(R.id.first_name);   tv1.setText(pref.getString("first_name",""));
        tv2=(TextView)rootView.findViewById(R.id.last_name); if(!pref.getString("last_name","").equals("null") )tv2.setText(pref.getString("last_name",""));
        tv3=(TextView)rootView.findViewById(R.id.email);    tv3.setText(pref.getString("email",""));

        tv4=(TextView)rootView.findViewById(R.id.mobile);   tv4.setText(pref.getString("phone",""));
        tv5=(TextView)rootView.findViewById(R.id.com_name); if(!pref.getString("company_name","").equals("null") )    tv5.setText(pref.getString("company_name",""));
        tv6=(TextView)rootView.findViewById(R.id.designation);   if(!pref.getString("designation","").equals("null")) tv6.setText(pref.getString("designation",""));
        tv7=(TextView)rootView.findViewById(R.id.address);   if(!pref.getString("address","").equals("null"))  tv7.setText(pref.getString("address",""));
        super.onResume();
    }


}
