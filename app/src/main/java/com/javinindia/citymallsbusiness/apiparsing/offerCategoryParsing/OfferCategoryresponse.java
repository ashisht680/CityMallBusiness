package com.javinindia.citymallsbusiness.apiparsing.offerCategoryParsing;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ProductCategoryDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish on 03-10-2016.
 */
public class OfferCategoryresponse extends ApiBaseData {

    ArrayList<OfferCategoryDetail> setOfferCategoryDetailsArrayList;

    public ArrayList<OfferCategoryDetail> getSetShopCategoryDetailsArrayList() {
        return setOfferCategoryDetailsArrayList;
    }

    public void setSetShopCategoryDetailsArrayList(ArrayList<OfferCategoryDetail> setShopCategoryDetailsArrayList) {
        this.setOfferCategoryDetailsArrayList = setShopCategoryDetailsArrayList;
    }

    public void responseImplement(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            setStatus(jsonObject.optString("status"));
            setMsg(jsonObject.optString("msg"));
            setSetShopCategoryDetailsArrayList(offerCategoryList(jsonObject.optJSONArray("offerCategoryDetail")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<OfferCategoryDetail> offerCategoryList(JSONArray offerCategoryDetail) {
        ArrayList<OfferCategoryDetail> offerCategoryDetails = new ArrayList<>();
        for (int i = 0; i < offerCategoryDetail.length(); i++) {
            OfferCategoryDetail detail = new OfferCategoryDetail();
            JSONObject jsonObject = offerCategoryDetail.optJSONObject(i);
            detail.setId(jsonObject.optString("id"));
            detail.setOfferCategory(jsonObject.optString("offerCategory"));
            offerCategoryDetails.add(detail);
        }
        return offerCategoryDetails;
    }


}
