package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.activity.NavigationActivity;
import com.javinindia.citymallsbusiness.apiparsing.CountryModel;
import com.javinindia.citymallsbusiness.apiparsing.loginsignupparsing.LoginSignupResponseParsing;
import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.DetailsList;
import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.OfferListResponseparsing;
import com.javinindia.citymallsbusiness.apiparsing.shoperprofileparsing.ShopViewResponse;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.recyclerview.AboutAdaptar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Ashish on 09-09-2016.
 */
public class NavigationAboutFragment extends BaseFragment implements View.OnClickListener, AboutAdaptar.MyClickListener, ListShopProductCategoryFragment.OnCallBackCategoryListListener, AddNewOfferFragment.OnCallBackAddOfferListener, EditProfileFragment1.OnCallBackEditProfileListener, UpdateOfferFragment.OnCallBackUpdateOfferListener, AllOffersFragment.OnCallBackRefreshListener, AddSubCatFragment.OnCallBackCAtegoryListener {
    private RecyclerView recyclerview;
    private List<CountryModel> mCountryModel;
    private AboutAdaptar adapter;
    private RequestQueue requestQueue;
    ArrayList catArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Log.e("id", SharedPreferencesManager.getUserID(activity));
        initialize(view);
        sendDataOnRegistrationApi();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    private void sendDataOnRegistrationApi() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        String status = null, sID = null, msg = null, sPic = null, banner;
                        String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong;
                        String shopCategory, shopSubCategory, country, pincode, rating, openTime, closeTime, distance, shopfavCount, sFloor, sNo;
                        int offerCount;
                        loading.dismiss();
                        ShopViewResponse shopViewResponse = new ShopViewResponse();
                        shopViewResponse.responseParseMethod(response);

                        status = shopViewResponse.getStatus().trim();

                        if (status.equalsIgnoreCase("true")) {
                            sID = shopViewResponse.getShopid().trim();
                            sPic = shopViewResponse.getProfilepic().trim();
                            sName = shopViewResponse.getStoreName().trim();
                            oName = shopViewResponse.getOwnerName().trim();
                            sEmail = shopViewResponse.getEmail().trim();
                            sMobileNum = shopViewResponse.getMobile().trim();
                            sLandline = shopViewResponse.getLandline().trim();
                            sState = shopViewResponse.getState().trim();
                            sCity = shopViewResponse.getCity().trim();
                            sAddress = shopViewResponse.getAddress().trim();
                            mName = shopViewResponse.getMallName().trim();
                            mAddress = shopViewResponse.getMallAddress();
                            mLat = shopViewResponse.getMallLat();
                            mLong = shopViewResponse.getMallLong();
                            country = shopViewResponse.getCountry().trim();
                            pincode = shopViewResponse.getPincode().trim();
                            rating = shopViewResponse.getRating().trim();
                            openTime = shopViewResponse.getShopOpenTime().trim();
                            closeTime = shopViewResponse.getShopCloseTime().trim();
                            distance = shopViewResponse.getDistance().trim();
                            offerCount = shopViewResponse.getOfferCount();
                            shopfavCount = shopViewResponse.getShopfavCount().trim();
                            banner = shopViewResponse.getBanner().trim();
                            sFloor = shopViewResponse.getFloor().trim();
                            sNo = shopViewResponse.getShopNum().trim();
                            saveDataOnPreference(sEmail, sName, mLat, mLong, sID, sPic, oName);
                            catArray = shopViewResponse.getShopCategoryDetailsArrayList();
                            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                            recyclerview.setLayoutManager(layoutManager);
                            adapter = new AboutAdaptar(activity, sFloor, sNo, shopfavCount, sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong, sPic, country, pincode, rating, openTime, closeTime, distance, offerCount, banner, catArray);
                            recyclerview.setAdapter(adapter);
                            adapter.setMyClickListener(NavigationAboutFragment.this);
                            recyclerview.setItemAnimator(new DefaultItemAnimator());

                            offerRequestApi();
                        } else {
                            if (!TextUtils.isEmpty(msg)) {
                                showDialogMethod(msg);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        noInternetToast(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", SharedPreferencesManager.getUserID(activity));
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void saveDataOnPreference(String sEmail, String sName, String mLat, String mLong, String sID, String profilepic, String oName) {
        SharedPreferencesManager.setUserID(activity, sID);
        SharedPreferencesManager.setEmail(activity, sEmail);
        SharedPreferencesManager.setUsername(activity, sName);
        SharedPreferencesManager.setLatitude(activity, mLat);
        SharedPreferencesManager.setLongitude(activity, mLong);
        SharedPreferencesManager.setProfileImage(activity, profilepic);
        SharedPreferencesManager.setOwnerName(activity, oName);
    }

    private void offerRequestApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.OFFER_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        OfferListResponseparsing responseparsing = new OfferListResponseparsing();
                        responseparsing.responseParseMethod(response);
                        Log.e("request", response);
                        String status = responseparsing.getStatus().trim();
                        if (status.equals("true")) {
                            if (responseparsing.getDetailsListArrayList().size() > 0) {
                                ArrayList arrayList = responseparsing.getDetailsListArrayList();
                                if (arrayList.size() > 0) {
                                    if (adapter.getData() != null && adapter.getData().size() > 0) {
                                        adapter.getData().addAll(arrayList);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        adapter.setData(arrayList);
                                        adapter.notifyDataSetChanged();

                                    }
                                }
                            } else {
                                Toast.makeText(activity, "No offers", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(activity, "No offers", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        noInternetToast(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", SharedPreferencesManager.getUserID(activity));
                params.put("startlimit", "0");
                params.put("countlimit", "5");
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void initialize(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        AppCompatButton btnAddOffer = (AppCompatButton) view.findViewById(R.id.btnAddOffer);
        btnAddOffer.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        btnAddOffer.setOnClickListener(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.about_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddOffer:
                AddNewOfferFragment fragment = new AddNewOfferFragment();
                fragment.setMyCallBackOfferListener(this);
                callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.navigationContainer);
                break;
        }
    }

    @Override
    public void onEditClick(int position) {
        Toast.makeText(activity, "aaaaa", Toast.LENGTH_LONG).show();
        EditProfileFragment1 fragment1 = new EditProfileFragment1();
        fragment1.setMyCallBackOfferListener(this);
        callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.navigationContainer);
    }

    @Override
    public void onAllOffers(int position) {
        AllOffersFragment fragment1 = new AllOffersFragment();
        fragment1.setMyCallBackRefreshListener(this);
        callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.navigationContainer);
    }

    @Override
    public void onAddCategory(int position) {
        AddSubCatFragment categoryFragment = new AddSubCatFragment();
        categoryFragment.setMyCallBackCategoryListener(this);
        callFragmentMethod(categoryFragment, this.getClass().getSimpleName(), R.id.navigationContainer);
    }

    @Override
    public void onOfferClick(int position, DetailsList detailsList) {
        String brandName = detailsList.getOfferBrandDetails().getBrandName().trim();
        String brandPic = detailsList.getOfferBrandDetails().getBrandLogo().trim();
        String shopName = detailsList.getOfferShopDetails().getShopName().trim();
        String mallName = detailsList.getOfferMallDetails().getMallName().trim();
        String offerRating = "4";
        String offerPic = detailsList.getOfferDetails().getOfferBanner().trim();
        String offerTitle = detailsList.getOfferDetails().getOfferTitle().trim();
        String offerCategory = detailsList.getOfferDetails().getOfferCategory();
        String offerSubCategory = detailsList.getOfferDetails().getOfferSubcategory().trim();
        String offerPercentType = detailsList.getOfferDetails().getOfferPercentageType().trim();
        String offerPercentage = detailsList.getOfferDetails().getOfferPercentage().trim();
        String offerActualPrice = detailsList.getOfferDetails().getOfferActualPrice().trim();
        String offerDiscountPrice = detailsList.getOfferDetails().getOfferDiscountedPrice().trim();
        String offerStartDate = detailsList.getOfferDetails().getOfferOpenDate().trim();
        String offerCloseDate = detailsList.getOfferDetails().getOfferCloseDate().trim();
        String offerDescription = detailsList.getOfferDetails().getOfferDescription().trim();
        String shopOpenTime = detailsList.getOfferShopDetails().getShopOpenTime().trim();
        String shopCloseTime = detailsList.getOfferShopDetails().getShopCloseTime().trim();
        String favCount = detailsList.getFavCount().trim();
        OfferPostFragment fragment1 = new OfferPostFragment();

        Bundle bundle = new Bundle();
        //    bundle.putSerializable("images", postImage);
        bundle.putString("favCount", favCount);
        bundle.putString("brandName", brandName);
        bundle.putString("brandPic", brandPic);
        bundle.putString("shopName", shopName);
        bundle.putString("mallName", mallName);
        bundle.putString("offerRating", offerRating);
        bundle.putString("offerPic", offerPic);
        bundle.putString("offerTitle", offerTitle);
        bundle.putString("offerCategory", offerCategory);
        bundle.putString("offerSubCategory", offerSubCategory);
        bundle.putString("offerPercentType", offerPercentType);
        bundle.putString("offerPercentage", offerPercentage);
        bundle.putString("offerActualPrice", offerActualPrice);
        bundle.putString("offerDiscountPrice", offerDiscountPrice);
        bundle.putString("offerStartDate", offerStartDate);
        bundle.putString("offerCloseDate", offerCloseDate);
        bundle.putString("offerDescription", offerDescription);
        bundle.putString("shopOpenTime", shopOpenTime);
        bundle.putString("shopCloseTime", shopCloseTime);
        fragment1.setArguments(bundle);
        callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.navigationContainer);
    }

    @Override
    public void onOfferEditClick(int position, DetailsList detailsList) {
        String brandId = detailsList.getOfferBrandDetails().getBrandId().trim();
        String brandName = detailsList.getOfferBrandDetails().getBrandName().trim();
        String brandPic = detailsList.getOfferBrandDetails().getBrandLogo().trim();
        String shopName = detailsList.getOfferShopDetails().getShopName().trim();
        String mallName = detailsList.getOfferMallDetails().getMallName().trim();
        String offerId = detailsList.getOfferDetails().getOfferId().trim();
        String offerRating = "4";
        String offerPic = detailsList.getOfferDetails().getOfferBanner().trim();
        String offerTitle = detailsList.getOfferDetails().getOfferTitle().trim();
        String offerCatId = detailsList.getOfferBrandDetails().getBrandOfferCategory().trim();
        String offerCategory = detailsList.getOfferDetails().getOfferCategory();
        String offerSubCatId = detailsList.getOfferBrandDetails().getBrandOfferSubCategory().trim();
        String offerSubCategory = detailsList.getOfferDetails().getOfferSubcategory().trim();
        String offerPercentType = detailsList.getOfferDetails().getOfferPercentageType().trim();
        String offerPercentage = detailsList.getOfferDetails().getOfferPercentage().trim();
        String offerActualPrice = detailsList.getOfferDetails().getOfferActualPrice().trim();
        String offerDiscountPrice = detailsList.getOfferDetails().getOfferDiscountedPrice().trim();
        String offerStartDate = detailsList.getOfferDetails().getOfferOpenDate().trim();
        String offerCloseDate = detailsList.getOfferDetails().getOfferCloseDate().trim();
        String offerDescription = detailsList.getOfferDetails().getOfferDescription().trim();
        String shopOpenTime = detailsList.getOfferShopDetails().getShopOpenTime().trim();
        String shopCloseTime = detailsList.getOfferShopDetails().getShopCloseTime().trim();

        UpdateOfferFragment fragment1 = new UpdateOfferFragment();

        Bundle bundle = new Bundle();
        //    bundle.putSerializable("images", postImage);
        bundle.putString("brandId", brandId);
        bundle.putString("offerCatId", offerCatId);
        bundle.putString("offerSubCatId", offerSubCatId);
        bundle.putString("offerId", offerId);
        bundle.putString("brandName", brandName);
        bundle.putString("brandPic", brandPic);
        bundle.putString("shopName", shopName);
        bundle.putString("mallName", mallName);
        bundle.putString("offerRating", offerRating);
        bundle.putString("offerPic", offerPic);
        bundle.putString("offerTitle", offerTitle);
        bundle.putString("offerCategory", offerCategory);
        bundle.putString("offerSubCategory", offerSubCategory);
        bundle.putString("offerPercentType", offerPercentType);
        bundle.putString("offerPercentage", offerPercentage);
        bundle.putString("offerActualPrice", offerActualPrice);
        bundle.putString("offerDiscountPrice", offerDiscountPrice);
        bundle.putString("offerStartDate", offerStartDate);
        bundle.putString("offerCloseDate", offerCloseDate);
        bundle.putString("offerDescription", offerDescription);
        bundle.putString("shopOpenTime", shopOpenTime);
        bundle.putString("shopCloseTime", shopCloseTime);
        fragment1.setArguments(bundle);
        fragment1.setMyCallBackUpdateOfferListener(this);
        callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.navigationContainer);
    }

    @Override
    public void onViewClick(int position, DetailsList detailsList) {
        String offerId = detailsList.getOfferDetails().getOfferId().trim();
        String numView = detailsList.getOfferViewCount().toString().trim();
        if (!TextUtils.isEmpty(numView) && numView.equals("0")){
            AllViewUserFragment fragment1 = new AllViewUserFragment();

            Bundle bundle = new Bundle();
            bundle.putString("offerId", offerId);
            fragment1.setArguments(bundle);
            callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.navigationContainer);
        }else {
            Toast.makeText(activity,"No views now",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCallBackCatList() {
        sendDataOnRegistrationApi();
    }

    @Override
    public void OnCallBackAddOffer() {
        sendDataOnRegistrationApi();
    }

    @Override
    public void OnCallBackEditProfile() {
        //sendDataOnRegistrationApi();
        Intent refresh = new Intent(activity, NavigationActivity.class);
        startActivity(refresh);
        activity.finish();
    }

    @Override
    public void OnCallBackUpdateOffer() {
        sendDataOnRegistrationApi();
    }

    @Override
    public void OnCallBackRefreshOffer() {
        sendDataOnRegistrationApi();
    }

    @Override
    public void onCallBackCat() {
        sendDataOnRegistrationApi();
    }
}
