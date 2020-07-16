package com.example.cst2335.soccer;

public class Videos {
    String title;
    String embed;

    public Videos(String title, String embed) {
        this.title = title;
        this.embed = embed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }
}
