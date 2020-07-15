package com.example.cst2335.geo_data_source;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cst2335.R;
import com.google.android.material.navigation.NavigationView;

public class GeoMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    FrameLayout container;

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
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(!currentFragment.equals("search")){
            currentFragment = "search";
            FragmentTransaction fragmentSearchTransaction = fragmentManager.beginTransaction();
            fragmentSearchTransaction.replace(R.id.view_container, geoSearchFragment);
            fragmentSearchTransaction.commit();
        }else{
            super.onBackPressed();
        }
    }
}
