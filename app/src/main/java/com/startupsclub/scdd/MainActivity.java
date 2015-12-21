package com.startupsclub.scdd;

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
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.startupsclub.scdd.Database.LocalDB;
import com.startupsclub.scdd.Fragments.VenueFrag;
import com.startupsclub.scdd.RowElements.VenueDetails;
import com.startupsclub.scdd.web.Sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Sync.SyncCompleteResponder {

    TextView subtitle;
    TextView nav_head_email, nav_head_name;
    Intent intent;
    String city_name;
    SharedPreferences name_email_prefs;
    CircleImageView nav_head_photo;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        subtitle = (TextView) findViewById(R.id.subtitle);


        navigationMenuAction(0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);


        nav_head_email = (TextView) header.findViewById(R.id.nav_head_email);
        nav_head_name = (TextView) header.findViewById(R.id.nav_head_name);
        nav_head_photo = (CircleImageView) header.findViewById(R.id.nav_head_photo);
        name_email_prefs = getSharedPreferences("login_data", MODE_PRIVATE);

        Log.e("ad", name_email_prefs.getString("username", "username"));
        nav_head_email.setText(name_email_prefs.getString("username", "username"));

        putPicture();
       CircleImageView im=(CircleImageView) header.findViewById(R.id.nav_head_photo);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Image","Image clickedd");
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        SharedPreferences pref = getSharedPreferences("user_data", 0);
        nav_head_name.setText(pref.getString("first_name", " ") + " " + pref.getString("last_name", " "));

        navigationView.setNavigationItemSelectedListener(this);

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
            case R.id.action_about:
                return true;
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
                fragment = new Profile();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities, fragment, "1st page")
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                SharedPreferences pref = getSharedPreferences("login_data", MODE_PRIVATE);
                new Sync(this).setListener(this);
                Toast.makeText(this, "Sync in progress", Toast.LENGTH_SHORT).show();
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
        if (status)
            Toast.makeText(this, "Sync completed.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error while syncing", Toast.LENGTH_SHORT).show();
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
        Fragment fragment = new VenueFrag();
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("map")
                .replace(R.id.home_cities, fragment, "1st page")
                .commit();
    }

    public void ImagePick(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            SharedPreferences.Editor pref = getSharedPreferences("login_data", MODE_PRIVATE).edit();
            pref.putString("photo", picturePath);
            pref.commit();
            putPicture();
        }
    }

    public void putPicture() {
        String picturePath;
        SharedPreferences prefs = this.getSharedPreferences("login_data", Context.MODE_PRIVATE);
        picturePath = prefs.getString("photo", "nopic");
        if (picturePath != "nopic") {
            nav_head_photo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

}
