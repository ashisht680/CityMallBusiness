package com.javinindia.citymallsbusiness.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
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
import java.util.Arrays;
import java.util.List;


public class AboutAdaptar extends RecyclerView.Adapter<AboutAdaptar.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> list;
    private Context context;
    MyClickListener myClickListener;
    ArrayList<CountryModel> countryModelArrayList;
    private String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong, sPic;
    private String shopCategory, shopSubCategory, country, pincode, rating, openTime, closeTime, distance, sbanner, shopfavCount, sFloor, sNo;
    private int offerCount;
    ArrayList arrayList;

    public AboutAdaptar(Context Scontext, String floor, String shopNum, String storefavCount, String storeName, String ownerName, String Email, String MobileNum, String Landline, String State, String City, String storeAddress, String mallName, String mallAddress, String Lat, String Long, String storePic, String storeCountry, String storePincode, String storeRating, String storeOpenTime, String storeCloseTime, String storeDistance, int storeOfferCount, String banner, ArrayList sPostUrl) {
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
        this.shopfavCount = storefavCount;
        this.sFloor = floor;
        this.sNo = shopNum;
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
        public CardView rlMain;
        public ImageView imgShopLogoOffer;
        LinearLayout llOffItem;
        public ProgressBar progressBar;

        //********************** about header
        ProgressBar progressBarHed;
        ImageView profileImageView, imgBannerAbout;
        public AppCompatTextView txtShopNameHeader, txtTimingHeader, txtRatingAbout, txtLocationHeader, txtCategoryHeader, txtFaverateCountHeader, txtOffersAbout;
        public AppCompatButton btnEditProfile, btnAllOffer, btnAddCategory;
        public RatingBar ratingBarAbout;
        RecyclerView catRecyclerView;


        public ViewHolder(View itemLayoutView, int ViewType) {
            super(itemLayoutView);

            if (ViewType == TYPE_ITEM) {
                progressBar = (ProgressBar) itemLayoutView.findViewById(R.id.progress);
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
                imgShopLogoOffer = (ImageView) itemLayoutView.findViewById(R.id.imgShopLogoOffer);
                rlMain = (CardView) itemLayoutView.findViewById(R.id.rlMain);
                holderId = 1;
            } else {
                profileImageView = (ImageView) itemLayoutView.findViewById(R.id.imgShopLogoAbout);
                imgBannerAbout = (ImageView) itemLayoutView.findViewById(R.id.imgBannerAbout);
                progressBarHed = (ProgressBar) itemLayoutView.findViewById(R.id.progress);
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
                txtOffersAbout = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtOffersAbout);
                txtOffersAbout.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
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

            if (!TextUtils.isEmpty(requestDetail.getOfferBrandDetails().getBrandName().trim())) {
                String brandName = requestDetail.getOfferBrandDetails().getBrandName().trim();
                viewHolder.txtShopName.setText(Html.fromHtml(brandName));
            }
            if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferSubcategory().trim())) {
                String subCategory = requestDetail.getOfferDetails().getOfferSubcategory().trim();
                viewHolder.txtSubCategoryItem.setText(Html.fromHtml(subCategory));
            }
            if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferTitle().trim())) {
                String offerTitle = requestDetail.getOfferDetails().getOfferTitle().trim();
                viewHolder.txtOfferTitle.setText(Html.fromHtml(offerTitle));
            }
            if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferCategory().trim())) {
                String category = requestDetail.getOfferDetails().getOfferCategory().trim();
                viewHolder.txtOfferCategoryItem.setText(Html.fromHtml("on " + category));
            }
            if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferOpenDate().trim()) && !TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferCloseDate().trim())) {
                String openTime = requestDetail.getOfferDetails().getOfferOpenDate().trim();
                String closeTime = requestDetail.getOfferDetails().getOfferCloseDate().trim();
                viewHolder.txtTimingOffer.setText(openTime + " till " + closeTime);
            } else {
                viewHolder.txtTimingOffer.setText("Timing: Not found");
            }
            if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferPercentageType().trim()) && !TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferPercentage().trim())) {
                String offerType = requestDetail.getOfferDetails().getOfferPercentageType().trim();
                String offerPercent = requestDetail.getOfferDetails().getOfferPercentage().trim();
                viewHolder.llOffItem.setBackgroundColor(Color.parseColor("#b94115"));
                viewHolder.txtOfferTypeOrActualPrice.setText(offerType);
                viewHolder.txtOfferTypeOrActualPrice.setPaintFlags(viewHolder.txtOfferTypeOrActualPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                viewHolder.txtOfferPercentOrDiscountPrice.setText(offerPercent + "% off");
            } else {
                if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferActualPrice().trim()) && !TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferDiscountedPrice().trim())) {
                    String offerActualPrice = requestDetail.getOfferDetails().getOfferActualPrice().trim();
                    String offerDiscountPrice = requestDetail.getOfferDetails().getOfferDiscountedPrice().trim();
                    viewHolder.llOffItem.setBackgroundColor(Color.parseColor("#1da6b9"));
                    viewHolder.txtOfferTypeOrActualPrice.setText("Rs " + offerActualPrice + "/-");
                    viewHolder.txtOfferTypeOrActualPrice.setPaintFlags(viewHolder.txtOfferTypeOrActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.txtOfferPercentOrDiscountPrice.setText("Rs " + offerDiscountPrice + "/-");
                }
            }
            if (!TextUtils.isEmpty(requestDetail.getOfferBrandDetails().getBrandLogo().trim())) {
                String brandLogo = requestDetail.getOfferBrandDetails().getBrandLogo().trim();
                Utility.imageLoadGlideLibrary(context, viewHolder.progressBar, viewHolder.imgShopLogoOffer, brandLogo);
            } else {
                viewHolder.imgShopLogoOffer.setImageResource(R.drawable.no_image_icon);
            }

            if (!TextUtils.isEmpty(requestDetail.getOfferViewCount().trim())) {
                String offerViewCount = requestDetail.getOfferViewCount().trim();
                viewHolder.txtViewItem.setText(offerViewCount + " view");
            } else {
                viewHolder.txtViewItem.setText("No views");
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

            viewHolder.txtViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.onViewClick(position, requestDetail);
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
                viewHolder.txtShopNameHeader.setText(Html.fromHtml(sName));
            } else {
                viewHolder.txtShopNameHeader.setText("Shop name: Not found");
            }
            if (!TextUtils.isEmpty(openTime)) {
                viewHolder.txtTimingHeader.setText((Html.fromHtml("<font color=#ffffff>" + "Timing:" + "</font>" + " " + "<font color=#ffffff>" + openTime + " - " + closeTime + "</font>")));
            } else {
                viewHolder.txtTimingHeader.setText("Timing:");
            }
            final ArrayList<String> data = new ArrayList<>();
            if (!TextUtils.isEmpty(sNo)) {
                data.add(sNo);
            }
            if (!TextUtils.isEmpty(sFloor)) {
                data.add(sFloor + " floor");
            }
            if (!TextUtils.isEmpty(sAddress)) {
                data.add(sAddress);
            }
            if (!TextUtils.isEmpty(sCity)) {
                data.add(sCity);
            }
            if (!TextUtils.isEmpty(sState)) {
                data.add(sState);
            }

            if (data.size() > 0) {
                String str = Arrays.toString(data.toArray());
                String test = str.replaceAll("[\\[\\](){}]", "");
                viewHolder.txtLocationHeader.setText(Html.fromHtml(test));
            } else {
                viewHolder.txtLocationHeader.setText("Address: Not found");
            }

            if (!TextUtils.isEmpty(sPic)) {
                Utility.imageLoadGlideLibrary(context, viewHolder.progressBarHed, viewHolder.profileImageView, sPic);
            } else {
                viewHolder.profileImageView.setImageResource(R.drawable.no_image_icon);
            }

            if (!TextUtils.isEmpty(rating) && !rating.equals("0")) {
                viewHolder.ratingBarAbout.setRating(Float.valueOf(rating));
                viewHolder.txtRatingAbout.setText("Rating: " + rating + "/5");
            } else {
                viewHolder.ratingBarAbout.setRating(Float.valueOf("0"));
                viewHolder.txtRatingAbout.setText("Rating: 0/5");
            }
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

            if (!TextUtils.isEmpty(shopfavCount)) {
                viewHolder.txtFaverateCountHeader.setText(shopfavCount);
            } else {
                viewHolder.txtFaverateCountHeader.setText("0");
            }

            if (offerCount != 0) {
                if (offerCount == 1) {
                    viewHolder.txtOffersAbout.setText(offerCount + " Offer");
                } else {
                    viewHolder.txtOffersAbout.setText(offerCount + " Offers");
                }

            } else {
                viewHolder.txtOffersAbout.setText("No offers");
            }

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

        void onViewClick(int position, DetailsList detailsList);

    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

}
