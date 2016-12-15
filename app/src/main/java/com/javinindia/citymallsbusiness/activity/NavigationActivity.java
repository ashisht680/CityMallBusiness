package com.javinindia.citymallsbusiness.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.fragments.NavigationAboutFragment;


/**
 * Created by Ashish on 12-09-2016.
 */
public class NavigationActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        NavigationAboutFragment fragment = new NavigationAboutFragment();
        mFragmentManager = this.getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.add(R.id.navigationContainer, fragment);
        mFragmentTransaction.addToBackStack("Angkjfhnk");
        mFragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }


}
