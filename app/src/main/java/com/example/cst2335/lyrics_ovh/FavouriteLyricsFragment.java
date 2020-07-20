package com.example.cst2335.lyrics_ovh;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335.R;

import java.util.ArrayList;

public class FavouriteLyricsFragment extends Fragment {

    /**
     * ListView for showing database data
     */
    ListView listView;
    /**
     * Layout for showing current fragment view
     */
    View view;
    /**
     * Adapter for setting up the ListView
     */
    SongListAdapter songListAdapter;

    /**
     * Object reference for database
     */
    LyricsDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourite_lyrics_list, container, false);
        database = new LyricsDatabase(getActivity());
        listView = view.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** show detail for the selected list item in another activity*/
                SongDetails songDetails = (SongDetails) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ShowLyricsActivity.class);
                intent.putExtra("artist", songDetails.getArtist());
                intent.putExtra("song", songDetails.getTitle());
                intent.putExtra("lyrics", songDetails.getLyrics());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readDatabaseData();
    }

    /**
     * Gets all the favourite song details from the database
     * and sets the retrieved data to ListView using adapter.
     */
    private void readDatabaseData() {
        ArrayList<SongDetails> songDetails = database.getAllFavourites();
        songListAdapter = new SongListAdapter(getActivity(), songDetails);
        listView.setAdapter(songListAdapter);
    }
}
