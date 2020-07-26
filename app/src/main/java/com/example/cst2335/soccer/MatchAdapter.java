package com.example.cst2335.soccer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cst2335.R;

import java.util.ArrayList;

/**
 * BaseAdapter class to set as an adapter using defined
 * XML layout to the listview
 * */
public class MatchAdapter extends BaseAdapter {

    private static LayoutInflater layoutInflater;
    private ArrayList<SoccerMatch> soccerMatches;

    public MatchAdapter(Context context, ArrayList<SoccerMatch> soccerMatches){
        this.soccerMatches = soccerMatches;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return soccerMatches.size();
    }

    @Override
    public Object getItem(int position) {
        return soccerMatches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_match, null);
        }
        TextView textViewCompetition = convertView.findViewById(R.id.textViewCompetition);
        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);

        String title = soccerMatches.get(position).getTitle();
        String competition = soccerMatches.get(position).getCompetition();
        textViewCompetition.setText(competition);
        textViewTitle.setText(title);
        return convertView;
    }
}
