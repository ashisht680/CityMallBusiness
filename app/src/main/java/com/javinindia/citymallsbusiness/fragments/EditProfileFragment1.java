package com.javinindia.citymallsbusiness.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.shoperprofileparsing.ShopViewResponse;
import com.javinindia.citymallsbusiness.apiparsing.shopmalllistparsing.ShopMallListResponseParsing;
import com.javinindia.citymallsbusiness.apiparsing.stateparsing.CityMasterParsing;
import com.javinindia.citymallsbusiness.apiparsing.stateparsing.CountryMasterApiParsing;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.recyclerview.ShopCategoryAdaptar;
import com.javinindia.citymallsbusiness.utility.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ashish on 12-10-2016.
 */
public class EditProfileFragment1 extends BaseFragment implements View.OnClickListener, ListShopProductCategoryFragment.OnCallBackCategoryListListener {
    private RequestQueue requestQueue;

    ImageView imgProfilePic,imgProfilePicNotFound;
    AppCompatEditText etStoreName, etOwner, etEmailAddress, etMobile, etLandLine, etMall, etStoreNum,
            etFloor, etStartTime, etEndTime, etState, etCity;
    RelativeLayout rlUpadteImg;
    AppCompatTextView txtUpdate, txtOwnerHd, txtEmailHd, txtMobileHd, txtLandLineHd,
            txtMallHd, txtStoreNumHd, txtFloorHd, txtTimingAbout, txtStateHd, txtCityHd;
    // AppCompatTextView txtAddNewCategory;

    Calendar calendar;
    TimePickerDialog timepickerdialog;
    private int CalendarHour, CalendarMinute;
    String format, mallId;

    public ArrayList<String> stateList = new ArrayList<>();
    String stateArray[] = null;
    public ArrayList<String> cityList = new ArrayList<>();
    String cityArray[] = null;
    public ArrayList<String> mallList = new ArrayList<>();
    String mallArray[] = null;

    private Uri mImageCaptureUri;
    private ImageView mImageView;
    private android.app.AlertDialog dialog;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    Bitmap photo=null;
    String sPic;
    int size = 0;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;

    private File outPutFile = null;

    //  public RecyclerView gridTags;
    private ShopCategoryAdaptar adaptar;

    private OnCallBackEditProfileListener onCallBackEditProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    public interface OnCallBackEditProfileListener {
        void OnCallBackEditProfile();
    }

    public void setMyCallBackOfferListener(OnCallBackEditProfileListener callback) {
        this.onCallBackEditProfile = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        initToolbar(view);
        initialize(view);

        return view;
    }


    private void initToolbar(View view) {
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
        final ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(null);
        AppCompatTextView textView =(AppCompatTextView)view.findViewById(R.id.tittle) ;
        textView.setText("Edit Profile");
        textView.setTextColor(activity.getResources().getColor(android.R.color.white));
        textView.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sendDataOnRegistrationApi();
        captureImageInitialization();
        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
       if (menu != null){
           menu.findItem(R.id.action_changePass).setVisible(false);
           menu.findItem(R.id.action_feedback).setVisible(false);
       }
    }

    private void initialize(View view) {

        imgProfilePicNotFound = (ImageView) view.findViewById(R.id.imgProfilePicNotFound);
        imgProfilePic = (ImageView) view.findViewById(R.id.imgProfilePic);
        rlUpadteImg = (RelativeLayout) view.findViewById(R.id.rlUpadteImg);
        etStoreName = (AppCompatEditText) view.findViewById(R.id.etStoreName);
        etStoreName.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtEmailHd = (AppCompatTextView) view.findViewById(R.id.txtEmailHd);
        txtEmailHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etEmailAddress = (AppCompatEditText) view.findViewById(R.id.etEmailAddress);
        etEmailAddress.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtMobileHd = (AppCompatTextView) view.findViewById(R.id.txtMobileHd);
        txtMobileHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etMobile = (AppCompatEditText) view.findViewById(R.id.etMobile);
        etMobile.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtOwnerHd = (AppCompatTextView) view.findViewById(R.id.txtOwnerHd);
        txtOwnerHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etOwner = (AppCompatEditText) view.findViewById(R.id.etOwner);
        etOwner.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtLandLineHd = (AppCompatTextView) view.findViewById(R.id.txtLandLineHd);
        txtLandLineHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etLandLine = (AppCompatEditText) view.findViewById(R.id.etLandLine);
        etLandLine.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtStateHd = (AppCompatTextView) view.findViewById(R.id.txtStateHd);
        txtStateHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etState = (AppCompatEditText) view.findViewById(R.id.etState);
        etState.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtCityHd = (AppCompatTextView) view.findViewById(R.id.txtCityHd);
        txtCityHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etCity = (AppCompatEditText) view.findViewById(R.id.etCity);
        etCity.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtMallHd = (AppCompatTextView) view.findViewById(R.id.txtStoreNumHd);
        txtMallHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etMall = (AppCompatEditText) view.findViewById(R.id.etMall);
        etMall.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtStoreNumHd = (AppCompatTextView) view.findViewById(R.id.txtStoreNumHd);
        txtStoreNumHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etStoreNum = (AppCompatEditText) view.findViewById(R.id.etStoreNum);
        etStoreNum.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtFloorHd = (AppCompatTextView) view.findViewById(R.id.txtFloorHd);
        txtFloorHd.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etFloor = (AppCompatEditText) view.findViewById(R.id.etFloor);
        etFloor.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtTimingAbout = (AppCompatTextView) view.findViewById(R.id.txtTimingAbout);
        txtTimingAbout.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etStartTime = (AppCompatEditText) view.findViewById(R.id.etStartTime);
        etStartTime.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etEndTime = (AppCompatEditText) view.findViewById(R.id.etEndTime);
        etEndTime.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtUpdate = (AppCompatTextView) view.findViewById(R.id.txtUpdate);
        txtUpdate.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());

        etState.setOnClickListener(this);
        etCity.setOnClickListener(this);
        etMall.setOnClickListener(this);
        etStartTime.setOnClickListener(this);
        etEndTime.setOnClickListener(this);
        txtUpdate.setOnClickListener(this);
        rlUpadteImg.setOnClickListener(this);
        //  txtAddNewCategory.setOnClickListener(this);
    }

    private void sendDataOnRegistrationApi() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response edit", response);
                        String status = null, sID = null, msg = null, banner;
                        String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong, sStoreNum, sFloorNum;
                        String shopCategory, shopSubCategory, country, pincode, rating, mallOpenTime, mallCloseTime, distance, dicription, shopOpenTime, shopCloseTime;
                        int offerCount;
                        loading.dismiss();
                        ShopViewResponse shopViewResponse = new ShopViewResponse();
                        shopViewResponse.responseParseMethod(response);
                        status = shopViewResponse.getStatus().trim();
                        if (status.equalsIgnoreCase("true") && !status.isEmpty()) {
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
                            mallId = shopViewResponse.getMallId().trim();
                            mName = shopViewResponse.getMallName().trim();
                            mAddress = shopViewResponse.getMallAddress();
                            mLat = shopViewResponse.getMallLat();
                            mLong = shopViewResponse.getMallLong();
                            country = shopViewResponse.getCountry().trim();
                            pincode = shopViewResponse.getPincode().trim();
                            rating = shopViewResponse.getRating().trim();
                            mallOpenTime = shopViewResponse.getOpenTime().trim();
                            mallCloseTime = shopViewResponse.getCloseTime().trim();
                            distance = shopViewResponse.getDistance().trim();
                            offerCount = shopViewResponse.getOfferCount();
                            dicription = shopViewResponse.getDescription().trim();
                            shopOpenTime = shopViewResponse.getShopOpenTime().trim();
                            shopCloseTime = shopViewResponse.getShopCloseTime().trim();
                            banner = shopViewResponse.getBanner().trim();
                            sStoreNum = shopViewResponse.getShopNum().trim();
                            sFloorNum = shopViewResponse.getFloor().trim();
                            if (!TextUtils.isEmpty(sName)) {
                                etStoreName.setText(Html.fromHtml(sName));
                            }
                            if (!TextUtils.isEmpty(oName)) {
                                etOwner.setText(Html.fromHtml(oName));
                            }
                            if (!TextUtils.isEmpty(sEmail)) {
                                etEmailAddress.setText(Html.fromHtml(sEmail));
                            }
                            if (!TextUtils.isEmpty(sMobileNum)) {
                                etMobile.setText(Html.fromHtml(sMobileNum));
                            }
                            if (!TextUtils.isEmpty(sLandline)) {
                                etLandLine.setText(Html.fromHtml(sLandline));
                            }
                            if (!TextUtils.isEmpty(sState)){
                                etState.setText(Html.fromHtml(sState));
                            }
                            if (!TextUtils.isEmpty(sCity)){
                                etCity.setText(Html.fromHtml(sCity));
                            }
                            if (!TextUtils.isEmpty(mName)) {
                                etMall.setText(Html.fromHtml(mName));
                            }
                            if (!TextUtils.isEmpty(sStoreNum)) {
                                etStoreNum.setText(Html.fromHtml(sStoreNum));
                            }
                            if (!TextUtils.isEmpty(sFloorNum)) {
                                etFloor.setText(Html.fromHtml(sFloorNum));
                            }
                            if (!TextUtils.isEmpty(shopOpenTime)) {
                                etStartTime.setText(Html.fromHtml(shopOpenTime));
                            }
                            if (!TextUtils.isEmpty(shopCloseTime)) {
                                etEndTime.setText(Html.fromHtml(shopCloseTime));
                            }
                            if (!TextUtils.isEmpty(sPic))
                                Utility.imageLoadGlideLibraryPic(activity, imgProfilePicNotFound, imgProfilePic, sPic);

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

    @Override
    protected int getFragmentLayout() {
        return R.layout.edit_profile_layout;
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
        int i;
        switch (v.getId()) {
            case R.id.etStartTime:
                i = 0;
                startTimeMethod(i);
                break;
            case R.id.etEndTime:
                i = 1;
                startTimeMethod(i);
                break;
            case R.id.txtUpdate:
                methodUpdateView();
                break;
            case R.id.rlUpadteImg:
                dialog.show();
                break;
            case R.id.etState:
                methodState();
                break;
            case R.id.etCity:
                if (!TextUtils.isEmpty(etState.getText())) {
                    methodCity();
                } else {
                    Toast.makeText(activity, "Select State first", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.etMall:
                if (!TextUtils.isEmpty(etCity.getText())) {
                    methodMallList();
                } else {
                    Toast.makeText(activity, "Select City first", Toast.LENGTH_LONG).show();
                }
                break;
          /*  case R.id.txtAddNewCategory:
                ListShopProductCategoryFragment categoryFragment = new ListShopProductCategoryFragment();
                categoryFragment.setMyCallBackCategoryListener(this);
                callFragmentMethod(categoryFragment, this.getClass().getSimpleName(),R.id.navigationContainer);
                break;*/
        }
    }

    private void methodUpdateView() {
        String open = etStartTime.getText().toString().trim();
        String close = etEndTime.getText().toString().trim();
        String store = etStoreName.getText().toString().trim();
        String owner = etOwner.getText().toString().trim();
        String email = etEmailAddress.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String landLine = etLandLine.getText().toString().trim();
        String mall = etMall.getText().toString().trim();
        String storeNo = etStoreNum.getText().toString().trim();
        String floor = etFloor.getText().toString().trim();
        if (TextUtils.isEmpty(store)) {
            Toast.makeText(activity, "Please write Store name", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(owner)) {
            Toast.makeText(activity, "Please write Owner name", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(activity, "Please write email", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(activity, "Please write Mobile number", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(mall)) {
            Toast.makeText(activity, "Please write Mall", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(floor)) {
            Toast.makeText(activity, "Please write Floor number", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(open)) {
            Toast.makeText(activity, "Please select Opening timing", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(close)) {
            Toast.makeText(activity, "Please select Closing timing", Toast.LENGTH_LONG).show();
        } else {
            methodSubbmit(store, owner, email, mobile, landLine, mall, storeNo, floor, open, close);
        }
    }

    private void methodSubbmit(final String store, final String owner, final String email, final String mobile, final String landLine, final String mall, final String storeNo, final String floor, final String open, final String close) {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATE_SHOP_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response edit", response);
                        String status = null, sID = null, msg = null, sPic = null, banner;
                        String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong;
                        String shopCategory, shopSubCategory, country, pincode, rating, openTime, closeTime, distance;
                        int offerCount;
                        loading.dismiss();
                        ShopViewResponse shopViewResponse = new ShopViewResponse();
                        shopViewResponse.responseParseMethod(response);

                        if (shopViewResponse.getStatus().equals("true")) {
                            status = shopViewResponse.getStatus().trim();
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
                            banner = shopViewResponse.getBanner().trim();
                            saveDataOnPreference(sEmail, sName, mLat, mLong, sID, sPic, oName);
                            onCallBackEditProfile.OnCallBackEditProfile();
                           // activity.onBackPressed();
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
                params.put("shopId", SharedPreferencesManager.getUserID(activity));
                // params.put("description", disc);
                params.put("shopOpenTime", open);
                params.put("shopClosetime", close);
                params.put("shopName", store);
                params.put("ownerName", owner);
                params.put("shopMobile", mobile);
                // params.put("address", close);
                params.put("shopNo", storeNo);
                params.put("floorNo", floor);
                //  params.put("shopProfilePic", close);
                params.put("landline", landLine);
                params.put("mall", mallId);
                params.put("email", email);
                if (photo != null) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] data = bos.toByteArray();
                    String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);
                    params.put("shopProfilePic", encodedImage + "image/jpeg");
                    params.put("action","new");
                } else {
                    params.put("shopProfilePic", sPic);
                    params.put("action","old");
                }
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


    public String encodeToBase64(Bitmap image) {
        Bitmap bitmap = image;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    private void startTimeMethod(final int i) {
        calendar = Calendar.getInstance();
        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        CalendarMinute = calendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(activity,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        if (hourOfDay == 0) {

                            hourOfDay += 12;

                            format = "AM";
                        } else if (hourOfDay == 12) {

                            format = "PM";

                        } else if (hourOfDay > 12) {

                            hourOfDay -= 12;

                            format = "PM";

                        } else {

                            format = "AM";
                        }

                        if (i == 0) {
                            etStartTime.setText(hourOfDay + ":" + minute + format);
                        } else {
                            etEndTime.setText(hourOfDay + ":" + minute + format);
                        }

                    }
                }, CalendarHour, CalendarMinute, false);
        timepickerdialog.show();

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
                                            etState.setText(stateArray[item]);
                                            etCity.setText("");
                                            etMall.setText("");
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
                                        etCity.setText(cityArray[item]);
                                        etMall.setText("");
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
                params.put("state", etState.getText().toString());
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
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
                                            etMall.setText(mallArray[item]);

                                            // Log.e("mall", shopMallListResponseParsing.getMallDetailsArrayList().get(1).getId());
                                            int index = Arrays.asList(mallArray).indexOf(mallArray[item]);
                                            ;
                                            mallId = shopMallListResponseParsing.getMallDetailsArrayList().get(index).getId();
                                            //   et_PinCode.setText(shopMallListResponseParsing.getMallDetailsArrayList().get(index).getPincode());
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
                params.put("state",etState.getText().toString().trim());
                params.put("city", etCity.getText().toString().trim());
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onCallBackCatList() {
        sendDataOnRegistrationApi();
        onCallBackEditProfile.OnCallBackEditProfile();
    }

    private void captureImageInitialization() {
        final String[] items = new String[]{"Take from camera",
                "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.select_dialog_item, items);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
                    mImageCaptureUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    startActivityForResult(intent, PICK_FROM_CAMERA);

                } else {
                    // pick from file
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, PICK_FROM_FILE);
                }
            }
        });

        dialog = builder.create();
    }

    public class CropOptionAdapter extends ArrayAdapter<CropOption> {
        private ArrayList<CropOption> mOptions;
        private LayoutInflater mInflater;

        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
            super(context, R.layout.crop_selector, options);

            mOptions = options;

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.crop_selector, null);

            CropOption item = mOptions.get(position);

            if (item != null) {
                ((ImageView) convertView.findViewById(R.id.iv_icon))
                        .setImageDrawable(item.icon);
                ((TextView) convertView.findViewById(R.id.tv_name))
                        .setText(item.title);

                return convertView;
            }

            return null;
        }
    }

    public class CropOption {
        public CharSequence title;
        public Drawable icon;
        public Intent appIntent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != activity.RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:

                doCrop();

                break;

            case PICK_FROM_FILE:

                // After selecting image from files, save the selected path
                mImageCaptureUri = data.getData();
                doCrop();
                break;

            case CROP_FROM_CAMERA:
                try {
                    if (outPutFile.exists()) {
                        photo = decodeFile(outPutFile.getAbsolutePath());

                        imgProfilePicNotFound.setImageBitmap(photo);
                        imgProfilePic.setImageBitmap(photo);
                    } else {
                        Toast.makeText(activity, "Error while save image", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    public Bitmap decodeFile(String filePath) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        final int REQUIRED_SIZE = 1024;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);
        return bitmap;
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();
        if (size == 0) {
            Toast.makeText(activity, "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);
                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));
                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();
                    co.title = activity.getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = activity.getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));
                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(
                        activity, cropOptions);
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
                builder.setTitle("Choose Crop App");
                builder.setCancelable(false);
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        CROP_FROM_CAMERA);
                            }
                        });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            activity.getContentResolver().delete(mImageCaptureUri, null,
                                    null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    return;


                }


            }
        }
    }

}