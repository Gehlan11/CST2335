package com.example.cst2335.soccer;

import java.io.Serializable;

/**
 * Match details
 * */
public class SoccerMatch implements Serializable {
    private String title;
    private String competition;
    private String date;
    private String side1Name;
    private String side2Name;
    private String videoURL;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSide1Name() {
        return side1Name;
    }

    public void setSide1Name(String side1Name) {
        this.side1Name = side1Name;
    }

    public String getSide2Name() {
        return side2Name;
    }

    public void setSide2Name(String side2Name) {
        this.side2Name = side2Name;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
