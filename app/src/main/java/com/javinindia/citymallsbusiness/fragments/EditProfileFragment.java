package com.javinindia.citymallsbusiness.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 16-09-2016.
 */
public class EditProfileFragment extends BaseFragment implements View.OnClickListener,BannerFragment.OnCallBackBannerListener {
    private RequestQueue requestQueue;
    AppCompatEditText etStoreName, etAddress, etDiscription, etStartTime, etEndTime;
    AppCompatTextView txtCategoryAbout, txtChooseCategory;
    AppCompatButton btnSubmitOffer;

    Calendar calendar;
    TimePickerDialog timepickerdialog;
    private int CalendarHour, CalendarMinute;
    String format;

    ProgressBar progressBar;
    ImageView profileImageView,imgEditBanner,imgBanner;
    private String picturePath;
    private static final int PICK_FROM_FILE = 3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize(view);
        sendDataOnRegistrationApi();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    private void initialize(View view) {
        profileImageView = (ImageView) view.findViewById(R.id.imgShopLogoAbout);
        imgBanner  = (ImageView)view.findViewById(R.id.imgBanner);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        etStoreName = (AppCompatEditText) view.findViewById(R.id.etStoreName);
        etAddress = (AppCompatEditText) view.findViewById(R.id.etAddress);
        etDiscription = (AppCompatEditText) view.findViewById(R.id.etDiscription);
       // txtCategoryAbout = (AppCompatTextView) view.findViewById(R.id.txtCategoryAbout);
        etStartTime = (AppCompatEditText) view.findViewById(R.id.etStartTime);
        etEndTime = (AppCompatEditText) view.findViewById(R.id.etEndTime);
        txtChooseCategory = (AppCompatTextView) view.findViewById(R.id.txtChooseCategory);
        btnSubmitOffer = (AppCompatButton) view.findViewById(R.id.btnSubmitOffer);
        imgEditBanner = (ImageView)view.findViewById(R.id.imgEditBanner);

        etStartTime.setOnClickListener(this);
        etEndTime.setOnClickListener(this);
        btnSubmitOffer.setOnClickListener(this);
        txtChooseCategory.setOnClickListener(this);
        profileImageView.setOnClickListener(this);
        imgEditBanner.setOnClickListener(this);
    }

    private void sendDataOnRegistrationApi() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        String status = null, sID = null, msg = null, sPic = null,banner,mallId;
                        String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong;
                        String shopCategory, shopSubCategory, country, pincode, rating, mallOpenTime, mallCloseTime, distance, dicription,shopOpenTime,shopCloseTime;
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
                            if (!TextUtils.isEmpty(sName)) {
                                etStoreName.setText(sName);
                            }
                            if (!TextUtils.isEmpty(shopOpenTime)) {
                                etStartTime.setText(shopOpenTime);
                            }
                            if (!TextUtils.isEmpty(shopCloseTime)) {
                                etEndTime.setText(shopCloseTime);
                            }
                            if (!TextUtils.isEmpty(sAddress)) {
                                etAddress.setText(sAddress +","+ sCity + "," + sState + "," + pincode);
                            }
                            if (!TextUtils.isEmpty(dicription)) {
                                etDiscription.setText(dicription);
                            }
                            if (sPic != null)
                                Utility.imageLoadGlideLibrary(activity,progressBar, profileImageView, sPic);
                            if (banner != null)
                                Utility.imageLoadGlideLibrary(activity,progressBar, imgBanner, banner);


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
        return R.layout.edit_about_layout;
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
            case R.id.txtChooseCategory:
                ListShopProductCategoryFragment categoryFragment = new ListShopProductCategoryFragment();
               // categoryFragment.setMyCallBackBannerListener(this);
                callFragmentMethod(categoryFragment, this.getClass().getSimpleName(),R.id.navigationContainer);
                break;
            case R.id.btnSubmitOffer:
                methodUpdateView();
                break;
            case R.id.imgShopLogoAbout:
                Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, PICK_FROM_FILE);
                break;
            case R.id.imgEditBanner:
                BannerFragment fragment1 = new BannerFragment();
                fragment1.setMyCallBackBannerListener(this);
                callFragmentMethod(fragment1, this.getClass().getSimpleName(),R.id.navigationContainer);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_FILE && resultCode == activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            profileImageView.setImageBitmap(decodeFile(picturePath));
            cursor.close();
        }
    }

    public Bitmap decodeFile(String filePath) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        final int REQUIRED_SIZE = 720;
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


    private void methodUpdateView() {
        methodUpdateAvtar();
        if (etStartTime.getText().equals("")) {
            Toast.makeText(activity, "Please select opening timing", Toast.LENGTH_LONG).show();
        } else if (etEndTime.getText().equals("")) {
            Toast.makeText(activity, "Please select closing timing", Toast.LENGTH_LONG).show();
        } else if (etDiscription.getText().equals("")) {
            Toast.makeText(activity, "Please write description", Toast.LENGTH_LONG).show();
        } else {
            String open = etStartTime.getText().toString().trim();
            String close = etEndTime.getText().toString().trim();
            String disc = etDiscription.getText().toString().trim();
            methodSubbmit(open, close, disc);
        }
    }

    private void methodUpdateAvtar() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATE_SHOP_LOGO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("res img",response);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorHandle(error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Bitmap bitmap = null;
                String encodedImage = null;
                if (picturePath != null)
                    bitmap = decodeFile(picturePath);
                if (bitmap != null) {
                    encodedImage = encodeToBase64(bitmap);
                }
                params.put("shopId", SharedPreferencesManager.getUserID(activity));
                if (TextUtils.isEmpty(picturePath)) {
                    params.put("image", "");
                } else {
                    Log.e("uri",picturePath);
                    params.put("image", encodedImage + "image/jpeg");
                    Log.e("encodedImage",encodedImage + "image/jpeg");
                }
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    public String encodeToBase64(Bitmap image) {
        Bitmap bitmap = image;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    private void methodSubbmit(final String open, final String close, final String disc) {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATE_SHOP_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response up", response);
                        loading.dismiss();
                        JSONObject jsonObject = null;
                        String status = null, msg = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.has("status"))
                                status = jsonObject.optString("status");
                            if (jsonObject.has("msg"))
                                msg = jsonObject.optString("msg");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (status.equals("true") && !TextUtils.isEmpty(status)) {
                            sendDataOnRegistrationApi();
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
                params.put("description", disc);
                params.put("shopOpenTime", open);
                params.put("shopClosetime", close);
                params.put("shopName", disc);
                params.put("ownerName", open);
                params.put("shopMobile", close);
                params.put("state", disc);
                params.put("city", open);
                params.put("address", close);
                params.put("shopNo", disc);
                params.put("floorNo", open);
                params.put("shopProfilePic", close);
                params.put("landline", disc);
                params.put("mall", open);
                params.put("email", close);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
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

    @Override
    public void onCallBack() {
        sendDataOnRegistrationApi();
    }
}



