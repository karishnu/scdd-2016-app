package com.startupsclub.scdd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.startupsclub.scdd.RowElements.City;
import com.startupsclub.scdd.Adapters.CityRVAdapter;
import com.startupsclub.scdd.web.Sync;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Sync.SyncCompleteResponder {

    TextView subtitle;
    TextView nav_head_email;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        subtitle = (TextView) findViewById(R.id.subtitle);

        navigationMenuAction(0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
            case R.id.action_settings:
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

    public void navigationMenuAction(int position){
        final Fragment fragment;
        switch (position) {
            case 0:
                fragment = new Home();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities,fragment,"1st page")
                        .commit();
                break;
            case 1:
                fragment = new Profile();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities,fragment,"1st page")
                        .commit();
                break;
            case 2:
                SharedPreferences pref=getSharedPreferences("login_data",MODE_PRIVATE);
                new Sync(pref.getString("username","test@gmail.com"),this).setListener(this);
                Toast.makeText(this,"Sync in progress",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                fragment = new TermsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities,fragment,"1st page")
                        .commit();
                break;
            case 4:
                fragment = new Privacy();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities,fragment,"1st page")
                        .commit();
                break;
            case 5:
                SharedPreferences pref2=getSharedPreferences("login_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref2.edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case 6:
                fragment = new EventsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_cities,fragment,"1st page")
                        .commit();
                break;
        }
    }

   public  void syncCompleteResponder(Boolean status)
   {
       if(status)
       Toast.makeText(this,"Sync completed.",Toast.LENGTH_SHORT).show();
       else
           Toast.makeText(this,"Error while syncing",Toast.LENGTH_SHORT).show();
   }


}