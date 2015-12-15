package com.startupsclub.scdd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }
    public void feedback(View view){
        ProgressBar pb = (ProgressBar) findViewById(R.id.fb_progressBar);
        EditText ed = (EditText) findViewById(R.id.ed_feedback);
        Button bt = (Button) findViewById(R.id.feedback_button);
        pb.setVisibility(View.VISIBLE);
        bt.setVisibility(View.INVISIBLE);

        //feedback backend code goes here
        // ed is input space
    }
}
