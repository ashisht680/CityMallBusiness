package com.javinindia.citymallsbusiness.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.CountryModel;
import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.DetailsList;
import com.javinindia.citymallsbusiness.font.FontAsapBoldSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.utility.Utility;
import com.javinindia.citymallsbusiness.volleycustomrequest.VolleySingleTon;

import java.util.ArrayList;
import java.util.List;


public class AboutAdaptar extends RecyclerView.Adapter<AboutAdaptar.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> list;
    private Context context;
    MyClickListener myClickListener;
    ArrayList<CountryModel> countryModelArrayList;
    private String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong, sPic;
    private String shopCategory, shopSubCategory, country, pincode, rating, openTime, closeTime, distance, sbanner;
    private int offerCount;
    ArrayList arrayList;

    public AboutAdaptar(Context Scontext, String storeName, String ownerName, String Email, String MobileNum, String Landline, String State, String City, String storeAddress, String mallName, String mallAddress, String Lat, String Long, String storePic, String storeCountry, String storePincode, String storeRating, String storeOpenTime, String storeCloseTime, String storeDistance, int storeOfferCount, String banner, ArrayList sPostUrl) {
        this.context = Scontext;
        this.country = storeCountry;
        this.closeTime = storeCloseTime;
        this.distance = storeDistance;
        this.mAddress = mallAddress;
        this.mLat = Lat;
        this.mName = mallName;
        this.mLong = Long;
        this.offerCount = storeOfferCount;
        this.oName = ownerName;
        this.openTime = storeOpenTime;
        this.pincode = storePincode;
        this.rating = storeRating;
        this.sAddress = storeAddress;
        this.sCity = City;
        this.sEmail = Email;
        this.sLandline = Landline;
        this.sMobileNum = MobileNum;
        this.sName = storeName;
        this.sPic = storePic;
        this.sState = State;
        this.sbanner = banner;
        this.arrayList = sPostUrl;
    }


    public void setData(List<Object> list) {
        this.list = list;
    }

    public List<Object> getData() {
        return list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;
        //***********************Recent list
        public AppCompatTextView txtShopName, txtSubCategoryItem, txtOfferTitle, txtOfferCategoryItem, txtTimingOffer,
                txtViewItem, txtEditOfferItem, txtOfferTypeOrActualPrice, txtOfferPercentOrDiscountPrice;
        public RelativeLayout rlMain;
        public NetworkImageView imgShopLogoOffer;
        LinearLayout llOffItem;

        //********************** about header
        ProgressBar progressBar;
        ImageView profileImageView, imgBannerAbout;
        public AppCompatTextView txtShopNameHeader, txtTimingHeader, txtRatingAbout, txtLocationHeader, txtCategoryHeader, txtFaverateCountHeader;
        public AppCompatButton btnEditProfile, btnAllOffer, btnAddCategory;
        public RatingBar ratingBarAbout;
        RecyclerView catRecyclerView;


        public ViewHolder(View itemLayoutView, int ViewType) {
            super(itemLayoutView);

            if (ViewType == TYPE_ITEM) {
                txtShopName = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtShopName);
                txtShopName.setTypeface(FontAsapBoldSingleTonClass.getInstance(context).getTypeFace());
                txtSubCategoryItem = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtSubCategoryItem);
                txtSubCategoryItem.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                txtOfferTitle = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtOfferTitle);
                txtOfferTitle.setTypeface(FontAsapBoldSingleTonClass.getInstance(context).getTypeFace());
                txtOfferCategoryItem = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtOfferCategoryItem);
                txtOfferCategoryItem.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                txtTimingOffer = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtTimingOffer);
                txtTimingOffer.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                txtViewItem = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtViewItem);
                txtViewItem.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                txtEditOfferItem = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtEditOfferItem);
                txtEditOfferItem.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                llOffItem = (LinearLayout) itemLayoutView.findViewById(R.id.llOffItem);
                txtOfferTypeOrActualPrice = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtOfferTypeOrActualPrice);
                txtOfferTypeOrActualPrice.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                txtOfferPercentOrDiscountPrice = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtOfferPercentOrDiscountPrice);
                txtOfferPercentOrDiscountPrice.setTypeface(FontAsapBoldSingleTonClass.getInstance(context).getTypeFace());
                imgShopLogoOffer = (NetworkImageView) itemLayoutView.findViewById(R.id.imgShopLogoOffer);
                rlMain = (RelativeLayout) itemLayoutView.findViewById(R.id.rlMain);
                holderId = 1;
            } else {
                profileImageView = (ImageView) itemLayoutView.findViewById(R.id.imgShopLogoAbout);
                imgBannerAbout = (ImageView) itemLayoutView.findViewById(R.id.imgBannerAbout);
                progressBar = (ProgressBar) itemLayoutView.findViewById(R.id.progress);
                txtShopNameHeader = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtShopNameAbout);
                txtShopNameHeader.setTypeface(FontAsapBoldSingleTonClass.getInstance(context).getTypeFace());
                txtTimingHeader = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtTimingAbout);
                txtTimingHeader.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                txtRatingAbout = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtRatingAbout);
                txtRatingAbout.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                txtLocationHeader = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtLocationAbout);
                txtLocationHeader.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                txtCategoryHeader = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtCategoryAbout);
                txtCategoryHeader.setTypeface(FontAsapBoldSingleTonClass.getInstance(context).getTypeFace());
                txtFaverateCountHeader = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtFaverateCountAbout);
                txtFaverateCountHeader.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                btnEditProfile = (AppCompatButton) itemLayoutView.findViewById(R.id.btnEditProfile);
                btnEditProfile.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                btnAllOffer = (AppCompatButton) itemLayoutView.findViewById(R.id.btnAllOffer);
                btnAllOffer.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                ratingBarAbout = (RatingBar) itemLayoutView.findViewById(R.id.ratingBarAbout);
                catRecyclerView = (RecyclerView) itemLayoutView.findViewById(R.id.recyclerviewCategory);
                btnAddCategory = (AppCompatButton) itemLayoutView.findViewById(R.id.btnAddCategory);
                btnAddCategory.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
                holderId = 0;
            }
        }
    }


    @Override
    public AboutAdaptar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item_layout, parent, false);
            ViewHolder vhItem = new ViewHolder(v, viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_header_layout, parent, false);
            ViewHolder vhHeader = new ViewHolder(v, viewType);
            return vhHeader;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final AboutAdaptar.ViewHolder viewHolder, final int position) {
        VolleySingleTon volleySingleTon = VolleySingleTon.getInstance(context);
        if (viewHolder.holderId == 1) {
            final DetailsList requestDetail = (DetailsList) list.get(position - 1);
            String brandName = requestDetail.getOfferBrandDetails().getBrandName().trim();
            String subCategory = requestDetail.getOfferDetails().getOfferSubcategory().trim();
            String offerTitle = requestDetail.getOfferDetails().getOfferTitle().trim();
            String category = requestDetail.getOfferDetails().getOfferCategory().trim();
            String offerType = requestDetail.getOfferDetails().getOfferPercentageType().trim();
            String offerPercent = requestDetail.getOfferDetails().getOfferPercentage().trim();
            String offerActualPrice = requestDetail.getOfferDetails().getOfferActualPrice().trim();
            String offerDiscountPrice = requestDetail.getOfferDetails().getOfferDiscountedPrice().trim();
            String brandLogo = requestDetail.getOfferBrandDetails().getBrandLogo().trim();
            String openTime = requestDetail.getOfferDetails().getOfferOpenDate().trim();
            String closeTime = requestDetail.getOfferDetails().getOfferCloseDate().trim();

            if (!TextUtils.isEmpty(brandName)) {
                viewHolder.txtShopName.setText(brandName);
            }
            if (!TextUtils.isEmpty(subCategory)) {
                viewHolder.txtSubCategoryItem.setText(subCategory);
            }
            if (!TextUtils.isEmpty(offerTitle)) {
                viewHolder.txtOfferTitle.setText(offerTitle);
            }
            if (!TextUtils.isEmpty(category)) {
                viewHolder.txtOfferCategoryItem.setText("on " + category);
            }
            if (!TextUtils.isEmpty(openTime) && !TextUtils.isEmpty(closeTime)) {
                viewHolder.txtTimingOffer.setText(openTime + " till " + closeTime);
            }
            if (!TextUtils.isEmpty(offerType) && !TextUtils.isEmpty(offerPercent)) {
                viewHolder.llOffItem.setBackgroundColor(Color.parseColor("#b94115"));
                viewHolder.txtOfferTypeOrActualPrice.setText(offerType);
                viewHolder.txtOfferTypeOrActualPrice.setPaintFlags(viewHolder.txtOfferTypeOrActualPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                viewHolder.txtOfferPercentOrDiscountPrice.setText(offerPercent + "% off");
            } else {
                if (!TextUtils.isEmpty(offerActualPrice) && !TextUtils.isEmpty(offerDiscountPrice)) {
                    viewHolder.llOffItem.setBackgroundColor(Color.parseColor("#1da6b9"));
                    viewHolder.txtOfferTypeOrActualPrice.setText("Rs " + offerActualPrice + "/-");
                    viewHolder.txtOfferTypeOrActualPrice.setPaintFlags(viewHolder.txtOfferTypeOrActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.txtOfferPercentOrDiscountPrice.setText("Rs " + offerDiscountPrice + "/-");
                }
            }
            if (!TextUtils.isEmpty(brandLogo)) {
                viewHolder.imgShopLogoOffer.setImageUrl(brandLogo, volleySingleTon.getImageLoader());
            }

            viewHolder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.onOfferClick(position, requestDetail);
                }
            });

            viewHolder.txtEditOfferItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.onOfferEditClick(position, requestDetail);
                }
            });


        } else {

            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            viewHolder.catRecyclerView.setLayoutManager(gridLayoutManager);
            ShopCategoryAdaptar shopCategoryAdaptar = new ShopCategoryAdaptar(context);
            if (arrayList != null && arrayList.size() > 0) {
                shopCategoryAdaptar.setData(arrayList);
            }
            viewHolder.catRecyclerView.setAdapter(shopCategoryAdaptar);
            if (!TextUtils.isEmpty(sName)) {
                viewHolder.txtShopNameHeader.setText(sName);
            }
            if (!TextUtils.isEmpty(openTime)) {
               // viewHolder.txtTimingHeader.setText("Timing:"+openTime + " to " + closeTime);
                 viewHolder.txtTimingHeader.setText((Html.fromHtml("<font color=#7b7b7b>" + "Timing:" + "</font>" + " " + "<font color=#ffffff>" + openTime + " - " + closeTime + "</font>")));
            }
            if (!TextUtils.isEmpty(sAddress)) {
                viewHolder.txtLocationHeader.setText(sAddress + "," + sCity + "," + sState);
            }

            if (!TextUtils.isEmpty(sPic))
                Utility.imageLoadGlideLibrary(context, viewHolder.progressBar, viewHolder.profileImageView, sPic);
            if (!TextUtils.isEmpty(sbanner))
                Utility.imageLoadGlideLibrary(context, viewHolder.progressBar, viewHolder.imgBannerAbout, sbanner);

            if (!TextUtils.isEmpty(rating) && !rating.equals("0"))
                viewHolder.ratingBarAbout.setRating(Float.valueOf(rating));
                viewHolder.txtRatingAbout.setText("Rating:"+rating+"/5");

            viewHolder.btnEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.onEditClick(position);
                }
            });

            viewHolder.btnAllOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.onAllOffers(position);
                }
            });

            viewHolder.btnAddCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.onAddCategory(position);
                }
            });


        }
    }

    public int getItemCount() {
        return list != null ? list.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public interface MyClickListener {
        void onEditClick(int position);

        void onAllOffers(int position);

        void onAddCategory(int position);

        void onOfferClick(int position, DetailsList detailsList);

        void onOfferEditClick(int position, DetailsList detailsList);

    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

}
