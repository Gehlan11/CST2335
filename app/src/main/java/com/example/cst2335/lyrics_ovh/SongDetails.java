package com.example.cst2335.lyrics_ovh;

import java.io.Serializable;

/**
 * A custom data structure for holding the required details for a song
 * to save and get the record from the database.
 * */
public class SongDetails implements Serializable {

    String recordId;
    String artist;
    String title;
    String lyrics;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
