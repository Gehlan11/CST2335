package com.example.cst2335.deezer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DeezerSongDatabse {

    DBHelper dbHelper;

    private final String TABLE_FAVOURITE_SONGS = "favourite_songs";
    private final String COL_SONG_ID = "id";
    private final String COL_SONG_TITLE = "title";
    private final String COL_SONG_ALBUM_NAME = "album";
    private final String COL_SONG_DURATION = "duration";
    private final String COL_SONG_ALBUM_COVER_PATH = "cover_path";

    public DeezerSongDatabse(Context context) {
        dbHelper = new DBHelper(context);
    }

    class DBHelper extends SQLiteOpenHelper {

        DBHelper(Context context) {
            super(context, "DEEZER_SONG_DATABASE", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createQuery = "CREATE TABLE " + TABLE_FAVOURITE_SONGS + "(" +
                    COL_SONG_ID + " TEXT PRIMARY KEY," +
                    COL_SONG_TITLE + " TEXT," +
                    COL_SONG_ALBUM_NAME + " TEXT," +
                    COL_SONG_DURATION + " TEXT," +
                    COL_SONG_ALBUM_COVER_PATH + " TEXT" +
                    ")";
            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE " + TABLE_FAVOURITE_SONGS);
            onCreate(db);
        }
    }

    public ArrayList<Song> getAllFavouriteSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVOURITE_SONGS,
                new String[]{
                        COL_SONG_ID, COL_SONG_TITLE,
                        COL_SONG_ALBUM_NAME,
                        COL_SONG_DURATION,
                        COL_SONG_ALBUM_COVER_PATH
                },
                null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Song song = new Song();
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String albumName = cursor.getString(2);
                String duration = cursor.getString(3);
                String album_cover = cursor.getString(4);
                song.setId(id);
                song.setTitle(title);
                song.setCover_big(album_cover);
                song.setAlbumTitle(albumName);
                song.setDuration(duration);
                songs.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    public Song getSongDetails(String songId) {
        Song song = new Song();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVOURITE_SONGS,
                new String[]{
                        COL_SONG_ID, COL_SONG_TITLE,
                        COL_SONG_ALBUM_NAME,
                        COL_SONG_DURATION,
                        COL_SONG_ALBUM_COVER_PATH
                },
                COL_SONG_ID + "=?", new String[]{songId},
                null, null, null, null);
        if (cursor.moveToFirst()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String albumName = cursor.getString(2);
            String duration = cursor.getString(3);
            String album_cover = cursor.getString(4);
            song.setId(id);
            song.setTitle(title);
            song.setCover_big(album_cover);
            song.setAlbumTitle(albumName);
            song.setDuration(duration);
        }

        cursor.close();
        db.close();
        return song;
    }

    public void insertSong(Song song) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SONG_ID, song.getId());
        contentValues.put(COL_SONG_TITLE, song.getTitle());
        contentValues.put(COL_SONG_ALBUM_NAME, song.getAlbumTitle());
        contentValues.put(COL_SONG_ALBUM_COVER_PATH, song.getCover_big());
        contentValues.put(COL_SONG_DURATION, song.getDuration());
        sqLiteDatabase.insert(TABLE_FAVOURITE_SONGS, null, contentValues);
        sqLiteDatabase.close();
    }

    public void deleteSong(String songId) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_FAVOURITE_SONGS, COL_SONG_ID + " = ?",
                new String[]{songId});
        sqLiteDatabase.close();
    }

    public boolean isInserted(String songId) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_FAVOURITE_SONGS,
                new String[]{COL_SONG_ID},
                COL_SONG_ID + "=?", new String[]{songId},
                null, null, null, null);
        boolean inserted = cursor.moveToFirst();
        cursor.close();
        sqLiteDatabase.close();
        return inserted;
    }


}
