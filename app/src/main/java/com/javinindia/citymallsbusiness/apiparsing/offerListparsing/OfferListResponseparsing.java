package com.javinindia.citymallsbusiness.apiparsing.offerListparsing;

import android.util.Log;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish on 22-10-2016.
 */
public class OfferListResponseparsing extends ApiBaseData {
    private ArrayList<DetailsList> detailsListArrayList;

    public ArrayList<DetailsList> getDetailsListArrayList() {
        return detailsListArrayList;
    }

    public void setDetailsListArrayList(ArrayList<DetailsList> detailsListArrayList) {
        this.detailsListArrayList = detailsListArrayList;
    }

    public void responseParseMethod(Object response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            setMsg(jsonObject.optString("msg"));
            setStatus(jsonObject.optString("status"));
            if (jsonObject.optString("status").equals("true") && jsonObject.has("Details") && jsonObject.optJSONArray("Details") != null)
                setDetailsListArrayList(getDetailInMethod(jsonObject.optJSONArray("Details")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<DetailsList> getDetailInMethod(JSONArray details) {
        ArrayList<DetailsList> wishDetailsArrayList = new ArrayList<>();
        for (int i = 0; i < details.length(); i++) {
            DetailsList wishDetails = new DetailsList();
            JSONObject jsonObject = details.optJSONObject(i);
            if (jsonObject.has("favCount"))
                wishDetails.setFavCount(jsonObject.optString("favCount"));
            if (jsonObject.has("offerViewCount"))
                wishDetails.setOfferViewCount(jsonObject.optString("offerViewCount"));
            if (jsonObject.has("offerDeatils") && jsonObject.optJSONObject("offerDeatils") != null)
                wishDetails.setOfferDetails(getOfferDeatilsMethod(jsonObject.optJSONObject("offerDeatils")));
            if (jsonObject.has("mallDetails") && jsonObject.optJSONObject("mallDetails") != null)
                wishDetails.setOfferMallDetails(getMallDetailsMethod(jsonObject.optJSONObject("mallDetails")));
            if (jsonObject.has("shopDetails") && jsonObject.optJSONObject("shopDetails") != null)
                wishDetails.setOfferShopDetails(getShopDetailsMethod(jsonObject.optJSONObject("shopDetails")));
            if (jsonObject.has("BrandDetails") && jsonObject.optJSONObject("BrandDetails") != null)
                wishDetails.setOfferBrandDetails(getBrandDetailsMethod(jsonObject.optJSONObject("BrandDetails")));

            wishDetailsArrayList.add(wishDetails);
            Log.d("Response", wishDetailsArrayList.toString());
        }
        return wishDetailsArrayList;
    }

    private OfferBrandDetails getBrandDetailsMethod(JSONObject jsonObject) {
        OfferBrandDetails offerBrandDetails = new OfferBrandDetails();
        if (jsonObject.has("id"))
            offerBrandDetails.setBrandId(jsonObject.optString("id"));
        if (jsonObject.has("offerCategory"))
            offerBrandDetails.setBrandOfferCategory(jsonObject.optString("offerCategory"));
        if (jsonObject.has("offerSubCategory"))
            offerBrandDetails.setBrandOfferSubCategory(jsonObject.optString("offerSubCategory"));
        if (jsonObject.has("name"))
            offerBrandDetails.setBrandName(jsonObject.optString("name"));
        if (jsonObject.has("brandlogo"))
            offerBrandDetails.setBrandLogo(jsonObject.optString("brandlogo"));
        if (jsonObject.has("status"))
            offerBrandDetails.setBrandStatus(jsonObject.optString("status"));
        if (jsonObject.has("insertDate"))
            offerBrandDetails.setBrandInsertDate(jsonObject.optString("insertDate"));
        if (jsonObject.has("updateDate"))
            offerBrandDetails.setBrandUpdateDate(jsonObject.optString("updateDate"));
        return offerBrandDetails;
    }

    private OfferShopDetails getShopDetailsMethod(JSONObject jsonObject) {
        OfferShopDetails offerShopDetails = new OfferShopDetails();
        if (jsonObject.has("shopId"))
            offerShopDetails.setShopId(jsonObject.optString("shopId"));
        if (jsonObject.has("shopName"))
            offerShopDetails.setShopName(jsonObject.optString("shopName"));
        if (jsonObject.has("ownerName"))
            offerShopDetails.setShopOwnerName(jsonObject.optString("ownerName"));
        if (jsonObject.has("shopMobile"))
            offerShopDetails.setShopMobile(jsonObject.optString("shopMobile"));
        if (jsonObject.has("state"))
            offerShopDetails.setShopState(jsonObject.optString("state"));
        if (jsonObject.has("city"))
            offerShopDetails.setShopCity(jsonObject.optString("city"));
        if (jsonObject.has("address"))
            offerShopDetails.setShopAddress(jsonObject.optString("address"));
        if (jsonObject.has("shopPincode"))
            offerShopDetails.setShopPincode(jsonObject.optString("shopPincode"));
        if (jsonObject.has("shopNo"))
            offerShopDetails.setShopNo(jsonObject.optString("shopNo"));
        if (jsonObject.has("floorNo"))
            offerShopDetails.setShopFloorNo(jsonObject.optString("floorNo"));
        if (jsonObject.has("updateDate"))
            offerShopDetails.setShopupdateDate(jsonObject.optString("updateDate"));
        if (jsonObject.has("shopOpenTime"))
            offerShopDetails.setShopOpenTime(jsonObject.optString("shopOpenTime"));
        if (jsonObject.has("shopClosetime"))
            offerShopDetails.setShopCloseTime(jsonObject.optString("shopClosetime"));
        if (jsonObject.has("description"))
            offerShopDetails.setShopDescription(jsonObject.optString("description"));
        if (jsonObject.has("shopProfilePic"))
            offerShopDetails.setShopProfilePic(jsonObject.optString("shopProfilePic"));
        if (jsonObject.has("shopBanner"))
            offerShopDetails.setShopBanner(jsonObject.optString("shopBanner"));
        if (jsonObject.has("shopCategory"))
            offerShopDetails.setShopCategory(jsonObject.optString("shopCategory"));
        if (jsonObject.has("shopSubCategory"))
            offerShopDetails.setShopSubCategory(jsonObject.optString("shopSubCategory"));
        return offerShopDetails;
    }

    private OfferMallDetails getMallDetailsMethod(JSONObject jsonObject) {
        OfferMallDetails offerMallDetails = new OfferMallDetails();
        if (jsonObject.has("id"))
            offerMallDetails.setMallid(jsonObject.optString("id"));
        if (jsonObject.has("mallName"))
            offerMallDetails.setMallName(jsonObject.optString("mallName"));
        if (jsonObject.has("description"))
            offerMallDetails.setMallDescription(jsonObject.optString("description"));
        if (jsonObject.has("mallAddress"))
            offerMallDetails.setMallAddress(jsonObject.optString("mallAddress"));
        if (jsonObject.has("mallLandmark"))
            offerMallDetails.setMallLandmark(jsonObject.optString("mallLandmark"));
        if (jsonObject.has("country"))
            offerMallDetails.setMallCountry(jsonObject.optString("country"));
        if (jsonObject.has("state"))
            offerMallDetails.setMallState(jsonObject.optString("state"));
        if (jsonObject.has("city"))
            offerMallDetails.setMallCity(jsonObject.optString("city"));
        if (jsonObject.has("pincode"))
            offerMallDetails.setMallPincode(jsonObject.optString("pincode"));
        if (jsonObject.has("rating"))
            offerMallDetails.setMallRating(jsonObject.optString("rating"));
        if (jsonObject.has("openTime"))
            offerMallDetails.setMallOpenTime(jsonObject.optString("openTime"));
        if (jsonObject.has("closeTime"))
            offerMallDetails.setMallCloseTime(jsonObject.optString("closeTime"));
        if (jsonObject.has("distance"))
            offerMallDetails.setMallDistance(jsonObject.optString("distance"));
        if (jsonObject.has("mallLat"))
            offerMallDetails.setMallLat(jsonObject.optString("mallLat"));
        if (jsonObject.has("mallLong"))
            offerMallDetails.setMallLong(jsonObject.optString("mallLong"));
        if (jsonObject.has("insertDate"))
            offerMallDetails.setMallInsertDate(jsonObject.optString("insertDate"));
        if (jsonObject.has("updateDate"))
            offerMallDetails.setMallUpdateDate(jsonObject.optString("updateDate"));
        if (jsonObject.has("status"))
            offerMallDetails.setMallStatus(jsonObject.optString("status"));
        return offerMallDetails;
    }

    private OfferDetails getOfferDeatilsMethod(JSONObject jsonObject) {
        OfferDetails offerDetails = new OfferDetails();
        if (jsonObject.has("offerId"))
            offerDetails.setOfferId(jsonObject.optString("offerId"));
        if (jsonObject.has("offerTitle"))
            offerDetails.setOfferTitle(jsonObject.optString("offerTitle"));
        if (jsonObject.has("openDate"))
            offerDetails.setOfferOpenDate(jsonObject.optString("openDate"));
        if (jsonObject.has("closeDate"))
            offerDetails.setOfferCloseDate(jsonObject.optString("closeDate"));
        if (jsonObject.has("percentageType"))
            offerDetails.setOfferPercentageType(jsonObject.optString("percentageType"));
        if (jsonObject.has("brandId"))
            offerDetails.setOfferBrandId(jsonObject.optString("brandId"));
        if (jsonObject.has("offerPercentage"))
            offerDetails.setOfferPercentage(jsonObject.optString("offerPercentage"));
        if (jsonObject.has("actualPrice"))
            offerDetails.setOfferActualPrice(jsonObject.optString("actualPrice"));
        if (jsonObject.has("discountedPrice"))
            offerDetails.setOfferDiscountedPrice(jsonObject.optString("discountedPrice"));
        if (jsonObject.has("Category"))
            offerDetails.setOfferCategory(jsonObject.optString("Category"));
        if (jsonObject.has("Subcategory"))
            offerDetails.setOfferSubcategory(jsonObject.optString("Subcategory"));
        if (jsonObject.has("offerbanner"))
            offerDetails.setOfferBanner(jsonObject.optString("offerbanner"));
        if (jsonObject.has("offerDescription"))
            offerDetails.setOfferDescription(jsonObject.optString("offerDescription"));
        if (jsonObject.has("updateDate"))
            offerDetails.setOfferUpdateDate(jsonObject.optString("updateDate"));
        return offerDetails;
    }
}
