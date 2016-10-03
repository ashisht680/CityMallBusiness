package com.javinindia.citymallsbusiness.apiparsing.productcategory;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish on 01-10-2016.
 */
public class ProductCategory extends ApiBaseData {

    ArrayList<ProductCategoryDetails> shopCategoryDetailsArrayList;

    public ArrayList<ProductCategoryDetails> getShopCategoryDetailsArrayList() {
        return shopCategoryDetailsArrayList;
    }

    public void setShopCategoryDetailsArrayList(ArrayList<ProductCategoryDetails> shopCategoryDetailsArrayList) {
        this.shopCategoryDetailsArrayList = shopCategoryDetailsArrayList;
    }


    public void responseImplement(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            setStatus(jsonObject.optString("status"));
            setMsg(jsonObject.optString("msg"));
            setShopCategoryDetailsArrayList(productCategoryList(jsonObject.optJSONArray("shopCategoryDetail")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<ProductCategoryDetails> productCategoryList(JSONArray shopCategoryDetailJsonArray) {
        ArrayList<ProductCategoryDetails> shopCategoryDetailsArrayList = new ArrayList<>();
        for (int i = 0; i < shopCategoryDetailJsonArray.length(); i++) {
            ProductCategoryDetails shopCategoryDetails = new ProductCategoryDetails();
            JSONObject jsonObject = shopCategoryDetailJsonArray.optJSONObject(i);
            shopCategoryDetails.setId(jsonObject.optString("id"));
            shopCategoryDetails.setShopCategory(jsonObject.optString("shopCategory"));
            shopCategoryDetailsArrayList.add(shopCategoryDetails);
        }
        return shopCategoryDetailsArrayList;
    }
}
