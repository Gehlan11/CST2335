package com.example.cst2335.lyrics_ovh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchLyricsFragment extends Fragment implements View.OnClickListener {

    /**
     * EditText for artist or band name
     */
    EditText artistName;
    /**
     * EditText for artist or band name
     */
    EditText songTitle;
    /**
     * Button for calling api
     */
    Button search;
    /**
     * ProgressBar for showing while API is being called
     */
    ProgressBar progressBar;

    /**
     * For storing successful searched data
     */
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_lyrics, container, false);
        sharedPreferences = getActivity().getSharedPreferences("LYRICS_SEARCH_DATA", Context.MODE_PRIVATE);

        artistName = view.findViewById(R.id.artist_name);
        songTitle = view.findViewById(R.id.song_title);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        search = view.findViewById(R.id.search);
        search.setOnClickListener(this);
        //get last searched data if exists
        getLastSearchedData();

        return view;
    }

    /**
     * Read the shared preference for getting last successful searched data
     */
    private void getLastSearchedData() {
        String strArtistName = sharedPreferences.getString("artistName", "");
        String strSongTitle = sharedPreferences.getString("songTitle", "");
        songTitle.setText(strSongTitle);
        artistName.setText(strArtistName);
    }

    /**
     * Save the last successful searched data to the sharedpreference for later viewing
     */
    private void saveSearchedData(String artistName, String songTitle) {
        sharedPreferences.edit()
                .putString("artistName", artistName)
                .putString("songTitle", songTitle)
                .apply();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search) {
            String artist = artistName.getText().toString();
            String song = songTitle.getText().toString();

            if (artist.trim().isEmpty()) {
                Toast.makeText(getActivity(), getResources().getString(R.string.lyrics_error_enter_artist_name), Toast.LENGTH_SHORT).show();
            } else if (song.trim().isEmpty()) {
                Toast.makeText(getActivity(), getResources().getString(R.string.lyrics_error_enter_song_title), Toast.LENGTH_SHORT).show();
            } else {
                new SearchLyrics(artist, song).execute();
            }
        }
    }

    /**
     * AsyncTask to perform API call
     */
    class SearchLyrics extends AsyncTask<Void, Void, String> {

        String artist;
        String song;

        /**
         * @param artist Name of the artist or band
         * @param song   name of the song
         */
        SearchLyrics(String artist, String song) {
            this.artist = artist;
            this.song = song;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hide progressbar
            progressBar.setVisibility(View.INVISIBLE);
            //parse the json data
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("lyrics")) {
                    String lyrics = jsonObject.getString("lyrics");
                    saveSearchedData(artist, song);
                    Intent intent = new Intent(getActivity(), ShowLyricsActivity.class);
                    intent.putExtra("artist", artist);
                    intent.putExtra("song", song);
                    intent.putExtra("lyrics", lyrics);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.lyrics_error_not_found), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), getResources().getString(R.string.lyrics_error_not_found), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //show progressbar
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String httpResponse = "";
            try {
                //replace spaces with %20 for URL safe arguments
                String encodedArtist = artist.replaceAll(" ", "%20");
                String encodedSong = song.replaceAll(" ", "%20");
                String apiUrl = "https://api.lyrics.ovh/v1/" + encodedArtist + "/" + encodedSong;
                //using HttpUrlConnection to call API
                URL url = new URL(apiUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                String responseLine;
                //reading the response
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                while ((responseLine = br.readLine()) != null) {
                    stringBuilder.append(responseLine);
                }
                httpResponse = stringBuilder.toString();
            } catch (IOException e) {
            }
            return httpResponse;
        }
    }
}
