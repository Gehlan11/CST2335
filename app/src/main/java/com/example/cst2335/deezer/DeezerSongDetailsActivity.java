package com.example.cst2335.deezer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335.R;

import java.io.IOException;
import java.net.URL;

public class DeezerSongDetailsActivity extends AppCompatActivity {

    ImageView imageAlbum;
    TextView textSongTitle, textSongDuration, textAlbumTitle;
    Button buttonSave, buttonDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deezer_song_details_activity);
        initViews();
        loadSongData();
    }

    private void initViews() {
        imageAlbum = findViewById(R.id.imageAlbum);
        textSongTitle = findViewById(R.id.textSongTitle);
        textSongDuration = findViewById(R.id.textSongDuration);
        textAlbumTitle = findViewById(R.id.textAlbumTitle);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavourites();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromFavourites();
            }
        });
    }

    private void loadSongData() {
        Song song = (Song) getIntent().getSerializableExtra("songDetails");
        if (song != null) {
            textSongTitle.setText(song.title);
            textSongDuration.setText(song.duration);
            textAlbumTitle.setText(song.albumTitle);
            new LoadImage(song.cover_big).execute();
        }
    }

    private void addToFavourites(){
    }

    private void removeFromFavourites(){

    }

    class LoadImage extends AsyncTask<Void, Void, Bitmap> {

        String imageUrl;
        LoadImage(String imageUrl){
            this.imageUrl = imageUrl;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URL url = new URL(imageUrl);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                imageAlbum.setImageBitmap(bitmap);
            }
        }
    }
}
