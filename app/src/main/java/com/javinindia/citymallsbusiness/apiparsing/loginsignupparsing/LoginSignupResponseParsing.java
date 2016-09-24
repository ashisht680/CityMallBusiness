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
   // LoginMallDeatils mallDetail;

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

            setShopid((jsonObject.optJSONObject("shopid") != null)? jsonObject.optString("shopid") : "");
            setStoreName((jsonObject.optJSONObject("storeName") != null)? jsonObject.optString("storeName") : "");
            setOwnerName((jsonObject.optJSONObject("ownerName") != null)? jsonObject.optString("ownerName") : "");
            setEmail((jsonObject.optJSONObject("email") != null)? jsonObject.optString("email") : "");
            setMobile((jsonObject.optJSONObject("mobile") != null)? jsonObject.optString("mobile") : "");
            setLandline((jsonObject.optJSONObject("landline") != null)? jsonObject.optString("landline") : "");
            setState((jsonObject.optJSONObject("state") != null)? jsonObject.optString("state") : "");
            setCity((jsonObject.optJSONObject("city") != null)? jsonObject.optString("city") : "");
            setAddress((jsonObject.optJSONObject("address") != null)? jsonObject.optString("address") : "");
            setProfilepic((jsonObject.optJSONObject("profilepic") != null)? jsonObject.optString("profilepic") : "");
            setMallId((jsonObject.optJSONObject("mallid") != null)? jsonObject.optString("mallid") : "");
            setDescription((jsonObject.optJSONObject("description") != null)? jsonObject.optString("description") : "");
            setMallAddress((jsonObject.optJSONObject("mallAddress") != null)? jsonObject.optString("mallAddress") : "");
            setMallLandmark((jsonObject.optJSONObject("mallLandmark") != null)? jsonObject.optString("mallLandmark") : "");
            setMallLat((jsonObject.optJSONObject("mallLat") != null)? jsonObject.optString("mallLat") : "");
            setMallLong((jsonObject.optJSONObject("mallLong") != null)? jsonObject.optString("mallLong") : "");

            Log.d("Response", this.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
