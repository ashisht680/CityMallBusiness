package com.javinindia.citymalls.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymalls.R;
import com.javinindia.citymalls.apiparsing.loginsignupparsing.LoginSignupResponseParsing;
import com.javinindia.citymalls.apiparsing.shopmalllistparsing.ShopMallListResponseParsing;
import com.javinindia.citymalls.apiparsing.stateparsing.CityMasterParsing;
import com.javinindia.citymalls.apiparsing.stateparsing.CountryMasterApiParsing;
import com.javinindia.citymalls.constant.Constants;
import com.javinindia.citymalls.font.FontRalewayMediumSingleTonClass;
import com.javinindia.citymalls.font.FontRalewayRegularSingleTonClass;
import com.javinindia.citymalls.preference.SharedPreferencesManager;
import com.javinindia.citymalls.utility.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 08-09-2016.
 */
public class SignUpAddressFragment extends BaseFragment implements View.OnClickListener {

    private AppCompatEditText et_State, et_City, et_PinCode, et_Address, et_Mall;
    RadioButton radioButton;
    TextView txtTermCondition;
    private RequestQueue requestQueue;
    private BaseFragment fragment;
    String storeName, owner, email, mobileNum, landline, password, mallId;
    public ArrayList<String> stateList = new ArrayList<>();
    String stateArray[] = null;
    public ArrayList<String> cityList = new ArrayList<>();
    String cityArray[] = null;
    public ArrayList<String> mallList = new ArrayList<>();
    String mallArray[] = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity.getSupportActionBar().hide();
        storeName = getArguments().getString("storeName");
        owner = getArguments().getString("owner");
        email = getArguments().getString("email");
        mobileNum = getArguments().getString("mobileNum");
        landline = getArguments().getString("landline");
        password = getArguments().getString("password");
        getArguments().remove("storeName");
        getArguments().remove("owner");
        getArguments().remove("email");
        getArguments().remove("mobileNum");
        getArguments().remove("landline");
        getArguments().remove("password");
        Log.e("address", storeName + "\t" + owner + "\t" + email + "\t" + mobileNum + "\t" + landline + "\t" + password + "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getFragmentLayout(), container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        ImageView imgBack = (ImageView) view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        AppCompatButton btn_regester = (AppCompatButton) view.findViewById(R.id.btn_regester);
        btn_regester.setTypeface(FontRalewayMediumSingleTonClass.getInstance(activity).getTypeFace());
        et_State = (AppCompatEditText) view.findViewById(R.id.et_State);
        et_State.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        et_City = (AppCompatEditText) view.findViewById(R.id.et_City);
        et_City.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        et_PinCode = (AppCompatEditText) view.findViewById(R.id.et_PinCode);
        et_PinCode.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        et_Address = (AppCompatEditText) view.findViewById(R.id.et_Address);
        et_Address.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        et_Mall = (AppCompatEditText) view.findViewById(R.id.et_Mall);
        et_Mall.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        radioButton = (RadioButton) view.findViewById(R.id.radioButton);
        txtTermCondition = (TextView) view.findViewById(R.id.txtTermCondition);
        txtTermCondition.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtTermCondition.setText(Html.fromHtml("<font color=#000000>" + "I Accept the" + "</font>" + "\t" + "<font color=#0d7bbf>" + "Terms and conditions" + "</font>"));
        txtTermCondition.setOnClickListener(this);

        et_State.setOnClickListener(this);
        et_City.setOnClickListener(this);
        et_Mall.setOnClickListener(this);
        btn_regester.setOnClickListener(this);

    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.sign_up_address_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_regester:
                Utility.hideKeyboard(activity);
                registrationMethod();
                break;
            case R.id.imgBack:
                activity.onBackPressed();
                break;
            case R.id.txtTermCondition:
               /* BaseFragment termFragment = new TermsFragment();
                callFragmentMethodDead(termFragment, Constants.OTHER_USER_FEED, R.id.container);*/
                break;
            case R.id.et_State:
                methodState();
                break;
            case R.id.et_City:
                if (!TextUtils.isEmpty(et_State.getText())) {
                    methodCity();
                } else {
                    Toast.makeText(activity, "Select State first", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.et_Mall:
                if (!TextUtils.isEmpty(et_City.getText())) {
                    methodMallList();
                } else {
                    Toast.makeText(activity, "Select City first", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void methodMallList() {
        mallList.removeAll(mallList);
        mallArray = null;

        sendRequestMallList();
    }

    private void sendRequestMallList() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.MALL_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.e("mall", response);
                        hideLoader();
                        final ShopMallListResponseParsing shopMallListResponseParsing = new ShopMallListResponseParsing();
                        shopMallListResponseParsing.responseParseMethod(response);
                        if (shopMallListResponseParsing.getMallDetailsArrayList().size() > 0) {
                            for (int i = 0; i < shopMallListResponseParsing.getMallDetailsArrayList().size(); i++) {
                                mallList.add(shopMallListResponseParsing.getMallDetailsArrayList().get(i).getMallName());
                            }
                            if (mallList.size() > 0) {
                                mallArray = new String[mallList.size()];
                                mallList.toArray(mallArray);

                                if (mallList != null && mallList.size() > 0) {
                                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                                    builder.setTitle("Select Mall");
                                    builder.setNegativeButton(android.R.string.cancel, null);
                                    builder.setItems(mallArray, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int item) {
                                            // Do something with the selection
                                            et_Mall.setText(mallArray[item]);

                                            // Log.e("mall", shopMallListResponseParsing.getMallDetailsArrayList().get(1).getId());
                                            int index = Arrays.asList(mallArray).indexOf(mallArray[item]);
                                            ;
                                            mallId = shopMallListResponseParsing.getMallDetailsArrayList().get(index).getId();
                                            et_PinCode.setText(shopMallListResponseParsing.getMallDetailsArrayList().get(index).getPincode());
                                            Log.e("mallId", mallId + "\t" + index);
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.create();
                                    builder.show();
                                }
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
                params.put("state", "Delhi");
                params.put("city", "New Delhi");
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void methodCity() {
        cityList.removeAll(cityList);
        cityArray = null;

        sendRequestOnCity();
    }

    private void sendRequestOnCity() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.STATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        //  progressDialog.dismiss();
                        Log.e("MasterTags", response);
                        hideLoader();
                        CityMasterParsing cityMasterParsing = new CityMasterParsing();
                        cityMasterParsing.responseParseMethod(response);
                        for (int i = 0; i < cityMasterParsing.getCountryDetails().getCityDetails().size(); i++) {
                            cityList.add(cityMasterParsing.getCountryDetails().getCityDetails().get(i).getCity());
                        }
                        if (cityList.size() > 0) {
                            cityArray = new String[cityList.size()];
                            cityList.toArray(cityArray);

                            if (cityList != null && cityList.size() > 0) {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                                builder.setTitle("Select City");
                                builder.setNegativeButton(android.R.string.cancel, null);
                                builder.setItems(cityArray, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        // Do something with the selection
                                        et_City.setText(cityArray[item]);
                                    }
                                });
                                builder.create();
                                builder.show();
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
                params.put("country", "india");
                params.put("state", et_State.getText().toString());
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void methodState() {
        stateList.removeAll(stateList);
        stateArray = null;
        sendRequestOnState();
    }

    private void sendRequestOnState() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.STATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.e("MasterTags", response);
                        hideLoader();
                        CountryMasterApiParsing countryMasterApiParsing = new CountryMasterApiParsing();
                        countryMasterApiParsing.responseParseMethod(response);
                        if (countryMasterApiParsing.getCountryDetails().getStateDetailsArrayList().size() > 0) {
                            for (int i = 0; i < countryMasterApiParsing.getCountryDetails().getStateDetailsArrayList().size(); i++) {
                                stateList.add(countryMasterApiParsing.getCountryDetails().getStateDetailsArrayList().get(i).getState());
                            }
                            if (stateList.size() > 0) {
                                stateArray = new String[stateList.size()];
                                stateList.toArray(stateArray);

                                if (stateList != null && stateList.size() > 0) {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                                    builder.setTitle("Select State");
                                    builder.setNegativeButton(android.R.string.cancel, null);
                                    builder.setItems(stateArray, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int item) {
                                            // Do something with the selection
                                            et_State.setText(stateArray[item]);
                                        }
                                    });
                                    builder.create();
                                    builder.show();
                                }
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
                params.put("country", "india");
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void registrationMethod() {
        String state = et_State.getText().toString().trim();
        String city = et_City.getText().toString().trim();
        String pinCode = et_PinCode.getText().toString().trim();
        String address = et_Address.getText().toString().trim();
        String mallname = et_Mall.getText().toString().trim();

        if (registerValidation(state, city, pinCode, address, mallname)) {
            sendDataOnRegistrationApi(state, city, pinCode, address, mallId, storeName, owner, email, mobileNum, landline, password);
        }

    }

    private void sendDataOnRegistrationApi(final String statehit, final String cityhit, final String pinCodehit, final String addresshit, final String mallhit, final String storeNamehit, final String ownerhit, final String emailhit, final String mobileNumhit, final String landlinehit, final String passwordhit) {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SIGN_UP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String status = null, sID = null, msg = null,sPic = null;
                        String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong;
                        loading.dismiss();
                        LoginSignupResponseParsing loginSignupResponseParsing = new LoginSignupResponseParsing();
                        loginSignupResponseParsing.responseParseMethod(response);

                        sID = loginSignupResponseParsing.getShopid().trim();
                        sPic = loginSignupResponseParsing.getProfilepic().trim();
                        status = loginSignupResponseParsing.getStatus().trim();
                        sName = loginSignupResponseParsing.getStoreName().trim();
                        oName = loginSignupResponseParsing.getOwnerName().trim();
                        sEmail = loginSignupResponseParsing.getEmail().trim();
                        sMobileNum = loginSignupResponseParsing.getMobile().trim();
                        sLandline = loginSignupResponseParsing.getLandline().trim();
                        sState = loginSignupResponseParsing.getState().trim();
                        sCity = loginSignupResponseParsing.getCity().trim();
                        sAddress = loginSignupResponseParsing.getAddress().trim();
                        mName = loginSignupResponseParsing.getMallName().trim();
                        mAddress = loginSignupResponseParsing.getMallAddress();
                        mLat = loginSignupResponseParsing.getMallLat();
                        mLong = loginSignupResponseParsing.getMallLong();
                        if (status.equalsIgnoreCase("true") && !status.isEmpty()) {
                            Log.e("sign up detail", sName + "\t" + oName + "\t" + sEmail + "\t" + sMobileNum + "\t" + sLandline + "\t" + sState + "\t" + sCity + "\t" + sAddress + "\t" + mName + "\t" + mAddress + "\t" + mLat + "\t" + mLong);
                            saveDataOnPreference(sEmail, sName, mLat,mLong, sID,sPic);
                            fragment = new OffersFragment();
                            callFragmentMethodDead(fragment, this.getClass().getSimpleName());
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
                String completeAddress = addresshit + ",\t" + pinCodehit;
                Map<String, String> params = new HashMap<String, String>();
                params.put("storeName", storeNamehit);
                params.put("ownerName", ownerhit);
                params.put("email", emailhit);
                params.put("password", passwordhit);
                params.put("mobile", mobileNumhit);
                params.put("landline", landlinehit);
                params.put("state", statehit);
                params.put("city", cityhit);
                params.put("mall", mallId);
                params.put("address", completeAddress);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private boolean registerValidation(String state, String city, String pinCode, String address, String mallname) {
        if (TextUtils.isEmpty(state)) {
            Toast.makeText(activity, "Please enter your state", Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(city)) {
            Toast.makeText(activity, "Please enter your city", Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(address)) {
            et_Address.setError("Please enter your address");
            et_Address.requestFocus();
            return false;
        } else if (pinCode.length() < 6) {
            et_PinCode.setError("Please enter valid pincode");
            et_PinCode.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(mallname)) {
            Toast.makeText(activity, "Please enter mall", Toast.LENGTH_LONG).show();
            return false;
        } else if (!radioButton.isChecked()) {
            Toast.makeText(activity, "Accept Terms and conditions first", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    private void saveDataOnPreference(String sEmail, String sName, String mLat, String mLong, String sID,String profilepic) {
        SharedPreferencesManager.setUserID(activity,sID);
        SharedPreferencesManager.setEmail(activity,sEmail);
        SharedPreferencesManager.setUsername(activity,sName);
        SharedPreferencesManager.setLatitude(activity,mLat);
        SharedPreferencesManager.setLongitude(activity,mLong);
        SharedPreferencesManager.setProfileImage(activity,profilepic);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(this.getClass().getSimpleName());
        }
    }
}