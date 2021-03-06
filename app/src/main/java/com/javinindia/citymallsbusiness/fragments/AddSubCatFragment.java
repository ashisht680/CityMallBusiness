package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.javinindia.citymallsbusiness.apiparsing.brandparsing.Brandresponse;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ProductCategory;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.utility.CheckConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 03-10-2016.
 */
public class AddSubCatFragment extends BaseFragment implements View.OnClickListener,CheckConnectionFragment.OnCallBackInternetListener {
    private RequestQueue requestQueue;
    AppCompatTextView txtSelectCategory, txtSelectSubCat, txtSelectBrand;
    AppCompatButton btnSubmit;
    AppCompatEditText etBrand;

    public ArrayList<String> categoryList = new ArrayList<>();
    String categoryArray[] = null;
    public ArrayList<String> suCatList = new ArrayList<>();
    String suCatArray[] = null;
    public ArrayList<String> brandList = new ArrayList<>();
    public ArrayList<String> brandIDList = new ArrayList<>();
    String brandArray[] = null;
    String brandIdArray[] = null;
    String catId;
    String subCatId;
    String BrandID;

    private OnCallBackCAtegoryListener callbackCat;

    @Override
    public void OnCallBackInternet() {
        activity.onBackPressed();
    }

    public interface OnCallBackCAtegoryListener {
        void onCallBackCat();
    }

    public void setMyCallBackCategoryListener(OnCallBackCAtegoryListener callback) {
        this.callbackCat = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {
            menu.findItem(R.id.action_changePass).setVisible(false);
            menu.findItem(R.id.action_feedback).setVisible(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
        AppCompatTextView textView = (AppCompatTextView) view.findViewById(R.id.tittle);
        textView.setText("Add category");
        textView.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
    }


    private void initialize(View view) {
        etBrand = (AppCompatEditText) view.findViewById(R.id.etBrand);
        etBrand.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtSelectCategory = (AppCompatTextView) view.findViewById(R.id.txtSelectCategory);
        txtSelectCategory.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtSelectSubCat = (AppCompatTextView) view.findViewById(R.id.txtSelectSubCat);
        txtSelectSubCat.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtSelectBrand = (AppCompatTextView) view.findViewById(R.id.txtSelectBrand);
        txtSelectBrand.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        btnSubmit = (AppCompatButton) view.findViewById(R.id.btnSubmit);
        btnSubmit.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        btnSubmit.setOnClickListener(this);
        txtSelectCategory.setOnClickListener(this);
        txtSelectSubCat.setOnClickListener(this);
        txtSelectBrand.setOnClickListener(this);
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
                methodCategory();
                break;
            case R.id.txtSelectSubCat:
                if (!txtSelectCategory.getText().equals("Category")) {
                    methodSubCategory();
                } else {
                    Toast.makeText(activity, "Select category first", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.txtSelectBrand:
                if (!txtSelectSubCat.getText().equals("Subcategory")) {
                    methodBrands();
                } else {
                    Toast.makeText(activity, "Select Subcategory first", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnSubmit:
                if (CheckConnection.haveNetworkConnection(activity)) {
                    if (!txtSelectCategory.getText().equals("Category")) {
                        if (!txtSelectSubCat.getText().equals("Subcategory")) {
                            if (!txtSelectBrand.getText().equals("Brands") && !etBrand.getText().toString().equals("")) {
                                Toast.makeText(activity, "Please either select brand or type brand.", Toast.LENGTH_LONG).show();
                            } else if (!txtSelectBrand.getText().equals("Brands") && etBrand.getText().toString().equals("")) {
                                methodSubmit();
                            } else if (txtSelectBrand.getText().equals("Brands") && !etBrand.getText().toString().equals("")) {
                                methodSubmit();
                            } else {
                                Toast.makeText(activity, "Select Brand or write brand please", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(activity, "Select Subcategory first", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(activity, "Select category first", Toast.LENGTH_LONG).show();
                    }
                } else {
                    methodCallCheckInternet();
                }

                break;

        }
    }


    public void methodCallCheckInternet() {
        CheckConnectionFragment fragment = new CheckConnectionFragment();
        fragment.setMyCallBackInternetListener(this);
        callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.container);
    }

    private void methodBrands() {
        brandList.removeAll(brandList);
        brandIDList.removeAll(brandIDList);
        brandArray = null;
        brandIdArray = null;
        BrandID = "";
        sendRequestOnBrand();
    }

    private void sendRequestOnBrand() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_BRAND_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        final Brandresponse cityMasterParsing = new Brandresponse();
                        cityMasterParsing.responseImplement(response);
                        if (cityMasterParsing.getStatus().equals("true")) {
                            if (cityMasterParsing.getBrandDetailArrayList().size() > 0) {
                                for (int i = 0; i < cityMasterParsing.getBrandDetailArrayList().size(); i++) {
                                    brandList.add(cityMasterParsing.getBrandDetailArrayList().get(i).getName().trim());
                                    brandIDList.add(cityMasterParsing.getBrandDetailArrayList().get(i).getId().trim());
                                }
                                if (brandList.size() > 0) {
                                    brandArray = new String[brandList.size()];
                                    brandList.toArray(brandArray);
                                    brandIdArray = new String[brandIDList.size()];
                                    brandIDList.toArray(brandIdArray);

                                    if (brandList != null && brandList.size() > 0) {

                                        AlertDialog.Builder d = new AlertDialog.Builder(activity);
                                        d.setTitle("Select Brands");
                                        final ArrayList<String> data = new ArrayList<>();
                                        final ArrayList<String> dataId = new ArrayList<>();
                                        d.setMultiChoiceItems(brandArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                                String str = brandArray[which];
                                                String strId = brandIdArray[which];

                                                if (isChecked) {
                                                    data.add(str);
                                                    dataId.add(strId);
                                                } else if (data.contains(which) && dataId.contains(which)) {
                                                    data.remove(str);
                                                    dataId.remove(strId);
                                                } else if (!isChecked) {
                                                    data.remove(str);
                                                    dataId.remove(strId);
                                                }
                                            }

                                        });
                                        d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {


                                                String str = Arrays.toString(data.toArray());
                                                String test = str.replaceAll("[\\[\\](){}]", "");
                                                String strID = Arrays.toString(dataId.toArray());
                                                BrandID = strID.replaceAll("[\\[\\](){}]", "");
                                                if (data.size() == 1) {
                                                    txtSelectBrand.setText(test);
                                                } else if (data.size() >= 2) {
                                                    int index = test.lastIndexOf(",");
                                                    StringBuilder myName = new StringBuilder(test);
                                                    myName.deleteCharAt(index);
                                                    myName.insert(index, " and");
                                                    txtSelectBrand.setText(myName);
                                                } else {
                                                    txtSelectBrand.setText(test);
                                                }

                                                dialog.dismiss();


                                            }
                                        });
                                        d.setNegativeButton("Cancel", null);
                                        d.show();
                                    }
                                } else {
                                    showDialogMethod("There are no Brand under this category");
                                }
                            } else {
                                showDialogMethod("There are no Brand under this category");
                            }
                        } else {
                            showDialogMethod("There are no Brand under this category");
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
                params.put("offerCatId", catId);
                params.put("offerSubCatId", subCatId);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void methodSubmit() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.INSERT_SHOP_CATEGORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
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
                            txtSelectCategory.setText("Category");
                            txtSelectSubCat.setText("Subcategory");
                            txtSelectBrand.setText("Brands");
                            etBrand.setText(" ");
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
                params.put("shopCatId", catId);
                params.put("shopSubCatId", subCatId);

                if (!txtSelectBrand.getText().equals("Brands") && etBrand.getText().toString().trim().equals("")) {
                    params.put("brands", BrandID);
                    params.put("newbrand", "na");

                } else if (txtSelectBrand.getText().equals("Brands") && !etBrand.getText().toString().trim().equals("")) {
                    params.put("brands", "na");
                    params.put("newbrand", etBrand.getText().toString().trim());
                }
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void methodCategory() {
        categoryList.removeAll(categoryList);
        categoryArray = null;
        sendRequestOnCategory();
    }

    private void sendRequestOnCategory() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_CATEGORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        final ProductCategory countryMasterApiParsing = new ProductCategory();
                        countryMasterApiParsing.responseImplement(response);
                        if (countryMasterApiParsing.getStatus().equals("true")) {
                            if (countryMasterApiParsing.getShopCategoryDetailsArrayList().size() > 0) {
                                for (int i = 0; i < countryMasterApiParsing.getShopCategoryDetailsArrayList().size(); i++) {
                                    categoryList.add(countryMasterApiParsing.getShopCategoryDetailsArrayList().get(i).getShopCategory().trim());
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
                                                txtSelectCategory.setText(categoryArray[item]);
                                                txtSelectSubCat.setText("Subcategory");
                                                txtSelectBrand.setText("Brands");
                                                int index = Arrays.asList(categoryArray).indexOf(categoryArray[item]);
                                                catId = countryMasterApiParsing.getShopCategoryDetailsArrayList().get(index).getId().trim();
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.create();
                                        builder.show();
                                    }
                                }
                            } else {
                                //A53) please change message to - There are no brands under this category.
                                showDialogMethod("There are no Category");
                            }
                        } else {
                            showDialogMethod("There are no Category");
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

    private void methodSubCategory() {
        suCatList.removeAll(suCatList);
        suCatArray = null;

        sendRequestOnSubCategory();
    }

    private void sendRequestOnSubCategory() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_SUBCATEGORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        final ProductCategory cityMasterParsing = new ProductCategory();
                        cityMasterParsing.responseImplement(response);
                        if (cityMasterParsing.getStatus().equals("true")) {
                            if (cityMasterParsing.getShopCategoryDetailsArrayList().size() > 0) {
                                for (int i = 0; i < cityMasterParsing.getShopCategoryDetailsArrayList().size(); i++) {
                                    suCatList.add(cityMasterParsing.getShopCategoryDetailsArrayList().get(i).getShopCategory().trim());
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
                                                txtSelectSubCat.setText(suCatArray[item]);
                                                txtSelectBrand.setText("Brands");
                                                int index = Arrays.asList(suCatArray).indexOf(suCatArray[item]);
                                                subCatId = cityMasterParsing.getShopCategoryDetailsArrayList().get(index).getId().trim();
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.create();
                                        builder.show();
                                    }
                                }
                            } else {
                                showDialogMethod("There are no Subcategory under this category");
                            }
                        } else {
                            showDialogMethod("There are no Subcategory under this category");
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
