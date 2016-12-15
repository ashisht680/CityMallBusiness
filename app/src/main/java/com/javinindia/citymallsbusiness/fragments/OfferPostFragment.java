package com.javinindia.citymallsbusiness.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.font.FontAsapBoldSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.utility.Utility;


public class OfferPostFragment extends BaseFragment implements View.OnClickListener {
    ImageView imgBrand, imgOffer;
    RatingBar ratingBar;
    AppCompatTextView txtOfferBrandNamePost, txtRating, txtMallNamePost, txtOffers, txtOfferPercentage, txtSubcategory,
            txtOfferDate, txtShopTiming, txtOfferDiscription, txtOfferActualPrice, txtOfferTitle, txtFavCount, txtOfferDiscountPrice,txtAddress;
    AppCompatButton btnRate;
    // CheckBox chkImageMall;
    ProgressBar progressBar;

    String brandName, brandPic, shopName, mallName, offerRating, offerPic, offerTitle, offerCategory, offerSubCategory, offerPercentType, shopPic,
            offerPercentage, offerActualPrice, offerDiscountPr, offerStartDate, offerCloseDate, offerDescription, shopOpenTime, shopCloseTime, favCount,shopNewAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        shopPic = getArguments().getString("shopPic");
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
        shopNewAddress = getArguments().getString("shopNewAddress");
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
        initialize(view);
        setDataOnView();
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
        if (!TextUtils.isEmpty(brandName)){
            textView.setText(brandName);
        }else if (!TextUtils.isEmpty(shopName)){
            textView.setText(shopName);
        }else {
            textView.setText("Offer");
        }
        textView.setTextColor(activity.getResources().getColor(android.R.color.white));
        textView.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
    }

    private void setDataOnView() {
        if (!TextUtils.isEmpty(brandName))
            txtOfferBrandNamePost.setText(Html.fromHtml(brandName));

        if (!TextUtils.isEmpty(offerRating))
            txtRating.setText("Rating:" + offerRating + "/5");
        ratingBar.setRating(Float.valueOf(offerRating));

        if (!TextUtils.isEmpty(mallName))
            txtMallNamePost.setText(Html.fromHtml(mallName + ",\n" + shopName));

        if (!TextUtils.isEmpty(offerTitle))
            txtOfferTitle.setText(Html.fromHtml(offerTitle));

        if (!TextUtils.isEmpty(offerPercentType) && !TextUtils.isEmpty(offerPercentage)) {
            txtOfferPercentage.setText(offerPercentType + " " + offerPercentage + "% off");
        } else if (!TextUtils.isEmpty(offerPercentType) && !TextUtils.isEmpty(offerActualPrice) && !TextUtils.isEmpty(offerDiscountPr) && TextUtils.isEmpty(offerPercentage)) {
            double actual = Double.parseDouble(offerActualPrice);
            double discount = Double.parseDouble(offerDiscountPr);
            int percent = (int) (100 - (discount * 100.0f) / actual);
            txtOfferPercentage.setText(offerPercentType + "\t" + percent + "% off");
        } else if (TextUtils.isEmpty(offerPercentType) && TextUtils.isEmpty(offerPercentage)) {
            if (!TextUtils.isEmpty(offerActualPrice) && !TextUtils.isEmpty(offerDiscountPr)) {
                double actual = Double.parseDouble(offerActualPrice);
                double discount = Double.parseDouble(offerDiscountPr);
                int percent = (int) (100 - (discount * 100.0f) / actual);
                txtOfferPercentage.setText(percent + "% off");
            }
        } else if (TextUtils.isEmpty(offerPercentType) && !TextUtils.isEmpty(offerPercentage)) {
            txtOfferPercentage.setText(offerPercentage + "% off");
        }

        if (!TextUtils.isEmpty(offerActualPrice) && !TextUtils.isEmpty(offerDiscountPr)) {
            txtOfferActualPrice.setText(Html.fromHtml("\u20B9" + offerActualPrice + "/-"));
            txtOfferActualPrice.setPaintFlags(txtOfferActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtOfferDiscountPrice.setText(Html.fromHtml("\u20B9" + offerDiscountPr + "/-"));
        }


        if (!TextUtils.isEmpty(offerCategory))
            txtSubcategory.setText(Html.fromHtml("on " + offerCategory + "(" + offerSubCategory + ")"));

        if (!TextUtils.isEmpty(offerStartDate) && !TextUtils.isEmpty(offerCloseDate))
            txtOfferDate.setText(offerStartDate + " till " + offerCloseDate);

        if (!TextUtils.isEmpty(shopOpenTime) && !TextUtils.isEmpty(shopCloseTime))
            txtShopTiming.setText(shopOpenTime + " to " + shopCloseTime);

        if (!TextUtils.isEmpty(offerDescription))
            txtOfferDiscription.setText(Html.fromHtml("Description: " + offerDescription));

        if (!TextUtils.isEmpty(offerPic)) {
            Utility.imageLoadGlideLibrary(activity, progressBar, imgOffer, offerPic);
        } else if (!TextUtils.isEmpty(brandPic)) {
            Utility.imageLoadGlideLibrary(activity, progressBar, imgOffer, brandPic);
        } else {

        }

        if (!TextUtils.isEmpty(shopPic)) {
            Utility.imageLoadGlideLibrary(activity, progressBar, imgBrand, shopPic);
        } else if (!TextUtils.isEmpty(brandPic)) {
            Utility.imageLoadGlideLibrary(activity, progressBar, imgBrand, brandPic);
        }
        if (!TextUtils.isEmpty(favCount)) {
            txtFavCount.setText(favCount);
        } else {
            txtFavCount.setText("0");
        }
        if (!TextUtils.isEmpty(shopNewAddress)){
            txtAddress.setText(shopNewAddress);
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
        txtAddress = (AppCompatTextView) view.findViewById(R.id.txtAddress);
        txtAddress.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtOfferDiscountPrice = (AppCompatTextView) view.findViewById(R.id.txtOfferDiscountPrice);
        txtOfferDiscountPrice.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtOfferPercentage = (AppCompatTextView) view.findViewById(R.id.txtOfferPercentage);
        txtOfferPercentage.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
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
        txtOfferActualPrice = (AppCompatTextView) view.findViewById(R.id.txtOfferActualPrice);
        txtOfferActualPrice.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtOfferTitle = (AppCompatTextView) view.findViewById(R.id.txtOfferTitle);
        txtOfferTitle.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtFavCount = (AppCompatTextView) view.findViewById(R.id.txtFavCount);
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
