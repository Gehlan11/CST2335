package com.example.cst2335.geo_data_source;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cst2335.R;
import com.example.cst2335.deezer.DeezerMainActivity;
import com.example.cst2335.lyrics_ovh.LyricsMainActivity;
import com.example.cst2335.soccer.SoccerMatchActivity;
import com.google.android.material.navigation.NavigationView;

public class GeoMainActivity extends AppCompatActivity {

    /**
     * to show toolbar menu
     */
    Toolbar toolbar;
    /**
     * to show drawer menu
     */
    NavigationView navigationView;
    /**
     * to show drawer
     */
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    FrameLayout container;

    /**
     * to replace/add fragments in the activity
     */
    FragmentManager fragmentManager;
    String currentFragment = "";
    GeoSearchFragment geoSearchFragment;
    GeoSavedCitiesFragment geoSavedCitiesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_main);

        toolbar = findViewById(R.id.geo_main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.geo_main_drawer);
        navigationView = findViewById(R.id.geo_navigation_view);
        container = findViewById(R.id.view_container);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.geo_drawer_open, R.string.geo_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        geoSearchFragment = new GeoSearchFragment();
        geoSavedCitiesFragment = new GeoSavedCitiesFragment();

        fragmentManager = getSupportFragmentManager();
        //show search cities fragment
        currentFragment = "search";
        FragmentTransaction fragmentSearchTransaction = fragmentManager.beginTransaction();
        fragmentSearchTransaction.replace(R.id.view_container, geoSearchFragment);
        fragmentSearchTransaction.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.geo_search_city:
                        currentFragment = "search";
                        FragmentTransaction fragmentSearchTransaction = fragmentManager.beginTransaction();
                        fragmentSearchTransaction.replace(R.id.view_container, geoSearchFragment);
                        fragmentSearchTransaction.commit();
                        break;
                    case R.id.geo_saved_cities:
                        currentFragment = "saved";
                        FragmentTransaction fragmentSavedTransaction = fragmentManager.beginTransaction();
                        fragmentSavedTransaction.replace(R.id.view_container, geoSavedCitiesFragment);
                        fragmentSavedTransaction.commit();
                        break;
                    case R.id.soccer_app:
                        Intent soccerIntent = new Intent(GeoMainActivity.this, SoccerMatchActivity.class);
                        startActivity(soccerIntent);
                        break;
                    case R.id.lyrics_search_app:
                        Intent lyricsIntent = new Intent(GeoMainActivity.this, LyricsMainActivity.class);
                        startActivity(lyricsIntent);
                        break;
                    case R.id.deezer_app:
                        Intent deezerIntent = new Intent(GeoMainActivity.this, DeezerMainActivity.class);
                        startActivity(deezerIntent);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.soccer_app:
                Intent soccerIntent = new Intent(GeoMainActivity.this, SoccerMatchActivity.class);
                startActivity(soccerIntent);
                return true;
            case R.id.lyrics_search_app:
                Intent lyricsIntent = new Intent(GeoMainActivity.this, LyricsMainActivity.class);
                startActivity(lyricsIntent);
                return true;
            case R.id.deezer_app:
                Intent deezerIntent = new Intent(GeoMainActivity.this, DeezerMainActivity.class);
                startActivity(deezerIntent);
                return true;
            case R.id.geo_help:
                //showing alert dialog to show help instruction to user
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.geo_menu_item_help));
                builder.setMessage(getString(R.string.geo_help));
                builder.setNeutralButton(getString(R.string.geo_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_geo_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //close drawer if open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!currentFragment.equals("search")) {
            //show search fragment if it's not being shown
            currentFragment = "search";
            FragmentTransaction fragmentSearchTransaction = fragmentManager.beginTransaction();
            fragmentSearchTransaction.replace(R.id.view_container, geoSearchFragment);
            fragmentSearchTransaction.commit();
        } else {
            //perform back
            super.onBackPressed();
        }
    }
}
