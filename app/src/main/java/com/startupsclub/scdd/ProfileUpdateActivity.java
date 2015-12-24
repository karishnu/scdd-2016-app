package com.startupsclub.scdd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.startupsclub.scdd.web.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUpdateActivity extends AppCompatActivity implements PostRequest.PostRequestResponseHandler {


    EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7;
    Button btnChange;
    String fn, ln, em, mob, com_name, des, add;
    ProgressBar pb;
    FloatingActionButton fab, fab_done;
    private static int RESULT_LOAD_IMAGE = 1;
    private final int SELECT_PHOTO = 1;
    private CircleImageView imageView;
    public static Bitmap selectedProfileImage;
    int PROFILE = R.drawable.avatar_user;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String PREFS_LAST_IMG = "prefs_last_img";
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_done = (FloatingActionButton) findViewById(R.id.fab_done);

        pb = (ProgressBar) findViewById(R.id.progressBar);

        TextView tv = (TextView) findViewById(R.id.tv_username);
        tv.setText(getSharedPreferences("login_data", 0).getString("username", ""));

        imageView = (CircleImageView) findViewById(R.id.profile_pic);

        btnChange = (Button) findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPic();
            }
        });

        SharedPreferences pref = getSharedPreferences("user_data", 0);
        retrievePreferences();

        ed1 = (EditText) findViewById(R.id.first_name);
        ed1.setText(pref.getString("first_name", ""));
        ed2 = (EditText) findViewById(R.id.last_name);
        if (!pref.getString("last_name", "").equals("null"))
            ed2.setText(pref.getString("last_name", ""));
        ed3 = (EditText) findViewById(R.id.email);
        ed3.setText(pref.getString("email", ""));

        ed4 = (EditText) findViewById(R.id.mobile);
        ed4.setText(pref.getString("phone", ""));
        ed5 = (EditText) findViewById(R.id.com_name);
        if (!pref.getString("company_name", "").equals("null"))
            ed5.setText(pref.getString("company_name", ""));
        ed6 = (EditText) findViewById(R.id.designation);
        if (!pref.getString("designation", "").equals("null"))
            ed6.setText(pref.getString("designation", ""));
        ed7 = (EditText) findViewById(R.id.address);
        if (!pref.getString("address", "").equals("null"))
            ed7.setText(pref.getString("address", ""));

        editable(false);
    }


    public Boolean validate() {
        return true;
    }

    @Override

    public void postRequestResponse(String jSONresponse) {

        String status = "";
        try {
            JSONObject jObject = new JSONObject(jSONresponse);
            Log.e("Json response", jObject.toString());
            JSONArray jArray = jObject.getJSONArray("update_profile");

            JSONObject jTemp = jArray.getJSONObject(0);
            status = jTemp.getString("status");
            Log.e("status", status + "");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            status = "201";
            e.printStackTrace();
        }

        progressbarvisiblity(false);

        if (status.equals("false")) {
            progressbarvisiblity(false);
            displayDialog("Update Failed", "Please check your internet connection");
        } else if (status.equals("true")) {


            SharedPreferences.Editor preferences = getSharedPreferences("user_data", 0).edit();
            preferences.putString("email", em);
            preferences.putString("first_name", fn);
            preferences.putString("last_name", ln);
            preferences.putString("phone", mob);
            preferences.putString("company_name", com_name);
            preferences.putString("designation", des);
            preferences.putString("address", add);
            preferences.commit();
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));

        } else {
            displayDialog("Update Failed", "Please check your internet connection");
        }
    }

    public void editable(View view){
        this.editable(true);
    }
    public void uneditable(View view){
        this.editable(false);
        fn = ed1.getText().toString() + "";
        ln = ed2.getText().toString() + "";
        em = ed3.getText().toString() + "";
        mob = ed4.getText().toString() + "";
        com_name = ed5.getText().toString() + "";
        des = ed6.getText().toString() + "";
        add = ed7.getText().toString() + "";

        if (ed3.getText().toString().contains("@")) {
            progressbarvisiblity(true);
            Hashtable<String, String> ht = new Hashtable<>();
            ht.put("first_name", fn);
            ht.put("last_name", ln);
            ht.put("email", em);
            ht.put("mobile", mob);
            ht.put("company_name", com_name);
            ht.put("designation", des);
            ht.put("address", add);

            ht.put("username", getSharedPreferences("login_data", 0).getString("username", "abc"));

            new PostRequest(ht, "http://myappserver.netau.net/SCDD/update_profile.php").setListener(ProfileUpdateActivity.this);
        } else
            ed3.setError("Invalid email");
    }

    public void editable(Boolean value){
        ed1.setEnabled(value);
        ed2.setEnabled(value);
        ed3.setEnabled(value);
        ed4.setEnabled(value);
        ed5.setEnabled(value);
        ed6.setEnabled(value);
        ed7.setEnabled(value);

        if (value){
            btnChange.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            fab_done.setVisibility(View.VISIBLE);
        }
        else{
            btnChange.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            fab_done.setVisibility(View.GONE);
        }
    }
    public void selectPic() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    private void retrievePreferences() {
        //Profile Pic

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(PREFS_LAST_IMG)) {

            String imageUriString = sharedPreferences.getString(PREFS_LAST_IMG, null);

            Uri profilePicUri = Uri.parse(imageUriString);
            final InputStream imageStream;

            try {
                imageStream = getContentResolver().openInputStream(profilePicUri);
                selectedProfileImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedProfileImage); //set the profile pic here

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "The Profile Pic has gone missing.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }else{
            imageView.setImageResource(PROFILE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final InputStream imageStream;
                        final Uri imageUri = imageReturnedIntent.getData();
                        //save to preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(PREFS_LAST_IMG, imageUri.toString());
                        editor.apply();
                        imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        imageView.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "The Profile Pic has gone missing.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }


                }
        }
    }

    public void displayDialog(String head, String msg) {
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setTitle(head);
        ad.setMessage(msg);
        ad.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        ad.show();
    }

    public void progressbarvisiblity(Boolean visible) {
        if (visible) {
            pb.setVisibility(View.VISIBLE);
        } else {
            pb.setVisibility(View.GONE);
        }
    }
    public void ImagePick(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
}
