package com.example.cst2335.deezer;

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

public class FavouriteSongsListFragment extends Fragment {

    ListView listFavouriteSongs;
    ArrayList<Song> songs;
    FavouriteListAdapter favouriteListAdapter;

    private DeezerSongDatabse deezerSongDatabse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_song_list_fragment, container, false);
        deezerSongDatabse = new DeezerSongDatabse(getActivity());
        listFavouriteSongs = view.findViewById(R.id.listFavouriteSongs);
        listFavouriteSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DeezerSongDetailsActivity.class);
                Song selectedSong  = (Song) parent.getItemAtPosition(position);
                intent.putExtra("songDetails", selectedSong);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        performSelectQuery();
    }

    private void performSelectQuery(){
        songs = deezerSongDatabse.getAllFavouriteSongs();
        favouriteListAdapter = new FavouriteListAdapter(getActivity(), songs);
        listFavouriteSongs.setAdapter(favouriteListAdapter);
    }


}
