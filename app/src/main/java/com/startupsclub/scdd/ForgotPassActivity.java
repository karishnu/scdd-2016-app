package com.startupsclub.scdd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.startupsclub.scdd.Fragments.ForgotPass_Frag1;
import com.startupsclub.scdd.Fragments.ForgotPass_Frag2;
import com.startupsclub.scdd.Fragments.ForgotPass_Frag3;
import com.startupsclub.scdd.Fragments.Signup_Frag1;

public class ForgotPassActivity extends AppCompatActivity implements ForgotPass_Frag1.OnSuccess,ForgotPass_Frag2.OnSuccess,ForgotPass_Frag3.OnSuccess{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        Fragment fragment = new ForgotPass_Frag1();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_fp,fragment,"1st page")
                .commit();

}

    @Override
    public void startFrag2(String email,String username,String code)
    {
        Fragment fragment = new ForgotPass_Frag2();
        Bundle b=new Bundle();
        b.putString("username",username);
        b.putString("email_id",email);
        b.putString("code",code);
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_out,R.anim.slide_out)
                .replace(R.id.frame_fp,fragment,"2nd page")
                .commit();
    }

    public void startFrag3(String user)
    {
        Fragment fragment = new ForgotPass_Frag3();
        Bundle b=new Bundle();
        b.putString("username",user);
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_out,R.anim.slide_out)
                .replace(R.id.frame_fp,fragment,"2nd page")
                .commit();
    }

    @Override
    public void finishActivity(Boolean status)
    {

        if(status) {

            Toast.makeText(this, "You Password was updated successfully", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else {
            AlertDialog ad=new AlertDialog.Builder(this).create();
            ad.setTitle("Error");
            ad.setMessage("Unfortunately some error occurred.Please check your network connection");
            ad.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                    startActivity(new Intent(ForgotPassActivity.this,LoginActivity.class));
                }
            });
            ad.show();

        }
    }

        }