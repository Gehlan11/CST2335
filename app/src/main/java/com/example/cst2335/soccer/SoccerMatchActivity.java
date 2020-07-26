package com.example.cst2335.soccer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
import com.example.cst2335.geo_data_source.GeoMainActivity;
import com.example.cst2335.lyrics_ovh.LyricsMainActivity;
import com.google.android.material.navigation.NavigationView;

/**
 * Main activity for soccer application
 */
public class SoccerMatchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Toolbar to show drawer icon and
     */
    private Toolbar soccerToolbar;
    /**
     * NavigationView to show menu inside the drawer
     */
    private NavigationView navigationView;
    /**
     * Drawer to open from the LEFT side
     */
    private DrawerLayout drawerLayout;
    /**
     * Reference to Latest matches fragment
     */
    private SoccerMatchListFragment matchListFragment;
    /**
     * Reference to Favourite matches fragment
     */
    private FavoriteMatchesFragment favoriteMatchesFragment;

    /**
     * Sharedpreferences to show last opened match details
     */
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_match);

        sharedPreferences = getSharedPreferences("last_viewed_match", MODE_PRIVATE);
        soccerToolbar = findViewById(R.id.soccerToolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(soccerToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        matchListFragment = new SoccerMatchListFragment();
        favoriteMatchesFragment = new FavoriteMatchesFragment();

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.soccer_open_drawer, R.string.soccer_close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        showRecentMatches();

        //read data from the shared preferences if there was last opened screen
        String title = sharedPreferences.getString("title", "");
        String competition = sharedPreferences.getString("competition", "");
        String side1 = sharedPreferences.getString("side1", "");
        String side2 = sharedPreferences.getString("side2", "");
        String date = sharedPreferences.getString("date", "");
        String videoUrl = sharedPreferences.getString("videoUrl", "");
        if (!title.equals("")) {
            SoccerMatch soccerMatch = new SoccerMatch();
            soccerMatch.setTitle(title);
            soccerMatch.setCompetition(competition);
            soccerMatch.setSide1Name(side1);
            soccerMatch.setSide2Name(side2);
            soccerMatch.setDate(date);
            soccerMatch.setVideoURL(videoUrl);

            Intent intent = new Intent(SoccerMatchActivity.this, MatchDetails.class);
            intent.putExtra("selectedMatch", soccerMatch);
            startActivity(intent);
        }
    }

    /**
     * Shows recent matches fragment
     */
    private void showRecentMatches() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, matchListFragment).commit();
    }

    /**
     * Shows favourite matches fragment
     */
    private void showFavoriteMatches() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, favoriteMatchesFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_soccer_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.geoActivity:
                startActivity(new Intent(SoccerMatchActivity.this, GeoMainActivity.class));
                break;
            case R.id.lyricsActivity:
                startActivity(new Intent(SoccerMatchActivity.this, LyricsMainActivity.class));
                break;
            case R.id.deezerActivity:
                startActivity(new Intent(SoccerMatchActivity.this, DeezerMainActivity.class));
                break;
            case R.id.soccerHelp:
                AlertDialog.Builder helpDialog = new AlertDialog.Builder(this);
                helpDialog.setTitle(getString(R.string.soccer_help));
                helpDialog.setMessage(getString(R.string.soccer_help_main));
                helpDialog.setNeutralButton(getString(R.string.soccer_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                helpDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.geoActivity:
                startActivity(new Intent(SoccerMatchActivity.this, GeoMainActivity.class));
                break;
            case R.id.lyricsActivity:
                startActivity(new Intent(SoccerMatchActivity.this, LyricsMainActivity.class));
                break;
            case R.id.deezerActivity:
                startActivity(new Intent(SoccerMatchActivity.this, DeezerMainActivity.class));
                break;
            case R.id.recentSoccerMatch:
                showRecentMatches();
                break;
            case R.id.favSoccerMatch:
                showFavoriteMatches();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
