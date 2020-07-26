package com.example.cst2335.soccer;

public interface MatchDataListener {

    //callback for async task's prexecute
    void onPreExecute();
    //callback for async task's postexecute
    void onPostExecute(String s);
}
