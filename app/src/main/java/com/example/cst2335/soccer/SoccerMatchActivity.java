package com.example.cst2335.soccer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cst2335.R;
import com.google.android.material.navigation.NavigationView;

public class SoccerMatchActivity extends AppCompatActivity {

    private Toolbar soccerToolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SoccerMatchListFragment matchListFragment;
    private FavoriteMatchesFragment favoriteMatchesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer_match);

        soccerToolbar = findViewById(R.id.soccerToolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        matchListFragment = new SoccerMatchListFragment();
        favoriteMatchesFragment = new FavoriteMatchesFragment();

        showRecentMatches();
    }

    private void showRecentMatches(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, matchListFragment).commit();
    }

    private void showFavoriteMatches(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, favoriteMatchesFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
