package com.example.cst2335.soccer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cst2335.R;
import com.example.cst2335.deezer.DeezerMainActivity;
import com.example.cst2335.geo_data_source.GeoMainActivity;
import com.example.cst2335.lyrics_ovh.LyricsMainActivity;
import com.google.android.material.snackbar.Snackbar;

/**
 * This activity shows the match details
 * User can watch highlights and save/delete the match detail to/from
 * SQLiteDatabase
 */
public class MatchDetails extends AppCompatActivity implements View.OnClickListener {

    /**
     * TextView references
     */
    private TextView textViewCompetition, textViewTitle, textViewSide1, textViewSide2;
    private TextView soccerMatchDate;
    /**
     * Toolbar
     */
    private Toolbar soccerToolbar;
    /**
     * Using coordinator layout to show the snackbar
     */
    private CoordinatorLayout coordinatorLayout;
    /**
     * Button references
     */
    private Button buttonHighlights, buttonAddToFav, buttonDeleteFav;
    /**
     * URL to watch highlights
     */
    private String highLightUrl = "";
    /**
     * Database helper class reference
     */
    private SoccerDatabase database;
    /**
     * Current match details reference
     */
    private SoccerMatch soccerMatch;

    /**
     * Sharedpreferences to store last opened match details
     */
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        sharedPreferences = getSharedPreferences("last_viewed_match", MODE_PRIVATE);

        //read the data sent from the previous screen intent
        Intent intent = getIntent();
        soccerMatch = (SoccerMatch) intent.getSerializableExtra("selectedMatch");

        //initialize database object
        database = new SoccerDatabase(this);

        soccerToolbar = findViewById(R.id.soccerToolbar);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        textViewCompetition = findViewById(R.id.textViewCompetition);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewSide1 = findViewById(R.id.textViewSide1);
        textViewSide2 = findViewById(R.id.textViewSide2);
        soccerMatchDate = findViewById(R.id.soccerMatchDate);
        buttonHighlights = findViewById(R.id.buttonHighlights);
        buttonAddToFav = findViewById(R.id.buttonAddToFav);
        buttonDeleteFav = findViewById(R.id.buttonDeleteFav);
        setSupportActionBar(soccerToolbar);

        buttonHighlights.setOnClickListener(this);
        buttonAddToFav.setOnClickListener(this);
        buttonDeleteFav.setOnClickListener(this);

        String competition = soccerMatch.getCompetition();
        String title = soccerMatch.getTitle();
        String side1 = soccerMatch.getSide1Name();
        String side2 = soccerMatch.getSide2Name();
        String date = soccerMatch.getDate();
        String videoUrl = soccerMatch.getVideoURL();
        textViewCompetition.setText(competition);
        textViewTitle.setText(title);
        textViewSide1.setText(side1);
        textViewSide2.setText(side2);
        soccerMatchDate.setText(date);
        highLightUrl = videoUrl;

        sharedPreferences.edit()
                .putString("title", title)
                .putString("competition", competition)
                .putString("side1", side1)
                .putString("side2", side2)
                .putString("date", date)
                .putString("videoUrl", videoUrl)
                .apply();


        //show the add button if match details does not exists in the database
        if (database.selectMatch(soccerMatch.getTitle())) {
            buttonAddToFav.setVisibility(View.GONE);
            buttonDeleteFav.setVisibility(View.VISIBLE);
        } else {
            //show the delete button if match details exists in the database
            buttonAddToFav.setVisibility(View.VISIBLE);
            buttonDeleteFav.setVisibility(View.GONE);
        }
    }

    //toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_soccer_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.geoActivity:
                startActivity(new Intent(MatchDetails.this, GeoMainActivity.class));
                break;
            case R.id.lyricsActivity:
                startActivity(new Intent(MatchDetails.this, LyricsMainActivity.class));
                break;
            case R.id.deezerActivity:
                startActivity(new Intent(MatchDetails.this, DeezerMainActivity.class));
                break;
            case R.id.soccerHelp:
                //alert dialog to show help
                AlertDialog.Builder helpDialog = new AlertDialog.Builder(this);
                helpDialog.setTitle(getString(R.string.soccer_help));
                helpDialog.setMessage(getString(R.string.soccer_help_detail));
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonHighlights:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(highLightUrl));
                startActivity(intent);
                break;
            case R.id.buttonAddToFav:
                if (soccerMatch != null) {
                    //insert the match details
                    database.insert(soccerMatch);
                    buttonAddToFav.setVisibility(View.GONE);
                    buttonDeleteFav.setVisibility(View.VISIBLE);
                    //show snackbar
                    Snackbar.make(coordinatorLayout, getString(R.string.soccer_match_added_to_list), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonDeleteFav:
                if (soccerMatch != null) {
                    //delete the match details
                    String matchName = soccerMatch.getTitle();
                    database.deleteByName(matchName);
                    buttonAddToFav.setVisibility(View.VISIBLE);
                    buttonDeleteFav.setVisibility(View.GONE);
                    //show snackbar
                    Snackbar.make(coordinatorLayout, getString(R.string.soccer_match_deleted_from_list), Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
