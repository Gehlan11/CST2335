package com.example.cst2335.deezer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cst2335.R;

import java.util.ArrayList;

/**
 * Adapter to show the tracklist of an artist
 */
public class TrackListAdapter extends BaseAdapter {

    /**
     * Inflater to attach view to the List item
     */
    static LayoutInflater inflater;
    /**
     * ListView data
     */
    ArrayList<Song> songs;

    public TrackListAdapter(Context context, ArrayList<Song> songs) {
        this.songs = songs;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.song_list, parent, false);
        }
        //set the data to the list item
        TextView textSongName = convertView.findViewById(R.id.textSongName);
        String songTitle = songs.get(position).getTitle();
        textSongName.setText(songTitle);
        return convertView;
    }
}
