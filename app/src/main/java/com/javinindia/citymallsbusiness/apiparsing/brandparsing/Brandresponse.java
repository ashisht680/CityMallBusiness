package com.javinindia.citymallsbusiness.apiparsing.brandparsing;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;
import com.javinindia.citymallsbusiness.apiparsing.offerCategoryParsing.OfferCategoryDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish on 03-10-2016.
 */
public class Brandresponse extends ApiBaseData {

    ArrayList<BrandDetail> brandDetailArrayList;

    public ArrayList<BrandDetail> getBrandDetailArrayList() {
        return brandDetailArrayList;
    }

    public void setBrandDetailArrayList(ArrayList<BrandDetail> brandDetailArrayList) {
        this.brandDetailArrayList = brandDetailArrayList;
    }

    public void responseImplement(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            setStatus(jsonObject.optString("status"));
            setMsg(jsonObject.optString("msg"));
            if (jsonObject.has("brandDetail") && jsonObject.optJSONArray("brandDetail") != null)
                setBrandDetailArrayList(brandList(jsonObject.optJSONArray("brandDetail")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<BrandDetail> brandList(JSONArray brandDetail) {
        ArrayList<BrandDetail> brandDetails = new ArrayList<>();
        for (int i = 0; i < brandDetail.length(); i++) {
            BrandDetail detail = new BrandDetail();
            JSONObject jsonObject = brandDetail.optJSONObject(i);
            if (jsonObject.has("id"))
                detail.setId(jsonObject.optString("id"));
            if (jsonObject.has("offerCatId"))
                detail.setOfferCatId(jsonObject.optString("offerCatId"));
            if (jsonObject.has("offerSubCatId"))
                detail.setOfferSubCatId(jsonObject.optString("offerSubCatId"));
            if (jsonObject.has("name"))
                detail.setName(jsonObject.optString("name"));
            if (jsonObject.has("brandLogo"))
                detail.setBrandLogo(jsonObject.optString("brandLogo"));

            brandDetails.add(detail);
        }
        return brandDetails;
    }
}
