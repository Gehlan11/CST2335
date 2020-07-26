package com.example.cst2335.geo_data_source;

import java.io.Serializable;

/**
 * class contains all the fields in single entity
 */
public class CityData implements Serializable {

    /**
     * Country of a city
     */
    private String country = "";
    /**
     * region of a city
     */
    private String region = "";
    /**
     * name of a city
     */
    private String city = "";
    /**
     * latitude of a city
     */
    private String latitude = "";
    /**
     * longitude of a city
     */
    private String longitude = "";
    /**
     * international currency code of a country
     */
    private String currency_code = "";
    /**
     * international currency name of a country
     */
    private String currency_name = "";
    /**
     * currency symbol of a country
     */
    private String currency_symbol = "";
    /**
     * sunrise time
     */
    private String sunrise = "";
    /**
     * sunset time
     */
    private String sunset = "";
    private String time_zone = "";
    /**
     * distance from entered latitude and longitude
     */
    private String distance_km = "";

    public CityData() {
    }

    public CityData(String country, String region, String city, String latitude, String longitude, String currency_code, String currency_name, String currency_symbol, String sunrise, String sunset, String time_zone, String distance_km) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currency_code = currency_code;
        this.currency_name = currency_name;
        this.currency_symbol = currency_symbol;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.time_zone = time_zone;
        this.distance_km = distance_km;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getDistance_km() {
        return distance_km;
    }

    public void setDistance_km(String distance_km) {
        this.distance_km = distance_km;
    }
}
