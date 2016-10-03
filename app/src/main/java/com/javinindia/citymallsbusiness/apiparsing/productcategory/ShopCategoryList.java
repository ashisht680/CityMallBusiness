package com.javinindia.citymallsbusiness.apiparsing.productcategory;

import java.util.ArrayList;

/**
 * Created by Ashish on 03-10-2016.
 */
public class ShopCategoryList {
    String id;
    String shopCatId;
    String shopCategory;
    private ArrayList<ShopSubCatDetail> subCatDetailArrayList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopCatId() {
        return shopCatId;
    }

    public void setShopCatId(String shopCatId) {
        this.shopCatId = shopCatId;
    }

    public ArrayList<ShopSubCatDetail> getSubCatDetailArrayList() {
        return subCatDetailArrayList;
    }

    public void setSubCatDetailArrayList(ArrayList<ShopSubCatDetail> subCatDetailArrayList) {
        this.subCatDetailArrayList = subCatDetailArrayList;
    }
}
