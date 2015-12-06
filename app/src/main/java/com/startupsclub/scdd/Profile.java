package com.startupsclub.scdd;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by admin on 12/4/2015.
 */
public class Profile extends Fragment {
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_update, container, false);

        Button b=(Button)rootView.findViewById(R.id.submit_bt);
        context=getActivity();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        return rootView;
    }

    void updateProfile(){
        Toast.makeText(context,"Update Task",Toast.LENGTH_LONG ).show();
    }

}
