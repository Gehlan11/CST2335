package com.example.cst2335.geo_data_source;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cst2335.R;

public class GeoCityDetailsActivity extends AppCompatActivity {

    TextView cityNameText, cityCountryText, cityRegionText,
            cityCurrencyName, cityCurrencySymbolText,
            cityCurrencyCode, cityLatitudeText, cityLongitudeText,
            cityDistance;
    Button googleMapButton, saveButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_city_details);

        cityNameText = findViewById(R.id.city_name);
        cityCountryText = findViewById(R.id.city_country_name);
        cityRegionText = findViewById(R.id.city_region_name);
        cityCurrencyName = findViewById(R.id.city_currency_name);
        cityCurrencySymbolText = findViewById(R.id.city_currency_symbol);
        cityCurrencyCode = findViewById(R.id.city_currency_code);
        cityLatitudeText = findViewById(R.id.city_latitude);
        cityLongitudeText = findViewById(R.id.city_longitude);
        cityDistance  = findViewById(R.id.city_distance);
        googleMapButton = findViewById(R.id.google_map_button);
        saveButton = findViewById(R.id.save_city_button);
        deleteButton = findViewById(R.id.delete_city_button);

        googleMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_geo_toolbar, menu);
        return true;
    }
}
