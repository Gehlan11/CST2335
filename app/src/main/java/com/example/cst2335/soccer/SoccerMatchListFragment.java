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

public class SoccerMatchListFragment extends Fragment implements MatchDataListener {

    private ProgressBar progressBar;
    private ArrayList<SoccerMatch> allMatches;
    private MatchAdapter matchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_soccer_match_list, container, false);
        progressBar = itemView.findViewById(R.id.progressBar);
        ListView soccerMatchList = itemView.findViewById(R.id.soccerMatchList);
        allMatches = new ArrayList<>();
        matchAdapter = new MatchAdapter(getActivity(), allMatches);
        SoccerMatch soccerMatch = new SoccerMatch();
        soccerMatch.setTitle("test");
        soccerMatch.setCompetition("test");
        allMatches.add(soccerMatch);
        soccerMatchList.setAdapter(matchAdapter);

        soccerMatchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MatchDetails.class);
                startActivity(intent);
            }
        });

        GetRecentMatches getRecentMatches = new GetRecentMatches(this);
        getRecentMatches.execute();

        return itemView;
    }

    @Override
    public void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

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
                        SoccerMatch soccerMatch = new SoccerMatch();
                        soccerMatch.setTitle(title);
                        soccerMatch.setCompetition(competition);
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
