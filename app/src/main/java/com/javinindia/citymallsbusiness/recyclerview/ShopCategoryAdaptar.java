package com.javinindia.citymallsbusiness.recyclerview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.shoperprofileparsing.ShopCategoryDetails;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;

import java.util.List;


/**
 * Created by Ashish on 08-08-2016.
 */
public class ShopCategoryAdaptar extends RecyclerView.Adapter<ShopCategoryAdaptar.ViewHolder> {

    private Context context;
    private List<Object> list;
    MyClickListener myClickListener;

    public void setData(List<Object> list) {
        this.list = list;
    }

    public List<Object> getData() {
        return list;
    }

    public ShopCategoryAdaptar(Context context) {
        this.context = context;
    }

    @Override
    public ShopCategoryAdaptar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_single, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShopCategoryAdaptar.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        final ShopCategoryDetails tagLineArray = (ShopCategoryDetails) list.get(position);
        String tag = tagLineArray.getCategoryName();
        if (!TextUtils.isEmpty(tag))
            holder.tagItem.setText(tag);

    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tagItem;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tagItem = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtTagItem);
            tagItem.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
        }
    }

    public interface MyClickListener {
      //  void onGridItemClick(int position, ShopCategoryDetails TagDetails);
    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void addItem(List dataObj, int index) {
        list.add(dataObj);
        notifyItemInserted(index);
    }
}
