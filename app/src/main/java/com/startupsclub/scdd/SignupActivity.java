package com.startupsclub.scdd;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity{

	EditText fn;
	EditText ln;
	EditText em;
	EditText ph;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_signup);
	        TextView tv=(TextView)findViewById(R.id.signin_tv);
	        tv.setPaintFlags(tv.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
	        fn=(EditText)findViewById(R.id.fn_edtext);
	        ln=(EditText)findViewById(R.id.ln_edtext);
	        em=(EditText)findViewById(R.id.email_edtext);
	        ph=(EditText)findViewById(R.id.ph_edtext);

}

	 
	 public void signin(View view)
	 {
		 finish();
		 startActivity(new Intent(this,LoginActivity.class));
	 }

	 public void next(View view)
	 {

	 }
	 public void back(View view)
	 {
		 finish();
		 startActivity(new Intent(this,LoginActivity.class));
	 }
	 
}
