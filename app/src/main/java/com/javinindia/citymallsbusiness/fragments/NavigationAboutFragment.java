package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.CountryModel;
import com.javinindia.citymallsbusiness.apiparsing.loginsignupparsing.LoginSignupResponseParsing;
import com.javinindia.citymallsbusiness.apiparsing.shoperprofileparsing.ShopViewResponse;
import com.javinindia.citymallsbusiness.constant.Constants;
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
public class NavigationAboutFragment extends BaseFragment implements View.OnClickListener, AboutAdaptar.MyClickListener {
    private RecyclerView recyclerview;
    private List<CountryModel> mCountryModel;
    private AboutAdaptar adapter;
    private RequestQueue requestQueue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Log.e("id",SharedPreferencesManager.getUserID(activity));
        initialize(view);
        sendDataOnRegistrationApi();
        return view;
    }
    private void sendDataOnRegistrationApi() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        String status = null, sID = null, msg = null,sPic = null;
                        String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong;
                        String shopCategory, shopSubCategory, country, pincode, rating, openTime, closeTime,distance;
                        int offerCount;
                        loading.dismiss();
                        ShopViewResponse shopViewResponse = new ShopViewResponse();
                        shopViewResponse.responseParseMethod(response);

                        sID = shopViewResponse.getShopid().trim();
                        sPic = shopViewResponse.getProfilepic().trim();
                        status = shopViewResponse.getStatus().trim();
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
                        shopCategory = shopViewResponse.getShopCategory().trim();
                        shopSubCategory = shopViewResponse.getShopSubCategory().trim();
                        country = shopViewResponse.getCountry().trim();
                        pincode = shopViewResponse.getPincode().trim();
                        rating = shopViewResponse.getRating().trim();
                        openTime = shopViewResponse.getOpenTime().trim();
                        closeTime = shopViewResponse.getCloseTime().trim();
                        distance = shopViewResponse.getDistance().trim();
                        offerCount = shopViewResponse.getOfferCount();
                       /*  context,  sName,  oName,  sEmail,  sMobileNum,  sLandline,  sState,  sCity,  sAddress,  mName,  mAddress,  mLat,  mLong,  sPic,  shopCategory,  shopSubCategory,  country,  pincode,  rating,  openTime,  closeTime,  distance,  offerCount*/
                        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                        recyclerview.setLayoutManager(layoutManager);
                        adapter = new AboutAdaptar(activity,  sName,  oName,  sEmail,  sMobileNum,  sLandline,  sState,  sCity,  sAddress,  mName,  mAddress,  mLat,  mLong,  sPic,  shopCategory,  shopSubCategory,  country,  pincode,  rating,  openTime,  closeTime,  distance,  offerCount);
                        recyclerview.setAdapter(adapter);
                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                        if (status.equalsIgnoreCase("true") && !status.isEmpty()) {
                            setRequest();
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
                        volleyErrorHandle(error);
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
    private void setRequest() {
        String[] locales = Locale.getISOCountries();
        mCountryModel = new ArrayList<>();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            mCountryModel.add(new CountryModel(obj.getDisplayCountry(), obj.getISO3Country()));
        }

      /*  adapter = new AboutAdaptar(mCountryModel);
        adapter.setMyClickListener(NavigationAboutFragment.this);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
    }

    private void initialize(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);

       /* AppCompatButton etEditProfile = (AppCompatButton)view.findViewById(R.id.etEditProfile);
        etEditProfile.setOnClickListener(this);*/
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

  /*  @Override
    public void onItemClick(int position, CountryModel model) {

    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.etEditProfile:
                EditProfileFragment fragment = new EditProfileFragment();
                callFragmentMethod(fragment,this.getClass().getSimpleName(),R.id.navigationContainer);
                break;*/
        }
    }
}
