package com.javinindia.citymallsbusiness.apiparsing.productcategory;

import android.util.Log;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish on 03-10-2016.
 */
public class ShopCategoryListResponse extends ApiBaseData {

    ArrayList<ShopCategoryList> shopCategoryListArrayList;

    public ArrayList<ShopCategoryList> getShopCategoryListArrayList() {
        return shopCategoryListArrayList;
    }

    public void setShopCategoryListArrayList(ArrayList<ShopCategoryList> shopCategoryListArrayList) {
        this.shopCategoryListArrayList = shopCategoryListArrayList;
    }

    public void responseParseMethod(Object response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            setMsg(jsonObject.optString("msg"));
            setStatus(jsonObject.optString("status"));
            if (jsonObject.optString("status").equals("true") && jsonObject.has("shopCategoryList") && jsonObject.optJSONArray("shopCategoryList")!=null)
                setShopCategoryListArrayList(getshopCategoryDetailMethod(jsonObject.optJSONArray("shopCategoryList")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<ShopCategoryList> getshopCategoryDetailMethod(JSONArray shopCategoryList) {
        ArrayList<ShopCategoryList> wishDetailsArrayList = new ArrayList<>();
        for (int i = 0; i < shopCategoryList.length(); i++) {
            ShopCategoryList categoryList = new ShopCategoryList();
            JSONObject jsonObject = shopCategoryList.optJSONObject(i);
            if (jsonObject.has("id"))
                categoryList.setId(jsonObject.optString("id"));
            if (jsonObject.has("shopCatId"))
                categoryList.setShopCatId(jsonObject.optString("shopCatId"));
            if (jsonObject.has("shopCategory"))
                categoryList.setShopCategory(jsonObject.optString("shopCategory"));
            if (jsonObject.has("shopSubCatDetail") && jsonObject.optJSONArray("shopSubCatDetail")!=null)
                categoryList.setSubCatDetailArrayList(getshopSubCatDetailMethod(jsonObject.optJSONArray("shopSubCatDetail")));

            wishDetailsArrayList.add(categoryList);
            Log.d("Response 2", wishDetailsArrayList.toString());
        }
        return wishDetailsArrayList;
    }

    private ArrayList<ShopSubCatDetail> getshopSubCatDetailMethod(JSONArray shopSubCatDetail) {
        ArrayList<ShopSubCatDetail> shopSubCatDetails = new ArrayList<>();
        ShopSubCatDetail subCatDetail;
        for (int i = 0; i < shopSubCatDetail.length(); i++) {
            subCatDetail = new ShopSubCatDetail();
            JSONObject jsonObject = shopSubCatDetail.optJSONObject(i);
            if (jsonObject.has("shopSubCatId"))
                subCatDetail.setShopSubCatId(jsonObject.optString("shopSubCatId"));
            if (jsonObject.has("shoSubpCategory"))
                subCatDetail.setShoSubpCategory(jsonObject.optString("shoSubpCategory"));

            shopSubCatDetails.add(subCatDetail);
        }
        return shopSubCatDetails;
    }
}
