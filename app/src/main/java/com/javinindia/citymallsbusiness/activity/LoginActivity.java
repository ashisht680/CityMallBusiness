package com.javinindia.citymallsbusiness.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.fragments.BaseFragment;
import com.javinindia.citymallsbusiness.fragments.LoginFragment;
import com.javinindia.citymallsbusiness.fragments.NavigationAboutFragment;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;

/**
 * Created by Ashish on 26-09-2016.
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        String username = SharedPreferencesManager.getUsername(getApplicationContext());
        if (TextUtils.isEmpty(username)) {
            BaseFragment baseFragment = new LoginFragment();
            FragmentManager fm = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(0, 0, 0, 0);
            fragmentTransaction.add(R.id.container, baseFragment);
            fragmentTransaction.commit();
        } else {
           /* Intent splashIntent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(splashIntent);
            finish();*/
            BaseFragment baseFragment = new NavigationAboutFragment();
            FragmentManager fm = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(0, 0, 0, 0);
            fragmentTransaction.add(R.id.container, baseFragment);
            fragmentTransaction.commit();
        }
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }
}
