package com.javinindia.citymallsbusiness.apiparsing.stateparsing;

import android.util.Log;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CountryMasterApiParsing extends ApiBaseData {

    private CountryDetails countryDetails;

    public CountryDetails getCountryDetails() {
        return countryDetails;
    }

    public void setCountryDetails(CountryDetails countryDetails) {
        this.countryDetails = countryDetails;
    }

    public void responseParseMethod(Object response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            setMsg(jsonObject.optString("msg"));
            setStatus(jsonObject.optString("status"));
            if (jsonObject.optString("status").equals("true") && jsonObject.has("countryDetail") && jsonObject.optJSONObject("countryDetail") != null)
                setCountryDetails(getCountryDetailsMethod(jsonObject.optJSONObject("countryDetail")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private CountryDetails getCountryDetailsMethod(JSONObject countryDetailJsonObject) {
        CountryDetails countryDetails = new CountryDetails();
        if (countryDetailJsonObject.has("country"))
            countryDetails.setCountry(countryDetailJsonObject.optString("country"));
        if (countryDetailJsonObject.has("stateDetail") && countryDetailJsonObject.optJSONArray("stateDetail") != null)
            countryDetails.setStateDetailsArrayList(getStateArrayListMethod(countryDetailJsonObject.optJSONArray("stateDetail")));

        return countryDetails;
    }

    private ArrayList<StateDetails> getStateArrayListMethod(JSONArray jsonArray) {
        ArrayList<StateDetails> stateDetailsArrayList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            StateDetails stateDetails = new StateDetails();
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            if (jsonObject.has("state"))
                stateDetails.setState(jsonObject.optString("state"));
            if (jsonObject.has("cityDetail") && jsonObject.optJSONArray("cityDetail")!=null)
                stateDetails.setCityDetailsArrayList(getCityArrayListMethod(jsonObject.optJSONArray("cityDetail")));

            stateDetailsArrayList.add(stateDetails);
        }
        return stateDetailsArrayList;
    }

    private ArrayList<CityDetails> getCityArrayListMethod(JSONArray jsonArray) {
        ArrayList<CityDetails> cityDetailsArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            CityDetails cityDetails = new CityDetails();
            JSONObject jsonObject = jsonArray.optJSONObject(i);
            if (jsonObject.has("id"))
                cityDetails.setId(jsonObject.optString("id"));
            if (jsonObject.has("city"))
                cityDetails.setCity(jsonObject.optString("city"));

            cityDetailsArrayList.add(cityDetails);
        }
        return cityDetailsArrayList;
    }
}
