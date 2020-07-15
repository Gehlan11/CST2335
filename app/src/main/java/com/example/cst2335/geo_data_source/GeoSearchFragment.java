package com.example.cst2335.geo_data_source;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335.R;

import java.util.ArrayList;

public class GeoSearchFragment extends Fragment {

    //https://api.geodatasource.com/city?key=XXXX&lat=YYYY&lon=ZZZZ&format=JSON
    //2FXW1A0VOV9FKATVQHF06JAEANRMOA0L

    EditText inputLat, inputLon;
    Button searchButton;
    ListView citiesList;
    ArrayList<CityData> cityData;
    CitySearchListAdapter citySearchListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.search_city_fragment, container, false);
        inputLat = layout.findViewById(R.id.city_lat_edittext);
        inputLon = layout.findViewById(R.id.city_long_edittext);
        searchButton = layout.findViewById(R.id.city_search_button);
        citiesList = layout.findViewById(R.id.cities_list);

        cityData = new ArrayList<>();
        citySearchListAdapter = new CitySearchListAdapter(getActivity(), cityData);
        citiesList.setAdapter(citySearchListAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GeoCityDetailsActivity.class);
                intent.putExtra("CityData", cityData.get(position));
                startActivity(intent);
            }
        });

        return layout;
    }

    class SearchNearByCities extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
