package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

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
import com.javinindia.citymallsbusiness.font.FontAsapBoldSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.recyclerview.ShopCatSubCatAdaptar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Ashish on 03-10-2016.
 */
public class ListShopProductCategoryFragment extends BaseFragment  implements ShopCatSubCatAdaptar.MyClickListener,View.OnClickListener,AddSubCatFragment.OnCallBackCAtegoryListener, TextWatcher {
    private ArrayList list;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RequestQueue requestQueue;
    private ShopCatSubCatAdaptar mAdapter;
    private RecyclerView recyclerView;
    AppCompatTextView txtNoData,txtTitle;
    AppCompatEditText etSearchCat;
    LinearLayout llMain;

    private OnCallBackCategoryListListener onCallBackCategoryList;

    public interface OnCallBackCategoryListListener {
        void onCallBackCatList();
    }

    public void setMyCallBackCategoryListener(OnCallBackCategoryListListener callback) {
        this.onCallBackCategoryList = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null){
            menu.findItem(R.id.action_changePass).setVisible(false);
            menu.findItem(R.id.action_feedback).setVisible(false);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initToolbar(view);
        sendRequestOnWowFeed();
        initializeMethod(view);
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
        textView.setText("Shop Categories");
        textView.setTextColor(activity.getResources().getColor(android.R.color.white));
        textView.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    private void initializeMethod(View view) {
        llMain = (LinearLayout)view.findViewById(R.id.llMain);
        txtTitle = (AppCompatTextView)view.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        etSearchCat = (AppCompatEditText)view.findViewById(R.id.etSearchCat);
        etSearchCat.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etSearchCat.addTextChangedListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewCatList);
        LinearLayoutManager layoutMangerDestination
                = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutMangerDestination);
        mAdapter = new ShopCatSubCatAdaptar(activity);
        mAdapter.setMyClickListener(ListShopProductCategoryFragment.this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        txtNoData = (AppCompatTextView) view.findViewById(R.id.txtNoData);
        txtNoData.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
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
                        if (wowListResponseParsing.getStatus().equals("true")){
                            if (wowListResponseParsing.getShopCategoryListArrayList().size()>0){
                                list = wowListResponseParsing.getShopCategoryListArrayList();
                                if (list != null && list.size() > 0) {
                                    txtNoData.setVisibility(View.GONE);
                                    llMain.setVisibility(View.VISIBLE);
                                    if (mAdapter.getData() != null && mAdapter.getData().size() > 0) {
                                        mAdapter.getData().addAll(list);
                                        mAdapter.notifyDataSetChanged();
                                    } else {
                                        mAdapter.setData(list);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    txtNoData.setVisibility(View.VISIBLE);
                                    llMain.setVisibility(View.GONE);
                                }
                            }
                        }else {
                            txtNoData.setVisibility(View.VISIBLE);
                            llMain.setVisibility(View.GONE);
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
                                    llMain.setVisibility(View.VISIBLE);
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
                callFragmentMethod(fragment1, this.getClass().getSimpleName(),R.id.container);
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
//            onCallBackCategoryList.onCallBackCatList();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString().toLowerCase(Locale.getDefault());
        mAdapter.filter(text);
    }
}
