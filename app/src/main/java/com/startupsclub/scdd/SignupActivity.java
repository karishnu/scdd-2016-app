package com.startupsclub.scdd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.startupsclub.scdd.web.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class SignupActivity extends AppCompatActivity implements PostRequest.PostRequestResponseHandler{
	EditText ed1,ed2,ed3,ed4,ed5,ed6;
	String fn,ln,email,ph,user,pass;
	ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		ed1=(EditText)findViewById(R.id.fn_edtext);
		ed2=(EditText)findViewById(R.id.ln_edtext);
		ed3=(EditText)findViewById(R.id.email_edtext);
		ed4=(EditText)findViewById(R.id.ph_edtext);
		ed5=(EditText)findViewById(R.id.username_edtext);
		ed6=(EditText)findViewById(R.id.password_edtext);
		pb=(ProgressBar)findViewById(R.id.progressBar);

	}


	public void register(View view)
	{
		if(attempt_register()){
			pb.setVisibility(View.VISIBLE);
			Hashtable<String,String> ht=new Hashtable<String,String>();
			ht.put("username",user);
			ht.put("password",pass);
			ht.put("email",email);
			ht.put("mobile",ph);
			ht.put("fn",fn);
			ht.put("ln",ln);

			new PostRequest(ht,"http://myappserver.netau.net/SCDD/register.php").setListener(this);


		}
	}


	public boolean attempt_register()
	{
		fn=ed1.getText().toString()+"";
		ln=ed2.getText().toString()+"";
		email=ed3.getText().toString()+"";
		ph=ed4.getText().toString()+"";
		user=ed5.getText().toString()+"";
		pass=ed6.getText().toString()+"";

		Boolean re=true;

		if(fn.length()==0)
		{
			ed1.setError("This field cannot be empty");
			re=false;
		}
		if(email.length()>0) {
			if (!email.contains("@")) {
				re = false;
				ed3.setError("This is invalid email");
			}
		}
		else{
			re=false;
			ed3.setError("This field cannot be empty");
		}

		if(ph.length()>0) {
			if (ph.length() < 10) {
				re = false;
				ed4.setError("Invalid mobile no.");
			}
		}
		else
		{
			re=false;
			ed4.setError("This field cannot be empty");
		}

		if(user.length()==0)
		{
			re=false;
			ed5.setError("This field cannot be empty");
		}
		if(pass.length()==0)
		{
			re=false;
			ed6.setError("This field cannot be empty");
		}

		return  re;
	}


	public void signin(View view)
	{
		finish();
		startActivity(new Intent(this,LoginActivity.class));
	}


	public void back(View view)
	{
		finish();
		startActivity(new Intent(this,LoginActivity.class));
	}



	@Override
	public void postRequestResponse(String jSONResponse) {

		try {
			JSONObject jObject = new JSONObject(jSONResponse);
			Log.e("Json response", jObject.toString());
			JSONArray jArray = jObject.getJSONArray("register");

			JSONObject jTemp = jArray.getJSONObject(0);
			String status=jTemp.getString("status");
			if(status.equals("1"))
				startActivity(new Intent(this,LoginActivity.class));
			else if(status.equals("2"))
				ed5.setError("This username already exists");
			else
				Toast.makeText(this,"Check your connection",Toast.LENGTH_LONG).show();
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Json response", e.toString());
		}
		pb.setVisibility(View.INVISIBLE);
	}
}