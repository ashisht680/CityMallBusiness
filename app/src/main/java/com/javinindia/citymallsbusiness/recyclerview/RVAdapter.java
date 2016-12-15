package com.javinindia.citymallsbusiness.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.CountryModel;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<CountryModel> mCountryModel;
    private List<CountryModel> mOriginalCountryModel;

    public RVAdapter(List<CountryModel> mCountryModel) {
        this.mCountryModel = mCountryModel;
        this.mOriginalCountryModel = mCountryModel;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        final CountryModel model = mOriginalCountryModel.get(i);
        itemViewHolder.bind(model);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mOriginalCountryModel.size();
    }

    public void setFilter(List<CountryModel> countryModels){
        mOriginalCountryModel = new ArrayList<>();
        mOriginalCountryModel.addAll(countryModels);
        notifyDataSetChanged();
    }

}
