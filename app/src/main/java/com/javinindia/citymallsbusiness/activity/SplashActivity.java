package com.javinindia.citymallsbusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.font.FontBookJackSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;

public class SplashActivity extends BaseActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private RequestQueue requestQueue;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    getSupportActionBar().hide();
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        SharedPreferencesManager.setDeviceToken(getApplicationContext(), android_id);
        setContentView(getLayoutResourceId());
        TextView txt_splash = (TextView) findViewById(R.id.txt_splash);
        txt_splash.setTypeface(FontBookJackSingleTonClass.getInstance(getApplicationContext()).getTypeFace());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashIntent = new Intent(SplashActivity.this, NavigationActivity.class);
                startActivity(splashIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

}
