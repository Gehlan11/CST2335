package com.example.cst2335.lyrics_ovh;

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

import com.example.cst2335.R;
import com.example.cst2335.geo_data_source.GeoMainActivity;
import com.example.cst2335.soccer.SoccerMatchActivity;
import com.google.android.material.navigation.NavigationView;

public class LyricsMainActivity extends AppCompatActivity {

    /**
     * For showing drawer on the left of the screen
     */
    DrawerLayout drawerLayout;
    /**
     * toolbar to show as actionbar
     */
    Toolbar toolbar;
    /**
     * For showing different fragments inside the FrameLayout
     */
    FrameLayout frameLayout;
    /**
     * For showing and handling drawer menu items
     */
    NavigationView navigationView;
    /**
     * Object to search fragment
     */
    SearchLyricsFragment searchLyricsFragment;
    /**
     * Object to Favourites List fragment
     */
    FavouriteLyricsFragment favouriteLyricsFragment;
    /**
     * Flag variable to check which fragment is being shown now, can be helpful on back press
     */
    boolean isShowingHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyrics_main_activity);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.frameLayout1);
        navigationView = findViewById(R.id.navigationView);

        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.lyrics_search_app, R.string.lyrics_search_app);

        searchLyricsFragment = new SearchLyricsFragment();
        favouriteLyricsFragment = new FavouriteLyricsFragment();

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //shows the search fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout1, searchLyricsFragment)
                .commit();
        isShowingHome = true;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemSearch:
                        //shows the search fragment
                        isShowingHome = true;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout1, searchLyricsFragment)
                                .commit();
                        break;
                    case R.id.itemFavourite:
                        //shows the favourite fragment
                        isShowingHome = false;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout1, favouriteLyricsFragment)
                                .commit();
                        break;
                    case R.id.geoDataActivity:
                        //start geo activity
                        Intent geoIntent = new Intent(LyricsMainActivity.this, GeoMainActivity.class);
                        startActivity(geoIntent);
                        break;
                    case R.id.soccerActivity:
                        //start soccer activity
                        Intent soccerIntent = new Intent(LyricsMainActivity.this, SoccerMatchActivity.class);
                        startActivity(soccerIntent);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    /**
     * inflate the menu to the action bar / toolbar
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lyrics_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //open the drawer
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.geoDataActivity:
                Intent geoIntent = new Intent(LyricsMainActivity.this, GeoMainActivity.class);
                startActivity(geoIntent);
                break;
            case R.id.soccerActivity:
                Intent soccerIntent = new Intent(LyricsMainActivity.this, SoccerMatchActivity.class);
                startActivity(soccerIntent);
                break;
            case R.id.help:
                showHelpDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //close the drawer if open
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!isShowingHome) {
            //show search fragment before finish
            isShowingHome = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout1, searchLyricsFragment)
                    .commit();
        } else {
            //perform "back" action
            super.onBackPressed();
        }
    }

    /**
     * Shows alert dialog containing help information regarding search and favourite screen
     */
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.lyrics_help));
        builder.setMessage(getResources().getString(R.string.lyrics_help_main));
        builder.setPositiveButton(getResources().getString(R.string.lyrics_dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
