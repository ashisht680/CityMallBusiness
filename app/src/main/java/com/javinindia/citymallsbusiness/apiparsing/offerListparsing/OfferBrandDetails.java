package com.javinindia.citymallsbusiness.apiparsing.offerListparsing;

/**
 * Created by Ashish on 22-10-2016.
 */
public class OfferBrandDetails {
    private String brandId;
    private String brandOfferCategory;
    private String brandOfferSubCategory;
    private String brandName;
    private String brandLogo;
    private String brandStatus;
    private String brandInsertDate;
    private String brandUpdateDate;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandInsertDate() {
        return brandInsertDate;
    }

    public void setBrandInsertDate(String brandInsertDate) {
        this.brandInsertDate = brandInsertDate;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandOfferCategory() {
        return brandOfferCategory;
    }

    public void setBrandOfferCategory(String brandOfferCategory) {
        this.brandOfferCategory = brandOfferCategory;
    }

    public String getBrandOfferSubCategory() {
        return brandOfferSubCategory;
    }

    public void setBrandOfferSubCategory(String brandOfferSubCategory) {
        this.brandOfferSubCategory = brandOfferSubCategory;
    }

    public String getBrandStatus() {
        return brandStatus;
    }

    public void setBrandStatus(String brandStatus) {
        this.brandStatus = brandStatus;
    }

    public String getBrandUpdateDate() {
        return brandUpdateDate;
    }

    public void setBrandUpdateDate(String brandUpdateDate) {
        this.brandUpdateDate = brandUpdateDate;
    }
}
