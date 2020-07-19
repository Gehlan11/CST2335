package com.example.cst2335.lyrics_ovh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cst2335.R;

import java.util.ArrayList;

/**
 * This helps the ListView to show the custom layout
 */
public class SongListAdapter extends BaseAdapter {

    /**
     * List of the data items to be shown in the lsit
     */
    ArrayList<SongDetails> songDetails;
    /**
     * Helps the class to bind the View and access it.
     */
    static LayoutInflater inflater;

    public SongListAdapter(Context context, ArrayList<SongDetails> songDetails) {
        this.songDetails = songDetails;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return songDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return songDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lyrics_list_item, parent, false);
        }
        //bind the textViews and show the passed data into it.
        SongDetails songDetail = songDetails.get(position);
        TextView songTitle = convertView.findViewById(R.id.song_title);
        TextView songArtist = convertView.findViewById(R.id.artist_name);
        songTitle.setText(songDetail.getTitle());
        songArtist.setText(songDetail.getArtist());
        return convertView;
    }
}
