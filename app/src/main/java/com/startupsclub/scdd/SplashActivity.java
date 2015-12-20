package com.startupsclub.scdd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.startupsclub.scdd.web.Sync;

public class SplashActivity extends AppCompatActivity implements Sync.SyncCompleteResponder {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = this.getSharedPreferences("login_data", Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String password = prefs.getString("password",null);

        intent = new Intent(this, LoginActivity.class);

        if (username != null && password != null) {
            new Sync(this).setListener(this);
        }
        else {
            finish();
            startActivity(intent);
        }
    }

    public  void syncCompleteResponder(Boolean status)
    {
        intent = new Intent(this, MainActivity.class);
        if(status==false) {
            Toast.makeText(this, "Error while syncing", Toast.LENGTH_SHORT).show();
        }
        finish();
        startActivity(intent);
    }
}
