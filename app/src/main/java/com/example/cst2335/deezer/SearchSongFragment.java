package com.example.cst2335.deezer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchSongFragment extends Fragment {

    EditText searchInput;
    Button searchButton;
    ProgressBar progressBar;
    ArrayList<Artist> artistArrayList;
    DeezerArtistListAdapter deezerArtistListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_song_fragment, container, false);

        initViews(view);
        hideProgressBar();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String artistName = searchInput.getText().toString();
                new SearchSong(artistName).execute();
            }
        });
        return view;
    }

    private void initViews(View view){
        searchInput = view.findViewById(R.id.searchInput);
        progressBar = view.findViewById(R.id.progressBar);
        searchButton = view.findViewById(R.id.searchButton);
        setupListView(view);
    }

    private void setupListView(View view){
        ListView searchResultsList = view.findViewById(R.id.searchResultsList);
        artistArrayList = new ArrayList<>();
        deezerArtistListAdapter = new DeezerArtistListAdapter(getActivity(), artistArrayList);
        searchResultsList.setAdapter(deezerArtistListAdapter);

        searchResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TrackListActivity.class);
                Artist artist = (Artist)parent.getItemAtPosition(position);
                intent.putExtra("tracklist_url", artist.getTracklist());
                startActivity(intent);
            }
        });
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    class SearchSong extends AsyncTask<Void, Void, String> {

        String artistName;

        SearchSong(String artistName) {
            this.artistName = artistName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected String doInBackground(Void... voids) {
            InputStream inputStream = null;
            try {
                String apiUrl = "https://api.deezer.com/search/artist/?q="
                        + URLEncoder.encode(artistName, "UTF-8")
                        + "&output=xml";
                URL mUrl = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection)
                        mUrl.openConnection();
                inputStream = connection.getInputStream();
                try {
                    XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser pullParser = pullParserFactory.newPullParser();
                    pullParser.setInput(inputStream, null);
                    int currentElement = pullParser.getEventType();
                    boolean insideArtist = false;
                    artistArrayList.clear();
                    Artist artist = null;
                    while (currentElement != XmlPullParser.END_DOCUMENT){
                        String tag = pullParser.getName();
                        switch (currentElement){
                            case XmlPullParser.START_TAG:
                                if(tag.equals("artist")){
                                    artist = new Artist();
                                    insideArtist = true;
                                }else if(tag.equals("name")){
                                    if(insideArtist){
                                        String artistName = pullParser.nextText();
                                        artist.setName(artistName);
                                    }
                                }else if(tag.equals("tracklist")){
                                    if(insideArtist){
                                        String tracklist = pullParser.nextText();
                                        artist.setTracklist(tracklist);
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if(tag.equals("artist")){
                                    artistArrayList.add(artist);
                                    insideArtist = false;
                                }
                                break;
                        }
                        currentElement = pullParser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressBar();
            deezerArtistListAdapter.notifyDataSetChanged();
        }
    }
}
