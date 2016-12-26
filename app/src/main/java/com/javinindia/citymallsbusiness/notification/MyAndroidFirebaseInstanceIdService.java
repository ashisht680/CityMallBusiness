package com.javinindia.citymallsbusiness.notification;


import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;


public class MyAndroidFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyAndroidFCMIIDService";

    @Override
    public void onTokenRefresh() {

        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token

        if (!TextUtils.isEmpty(refreshedToken)){
            Log.d(TAG, "Refreshed token: " + refreshedToken);
            SharedPreferencesManager.setDeviceToken(getApplicationContext(),refreshedToken);
        }else {
            Log.d("device token", "not found");
        }


    }

}