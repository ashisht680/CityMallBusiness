package com.javinindia.citymallsbusiness.apiparsing.loginsignupparsing;

/**
 * Created by Ashish on 21-09-2016.
 */
public class LoginMallDeatils {
    String id;
    String mallName;
    String description;
    String mallAddress;
    String mallLandmark;
    String mallLat;
    String mallLong;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
