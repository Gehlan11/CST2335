package com.example.cst2335.soccer;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * this class helps to call API url and get the
 * recent matches using AsyncTask
 * */
public class GetRecentMatches extends AsyncTask<Void, Void, String> {

    //listener to send callbacks to the UI
    private MatchDataListener matchDataListener;

    /**
     * @param matchDataListener Listener to set
     * */
    public GetRecentMatches(MatchDataListener matchDataListener) {
        this.matchDataListener = matchDataListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        matchDataListener.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String response = "";
        try {
            //call API
            URL mUrl = new URL("https://www.scorebat.com/video-api/v1/");
            HttpURLConnection connection = (HttpURLConnection)
                    mUrl.openConnection();

            response = getString(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        matchDataListener.onPostExecute(s);
    }

    /**
     * This function reads the data from HTTP call response
     */
    private String getString(InputStream is) {
        String line = "";
        InputStreamReader isr = new
                InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(line);
        }
        return sb.toString();
    }
}
