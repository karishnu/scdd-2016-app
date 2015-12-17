package com.startupsclub.scdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.startupsclub.scdd.Fragments.Signup_Frag1;
import com.startupsclub.scdd.Fragments.Signup_Frag2;
import com.startupsclub.scdd.web.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class SignupActivity extends AppCompatActivity  implements Signup_Frag1.OnCLickNextButton{



    String user,pass,email,ph,fn,ln;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

       Fragment fragment = new Signup_Frag1();

        getSupportFragmentManager().beginTransaction()
                .addToBackStack("abc")
                .replace(R.id.frame_signup,fragment,"1st page")
                .commit();
    }

    public void signin(View view) {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }


    @Override
    public void next_button(String fn,String ln,String email,String phone)
    {
        Fragment fragment = new Signup_Frag2();
        Bundle b=new Bundle();
        b.putString("fn",fn);
        b.putString("ln",ln);
        b.putString("email",email);
        b.putString("phone",phone);
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_out,R.anim.slide_out)
                .replace(R.id.frame_signup,fragment,"1st page")
                .commit();
    }

}
