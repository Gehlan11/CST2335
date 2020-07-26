package com.example.cst2335.geo_data_source;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.cst2335.R;
import com.example.cst2335.deezer.DeezerMainActivity;
import com.example.cst2335.lyrics_ovh.LyricsMainActivity;
import com.example.cst2335.soccer.SoccerMatchActivity;
import com.google.android.material.snackbar.Snackbar;

public class GeoCityDetailsActivity extends AppCompatActivity {

    /**
     * Toolbar to show menu
     */
    Toolbar toolbar;
    /***
     * To show Snackbar
     * */
    CoordinatorLayout coordinatorLayout;
    TextView cityNameText, cityCountryText, cityRegionText,
            cityCurrencyName, cityCurrencySymbolText,
            cityCurrencyCode, cityLatitudeText, cityLongitudeText,
            cityDistance;
    Button googleMapButton, saveButton, deleteButton;

    /**
     * Showing City details
     */
    String latitude = "0.0", longitude = "0.0", cityName = "";
    CityData cityData;
    /**
     * Database helper class
     * */
    CityDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_city_details);
        databaseHelper = new CityDatabaseHelper(this);
        cityData = (CityData) getIntent().getSerializableExtra("CityData");
        if (cityData != null) {
            cityName = cityData.getCity();
            latitude = cityData.getLatitude();
            longitude = cityData.getLongitude();
        }
        toolbar = findViewById(R.id.geo_main_toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        cityNameText = findViewById(R.id.city_name);
        cityCountryText = findViewById(R.id.city_country_name);
        cityRegionText = findViewById(R.id.city_region_name);
        cityCurrencyName = findViewById(R.id.city_currency_name);
        cityCurrencySymbolText = findViewById(R.id.city_currency_symbol);
        cityCurrencyCode = findViewById(R.id.city_currency_code);
        cityLatitudeText = findViewById(R.id.city_latitude);
        cityLongitudeText = findViewById(R.id.city_longitude);
        cityDistance = findViewById(R.id.city_distance);
        googleMapButton = findViewById(R.id.google_map_button);
        saveButton = findViewById(R.id.save_city_button);
        deleteButton = findViewById(R.id.delete_city_button);

        cityNameText.setText(cityName);
        cityCountryText.setText(cityData.getCountry());
        cityRegionText.setText(cityData.getRegion());
        cityCurrencyName.setText(cityData.getCurrency_name());
        cityCurrencySymbolText.setText(cityData.getCurrency_symbol());
        cityCurrencyCode.setText(cityData.getCurrency_code());
        cityLatitudeText.setText(latitude);
        cityLongitudeText.setText(longitude);
        cityDistance.setText(cityData.getDistance_km());

        googleMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open google maps with pin
                //reference taken from: https://developers.google.com/maps/documentation/urls/android-intents
                String cityName = cityData.getCity().replace(" ", "-");
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + latitude + "," + longitude + "(" + cityName + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the details of the city to database
                databaseHelper.addToFavouriteCities(cityData);
                saveButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);
                Snackbar.make(coordinatorLayout,
                        getString(R.string.geo_snackbar_added),
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete the details of the city from database
                databaseHelper.deleteFavouriteCity(cityName);
                saveButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.GONE);
                Snackbar.make(coordinatorLayout,
                        getString(R.string.geo_snackbar_deleted),
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        String dbCity = databaseHelper.getCity(cityName).getCity();
        if (dbCity.equals(cityName)) {
            saveButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            saveButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_geo_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.soccer_app:
                Intent soccerIntent = new Intent(GeoCityDetailsActivity.this, SoccerMatchActivity.class);
                startActivity(soccerIntent);
                return true;
            case R.id.lyrics_search_app:
                Intent lyricsIntent = new Intent(GeoCityDetailsActivity.this, LyricsMainActivity.class);
                startActivity(lyricsIntent);
                return true;
            case R.id.deezer_app:
                Intent deezerIntent = new Intent(GeoCityDetailsActivity.this, DeezerMainActivity.class);
                startActivity(deezerIntent);
                return true;
            case R.id.geo_help:
                //showing alert dialog to show help instruction to user
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.geo_menu_item_help));
                builder.setMessage(getString(R.string.geo_help_details));
                builder.setNeutralButton(getString(R.string.geo_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
