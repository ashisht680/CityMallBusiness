package com.javinindia.citymallsbusiness.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.utility.Utility;


public class OfferPostFragment extends BaseFragment implements View.OnClickListener {
    ImageView imgBrand,imgOffer;
    RatingBar ratingBar;
    AppCompatTextView txtOfferBrandNamePost,txtRating,txtMallNamePost,txtOffers,txtOfferTitle,txtSubcategory,
    txtOfferDate,txtShopTiming,txtOfferDiscription;
    AppCompatButton btnRate;
    CheckBox chkImageMall;
    ProgressBar progressBar;

    String brandName,brandPic,shopName,mallName,offerRating,offerPic,offerTitle,offerCategory,offerSubCategory,offerPercentType,
    offerPercentage,offerActualPrice,offerDiscountPr,offerStartDate,offerCloseDate,offerDescription,shopOpenTime, shopCloseTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   images = (ArrayList<PostImage>) getArguments().getSerializable("images");
        brandName = getArguments().getString("brandName");
        brandPic = getArguments().getString("brandPic");
        shopName = getArguments().getString("shopName");
        mallName = getArguments().getString("mallName");
        offerRating = getArguments().getString("offerRating");
        offerPic = getArguments().getString("offerPic");
        offerTitle = getArguments().getString("offerTitle");
        offerCategory = getArguments().getString("offerCategory");
        offerSubCategory = getArguments().getString("offerSubCategory");
        offerPercentType = getArguments().getString("offerPercentType");
        offerPercentage = getArguments().getString("offerPercentage");
        offerActualPrice = getArguments().getString("offerActualPrice");
        offerDiscountPr = getArguments().getString("offerDiscountPrice");
        offerStartDate = getArguments().getString("offerStartDate");
        offerCloseDate = getArguments().getString("offerCloseDate");
        offerDescription = getArguments().getString("offerDescription");
        shopOpenTime = getArguments().getString("shopOpenTime");
        shopCloseTime = getArguments().getString("shopCloseTime");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize(view);
        setDataOnView();
        return view;
    }

    private void setDataOnView() {
        if(!TextUtils.isEmpty(brandName))
        txtOfferBrandNamePost.setText(brandName);

        if(!TextUtils.isEmpty(offerRating))
            txtRating.setText("Rating:"+offerRating+"/5");
            ratingBar.setRating(Float.valueOf(offerRating));

        if(!TextUtils.isEmpty(mallName))
            txtMallNamePost.setText(mallName+"\n"+shopName);

        if(!TextUtils.isEmpty(offerTitle))
            txtOfferTitle.setText(offerTitle);

        if(!TextUtils.isEmpty(offerCategory))
            txtSubcategory.setText(offerCategory+","+offerSubCategory);

        if(!TextUtils.isEmpty(offerStartDate) && !TextUtils.isEmpty(offerCloseDate))
            txtOfferDate.setText(offerStartDate+" till "+offerCloseDate);

        if(!TextUtils.isEmpty(shopOpenTime) && !TextUtils.isEmpty(shopCloseTime))
            txtShopTiming.setText(shopOpenTime+" to "+shopCloseTime);

        if(!TextUtils.isEmpty(offerDescription))
            txtOfferDiscription.setText("Description:"+offerDescription);

        if (!TextUtils.isEmpty(offerPic))
            Utility.imageLoadGlideLibrary(activity, progressBar,imgOffer, offerPic);
        if (!TextUtils.isEmpty(brandPic))
            Utility.imageLoadGlideLibrary(activity, progressBar, imgBrand, brandPic);

    }

    private void initialize(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        chkImageMall = (CheckBox)view.findViewById(R.id.chkImageMall);
        ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        imgBrand = (ImageView)view.findViewById(R.id.imgBrand);
        imgOffer = (ImageView)view.findViewById(R.id.imgOffer);
        txtOfferBrandNamePost = (AppCompatTextView)view.findViewById(R.id.txtOfferBrandNamePost);
        txtRating = (AppCompatTextView)view.findViewById(R.id.txtRating);
        txtMallNamePost = (AppCompatTextView)view.findViewById(R.id.txtMallNamePost);
        txtOffers = (AppCompatTextView)view.findViewById(R.id.txtOffers);
        txtOfferTitle = (AppCompatTextView)view.findViewById(R.id.txtOfferTitle);
        txtSubcategory = (AppCompatTextView)view.findViewById(R.id.txtSubcategory);
        txtOfferDate = (AppCompatTextView)view.findViewById(R.id.txtOfferDate);
        txtShopTiming = (AppCompatTextView)view.findViewById(R.id.txtShopTiming);
        txtOfferDiscription = (AppCompatTextView)view.findViewById(R.id.txtOfferDiscription);
        btnRate = (AppCompatButton)view.findViewById(R.id.btnRate);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.offer_detail_layout;
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

        }
    }

}
