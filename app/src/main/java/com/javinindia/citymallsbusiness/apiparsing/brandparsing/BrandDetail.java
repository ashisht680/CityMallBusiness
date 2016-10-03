package com.javinindia.citymallsbusiness.apiparsing.brandparsing;

/**
 * Created by Ashish on 03-10-2016.
 */
public class BrandDetail {
    String id;
    String offerCatId;
    String offerSubCatId;
    String name;
    String brandLogo;

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfferCatId() {
        return offerCatId;
    }

    public void setOfferCatId(String offerCatId) {
        this.offerCatId = offerCatId;
    }

    public String getOfferSubCatId() {
        return offerSubCatId;
    }

    public void setOfferSubCatId(String offerSubCatId) {
        this.offerSubCatId = offerSubCatId;
    }
}
