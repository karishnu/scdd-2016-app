package com.startupsclub.scdd;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tv = (TextView) findViewById(R.id.forgot_passwd_tv);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        TextView tv1 = (TextView) findViewById(R.id.newusr_tv);
        tv1.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void login(View view) {
        //Perform login task here.
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void forgot_password(View view) {
        //Perform forgor password task here
    }

    public void signup(View view) {
        finish();
        startActivity(new Intent(this, SignupActivity.class));
    }

}
