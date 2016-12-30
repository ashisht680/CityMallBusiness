package com.javinindia.citymallsbusiness.recyclerview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.allViewparsing.UsersInfo;
import com.javinindia.citymallsbusiness.font.FontAsapBoldSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.utility.Utility;
import com.javinindia.citymallsbusiness.volleycustomrequest.VolleySingleTon;

import java.util.List;

/**
 * Created by Ashish on 18-11-2016.
 */
public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.ViewHolder> {
    List<Object> list;
    Context context;
    MyClickListener myClickListener;

    public AllUserAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Object> list) {
        this.list = list;
    }

    public List<Object> getData() {
        return list;
    }

    @Override
    public AllUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_user_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllUserAdapter.ViewHolder viewHolder, final int position) {
        final VolleySingleTon volleySingleTon = VolleySingleTon.getInstance(context);
        final UsersInfo requestDetail = (UsersInfo) list.get(position);

        if (!TextUtils.isEmpty(requestDetail.getName().trim())) {
            String Name = requestDetail.getName().trim();
            viewHolder.txtName.setText(Utility.fromHtml(Name));
        }
        if (!TextUtils.isEmpty(requestDetail.getDate().trim())) {
            String date = requestDetail.getDate().trim();
            viewHolder.txtTime.setText(date);
        }
        if (!TextUtils.isEmpty(requestDetail.getPic().trim())) {
            String pic = requestDetail.getPic().trim();
            Utility.imageLoadGlideLibrary(context, viewHolder.progressBar, viewHolder.imgProfile, pic);
        } else {
           viewHolder.imgProfile.setImageResource(R.drawable.no_image_icon);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView txtName, txtTime;
        public ImageView imgProfile;
        public ProgressBar progressBar;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            progressBar = (ProgressBar) itemLayoutView.findViewById(R.id.progress);
            txtName = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtName);
            txtName.setTypeface(FontAsapBoldSingleTonClass.getInstance(context).getTypeFace());
            txtTime = (AppCompatTextView) itemLayoutView.findViewById(R.id.txtTime);
            txtTime.setTypeface(FontAsapRegularSingleTonClass.getInstance(context).getTypeFace());
            imgProfile = (ImageView) itemLayoutView.findViewById(R.id.imgProfile);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public interface MyClickListener {

    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void deleteItem(int index) {
        list.remove(index);
        notifyItemRemoved(index);
    }
}