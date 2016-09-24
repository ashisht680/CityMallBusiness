package com.javinindia.citymalls.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.javinindia.citymalls.R;
import com.javinindia.citymalls.apiparsing.CountryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    List<CountryModel> list;
    Context context;
    MyClickListener myClickListener;
    ArrayList<CountryModel> countryModelArrayList;


    public CountryAdapter(List<CountryModel> mCountryModel) {
        this.list = mCountryModel;
        this.countryModelArrayList = new ArrayList<>();
        this.countryModelArrayList.addAll(mCountryModel);
    }



    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final CountryModel countryModel = (CountryModel) list.get(position);
        String icoCode = countryModel.getisoCode();
        final String name = countryModel.getName();
        if (!TextUtils.isEmpty(name))
            viewHolder.name_TextView.setText(name+"\t"+position);
        if (!TextUtils.isEmpty(name))
            viewHolder.iso_TextView.setText(icoCode);

        viewHolder.name_TextView.setOnClickListener(new View.OnClickListener() {
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

        public TextView name_TextView;
        public TextView iso_TextView;
        public LinearLayout llMain;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name_TextView = (TextView) itemLayoutView.findViewById(R.id.country_name);
            iso_TextView = (TextView) itemLayoutView.findViewById(R.id.country_iso);
            llMain = (LinearLayout) itemLayoutView.findViewById(R.id.llMain);

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



