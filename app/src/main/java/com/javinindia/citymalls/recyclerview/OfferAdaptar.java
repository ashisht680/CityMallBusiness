package com.javinindia.citymalls.recyclerview;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.RelativeLayout;


import com.javinindia.citymalls.R;
import com.javinindia.citymalls.apiparsing.CountryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ashish on 14-09-2016.
 */
public class OfferAdaptar extends RecyclerView.Adapter<OfferAdaptar.ViewHolder> {
    List<CountryModel> list;
    Context context;
    MyClickListener myClickListener;
    ArrayList<CountryModel> countryModelArrayList;


    public OfferAdaptar(List<CountryModel> mCountryModel) {
        this.list = mCountryModel;
        this.countryModelArrayList = new ArrayList<>();
        this.countryModelArrayList.addAll(mCountryModel);
    }


    @Override
    public OfferAdaptar.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final CountryModel countryModel = (CountryModel) list.get(position);
        String icoCode = countryModel.getisoCode();
        final String name = countryModel.getName();


        viewHolder.ratingBar.setRating(Float.parseFloat("2.0"));

  /*      viewHolder.chkImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(viewHolder.chkImage.isChecked()){
                    viewHolder.chkImage.setChecked(true);
                }else {
                    viewHolder.chkImage.setChecked(false);
                }
            }
        });*/


        viewHolder.txtShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(position, countryModel);
            }
        });
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(countryModelArrayList);
        } else {
            for (CountryModel model : countryModelArrayList) {
                if (model.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    list.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView txtShopName,txtRating,txtAddress,txtTiming,txtDistance,txtOffers;
        public RatingBar ratingBar;
        public RelativeLayout rlMain;
        public CheckBox chkImage;
        public AppCompatButton btnDirection,btnViewOffers;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtShopName = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtShopName);
            txtRating = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtRating);
            txtAddress = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtAddress);
            txtTiming = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtTimingAbout);
            txtDistance = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtDistance);
            txtOffers = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtOffers);
            ratingBar = (RatingBar) itemLayoutView.findViewById(R.id.ratingBar);
            rlMain = (RelativeLayout)itemLayoutView.findViewById(R.id.rlMain);
            chkImage = (CheckBox) itemLayoutView.findViewById(R.id.chkImage);
           /* btnDirection = (AppCompatButton)itemLayoutView.findViewById(R.id.btnDirection);
            btnViewOffers = (AppCompatButton)itemLayoutView.findViewById(R.id.btnViewOffers);*/

        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public interface MyClickListener {
        void onItemClick(int position, CountryModel model);
    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
}