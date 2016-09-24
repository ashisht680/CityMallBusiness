package com.javinindia.citymalls.apiparsing.stateparsing;

import android.util.Log;

import com.javinindia.citymalls.apiparsing.base.ApiBaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/**
 * Created by Ashish on 21-06-2016.
 */
public class CityMasterParsing extends ApiBaseData {
    private CityCountryDetails countryDetails;

    public CityCountryDetails getCountryDetails() {
        return countryDetails;
    }

    public void setCountryDetails(CityCountryDetails countryDetails) {
        this.countryDetails = countryDetails;
    }

    public void responseParseMethod(Object response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            setMsg(jsonObject.optString("msg"));
            setStatus(jsonObject.optString("status"));
            if (jsonObject.optString("status").equals("true") &&  jsonObject.has("countryDetail"))
                setCountryDetails(getCountryDetailsMethod(jsonObject.optJSONObject("countryDetail")));

            Log.d("Response", this.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private CityCountryDetails getCountryDetailsMethod(JSONObject countryDetailJsonObject) {
        CityCountryDetails countryDetails = new CityCountryDetails();
        if (countryDetailJsonObject.has("country"))
            countryDetails.setCountry(countryDetailJsonObject.optString("country"));
        if (countryDetailJsonObject.has("state"))
            countryDetails.setCountry(countryDetailJsonObject.optString("state"));
        if (countryDetailJsonObject.has("cityDetail"))
            countryDetails.setCityDetails(getCityArrayListMethod(countryDetailJsonObject.optJSONArray("cityDetail")));

        return countryDetails;
    }
    private ArrayList<CityDetails> getCityArrayListMethod(JSONArray jsonArray) {
        ArrayList<CityDetails> cityDetailsArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
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
