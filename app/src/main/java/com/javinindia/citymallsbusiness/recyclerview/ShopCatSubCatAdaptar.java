package com.javinindia.citymallsbusiness.recyclerview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ProductCategoryDetails;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ShopCategoryList;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ShopSubCatDetail;
import com.javinindia.citymallsbusiness.utility.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ashish on 03-10-2016.
 */
public class ShopCatSubCatAdaptar extends RecyclerView.Adapter<ShopCatSubCatAdaptar.ViewHolder> {
    List<Object> list;
    Context context;
    MyClickListener myClickListener;

    public ShopCatSubCatAdaptar(Context context) {
        this.context = context;
    }

    public void setData(List<Object> list) {
        this.list = list;
    }

    public List<Object> getData() {
        return list;
    }

    @Override
    public ShopCatSubCatAdaptar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_subcat_list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final ShopCategoryList shopCategoryDetails = (ShopCategoryList) list.get(position);
        String catListId = shopCategoryDetails.getId();
        String shopCatId = shopCategoryDetails.getShopCatId();
        String shopCategory = shopCategoryDetails.getShopCategory();
        List listSub = shopCategoryDetails.getSubCatDetailArrayList();
        ArrayList<String> arrayList = new ArrayList<>();

        if (!TextUtils.isEmpty(shopCategory))
            viewHolder.txtCategory.setText(shopCategory);

        for(int i =0;i<listSub.size();i++){
           arrayList.add(shopCategoryDetails.getSubCatDetailArrayList().get(i).getShoSubpCategory());
        }

        if(arrayList.size()>0){
           String[] subBategoryArray = new String[arrayList.size()];
            arrayList.toArray(subBategoryArray);

            String test = Arrays.toString(subBategoryArray);
            String test1 = test.replaceAll("[\\[\\](){}]", "");
            if (!TextUtils.isEmpty(test)) {
                viewHolder.txtSubCat.setText(test1);
            }
        }

        viewHolder.llMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myClickListener.onItemClick(position, shopCategoryDetails);
                return true;
            }
        });




    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView txtCategory, txtSubCat;
        LinearLayout llMain;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtCategory = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtCategory);
            // namePerson.setTypeface(FontRalewayMediumSingleTonClass.getInstance(context).getTypeFace());
            txtSubCat = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtSubCat);
            // time.setTypeface(FontRalewayMediumSingleTonClass.getInstance(context).getTypeFace());
            llMain = (LinearLayout)itemLayoutView.findViewById(R.id.llMain);
        }
    }

    public interface MyClickListener {

        void onItemClick(int position, ShopCategoryList categoryList);

    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void addItem(List dataObj, int index) {
        list.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        list.remove(index);
        notifyItemRemoved(index);
    }

}