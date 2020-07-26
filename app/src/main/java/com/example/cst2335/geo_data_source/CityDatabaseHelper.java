package com.example.cst2335.geo_data_source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * a helper class to interact with the SQLiteDatabase
 */
public class CityDatabaseHelper {

    DBOpenHelper dbOpenHelper;

    /**
     * Initializes the sqlite open helper class
     *
     * @param context Context
     */
    public CityDatabaseHelper(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }

    /**
     * adds the city details to favourite table
     *
     * @param cityData Object containing all the information of a city
     */
    void addToFavouriteCities(CityData cityData) {
        ContentValues cv = new ContentValues();
        cv.put("name", cityData.getCity());
        cv.put("country", cityData.getCountry());
        cv.put("region", cityData.getRegion());
        cv.put("latitude", cityData.getLatitude());
        cv.put("longitude", cityData.getLongitude());
        cv.put("currency_code", cityData.getCurrency_code());
        cv.put("currency_name", cityData.getCurrency_name());
        cv.put("currency_symbol", cityData.getCurrency_symbol());
        cv.put("sunrise", cityData.getSunrise());
        cv.put("sunset", cityData.getSunset());
        cv.put("time_zone", cityData.getTime_zone());
        cv.put("distance_km", cityData.getDistance_km());
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        database.insert("geo_cities", null, cv);
        database.close();
    }

    /**
     * returns all the cities saved in favourite table: geo_cities
     *
     * @return all the cities exists in the geo_cities table
     */
    ArrayList<CityData> getAllFavouriteCities() {
        ArrayList<CityData> cityDataList = new ArrayList<>();
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = database.query("geo_cities",
                new String[]{"name", "country", "region", "latitude", "longitude",
                        "currency_code", "currency_name", "currency_symbol",
                        "sunrise", "sunset", "time_zone", "distance_km",
                },
                null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String country = cursor.getString(1);
                String region = cursor.getString(2);
                String latitude = cursor.getString(3);
                String longitude = cursor.getString(4);
                String currency_code = cursor.getString(5);
                String currency_name = cursor.getString(6);
                String currency_symbol = cursor.getString(7);
                String sunrise = cursor.getString(8);
                String sunset = cursor.getString(9);
                String time_zone = cursor.getString(10);
                String distance_km = cursor.getString(11);
                CityData cityData = new CityData();
                cityData.setCity(name);
                cityData.setCountry(country);
                cityData.setRegion(region);
                cityData.setLatitude(latitude);
                cityData.setLongitude(longitude);
                cityData.setCurrency_code(currency_code);
                cityData.setCurrency_symbol(currency_symbol);
                cityData.setCurrency_name(currency_name);
                cityData.setSunrise(sunrise);
                cityData.setSunset(sunset);
                cityData.setTime_zone(time_zone);
                cityData.setDistance_km(distance_km);
                cityDataList.add(cityData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return cityDataList;
    }

    /**
     * @param cityName name of the city to retrieve
     * @return a City with provided city name
     */
    CityData getCity(String cityName) {
        CityData cityData = new CityData();
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = database.query("geo_cities",
                new String[]{"name", "country", "region", "latitude", "longitude",
                        "currency_code", "currency_name", "currency_symbol",
                        "sunrise", "sunset", "time_zone", "distance_km",
                },
                "name=?", new String[]{cityName},
                null, null, null, null);

        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String country = cursor.getString(1);
            String region = cursor.getString(2);
            String latitude = cursor.getString(3);
            String longitude = cursor.getString(4);
            String currency_code = cursor.getString(5);
            String currency_name = cursor.getString(6);
            String currency_symbol = cursor.getString(7);
            String sunrise = cursor.getString(8);
            String sunset = cursor.getString(9);
            String time_zone = cursor.getString(10);
            String distance_km = cursor.getString(11);
            cityData.setCity(name);
            cityData.setCountry(country);
            cityData.setRegion(region);
            cityData.setLatitude(latitude);
            cityData.setLongitude(longitude);
            cityData.setCurrency_code(currency_code);
            cityData.setCurrency_symbol(currency_symbol);
            cityData.setCurrency_name(currency_name);
            cityData.setSunrise(sunrise);
            cityData.setSunset(sunset);
            cityData.setTime_zone(time_zone);
            cityData.setDistance_km(distance_km);
        }
        cursor.close();
        database.close();
        return cityData;
    }

    /**
     * removes the city details from the geo_cities table
     *
     * @param cityName name of the city to remove
     */
    void deleteFavouriteCity(String cityName) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        database.delete("geo_cities", "name=?", new String[]{cityName});
        database.close();
    }

    /**
     * Database open class helper
     */
    class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context) {
            super(context, "geo_db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE geo_cities ("
                    + " name text, "
                    + " country text, "
                    + " region text, "
                    + " latitude text, "
                    + " longitude text, "
                    + " currency_code text, "
                    + " currency_name text, "
                    + " currency_symbol text, "
                    + " sunrise text, "
                    + " sunset text, "
                    + " time_zone text, "
                    + " distance_km text "
                    + ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE geo_cities");
            onCreate(db);
        }
    }
}
