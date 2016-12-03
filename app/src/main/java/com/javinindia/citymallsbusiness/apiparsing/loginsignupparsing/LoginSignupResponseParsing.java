package com.javinindia.citymallsbusiness.apiparsing.loginsignupparsing;

import android.util.Log;

import com.javinindia.citymallsbusiness.apiparsing.base.ApiBaseData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ashish on 21-09-2016.
 */
public class LoginSignupResponseParsing extends ApiBaseData {
    String shopid;
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
    String banner;
   // LoginMallDeatils mallDetail;


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

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

   /* public LoginMallDeatils getMallDetail() {
        return mallDetail;
    }

    public void setMallDetail(LoginMallDeatils mallDetail) {
        this.mallDetail = mallDetail;
    }*/

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
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

    public String getMallLat() {
        return mallLat;
    }

    public void setMallLat(String mallLat) {
        this.mallLat = mallLat;
    }

    public String getMallLong() {
        return mallLong;
    }

    public void setMallLong(String mallLong) {
        this.mallLong = mallLong;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public void responseParseMethod(Object response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            setStatus(jsonObject.optString("status"));
            setMsg(jsonObject.optString("msg"));
            if (jsonObject.has("shopid"))
                setShopid(jsonObject.optString("shopid"));
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
            if (jsonObject.has("profilePic"))
                setProfilepic(jsonObject.optString("profilePic"));
            if (jsonObject.has("mallid"))
                setMallId(jsonObject.optString("mallid"));
            if (jsonObject.has("description"))
                setDescription(jsonObject.optString("description"));
            if (jsonObject.has("mallAddress"))
                setMallAddress(jsonObject.optString("mallAddress"));
            if (jsonObject.has("mallLandmark"))
                setMallLandmark(jsonObject.optString("mallLandmark"));
            if (jsonObject.has("mallLat"))
                setMallLat(jsonObject.optString("mallLat"));
            if (jsonObject.has("mallLong"))
                setMallLong(jsonObject.optString("mallLong"));
            if (jsonObject.has("banner"))
                setBanner(jsonObject.optString("banner"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
