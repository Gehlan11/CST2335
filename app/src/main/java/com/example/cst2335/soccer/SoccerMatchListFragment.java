package com.example.cst2335.soccer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Recent matches fragment, shows the recently played matches
 * by calling API
 * */
public class SoccerMatchListFragment extends Fragment implements MatchDataListener {

    /**
     * ProgressBar to display while fetching match list
     * */
    private ProgressBar progressBar;
    /**
     * List of data to show inside the list view
     * */
    private ArrayList<SoccerMatch> allMatches;
    /**
     * Adapter to set on the listview
     * */
    private MatchAdapter matchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_soccer_match_list, container, false);
        progressBar = itemView.findViewById(R.id.progressBar);
        ListView soccerMatchList = itemView.findViewById(R.id.soccerMatchList);
        allMatches = new ArrayList<>();
        matchAdapter = new MatchAdapter(getActivity(), allMatches);
        soccerMatchList.setAdapter(matchAdapter);

        soccerMatchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SoccerMatch selectedMatch = (SoccerMatch) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), MatchDetails.class);
                intent.putExtra("selectedMatch", selectedMatch);
                startActivity(intent);
            }
        });

        GetRecentMatches getRecentMatches = new GetRecentMatches(this);
        getRecentMatches.execute();

        return itemView;
    }

    /**
     * Called before async task starts to execute the API
     * */
    @Override
    public void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Called after async task executed the API
     * Parses the JSON data and adds each record to the list view
     * */
    @Override
    public void onPostExecute(String s) {
        progressBar.setVisibility(View.GONE);
        if (s != null) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() > 0) {
                    allMatches.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject matchJson = jsonArray.getJSONObject(i);
                        String title = matchJson.getString("title");
                        JSONObject competitionJson = matchJson.getJSONObject("competition");
                        String competition = competitionJson.getString("name");
                        JSONObject side1 = matchJson.getJSONObject("side1");
                        JSONObject side2 = matchJson.getJSONObject("side2");
                        String side1Name = side1.getString("name");
                        String side2Name = side2.getString("name");
                        String date = matchJson.getString("date");
                        JSONArray videos = matchJson.getJSONArray("videos");
                        String videoURL = "";
                        if(videos.length() > 0){
                            String embed = videos.getJSONObject(0).getString("embed");
                            String src = embed.substring(embed.indexOf("https://www."));
                            videoURL = src.substring(0, src.indexOf("\'"));
                        }

                        SoccerMatch soccerMatch = new SoccerMatch();
                        soccerMatch.setTitle(title);
                        soccerMatch.setCompetition(competition);
                        soccerMatch.setDate(date);
                        soccerMatch.setVideoURL(videoURL);
                        soccerMatch.setSide1Name(side1Name);
                        soccerMatch.setSide2Name(side2Name);
                        allMatches.add(soccerMatch);
                    }
                    matchAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
