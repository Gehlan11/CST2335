package com.example.cst2335.soccer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335.R;

import java.util.ArrayList;

public class FavoriteMatchesFragment extends Fragment implements AdapterView.OnItemClickListener {

    /**
     * View to be shown as fragment layout
     */
    private View itemView;
    /**
     * List of matches to be displayed on the ListView
     */
    private ArrayList<SoccerMatch> allMatches;
    /**
     * Adapter to set on the ListView
     */
    private MatchAdapter matchAdapter;
    /**
     * ListView to show saved matches list
     */
    private ListView soccerMatchList;
    /**
     * ProgressBar to display while fetching the data from the database
     */
    private ProgressBar progressBar;
    /**
     * Reference to the Database helper class
     */
    private SoccerDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_soccer_match_list, container, false);
        database = new SoccerDatabase(getActivity());
        soccerMatchList = itemView.findViewById(R.id.soccerMatchList);
        progressBar = itemView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        allMatches = database.selectAll();
        //show toast if no record found in the database
        if (allMatches.size() == 0) {
            Toast.makeText(getActivity(), getString(R.string.soccer_error_no_fav), Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.GONE);
        matchAdapter = new MatchAdapter(getActivity(), allMatches);
        soccerMatchList.setAdapter(matchAdapter);
        soccerMatchList.setOnItemClickListener(this);
        return itemView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //show match details to user on another page
        SoccerMatch selectedMatch = (SoccerMatch) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), MatchDetails.class);
        intent.putExtra("selectedMatch", selectedMatch);
        startActivity(intent);
    }
}
