package com.example.cst2335.geo_data_source;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cst2335.R;

import java.util.ArrayList;

public class CitySearchListAdapter extends BaseAdapter {

    private ArrayList<CityData> cityDataList;
    private static LayoutInflater inflater;

    /**
     * @param context context
     * @param cityDataList List containing CityData objects
     * */
    public CitySearchListAdapter(Context context, ArrayList<CityData> cityDataList){
        this.cityDataList = cityDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cityDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_near_by_city_list_item, null);
        }
        TextView textCityName = convertView.findViewById(R.id.city_name);
        TextView textCityRegion = convertView.findViewById(R.id.city_region);
        TextView textCityDistance = convertView.findViewById(R.id.city_distance);

        CityData cityData = (CityData) getItem(position);
        String cityName = cityData.getCity();
        String distance = cityData.getDistance_km();
        String region = cityData.getRegion();


        textCityName.setText(cityName);
        textCityRegion.setText(region);
        textCityDistance.setText(distance);
        return convertView;
    }
}
