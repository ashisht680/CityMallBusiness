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
            detail.setId(jsonObject.optString("id"));
            detail.setOfferCatId(jsonObject.optString("offerCatId"));
            detail.setOfferSubCatId(jsonObject.optString("offerSubCatId"));
            detail.setName(jsonObject.optString("name"));
            detail.setBrandLogo(jsonObject.optString("brandLogo"));

          /*  "id":"1",
                    "offerCatId":"1",
                    "offerSubCatId":"1",
                    "name":"Precor",
                    "brandLogo":*/
            brandDetails.add(detail);
        }
        return brandDetails;
    }
}
