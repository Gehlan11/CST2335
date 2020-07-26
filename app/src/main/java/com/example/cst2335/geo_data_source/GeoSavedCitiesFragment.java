package com.example.cst2335.geo_data_source;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cst2335.R;

import java.util.ArrayList;

public class GeoSavedCitiesFragment extends Fragment {

    /**
     * To show list of city records
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
     * Database helper class object
     * */
    CityDatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.saved_cities_fragment, container, false);
        databaseHelper = new CityDatabaseHelper(getActivity());
        citiesList = layout.findViewById(R.id.list_favourite_cities);

        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GeoCityDetailsActivity.class);
                intent.putExtra("CityData", cityDataList.get(position));
                startActivity(intent);
            }
        });
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        //get all the favourite cities from the database
        cityDataList = databaseHelper.getAllFavouriteCities();
        citySearchListAdapter = new CitySearchListAdapter(getActivity(), cityDataList);
        citiesList.setAdapter(citySearchListAdapter);
    }
}
