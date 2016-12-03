package com.javinindia.citymallsbusiness.apiparsing.shopmalllistparsing;

import android.util.Log;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish on 21-09-2016.
 */
public class ShopMallListResponseParsing  extends ApiBaseData {
    private ArrayList<MallDetails> mallDetailsArrayList;

    public ArrayList<MallDetails> getMallDetailsArrayList() {
        return mallDetailsArrayList;
    }

    public void setMallDetailsArrayList(ArrayList<MallDetails> mallDetailsArrayList) {
        this.mallDetailsArrayList = mallDetailsArrayList;
    }
    public void responseParseMethod(Object response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            setStatus(jsonObject.optString("status"));
            setMsg(jsonObject.optString("msg"));
            if (jsonObject.has("mallDetails") && jsonObject.optJSONArray("mallDetails")!=null)
                setMallDetailsArrayList(getMallListMethod(jsonObject.optJSONArray("mallDetails")));
            Log.d("Response", this.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<MallDetails> getMallListMethod(JSONArray mallDetails) {
        ArrayList<MallDetails> mallDetailses = new ArrayList<>();
        for (int i = 0; i < mallDetails.length(); i++) {
            MallDetails details = new MallDetails();
            JSONObject jsonObject = mallDetails.optJSONObject(i);
            if (jsonObject.has("id"))
                details.setId(jsonObject.optString("id"));
            if (jsonObject.has("mallName"))
                details.setMallName(jsonObject.optString("mallName"));
            if (jsonObject.has("description"))
                details.setDescription(jsonObject.optString("description"));
            if (jsonObject.has("mallAddress"))
                details.setMallAddress(jsonObject.optString("mallAddress"));
            if (jsonObject.has("mallLandmark"))
                details.setMallLandmark(jsonObject.optString("mallLandmark"));
            if (jsonObject.has("mallLat"))
                details.setMallLat(jsonObject.optString("mallLat"));
            if (jsonObject.has("mallLong"))
                details.setMallLong(jsonObject.optString("mallLong"));
            if (jsonObject.has("country"))
                details.setCountry(jsonObject.optString("country"));
            if (jsonObject.has("state"))
                details.setState(jsonObject.optString("state"));
            if (jsonObject.has("city"))
                details.setCity(jsonObject.optString("city"));
            if (jsonObject.has("pincode"))
                details.setPincode(jsonObject.optString("pincode"));
            if (jsonObject.has("rating"))
                details.setRating(jsonObject.optString("rating"));
            if (jsonObject.has("openTime"))
                details.setOpenTime(jsonObject.optString("openTime"));
            if (jsonObject.has("closeTime"))
                details.setCloseTime(jsonObject.optString("closeTime"));
            if (jsonObject.has("offerCount"))
                details.setOfferCount(jsonObject.optInt("offerCount"));
            if (jsonObject.has("distance"))
                details.setDistance(jsonObject.optString("distance"));

            mallDetailses.add(details);
        }
        return mallDetailses;
    }
}
