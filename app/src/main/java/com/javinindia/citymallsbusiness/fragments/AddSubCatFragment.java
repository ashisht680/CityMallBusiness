package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ProductCategory;
import com.javinindia.citymallsbusiness.apiparsing.stateparsing.CityMasterParsing;
import com.javinindia.citymallsbusiness.apiparsing.stateparsing.CountryMasterApiParsing;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 03-10-2016.
 */
public class AddSubCatFragment extends BaseFragment implements View.OnClickListener {
    private RequestQueue requestQueue;
    AppCompatTextView txtSelectCategory, txtSelectSubCat;
    AppCompatButton btnSubmit;

    public ArrayList<String> categoryList = new ArrayList<>();
    String categoryArray[] = null;
    public ArrayList<String> suCatList = new ArrayList<>();
    String suCatArray[] = null;
    String catId;
    String subCatId;

    private OnCallBackCAtegoryListener callbackCat;

    public interface OnCallBackCAtegoryListener {
        void onCallBackCat();
    }

    public void setMyCallBackCategoryListener(OnCallBackCAtegoryListener callback) {
        this.callbackCat = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        txtSelectCategory = (AppCompatTextView) view.findViewById(R.id.txtSelectCategory);
        txtSelectSubCat = (AppCompatTextView) view.findViewById(R.id.txtSelectSubCat);
        btnSubmit = (AppCompatButton) view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        txtSelectCategory.setOnClickListener(this);
        txtSelectSubCat.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.add_sub_cat_layout;
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
            case R.id.txtSelectCategory:
                methodState();
                break;
            case R.id.txtSelectSubCat:
                if (!txtSelectCategory.getText().equals("Category")) {
                    methodCity();
                } else {
                    Toast.makeText(activity, "Select category first", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnSubmit:
                methodSubmit();
                break;

        }
    }

    private void methodSubmit() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.INSERT_SHOP_CATEGORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.e("MasterTags", response);
                        JSONObject jsonObject = null;
                        String status = null, userId = null, socialId = null, msg = null;
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
                            callbackCat.onCallBackCat();
                            activity.onBackPressed();
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
                params.put("shopCatId", catId);
                params.put("shopSubCatId", subCatId);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void methodState() {
        categoryList.removeAll(categoryList);
        categoryArray = null;
        sendRequestOnState();
    }

    private void sendRequestOnState() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_CATEGORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.e("MasterTags", response);
                        hideLoader();
                        final ProductCategory countryMasterApiParsing = new ProductCategory();
                        countryMasterApiParsing.responseImplement(response);
                        if (countryMasterApiParsing.getShopCategoryDetailsArrayList().size() > 0) {
                            for (int i = 0; i < countryMasterApiParsing.getShopCategoryDetailsArrayList().size(); i++) {
                                categoryList.add(countryMasterApiParsing.getShopCategoryDetailsArrayList().get(i).getShopCategory());
                            }
                            if (categoryList.size() > 0) {
                                categoryArray = new String[categoryList.size()];
                                categoryList.toArray(categoryArray);

                                if (categoryList != null && categoryList.size() > 0) {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                                    builder.setTitle("Select Category");
                                    builder.setNegativeButton(android.R.string.cancel, null);
                                    builder.setItems(categoryArray, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int item) {
                                            // Do something with the selection
                                            txtSelectCategory.setText(categoryArray[item]);
                                            txtSelectSubCat.setText("Subcategory");
                                            int index = Arrays.asList(categoryArray).indexOf(categoryArray[item]);
                                            ;
                                            catId = countryMasterApiParsing.getShopCategoryDetailsArrayList().get(index).getId();
                                            // et_PinCode.setText(shopMallListResponseParsing.getMallDetailsArrayList().get(index).getPincode());
                                            Log.e("catId", catId + "\t" + index);
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
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void methodCity() {
        suCatList.removeAll(suCatList);
        suCatArray = null;

        sendRequestOnCity();
    }

    private void sendRequestOnCity() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_SUBCATEGORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        //  progressDialog.dismiss();
                        Log.e("MasterTags", response);
                        hideLoader();
                        final ProductCategory cityMasterParsing = new ProductCategory();
                        cityMasterParsing.responseImplement(response);
                        for (int i = 0; i < cityMasterParsing.getShopCategoryDetailsArrayList().size(); i++) {
                            suCatList.add(cityMasterParsing.getShopCategoryDetailsArrayList().get(i).getShopCategory());
                        }
                        if (suCatList.size() > 0) {
                            suCatArray = new String[suCatList.size()];
                            suCatList.toArray(suCatArray);

                            if (suCatList != null && suCatList.size() > 0) {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
                                builder.setTitle("Select Subcategory");
                                builder.setNegativeButton(android.R.string.cancel, null);
                                builder.setItems(suCatArray, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        // Do something with the selection
                                        txtSelectSubCat.setText(suCatArray[item]);
                                        int index = Arrays.asList(categoryArray).indexOf(categoryArray[item]);
                                        ;
                                        subCatId = cityMasterParsing.getShopCategoryDetailsArrayList().get(index).getId();
                                        // et_PinCode.setText(shopMallListResponseParsing.getMallDetailsArrayList().get(index).getPincode());
                                        Log.e("subCatId", subCatId + "\t" + index);
                                        dialog.dismiss();
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
                params.put("id", catId);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }


}
