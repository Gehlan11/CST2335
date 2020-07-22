package com.example.cst2335.deezer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TrackListActivity extends AppCompatActivity {

    ListView trackListView;
    String trackListUrl = "";
    ArrayList<Song> songs;
    TrackListAdapter trackListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_list_activity);
        initViews();
        getTrackList();
    }

    private void getTrackList() {
        trackListUrl = getIntent().getStringExtra("tracklist_url");
        new GetTrackList(trackListUrl).execute();
    }

    private void initViews() {
        trackListView = findViewById(R.id.trackListView);
        songs = new ArrayList<>();
        trackListAdapter = new TrackListAdapter(this, songs);
        trackListView.setAdapter(trackListAdapter);
        trackListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrackListActivity.this, DeezerSongDetailsActivity.class);
                Song song = songs.get(position);
                intent.putExtra("songDetails", song);
                startActivity(intent);
            }
        });
    }

    class GetTrackList extends AsyncTask<Void, Void, String> {

        String trackListUrl;

        public GetTrackList(String trackListUrl) {
            this.trackListUrl = trackListUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            URL mUrl = null;
            String trackData = "";
            try {
                mUrl = new URL(trackListUrl);
                HttpURLConnection connection = (HttpURLConnection)
                        mUrl.openConnection();
                InputStream inputStream = connection.getInputStream();

                trackData = readInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return trackData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject data = jsonArray.getJSONObject(i);
                    int id = data.getInt("id");
                    String title = data.getString("title");
                    int duration = data.getInt("duration");
                    JSONObject album = data.getJSONObject("album");
                    String albumTitle = album.getString("title");
                    String cover_big = album.getString("cover_big");
                    Song song = new Song();
                    song.setId(String.valueOf(id));
                    song.setTitle(title);
                    song.setDuration(String.valueOf(duration));
                    song.setAlbumTitle(albumTitle);
                    song.setCover_big(cover_big);
                    songs.add(song);
                }
                trackListAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private String readInputStream(InputStream inputStream) {
            StringBuilder stringBuilder = new StringBuilder();
            String readLine;
            try {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(inputStream));
                while ((readLine = br.readLine()) != null) {
                    stringBuilder.append(readLine);
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                return "";
            }
        }
    }
}