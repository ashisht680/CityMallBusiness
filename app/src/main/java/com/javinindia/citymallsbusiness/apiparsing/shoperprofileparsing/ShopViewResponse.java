package com.javinindia.citymallsbusiness.apiparsing.shoperprofileparsing;

import android.util.Log;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ashish on 26-09-2016.
 */
public class ShopViewResponse extends ApiBaseData {
    String shopid;
    String shopCategory;
    String shopSubCategory;
    String banner;
    String storeName;
    String ownerName;
    String email;
    String mobile;
    String landline;
    String state;
    String city;
    String address;
    String profilepic;
    String mallId;
    String mallName;
    String description;
    String mallAddress;
    String mallLandmark;
    String mallLat;
    String mallLong;
    String country;
    String pincode;
    String rating;
    String openTime;
    String closeTime;
    int offerCount;
    String distance;
    String shopOpenTime;
    String shopCloseTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getMallAddress() {
        return mallAddress;
    }

    public void setMallAddress(String mallAddress) {
        this.mallAddress = mallAddress;
    }

    public String getMallLandmark() {
        return mallLandmark;
    }

    public void setMallLandmark(String mallLandmark) {
        this.mallLandmark = mallLandmark;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getMallLat() {
        return mallLat;
    }

    public void setMallLat(String mallLat) {
        this.mallLat = mallLat;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getMallLong() {
        return mallLong;
    }

    public void setMallLong(String mallLong) {
        this.mallLong = mallLong;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(int offerCount) {
        this.offerCount = offerCount;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getShopSubCategory() {
        return shopSubCategory;
    }

    public void setShopSubCategory(String shopSubCategory) {
        this.shopSubCategory = shopSubCategory;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getShopCloseTime() {
        return shopCloseTime;
    }

    public void setShopCloseTime(String shopCloseTime) {
        this.shopCloseTime = shopCloseTime;
    }

    public String getShopOpenTime() {
        return shopOpenTime;
    }

    public void setShopOpenTime(String shopOpenTime) {
        this.shopOpenTime = shopOpenTime;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void responseParseMethod(Object response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            setStatus(jsonObject.optString("status"));
            setMsg(jsonObject.optString("msg"));

            if (jsonObject.has("shopid"))
                setShopid(jsonObject.optString("shopid"));
            if (jsonObject.has("shopCategory"))
                setShopCategory(jsonObject.optString("shopCategory"));
            if (jsonObject.has("shopSubCategory"))
                setShopSubCategory(jsonObject.optString("shopSubCategory"));
            if (jsonObject.has("banner"))
                setBanner(jsonObject.optString("banner"));
            if (jsonObject.has("storeName"))
                setStoreName(jsonObject.optString("storeName"));
            if (jsonObject.has("ownerName"))
                setOwnerName(jsonObject.optString("ownerName"));
            if (jsonObject.has("email"))
                setEmail(jsonObject.optString("email"));
            if (jsonObject.has("mobile"))
                setMobile(jsonObject.optString("mobile"));
            if (jsonObject.has("landline"))
                setLandline(jsonObject.optString("landline"));
            if (jsonObject.has("state"))
                setState(jsonObject.optString("state"));
            if (jsonObject.has("city"))
                setCity(jsonObject.optString("city"));
            if (jsonObject.has("address"))
                setAddress(jsonObject.optString("address"));
            if (jsonObject.has("profilepic"))
                setProfilepic(jsonObject.optString("profilepic"));
            if (jsonObject.has("mallid"))
                setMallId(jsonObject.optString("mallid"));
            if (jsonObject.has("mallName"))
                setMallName(jsonObject.optString("mallName"));
            if (jsonObject.has("description"))
                setDescription(jsonObject.optString("description"));
            if (jsonObject.has("shopOpenTime"))
                setShopOpenTime(jsonObject.optString("shopOpenTime"));
            if (jsonObject.has("shopCloseTime"))
                setShopCloseTime(jsonObject.optString("shopCloseTime"));
            if (jsonObject.has("mallAddress"))
                setMallAddress(jsonObject.optString("mallAddress"));
            if (jsonObject.has("mallLandmark"))
                setMallLandmark(jsonObject.optString("mallLandmark"));
            if (jsonObject.has("mallLat"))
                setMallLat(jsonObject.optString("mallLat"));
            if (jsonObject.has("mallLong"))
                setMallLong(jsonObject.optString("mallLong"));

            if (jsonObject.has("country"))
                setCountry(jsonObject.optString("country"));
            if (jsonObject.has("pincode"))
                setPincode(jsonObject.optString("pincode"));
            if (jsonObject.has("rating"))
                setRating(jsonObject.optString("rating"));
            if (jsonObject.has("openTime"))
                setOpenTime(jsonObject.optString("openTime"));
            if (jsonObject.has("closeTime"))
                setCloseTime(jsonObject.optString("closeTime"));
            if (jsonObject.has("offerCount"))
                setOfferCount(jsonObject.optInt("offerCount"));
            if (jsonObject.has("distance"))
                setDistance(jsonObject.optString("distance"));


            Log.d("Response", this.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
