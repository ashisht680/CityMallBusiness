package com.javinindia.citymallsbusiness.apiparsing.stateparsing;

import java.util.ArrayList;

/**
 * Created by Ashish on 21-06-2016.
 */
public class CityCountryDetails {
    String country;
    String state;
    ArrayList<CityDetails> cityDetails;

    public ArrayList<CityDetails> getCityDetails() {
        return cityDetails;
    }

    public void setCityDetails(ArrayList<CityDetails> cityDetails) {
        this.cityDetails = cityDetails;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
