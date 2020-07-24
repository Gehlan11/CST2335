package com.example.cst2335.deezer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335.R;
import com.example.cst2335.geo_data_source.GeoMainActivity;
import com.example.cst2335.lyrics_ovh.LyricsMainActivity;
import com.example.cst2335.soccer.SoccerMatchActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class DeezerSongDetailsActivity extends AppCompatActivity {

    ImageView imageAlbum;
    TextView textSongTitle, textSongDuration, textAlbumTitle;
    Button buttonSave, buttonDelete;
    Bitmap albumBitmap = null;
    String songTitle, duration, albumName, imagePath, songId;
    DeezerSongDatabse deezerSongDatabse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deezer_song_details_activity);
        deezerSongDatabse = new DeezerSongDatabse(this);
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
            songId = song.getId();
            if (deezerSongDatabse.isInserted(songId)) {
                Song storedSong = deezerSongDatabse.getSongDetails(songId);
                songTitle = storedSong.getTitle();
                duration = storedSong.getDuration();
                albumName = storedSong.getAlbumTitle();
                imagePath = storedSong.getCover_big();
                getImage(imagePath);
                showDeleteButton();
                hideSaveButton();
            } else {
                songTitle = song.getTitle();
                duration = song.getDuration();
                albumName = song.getAlbumTitle();
                imagePath = song.getCover_big();
                new LoadImage(imagePath).execute();
                showSaveButton();
                hideDeleteButton();
            }
            textSongTitle.setText(songTitle);
            textSongDuration.setText(duration);
            textAlbumTitle.setText(albumName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.geoApp){
            startActivity(new Intent(DeezerSongDetailsActivity.this, GeoMainActivity.class));
        }else if(item.getItemId() == R.id.soccerApp){
            startActivity(new Intent(DeezerSongDetailsActivity.this, SoccerMatchActivity.class));
        }else if(item.getItemId() == R.id.lyricsApp){
            startActivity(new Intent(DeezerSongDetailsActivity.this, LyricsMainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSaveButton(){
        buttonSave.setVisibility(View.VISIBLE);
    }

    private void hideSaveButton(){
        buttonSave.setVisibility(View.GONE);
    }

    private void showDeleteButton(){
        buttonDelete.setVisibility(View.VISIBLE);
    }

    private void hideDeleteButton(){
        buttonDelete.setVisibility(View.GONE);
    }

    private void enableSaveButton(){
        buttonSave.setEnabled(true);
    }

    private void disableSaveButton(){
        buttonSave.setEnabled(false);
    }

    private void addToFavourites() {
        try {
            String imagePath = saveImage(albumBitmap, songTitle);
            Song song = new Song();
            song.setTitle(songTitle);
            song.setCover_big(imagePath);
            song.setDuration(duration);
            song.setAlbumTitle(albumName);
            song.setId(songId);
            deezerSongDatabse.insertSong(song);
            hideSaveButton();
            showDeleteButton();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeFromFavourites() {
        deezerSongDatabse.deleteSong(songId);
        hideDeleteButton();
        showSaveButton();
    }

    private String saveImage(Bitmap bitmap, String songTitle) throws IOException {
        String imagePath = "";
        if (bitmap != null) {
            ContentResolver contentResolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, songTitle + ".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1);
            }
            Uri albumImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri fileUri = null;
            OutputStream outputStream = null;
            try {
                fileUri = contentResolver.insert(albumImageUri, contentValues);
                imagePath = fileUri.toString();
                if (fileUri == null) {
                    throw new IOException("can not create MediaStore node");
                }
                outputStream = contentResolver.openOutputStream(fileUri);
                if (outputStream == null) {
                    throw new IOException("unable to read file stream");
                }
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                    throw new IOException("unable to save image");
                }
            } catch (IOException e) {
                if (fileUri != null) {
                    contentResolver.delete(fileUri, null, null);
                }
            }
            if (outputStream != null) {
                outputStream.close();
            }

        }
        return imagePath;
    }

    public void getImage(String imagePath) {
        ImageDecoder.Source source = null;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                source = ImageDecoder.createSource(this.getContentResolver(), Uri.parse(imagePath));
                albumBitmap = ImageDecoder.decodeBitmap(source);
            } else {
                albumBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(imagePath));
            }
            imageAlbum.setImageBitmap(albumBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class LoadImage extends AsyncTask<Void, Void, Bitmap> {

        String imageUrl;

        LoadImage(String imageUrl) {
            this.imageUrl = imageUrl;
            disableSaveButton();
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
                enableSaveButton();
                albumBitmap = bitmap;
                imageAlbum.setImageBitmap(albumBitmap);
            }
        }
    }
}
