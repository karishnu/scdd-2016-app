package com.startupsclub.scdd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.startupsclub.scdd.Database.LocalDB;
import com.startupsclub.scdd.Fragments.VenueFrag;
import com.startupsclub.scdd.RowElements.VenueDetails;
import com.startupsclub.scdd.web.Sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Sync.SyncCompleteResponder {

    TextView subtitle;
    TextView nav_head_email, nav_head_name;
    Intent intent;
    String city_name;
    SharedPreferences name_email_prefs;
    CircleImageView header_image;
    Boolean showToast=true;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        subtitle = (TextView) findViewById(R.id.subtitle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);


        nav_head_email = (TextView) header.findViewById(R.id.nav_head_email);
        nav_head_name = (TextView) header.findViewById(R.id.nav_head_name);
        header_image = (CircleImageView) header.findViewById(R.id.nav_head_photo);
        name_email_prefs = getSharedPreferences("login_data", MODE_PRIVATE);

        Log.e("ad", name_email_prefs.getString("username", "username"));
        nav_head_email.setText(name_email_prefs.getString("username", "username"));

        final SharedPreferences pref = getSharedPreferences("user_data", 0);
        nav_head_name.setText(pref.getString("first_name", " ") + " " + pref.getString("last_name", " "));

        header_image_update();

        navigationView.setNavigationItemSelectedListener(this);
        navigationMenuAction(0);

        if(pref.getBoolean("profile_edit",true)){
            new AlertDialog.Builder(this)
                    .setTitle("Complete Profile")
                    .setMessage("Your profile seems to be incomplete. Would you like to edit it?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("profile_edit",false);
                            editor.commit();
                            Intent profile_intent = new Intent(MainActivity.this, ProfileUpdateActivity.class);
                            startActivity(profile_intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("profile_edit",false);
                            editor.commit();
                            final View coordinatorLayoutView = findViewById(R.id.main_cood);

                            Snackbar
                                    .make(coordinatorLayoutView, "You can edit your profile later from the 'Profile' page!", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.action_feedback:
                intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            subtitle.setText("Home");
            navigationMenuAction(0);
        } else if (id == R.id.nav_profile) {
            subtitle.setText("Profile");
            navigationMenuAction(1);
        } else if (id == R.id.nav_sync) {
            navigationMenuAction(2);
        } else if (id == R.id.nav_terms) {
            subtitle.setText("Terms and Conditions");
            navigationMenuAction(3);
        } else if (id == R.id.nav_privacy) {
            subtitle.setText("Privacy Policy");
            navigationMenuAction(4);
        } else if (id == R.id.nav_logout) {
            navigationMenuAction(5);
        } else if (id == R.id.nav_events) {
            subtitle.setText("Upcoming Events");
            navigationMenuAction(6);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navigationMenuAction(int position) {
        final Fragment fragment;
        switch (position) {
            case 0:
                fragment = new Home();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities, fragment, "1st page")
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                Intent profileintent = new Intent(this,ProfileUpdateActivity.class);
                startActivity(profileintent);
                break;
            case 2:
               pd=ProgressDialog.show(this, "Sync in progess", "SYNCING", true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Sync Cancelled",Toast.LENGTH_SHORT).show();
                        showToast=false;
                    }
                });
//                AlertDialog ad=new AlertDialog.Builder(context).create();
//                ad.setTitle(title);
//                ad.setMessage(message);
//                ad.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // TODO Auto-generated method stub
//                        dialog.dismiss();
//                    }
//                });
//                ad.show();
                new Sync(this).setListener(this);

                break;
            case 3:
                fragment = new TermsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities, fragment, "1st page")
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                fragment = new Privacy();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities, fragment, "1st page")
                        .addToBackStack(null)
                        .commit();
                break;
            case 5:
                SharedPreferences pref2 = getSharedPreferences("login_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref2.edit();
                editor.remove("username");
                editor.remove("password");
                editor.remove("photo");
                editor.commit();
                pref2 = getSharedPreferences("user_data", MODE_PRIVATE);
                editor=pref2.edit();
                editor.remove("profile_edit");
                editor.remove("prefs_last_img");
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                finish();
                startActivity(intent);
                break;
            case 6:
                fragment = new EventsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities, fragment, "1st page")
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public void syncCompleteResponder(Boolean status) {
        if (showToast) {


            if (status)
                Toast.makeText(this, "Sync completed.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error while syncing", Toast.LENGTH_SHORT).show();

            pd.dismiss();
        }
    }

    public void agendaclick(View view) {
        Fragment fragment = new AgendaFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_cities, fragment, "1st page")
                .addToBackStack(null)
                .commit();
    }

    public void speakerclick(View view) {
        Fragment fragment = new SpeakerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_cities, fragment, "1st page")
                .addToBackStack(null)
                .commit();
    }

    public void sponsorclick(View view) {
        Fragment fragment = new SponsorFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_cities, fragment, "1st page")
                .addToBackStack(null)
                .commit();
    }

    public void ticketclick(View view) {
        String url = "https://www.eventshigh.com/detail/bangalore/1f53abf12a4bc1b30406e5049dab946a";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void venueclick(View view) {
        LocalDB db = new LocalDB(this);
        VenueDetails vd = db.getVenueDetailsByCity(city_name);
        Bundle b = new Bundle();
        b.putString("city_name", city_name);
        b.putFloat("latitude", vd.getLatitude());
        b.putFloat("longitude", vd.getLongitude());
        b.putString("address",vd.getAddress());
        Fragment fragment = new VenueFrag();
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("map")
                .replace(R.id.home_cities, fragment, "1st page")
                .commit();
    }



    public void calendarclick(View view) {
        LocalDB db = new LocalDB(this);
        VenueDetails vd = db.getVenueDetailsByCity(city_name);

        Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
        calendarIntent.setData(CalendarContract.Events.CONTENT_URI);
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Startups Club DEMO DAY, "+city_name);
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, vd.getAddress());
        Calendar cal = Calendar.getInstance();

        switch (city_name){
            case "Bangalore" :
                cal.set(Calendar.DAY_OF_MONTH, 16);
                cal.set(Calendar.MONTH, 0);
                break;
            case "Chennai" :
                cal.set(Calendar.DAY_OF_MONTH, 20);
                cal.set(Calendar.MONTH, 1);
                break;
            case "Hyderabad" :
                cal.set(Calendar.DAY_OF_MONTH, 19);
                cal.set(Calendar.MONTH, 2);
                break;
            case "Coimbatore" :
                cal.set(Calendar.DAY_OF_MONTH, 16);
                cal.set(Calendar.MONTH, 3);
                break;
            case "Mumbai" :
                cal.set(Calendar.DAY_OF_MONTH, 21);
                cal.set(Calendar.MONTH, 4);
                break;
            case "Delhi" :
                cal.set(Calendar.DAY_OF_MONTH, 18);
                cal.set(Calendar.MONTH, 5);
                break;
            case "Pune" :
                cal.set(Calendar.DAY_OF_MONTH, 16);
                cal.set(Calendar.MONTH, 6);
                break;
            case "Kochi" :
                cal.set(Calendar.DAY_OF_MONTH, 20);
                cal.set(Calendar.MONTH, 7);
                break;
            case "Ahmedabad" :
                cal.set(Calendar.DAY_OF_MONTH, 17);
                cal.set(Calendar.MONTH, 8);
                break;
            case "Vizag" :
                cal.set(Calendar.DAY_OF_MONTH, 15);
                cal.set(Calendar.MONTH, 9);
                break;
        }

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTime().getTime());
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTime().getTime());
        startActivity(calendarIntent);
    }
    public void shareclick(View view){
        LocalDB db = new LocalDB(this);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Join me at Startups Club DEMO DAY 2016 - "+city_name+"! \n"+
            "Date - "+db.getDateDetailsByCity(city_name)+", 2016\n"+
            "Time - 9.30 AM to 5.30 PM\n"+
            "Venue - "+db.getVenueDetailsByCity(city_name).getAddress()+"\n"+
            "Tickets - https://goo.gl/aEVslf \n"+
            "Website - http://startupsclub.org/demoday/");
        startActivity(sharingIntent);
    }
    public void header_image_update(){
        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        String image_string = sharedPreferences.getString("prefs_last_img","null");
        if (image_string != "null"){
            Uri image_uri = Uri.parse(image_string);
            try {
                Log.e("TEST", "header_image_update: try started");
                InputStream imageStream = getContentResolver().openInputStream(image_uri);
                Bitmap profile_image = BitmapFactory.decodeStream(imageStream);
                header_image.setImageBitmap(profile_image);
            }
            catch (Exception e){}
        }
        else{
            header_image.setImageResource(R.drawable.avatar_user);
        }

    }
}
