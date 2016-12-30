package com.javinindia.citymallsbusiness.recyclerview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.productcategory.ShopCategoryList;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.utility.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ashish on 03-10-2016.
 */
public class ShopCatSubCatAdaptar extends RecyclerView.Adapter<ShopCatSubCatAdaptar.ViewHolder> {
    List<ShopCategoryList> list;
    Context context;
    MyClickListener myClickListener;
    ArrayList<ShopCategoryList> shopCategoryListArrayList;

    public ShopCatSubCatAdaptar(Context context) {
        this.context = context;
    }

    public void setData(List<ShopCategoryList> list) {
        this.list = list;
        this.shopCategoryListArrayList = new ArrayList<>();
        this.shopCategoryListArrayList.addAll(list);
    }

    public List<ShopCategoryList> getData() {
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


        if (!TextUtils.isEmpty(shopCategoryDetails.getShopCategory().trim())) {
            String shopCategory = shopCategoryDetails.getShopCategory().trim();
            viewHolder.txtCategory.setText(Utility.fromHtml(shopCategory));
        }

        if (shopCategoryDetails.getSubCatDetailArrayList().size() > 0) {
            List listSub = shopCategoryDetails.getSubCatDetailArrayList();
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < listSub.size(); i++) {
                arrayList.add(shopCategoryDetails.getSubCatDetailArrayList().get(i).getShoSubpCategory().trim());
            }

            if (arrayList.size() > 0) {
                String[] subBategoryArray = new String[arrayList.size()];
                arrayList.toArray(subBategoryArray);

                String test = Arrays.toString(subBategoryArray);
                String test1 = test.replaceAll("[\\[\\](){}]", "");
                if (!TextUtils.isEmpty(test)) {
                    viewHolder.txtSubCat.setText(Utility.fromHtml(test1));
                }
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
            txtCategory.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
            txtSubCat = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtSubCat);
            txtSubCat.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
            llMain = (LinearLayout) itemLayoutView.findViewById(R.id.llMain);
        }
    }

    public interface MyClickListener {

        void onItemClick(int position, ShopCategoryList categoryList);

    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
/*
    public void addItem(List dataObj, int index) {
        list.add(dataObj);
        notifyItemInserted(index);
    }*/

    public void deleteItem(int index) {
        list.remove(index);
        notifyItemRemoved(index);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(shopCategoryListArrayList);
        } else {
            for (ShopCategoryList thankFulDetail : shopCategoryListArrayList) {
                if (thankFulDetail.getShopCategory().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    list.add(thankFulDetail);
                }
            }
        }
        notifyDataSetChanged();
    }

}