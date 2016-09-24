package com.javinindia.citymalls.recyclerview;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.javinindia.citymalls.R;
import com.javinindia.citymalls.apiparsing.CountryModel;

import java.util.ArrayList;
import java.util.List;


public class AboutAdaptar extends RecyclerView.Adapter<AboutAdaptar.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    List<CountryModel> list;
    Context context;
    MyClickListener myClickListener;
    ArrayList<CountryModel> countryModelArrayList;


    public AboutAdaptar(List<CountryModel> mCountryModel) {
        this.list = mCountryModel;
        this.countryModelArrayList = new ArrayList<>();
        this.countryModelArrayList.addAll(mCountryModel);
    }

  /*  public AboutAdaptar(Context context, String name, String description, String imgUrl, ArrayList postUrl, String completionStatus, String createDate, String typeWish, String uId) {
        this.context = context;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.postUrl = postUrl;
        this.completionStatus = completionStatus;
        this.createDate = createDate;
        this.typeWish = typeWish;
        this.uId = uId;
    }*/

   /* public void setData(List<Object> list) {
        this.list = list;
    }

    public List<Object> getData() {
        return list;
    }
*/
    public class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;
       //***********************Recent list
        public AppCompatTextView txtShopName,txtRating,txtAddress,txtTiming,txtDistance,txtOffers;
        public RatingBar ratingBar;
        public RelativeLayout rlMain;
        public CheckBox chkImage;
        public AppCompatButton btnDirection,btnViewOffers;
        public ImageView imgShopLogo;

       //********************** about header
       public AppCompatTextView txtShopNameHeader,txtTimingHeader,txtViewsHeader,txtLocationHeader,txtCategoryHeader,txtFaverateCountHeader;
       public AppCompatButton btnEditProfile;
       public ImageView imgLogoAbout;


        public ViewHolder(View itemLayoutView, int ViewType) {
            super(itemLayoutView);

            if (ViewType == TYPE_ITEM) {
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
                holderId = 1;
            } else {
                txtShopNameHeader = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtShopNameAbout);
                txtTimingHeader  = (AppCompatTextView)itemLayoutView.findViewById(R.id.txtTimingAbout);
                txtViewsHeader = (AppCompatTextView)itemLayoutView.findViewById(R.id.txtViewsAbout);
                txtLocationHeader = (AppCompatTextView)itemLayoutView.findViewById(R.id.txtLocationAbout);
                txtCategoryHeader = (AppCompatTextView)itemLayoutView.findViewById(R.id.txtCategoryAbout);
                txtFaverateCountHeader = (AppCompatTextView)itemLayoutView.findViewById(R.id.txtFaverateCountAbout);
                btnEditProfile = (AppCompatButton)itemLayoutView.findViewById(R.id.btnEditProfile);
                holderId = 0;
            }
        }
    }


    @Override
    public AboutAdaptar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_layout, parent, false);
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
//        VolleySingleTon volleySingleTon = VolleySingleTon.getInstance(context);
        if (viewHolder.holderId == 1) {

            final CountryModel countryModel = (CountryModel) list.get(position);
            String icoCode = countryModel.getisoCode();
            final String name = countryModel.getName();


            viewHolder.ratingBar.setRating(Float.parseFloat("2.0"));

          /*  viewHolder.chkImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                  //  myClickListener.onItemClick(position, countryModel);
                }
            });



        } else {

        }
    }

    @Override
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

    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

}
