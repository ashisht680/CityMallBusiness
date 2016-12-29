package com.javinindia.citymallsbusiness.recyclerview;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.DetailsList;
import com.javinindia.citymallsbusiness.font.FontAsapBoldSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.utility.Utility;
import com.javinindia.citymallsbusiness.volleycustomrequest.VolleySingleTon;

import java.util.List;

/**
 * Created by Ashish on 22-07-2016.
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    List<Object> list;
    Context context;
    MyClickListener myClickListener;

    public OfferAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Object> list) {
        this.list = list;
    }

    public List<Object> getData() {
        return list;
    }

    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OfferAdapter.ViewHolder viewHolder, final int position) {
        final VolleySingleTon volleySingleTon = VolleySingleTon.getInstance(context);
        final DetailsList requestDetail = (DetailsList) list.get(position);


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
            viewHolder.txtUPTO.setText(offerType);
            viewHolder.txtOfferPercent.setText(offerPercent + "% off");
        } else if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferActualPrice().trim()) && !TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferDiscountedPrice().trim()) && !TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferPercentageType().trim()) && TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferPercentage().trim())) {
            String offerActualPrice = requestDetail.getOfferDetails().getOfferActualPrice().trim();
            String offerDiscountPrice = requestDetail.getOfferDetails().getOfferDiscountedPrice().trim();
            String offerType = requestDetail.getOfferDetails().getOfferPercentageType().trim();
            double actual = Double.parseDouble(offerActualPrice);
            double discount = Double.parseDouble(offerDiscountPrice);
            int percent = (int) (100 - (discount * 100.0f) / actual);
            viewHolder.txtUPTO.setText(offerType);
            viewHolder.txtOfferPercent.setText(percent + "% off");
        } else if (TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferPercentageType().trim()) && !TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferPercentage().trim())) {
            viewHolder.txtUPTO.setText("UPTO");
            viewHolder.txtOfferPercent.setText(requestDetail.getOfferDetails().getOfferPercentage().trim() + "% off");
        } else {
            if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferActualPrice().trim()) && !TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferDiscountedPrice().trim())) {
                String offerActualPrice = requestDetail.getOfferDetails().getOfferActualPrice().trim();
                String offerDiscountPrice = requestDetail.getOfferDetails().getOfferDiscountedPrice().trim();
                double actual = Double.parseDouble(offerActualPrice);
                double discount = Double.parseDouble(offerDiscountPrice);
                int percent = (int) (100 - (discount * 100.0f) / actual);
                viewHolder.txtUPTO.setText("UPTO");
                viewHolder.txtOfferPercent.setText(percent + "% off");
            }

        }

        if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferActualPrice().trim()) && !TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferDiscountedPrice().trim())) {
            String offerActualPrice = requestDetail.getOfferDetails().getOfferActualPrice().trim();
            String offerDiscountPrice = requestDetail.getOfferDetails().getOfferDiscountedPrice().trim();
            //  viewHolder.llOffItem.setBackgroundColor(Color.parseColor("#1da6b9"));
            viewHolder.txtActualPrice.setText(Html.fromHtml("\u20B9" + offerActualPrice + "/-"));
            viewHolder.txtActualPrice.setPaintFlags(viewHolder.txtActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtDiscountPrice.setText(Html.fromHtml("\u20B9" + offerDiscountPrice + "/-"));
        }

        if (!TextUtils.isEmpty(requestDetail.getOfferDetails().getOfferBanner().trim())) {
            String offerBanner = requestDetail.getOfferDetails().getOfferBanner().trim();
            Utility.imageLoadGlideLibrary(context, viewHolder.progressBar, viewHolder.imgShopLogoOffer, offerBanner);
        } else if (!TextUtils.isEmpty(requestDetail.getOfferBrandDetails().getBrandLogo().trim())) {
            String brandLogo = requestDetail.getOfferBrandDetails().getBrandLogo().trim();
            Utility.imageLoadGlideLibrary(context, viewHolder.progressBar, viewHolder.imgShopLogoOffer, brandLogo);
        } else if (!TextUtils.isEmpty(requestDetail.getOfferShopDetails().getShopProfilePic().trim())) {
            String shopLogo = requestDetail.getOfferShopDetails().getShopProfilePic().trim();
            Utility.imageLoadGlideLibrary(context, viewHolder.progressBar, viewHolder.imgShopLogoOffer, shopLogo);
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
                myClickListener.onOfferItemClick(position, requestDetail);
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
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView txtShopName, txtSubCategoryItem, txtOfferTitle, txtOfferCategoryItem, txtTimingOffer,
                txtViewItem, txtEditOfferItem, txtActualPrice, txtDiscountPrice, txtOfferPercent, txtMRP,txtUPTO,txtOFFER;
        public CardView rlMain;
        public ImageView imgShopLogoOffer;
        LinearLayout llOffItem;
        public ProgressBar progressBar;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
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
            txtMRP = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtMRP);
            txtMRP.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
            txtUPTO = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtUPTO);
            txtUPTO.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
            txtOFFER = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtOFFER);
            txtOFFER.setTypeface(FontAsapBoldSingleTonClass.getInstance(context).getTypeFace());
            txtActualPrice = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtActualPrice);
            txtActualPrice.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
            txtDiscountPrice = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtDiscountPrice);
            txtDiscountPrice.setTypeface(FontAsapBoldSingleTonClass.getInstance(context).getTypeFace());
            txtOfferPercent = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtOfferPercent);
            txtOfferPercent.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
            imgShopLogoOffer = (ImageView) itemLayoutView.findViewById(R.id.imgShopLogoOffer);
            rlMain = (CardView) itemLayoutView.findViewById(R.id.rlMain);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public interface MyClickListener {
        void onOfferItemClick(int position, DetailsList detailsList);

        void onOfferEditClick(int position, DetailsList detailsList);

        void onViewClick(int position, DetailsList detailsList);

    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void deleteItem(int index) {
        list.remove(index);
        notifyItemRemoved(index);
    }
}
