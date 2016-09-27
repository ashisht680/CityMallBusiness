package com.javinindia.citymallsbusiness.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 27-09-2016.
 */
public class BannerFragment extends BaseFragment implements View.OnClickListener {
    private RequestQueue requestQueue;

    ProgressBar progressBar;
    ImageView imgBannerAdd;
    private String picturePath;
    private static final int PICK_FROM_FILE = 3;

    private OnCallBackBannerListener callback;

    public interface OnCallBackBannerListener {
        void onCallBack();
    }

    public void setMyCallBackBannerListener(OnCallBackBannerListener callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    private void initialize(View view) {
        imgBannerAdd = (ImageView) view.findViewById(R.id.imgBannerAdd);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        AppCompatTextView txtBack = (AppCompatTextView) view.findViewById(R.id.txtBack);
        AppCompatButton btnUpdateBanner = (AppCompatButton) view.findViewById(R.id.btnUpdateBanner);
        AppCompatTextView txtAddBanner = (AppCompatTextView) view.findViewById(R.id.txtAddBanner);
        txtAddBanner.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        btnUpdateBanner.setOnClickListener(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.banner_layout;
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
            case R.id.txtBack:
                activity.onBackPressed();
                break;
            case R.id.btnUpdateBanner:
                methodUpdateAvtar();
                break;
            case R.id.txtAddBanner:
                Intent in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in, PICK_FROM_FILE);
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
            imgBannerAdd.setImageBitmap(decodeFile(picturePath));
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

    private void methodUpdateAvtar() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATE_SHOP_BANNER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("res img", response);
                        progressBar.setVisibility(View.GONE);
                        callback.onCallBack();
                        activity.onBackPressed();
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
                    Log.e("uri", picturePath);
                    params.put("image", encodedImage + "image/jpeg");
                    Log.e("encodedImage", encodedImage + "image/jpeg");
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

}
