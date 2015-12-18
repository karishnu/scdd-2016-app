package com.startupsclub.scdd.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.startupsclub.scdd.R;

/**
 * Created by Amartya on 12/18/2015.
 */
public class Signup_Frag1 extends Fragment {

    EditText ed1, ed2, ed3, ed4;
    String fn, ln, email, ph;
    OnCLickNextButton mCallback;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signup_frag1, container, false);

        ed1 = (EditText) rootView.findViewById(R.id.fn_edtext);
        ed2 = (EditText) rootView.findViewById(R.id.ln_edtext);
        ed3 = (EditText) rootView.findViewById(R.id.email_edtext);
        ed4 = (EditText) rootView.findViewById(R.id.ph_edtext);
        Button b = (Button) rootView.findViewById(R.id.next_bt);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fn = ed1.getText().toString() + "";
                ln = ed2.getText().toString() + "";
                email = ed3.getText().toString() + "";
                ph = ed4.getText().toString() + "";
                if (attempt_next()) {
                    mCallback.next_button(fn, ln, email, ph);
                }
            }
        });
        return rootView;
    }

    public boolean attempt_next() {

        Boolean re = true;

        if (fn.length() == 0) {
            ed1.setError("This field cannot be empty");
            re = false;
        }
        if (email.length() > 0) {
            if (!email.contains("@")) {
                re = false;
                ed3.setError("This is invalid email");
            }
        } else {
            re = false;
            ed3.setError("This field cannot be empty");
        }

        if (ph.length() > 0) {
            if (ph.length() < 10) {
                re = false;
                ed4.setError("Invalid mobile no.");
            }
        } else {
            re = false;
            ed4.setError("This field cannot be empty");
        }


        return re;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnCLickNextButton) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement this interface");
        }
    }


    public interface OnCLickNextButton {
        void next_button(String fn, String ln, String email, String phone);
    }
}
