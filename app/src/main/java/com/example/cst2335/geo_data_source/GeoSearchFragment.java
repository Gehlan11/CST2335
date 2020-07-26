package com.example.cst2335.geo_data_source;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GeoSearchFragment extends Fragment {

    /**
     * To take latitude and longitude from the user
     * */
    EditText inputLat, inputLon;
    /**
     * To call the API
     * */
    Button searchButton;
    ProgressBar progressBar;
    /**
     * To show the list of retrieved cities
     * */
    ListView citiesList;
    /**
     * To pass the list of data items to adapter
     * */
    ArrayList<CityData> cityDataList;
    /**
     * to show list items into the listview
     * */
    CitySearchListAdapter citySearchListAdapter;

    /**
     * to save searched data
     * */
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.search_city_fragment, container, false);
        sharedPreferences = getActivity().getSharedPreferences("city_search", Context.MODE_PRIVATE);
        inputLat = layout.findViewById(R.id.city_lat_edittext);
        inputLon = layout.findViewById(R.id.city_long_edittext);
        searchButton = layout.findViewById(R.id.city_search_button);
        progressBar = layout.findViewById(R.id.progressBar);
        citiesList = layout.findViewById(R.id.cities_list);

        cityDataList = new ArrayList<>();
        citySearchListAdapter = new CitySearchListAdapter(getActivity(), cityDataList);
        citiesList.setAdapter(citySearchListAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = inputLat.getText().toString();
                String longitude = inputLon.getText().toString();
                if (latitude.length() > 0 && longitude.length() > 0) {
                    new SearchNearByCities(latitude, longitude).execute();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.geo_toast_enter_lat_lng), Toast.LENGTH_SHORT).show();
                }
            }
        });

        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GeoCityDetailsActivity.class);
                intent.putExtra("CityData", cityDataList.get(position));
                startActivity(intent);
            }
        });

        String lat = sharedPreferences.getString("lat", "");
        String lng = sharedPreferences.getString("lng", "");
        if (lat.length() > 0 && lng.length() > 0) {
            inputLat.setText(lat);
            inputLon.setText(lng);
            new SearchNearByCities(lat, lng).execute();
        }

        return layout;
    }

    /**
     * AsyncTask child class to call API for get nearby cities list
     * using HttpUrlConnection.
     */
    class SearchNearByCities extends AsyncTask<Void, Void, String> {

        String apiUrl = "https://api.geodatasource.com/cities" +
                "?key=2FXW1A0VOV9FKATVQHF06JAEANRMOA0L" +
                "&format=json&";

        SearchNearByCities(String lat, String lng) {
            //completing the URL with entered lat and long from user
            apiUrl += "lat=" + lat + "&lng=" + lng;
            //save the lat long data to shared preferences for showing next time
            sharedPreferences.edit()
                    .putString("lat", lat)
                    .putString("lng", lng).apply();
        }

        @Override
        protected void onPreExecute() {
            //show progressbar
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            StringBuilder output = new StringBuilder();
            try {
                //url
                url = new URL(apiUrl);
                //open connection to provided URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //get the input stream to read the HTTP response
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                //read the data from input stream
                String s;
                while ((s = br.readLine()) != null) {
                    output.append(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hide the progressbar
            progressBar.setVisibility(View.GONE);
            if (s.length() > 0) {
                try {
                    //parse json data only if it's a valid JSON
                    JSONArray jsonArray = new JSONArray(s);
                    cityDataList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //parsing JSON
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String country = jsonObject.getString("country");
                        String region = jsonObject.getString("region");
                        String city = jsonObject.getString("city");
                        String latitude = jsonObject.getString("latitude");
                        String longitude = jsonObject.getString("longitude");
                        String currency_code = jsonObject.getString("currency_code");
                        String currency_name = jsonObject.getString("currency_name");
                        String currency_symbol = jsonObject.getString("currency_symbol");
                        String sunrise = jsonObject.getString("sunrise");
                        String sunset = jsonObject.getString("sunset");
                        String time_zone = jsonObject.getString("time_zone");
                        String distance_km = jsonObject.getString("distance_km");

                        CityData cityData = new CityData();
                        cityData.setCountry(country);
                        cityData.setRegion(region);
                        cityData.setCity(city);
                        cityData.setLatitude(latitude);
                        cityData.setLongitude(longitude);
                        cityData.setCurrency_code(currency_code);
                        cityData.setCurrency_name(currency_name);
                        cityData.setCurrency_symbol(currency_symbol);
                        cityData.setSunrise(sunrise);
                        cityData.setSunset(sunset);
                        cityData.setTime_zone(time_zone);
                        cityData.setDistance_km(distance_km);
                        //add city data to list item arrayList
                        cityDataList.add(cityData);
                    }
                    citySearchListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
