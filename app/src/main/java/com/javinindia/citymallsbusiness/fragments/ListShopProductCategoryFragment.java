package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ShopCategoryList;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ShopCategoryListResponse;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.recyclerview.ShopCatSubCatAdaptar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 03-10-2016.
 */
public class ListShopProductCategoryFragment extends BaseFragment  implements ShopCatSubCatAdaptar.MyClickListener,View.OnClickListener,AddSubCatFragment.OnCallBackCAtegoryListener {
    private ArrayList list;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RequestQueue requestQueue;
    private ShopCatSubCatAdaptar mAdapter;
    private RecyclerView recyclerView;
    AppCompatTextView txtNoData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        showLoader();
        Bundle bundle = this.getArguments();
        sendRequestOnWowFeed();
        initializeMethod(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    private void initializeMethod(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewCatList);
        LinearLayoutManager layoutMangerDestination
                = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutMangerDestination);
        mAdapter = new ShopCatSubCatAdaptar(activity);
        mAdapter.setMyClickListener(ListShopProductCategoryFragment.this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        txtNoData = (AppCompatTextView) view.findViewById(R.id.txtNoData);
       // txtNoData.setTypeface(FontRalewayMediumSingleTonClass.getInstance(activity).getTypeFace());
        FloatingActionButton flotBtnAddCat = (FloatingActionButton)view.findViewById(R.id.flotBtnAddCat);
        flotBtnAddCat.setOnClickListener(this);

    }

    private void sendRequestOnWowFeed() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_CAT_SUBCAT_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("sdffdf",response);
                        loading.dismiss();
                        ShopCategoryListResponse wowListResponseParsing = new ShopCategoryListResponse();
                        wowListResponseParsing.responseParseMethod(response);
                        list = wowListResponseParsing.getShopCategoryListArrayList();
                        if (list != null && list.size() > 0) {
                            txtNoData.setVisibility(View.GONE);
                            if (mAdapter.getData() != null && mAdapter.getData().size() > 0) {
                                mAdapter.getData().addAll(list);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.setData(list);
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            txtNoData.setVisibility(View.VISIBLE);
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
        return R.layout.cat_subcat_view_list_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }


    @Override
    public void onItemClick(final int position, ShopCategoryList categoryList) {
        final String id =categoryList.getId();
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("Are you sure you want to delete?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        methodPostDelete(id,position);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void methodPostDelete(final String id, final int position) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_SHOP_CATEGORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideLoader();
                        Log.e("delete",response);
                        JSONObject jsonObject = null;
                        String status = null, userid = null, msg = null, username = null, phone = null, gender = null, email = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.has("status"))
                                status = jsonObject.optString("status");
                            if (status.equals("true")) {
                                mAdapter.deleteItem(position);
                                mAdapter.notifyItemChanged(position);
                                if (list.size()==0){
                                    txtNoData.setVisibility(View.VISIBLE);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideLoader();
                        //volleyErrorHandle(error);
                        noInternetToast(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",id);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flotBtnAddCat:
                AddSubCatFragment fragment1 = new AddSubCatFragment();
                fragment1.setMyCallBackCategoryListener(this);
                callFragmentMethod(fragment1, this.getClass().getSimpleName(),R.id.navigationContainer);
                break;

        }
    }

    @Override
    public void onCallBackCat() {
        list.removeAll(list);
        mAdapter.notifyDataSetChanged();
        mAdapter.setData(list);
        if (list.size() > 0) {
        } else {
            sendRequestOnWowFeed();
        }
    }
}
