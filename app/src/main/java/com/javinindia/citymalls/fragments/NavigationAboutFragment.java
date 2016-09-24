package com.javinindia.citymalls.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.javinindia.citymalls.R;
import com.javinindia.citymalls.apiparsing.CountryModel;
import com.javinindia.citymalls.recyclerview.AboutAdaptar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ashish on 09-09-2016.
 */
public class NavigationAboutFragment extends BaseFragment implements View.OnClickListener, AboutAdaptar.MyClickListener {
    private RecyclerView recyclerview;
    private List<CountryModel> mCountryModel;
    private AboutAdaptar adapter;
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

        adapter = new AboutAdaptar(mCountryModel);
        adapter.setMyClickListener(NavigationAboutFragment.this);
        recyclerview.setAdapter(adapter);
    }

    private void initialize(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
       /* AppCompatButton etEditProfile = (AppCompatButton)view.findViewById(R.id.etEditProfile);
        etEditProfile.setOnClickListener(this);*/
    }
    @Override
    protected int getFragmentLayout() {
        return R.layout.about_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }

  /*  @Override
    public void onItemClick(int position, CountryModel model) {

    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.etEditProfile:
                EditProfileFragment fragment = new EditProfileFragment();
                callFragmentMethod(fragment,this.getClass().getSimpleName(),R.id.navigationContainer);
                break;*/
        }
    }
}
