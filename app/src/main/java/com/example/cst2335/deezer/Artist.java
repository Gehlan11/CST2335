package com.example.cst2335.deezer;

/**
 * Used for store a search result in a list view
 */
public class Artist {

    /**
     * Name of an artist
     */
    String name;

    /**
     * URL for the artist's tracklist
     */
    String tracklist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }
}
