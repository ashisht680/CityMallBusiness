package com.javinindia.citymallsbusiness.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.CountryModel;
import com.javinindia.citymallsbusiness.recyclerview.CountryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ashish on 12-09-2016.
 */
public class LocationSearchFragment extends BaseFragment implements TextWatcher, View.OnClickListener, CountryAdapter.MyClickListener {

    private RecyclerView recyclerview;
    private List<CountryModel> mCountryModel;
    private CountryAdapter adapter;
    private AppCompatEditText appCompatEditText;
    private OnCallBackListener callback;

    public interface OnCallBackListener {
        void onCallBack(String a);
    }

    public void setMyCallBackListener(OnCallBackListener callback) {
        this.callback = callback;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize(view);
        setRequest();
        return view;
    }

    private void setRequest() {
        String[] locales = Locale.getISOCountries();
        mCountryModel = new ArrayList<>();

        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            mCountryModel.add(new CountryModel(obj.getDisplayCountry(), obj.getISO3Country()));
        }

        adapter = new CountryAdapter(mCountryModel);
        adapter.setMyClickListener(LocationSearchFragment.this);
        recyclerview.setAdapter(adapter);
    }

    private void initialize(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        appCompatEditText = (AppCompatEditText) view.findViewById(R.id.etName);
        appCompatEditText.addTextChangedListener(this);


    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.offer_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString().toLowerCase(Locale.getDefault());
        adapter.filter(text);
    }

    @Override
    public void onItemClick(int position, CountryModel model) {
        String name = model.getName();
     //   Toast.makeText(activity,name,Toast.LENGTH_LONG).show();
      //  activity.onBackPressed();
        callback.onCallBack(model.getName());
    }
}
