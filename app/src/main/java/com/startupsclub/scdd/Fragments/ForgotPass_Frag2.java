package com.startupsclub.scdd.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.startupsclub.scdd.R;

/**
 * Created by Amartya on 12/18/2015.
 */
public class ForgotPass_Frag2 extends Fragment {

    OnSuccess mCallback;
    Button bt;
    ProgressBar pb;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forgotcode_fragment, container, false);
        Bundle b=getArguments();
      final String user= b.getString("username");
       String email= b.getString("email_id");
        final String code=b.getString("code");
        TextView tv=(TextView)rootView.findViewById(R.id.tv_email);
        tv.setText("An email was sent to:"+email+"");
        pb=(ProgressBar)rootView.findViewById(R.id.progressBar);
        final  EditText ed=(EditText)rootView.findViewById(R.id.verification_code);

         bt=(Button)rootView.findViewById(R.id.submit_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  showDummyProgress();
                if(ed.getText().toString().equals(code))
                    mCallback.startFrag3(user);
                else
                    ed.setError("Verification code mismatch");

            }
        });
        return rootView;
    }

    public void showDummyProgress()
    {
//        bt.setVisibility(View.INVISIBLE);
//        pb.setVisibility(View.VISIBLE);
//
//        bt.setVisibility(View.VISIBLE);
//        bt.setVisibility(View.INVISIBLE);
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
        void startFrag3(String user);
    }
}
