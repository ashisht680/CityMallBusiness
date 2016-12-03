package com.javinindia.citymallsbusiness.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
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
import com.javinindia.citymallsbusiness.font.FontAsapBoldSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.utility.Utility;


public class OfferPostFragment extends BaseFragment implements View.OnClickListener {
    ImageView imgBrand, imgOffer;
    RatingBar ratingBar;
    AppCompatTextView txtOfferBrandNamePost, txtRating, txtMallNamePost, txtOffers, txtOfferPrice, txtSubcategory,
            txtOfferDate, txtShopTiming, txtOfferDiscription,txtOfferActualPrice,txtOfferTitle,txtFavCount;
    AppCompatButton btnRate;
   // CheckBox chkImageMall;
    ProgressBar progressBar;

    String brandName, brandPic, shopName, mallName, offerRating, offerPic, offerTitle, offerCategory, offerSubCategory, offerPercentType,
            offerPercentage, offerActualPrice, offerDiscountPr, offerStartDate, offerCloseDate, offerDescription, shopOpenTime, shopCloseTime,favCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   images = (ArrayList<PostImage>) getArguments().getSerializable("images");
        favCount = getArguments().getString("favCount");
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
        if (!TextUtils.isEmpty(brandName))
            txtOfferBrandNamePost.setText(Html.fromHtml(brandName));

        if (!TextUtils.isEmpty(offerRating))
            txtRating.setText("Rating:" + offerRating + "/5");
        ratingBar.setRating(Float.valueOf(offerRating));

        if (!TextUtils.isEmpty(mallName))
            txtMallNamePost.setText(Html.fromHtml(mallName + "\n" + shopName));

        if (!TextUtils.isEmpty(offerTitle))
            txtOfferTitle.setText(Html.fromHtml(offerTitle));

        if(!TextUtils.isEmpty(offerPercentType) && !TextUtils.isEmpty(offerPercentage)){
            txtOfferActualPrice.setVisibility(View.GONE);
            txtOfferPrice.setText(offerPercentType+" "+offerPercentage+"% off");
        }else {
            if(!TextUtils.isEmpty(offerActualPrice) && !TextUtils.isEmpty(offerDiscountPr)){
                txtOfferActualPrice.setVisibility(View.VISIBLE);
                txtOfferActualPrice.setText("Price "+offerActualPrice+" Rs");
                txtOfferActualPrice.setPaintFlags(txtOfferActualPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                txtOfferPrice.setText("Discount Price "+offerDiscountPr+" Rs");
            }
        }

        if (!TextUtils.isEmpty(offerCategory))
            txtSubcategory.setText(Html.fromHtml("on "+offerCategory + "(" + offerSubCategory+")"));

        if (!TextUtils.isEmpty(offerStartDate) && !TextUtils.isEmpty(offerCloseDate))
            txtOfferDate.setText(offerStartDate + " till " + offerCloseDate);

        if (!TextUtils.isEmpty(shopOpenTime) && !TextUtils.isEmpty(shopCloseTime))
            txtShopTiming.setText(shopOpenTime + " to " + shopCloseTime);

        if (!TextUtils.isEmpty(offerDescription))
            txtOfferDiscription.setText(Html.fromHtml("Description: " + offerDescription));

        if (!TextUtils.isEmpty(offerPic)) {
            Utility.imageLoadGlideLibrary(activity, progressBar, imgOffer, offerPic);
        } else if (!TextUtils.isEmpty(brandPic)){
            Utility.imageLoadGlideLibrary(activity, progressBar, imgOffer, brandPic);
        }else {

        }

        if (!TextUtils.isEmpty(brandPic)) {
            Utility.imageLoadGlideLibrary(activity, progressBar, imgBrand, brandPic);
        }
        if (!TextUtils.isEmpty(favCount)){
            txtFavCount.setText(favCount);
        }else {
            txtFavCount.setText("0");
        }


    }

    private void initialize(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
       // chkImageMall = (CheckBox) view.findViewById(R.id.chkImageMall);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        imgBrand = (ImageView) view.findViewById(R.id.imgBrand);
        imgOffer = (ImageView) view.findViewById(R.id.imgOffer);
        txtOfferBrandNamePost = (AppCompatTextView) view.findViewById(R.id.txtOfferBrandNamePost);
        txtOfferBrandNamePost.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtRating = (AppCompatTextView) view.findViewById(R.id.txtRating);
        txtRating.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtMallNamePost = (AppCompatTextView) view.findViewById(R.id.txtMallNamePost);
        txtMallNamePost.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        /*txtOffers = (AppCompatTextView) view.findViewById(R.id.txtOffers);
        txtOffers.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());*/
        txtOfferPrice = (AppCompatTextView) view.findViewById(R.id.txtOfferPrice);
        txtOfferPrice.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtSubcategory = (AppCompatTextView) view.findViewById(R.id.txtSubcategory);
        txtSubcategory.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtOfferDate = (AppCompatTextView) view.findViewById(R.id.txtOfferDate);
        txtOfferDate.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtShopTiming = (AppCompatTextView) view.findViewById(R.id.txtShopTiming);
        txtShopTiming.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtOfferDiscription = (AppCompatTextView) view.findViewById(R.id.txtOfferDiscription);
        txtOfferDiscription.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        btnRate = (AppCompatButton) view.findViewById(R.id.btnRate);
        btnRate.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtOfferActualPrice = (AppCompatTextView)view.findViewById(R.id.txtOfferActualPrice);
        txtOfferActualPrice.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtOfferTitle = (AppCompatTextView)view.findViewById(R.id.txtOfferTitle);
        txtOfferTitle.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtFavCount = (AppCompatTextView)view.findViewById(R.id.txtFavCount);
        txtFavCount.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        imgBrand.setOnClickListener(this);
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
            case R.id.imgBrand:
                activity.onBackPressed();
                break;
        }
    }

}
