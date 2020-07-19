package com.example.cst2335.lyrics_ovh;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.cst2335.R;
import com.example.cst2335.geo_data_source.GeoMainActivity;
import com.example.cst2335.soccer.SoccerMatchActivity;
import com.google.android.material.snackbar.Snackbar;

public class ShowLyricsActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * TextViews for showing required data
     */
    TextView artistName, songTitle, songLyrics;
    /**
     * Buttons for calling specific operations
     */
    Button googleSearch, addToFavourite, removeFavourite;
    /**
     * String Objects for holding the intent data
     */
    String song, artist, lyrics;
    /**
     * CoordinatorLayout for showing SnackBar
     */
    CoordinatorLayout coordinatorLayout;
    /**
     * Toolbar to set as the AcitonBar
     */
    Toolbar toolbar;

    /**
     * Reference to the SQLite database object
     */
    LyricsDatabase database;
    /**
     * To hold the recordId if the data if loaded from the database
     */
    String recordId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_lyrics);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        artistName = findViewById(R.id.artist_name);
        songTitle = findViewById(R.id.song_title);
        songLyrics = findViewById(R.id.song_lyrics);
        googleSearch = findViewById(R.id.google_search);
        addToFavourite = findViewById(R.id.add_favourite);
        removeFavourite = findViewById(R.id.remove_favourite);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new LyricsDatabase(this);

        Intent intent = getIntent();
        artist = intent.getStringExtra("artist");
        song = intent.getStringExtra("song");
        lyrics = intent.getStringExtra("lyrics");

        artistName.setText(artist);
        songTitle.setText(song);
        songLyrics.setText(lyrics);

        googleSearch.setOnClickListener(this);
        addToFavourite.setOnClickListener(this);
        removeFavourite.setOnClickListener(this);

        recordId = database.isFavouriteExists(artist, song);
        boolean isFavouriteAdded = !recordId.equals("0");
        if (isFavouriteAdded) {
            addToFavourite.setVisibility(View.GONE);
            removeFavourite.setVisibility(View.VISIBLE);
        } else {
            removeFavourite.setVisibility(View.GONE);
            addToFavourite.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lyrics_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.geoDataActivity:
                Intent geoIntent = new Intent(ShowLyricsActivity.this, GeoMainActivity.class);
                startActivity(geoIntent);
                break;
            case R.id.soccerActivity:
                Intent soccerIntent = new Intent(ShowLyricsActivity.this, SoccerMatchActivity.class);
                startActivity(soccerIntent);
                break;
            case R.id.help:
                showHelpDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_search:
                String url = "https://www.google.com/search?q=" + artist + "+" + song;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.add_favourite:
                //perform insert operation
                SongDetails songDetails = new SongDetails();
                songDetails.setArtist(artist);
                songDetails.setTitle(song);
                songDetails.setLyrics(lyrics);
                database.addToFavourites(songDetails);
                addToFavourite.setVisibility(View.GONE);
                removeFavourite.setVisibility(View.VISIBLE);
                //show the snackbar
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.lyrics_added_favourite), Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.remove_favourite:
                showAlertDialog();
                break;
        }
    }

    /**
     * Shows the alert dialog for showing the confirmation
     * */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_remove_fav_lyrics));
        builder.setMessage(getResources().getString(R.string.dialog_lyrics_sure));
        builder.setCancelable(true);
        builder.setPositiveButton(getResources().getString(R.string.dialog_lyrics_sure_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //deletes the record from the database
                database.deleteFavourite(recordId);
                removeFavourite.setVisibility(View.GONE);
                addToFavourite.setVisibility(View.VISIBLE);
                //shows the snackbar as confirmation
                Snackbar.make(coordinatorLayout, getResources().getString(R.string.lyrics_removed_favourite), Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.dialog_lyrics_sure_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Shows AlertDialog for help
     * */
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.lyrics_help));
        builder.setMessage(getResources().getString(R.string.lyrics_help_show));
        builder.setPositiveButton(getResources().getString(R.string.lyrics_dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
