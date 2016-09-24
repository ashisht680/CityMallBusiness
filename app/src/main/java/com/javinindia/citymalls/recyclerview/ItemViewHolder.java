package com.javinindia.citymalls.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.javinindia.citymalls.R;
import com.javinindia.citymalls.apiparsing.CountryModel;


public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView name_TextView;
    public TextView iso_TextView;
    Context context;



    public ItemViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        name_TextView = (TextView) itemView.findViewById(R.id.country_name);
        iso_TextView = (TextView) itemView.findViewById(R.id.country_iso);

    }

    public void bind(final CountryModel countryModel) {
        name_TextView.setText(countryModel.getName());
        iso_TextView.setText(countryModel.getisoCode());


    }


}
