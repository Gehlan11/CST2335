package com.example.cst2335.deezer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DeezerSongDatabse {

    DBHelper dbHelper;

    final String table_favourite_songs = "favourite_songs";
    final String col_song_id = "id";
    final String col_song_title = "title";
    final String col_song_album_name = "album";
    final String col_song_album_cover_path = "cover_path";

    public DeezerSongDatabse(Context context){
        dbHelper = new DBHelper(context);
    }

    class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, "", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
