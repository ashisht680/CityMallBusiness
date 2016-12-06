package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.TimeUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.DetailsList;
import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.OfferListResponseparsing;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.recyclerview.OfferAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 22-10-2016.
 */
public class AllOffersFragment extends BaseFragment implements OfferAdapter.MyClickListener, UpdateOfferFragment.OnCallBackUpdateOfferListener {
    private RecyclerView recyclerview;
    private int startLimit = 0;
    private int countLimit = 10;
    private boolean loading = true;
    private RequestQueue requestQueue;
    private OfferAdapter adapter;
    ArrayList arrayList;
    AppCompatTextView txtDataNotFound;

    private OnCallBackRefreshListener onCallBackRefreshListener;

    public interface OnCallBackRefreshListener {
        void OnCallBackRefreshOffer();
    }

    public void setMyCallBackRefreshListener(OnCallBackRefreshListener callback) {
        this.onCallBackRefreshListener = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        initializeMethod(view);
        sendRequestOnReplyFeed(startLimit, countLimit);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    private void sendRequestOnReplyFeed(final int AstartLimit, final int AcountLimit) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.OFFER_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("limits", AstartLimit + "" + AcountLimit);
                        OfferListResponseparsing responseparsing = new OfferListResponseparsing();
                        responseparsing.responseParseMethod(response);
                        Log.e("request", response);
                        String status = responseparsing.getStatus().trim();
                        if (status.equals("true")) {
                            if (responseparsing.getDetailsListArrayList().size() > 0) {
                                arrayList = responseparsing.getDetailsListArrayList();
                                if (arrayList.size() > 0) {
                                    txtDataNotFound.setVisibility(View.GONE);
                                    if (adapter.getData() != null && adapter.getData().size() > 0) {
                                        adapter.getData().addAll(arrayList);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        adapter.setData(arrayList);
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    txtDataNotFound.setVisibility(View.VISIBLE);
                                }
                            } else {
                                txtDataNotFound.setVisibility(View.VISIBLE);
                            }
                        } else {
                            txtDataNotFound.setVisibility(View.VISIBLE);
                        }
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
                params.put("uid", SharedPreferencesManager.getUserID(activity));
                params.put("startlimit", String.valueOf(AstartLimit));
                params.put("countlimit", String.valueOf(AcountLimit));
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void initializeMethod(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        adapter = new OfferAdapter(activity);
        LinearLayoutManager layoutMangerDestination
                = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutMangerDestination);
        recyclerview.addOnScrollListener(new replyScrollListener());
        recyclerview.setAdapter(adapter);
        adapter.setMyClickListener(AllOffersFragment.this);
        txtDataNotFound = (AppCompatTextView) view.findViewById(R.id.txtDataNotFound);
        txtDataNotFound.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.all_offers_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }

    @Override
    public void onOfferItemClick(int position, DetailsList detailsList) {
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
    public void OnCallBackUpdateOffer() {
        arrayList.removeAll(arrayList);
        adapter.notifyDataSetChanged();
        adapter.setData(arrayList);
        if (arrayList.size() > 0) {
        } else {
            sendRequestOnReplyFeed(0, 10);
            onCallBackRefreshListener.OnCallBackRefreshOffer();
        }
    }

    public class replyScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager recyclerLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = recyclerLayoutManager.getItemCount();

            int visibleThreshold = ((totalItemCount / 2) < 20) ? totalItemCount / 2 : 20;
            int firstVisibleItem = recyclerLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > startLimit) {
                    loading = false;
                    startLimit = totalItemCount;
                }
            } else {
                int nonVisibleItemCounts = totalItemCount - visibleItemCount;
                int effectiveVisibleThreshold = firstVisibleItem + visibleThreshold;

                if (nonVisibleItemCounts <= effectiveVisibleThreshold) {
                    startLimit = startLimit + 1;
                    countLimit = 10;

                    showLoader();

                    sendRequestOnReplyFeed(startLimit, countLimit);
                    loading = true;
                }
            }
            super.onScrollStateChanged(recyclerView, newState);
        }
    }
}