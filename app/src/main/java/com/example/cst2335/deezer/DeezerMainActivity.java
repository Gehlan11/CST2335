package com.example.cst2335.deezer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.cst2335.R;
import com.example.cst2335.geo_data_source.GeoMainActivity;
import com.example.cst2335.lyrics_ovh.LyricsMainActivity;
import com.example.cst2335.soccer.SoccerMatchActivity;
import com.google.android.material.navigation.NavigationView;

public class DeezerMainActivity extends AppCompatActivity {

    /**
     * For showing navigation drawer in the activity
     * */
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    /**
     * Navigation view to show menu inside the drawer layout
     * */
    NavigationView navigationView;
    /**
     * Object of a search fragment that could be placed inside this activity
     * */
    SearchSongFragment searchSongFragment;
    /**
     * Object of a favourite song fragment that could be replaced over search fragment
     * */
    FavouriteSongsListFragment favouriteSongsListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deezer_main_activity);
        initViews();
        showSearchFragment();
    }

    /**
     * Initializes the View components
     * */
    private void initViews() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setupActionBar();
        setupDrawer();

        searchSongFragment = new SearchSongFragment();
        favouriteSongsListFragment = new FavouriteSongsListFragment();
    }

    /**
     * Set toolbar as ActionBar
     * */
    private void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * set navigation drawer with actionbar toggle icon
     * and show menu inside the drawer
     * */
    private void setupDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.deezer_name, R.string.deezer_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //handle drawer menu item clicks
                switch (item.getItemId()) {
                    case R.id.search:
                        showSearchFragment();
                        break;
                    case R.id.favourites:
                        showSavedSearchesFragment();
                        break;
                    case R.id.geoApp:
                        startActivity(new Intent(DeezerMainActivity.this, GeoMainActivity.class));
                        break;
                    case R.id.soccerApp:
                        startActivity(new Intent(DeezerMainActivity.this, SoccerMatchActivity.class));
                        break;
                    case R.id.lyricsApp:
                        startActivity(new Intent(DeezerMainActivity.this, LyricsMainActivity.class));
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    /**
     * Inflater toolbar menu
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle toolbar menu item click
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else if (item.getItemId() == R.id.geoApp) {
            startActivity(new Intent(DeezerMainActivity.this, GeoMainActivity.class));
        } else if (item.getItemId() == R.id.soccerApp) {
            startActivity(new Intent(DeezerMainActivity.this, SoccerMatchActivity.class));
        } else if (item.getItemId() == R.id.lyricsApp) {
            startActivity(new Intent(DeezerMainActivity.this, LyricsMainActivity.class));
        } else if (item.getItemId() == R.id.deezer_help) {
            //show help dialog
            DialogHelper.showHelpDialog(this,
                    getResources().getString(R.string.deezer_toolbar_help),
                    getResources().getString(R.string.deezer_help_1),
                    getResources().getString(R.string.deezer_help_diaog_ok));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show the search fragment inside DeezerMainActivity
     * */
    private void showSearchFragment() {
        FragmentTransaction fragmentSearchTransaction = getSupportFragmentManager().beginTransaction();
        fragmentSearchTransaction.replace(R.id.frameLayout, searchSongFragment);
        fragmentSearchTransaction.commit();
    }

    /**
     * Show the saved songs fragment inside DeezerMainActivity
     * */
    private void showSavedSearchesFragment() {
        FragmentTransaction fragmentSearchTransaction = getSupportFragmentManager().beginTransaction();
        fragmentSearchTransaction.replace(R.id.frameLayout, favouriteSongsListFragment);
        fragmentSearchTransaction.commit();
    }
}
