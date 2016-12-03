package com.javinindia.citymallsbusiness.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.javinindia.citymallsbusiness.constant.Constants;

public class SharedPreferencesManager {

    private SharedPreferencesManager() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static String getUsername(Context context) {
        return getSharedPreferences(context).getString(Constants.USERNAME, null);
    }

    public static void setUsername(Context context, String username) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.USERNAME, username);
        editor.commit();
    }


    public static String getEmail(Context context) {
        return getSharedPreferences(context).getString(Constants.EMAIL, null);
    }

    public static void setEmail(Context context, String email) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.EMAIL, email);
        editor.commit();
    }


    public static String getMobile(Context context) {
        return getSharedPreferences(context).getString(Constants.PHONE, null);
    }

    public static void setMobile(Context context, String phone) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.PHONE, phone);
        editor.commit();
    }


    public static String getUserID(Context context) {
        return getSharedPreferences(context).getString(Constants.USER_ID, null);
    }

    public static void setUserID(Context context, String userId) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(Constants.USER_ID, userId);
        editor.commit();
    }

    public static String getPassword(Context context) {
        return getSharedPreferences(context).getString("Password", null);
    }

    public static void setPassword(Context context, String userId) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("Password", userId);
        editor.commit();
    }


    public static String getDeviceToken(Context context) {
        return getSharedPreferences(context).getString("DeviceToken", null);
    }

    public static void setDeviceToken(Context context, String friendID) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("DeviceToken", friendID);
        editor.commit();
    }


    public static String getProfileImage(Context context) {
        return getSharedPreferences(context).getString("ProfileImage", null);
    }

    public static void setProfileImage(Context context, String profileImage) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("ProfileImage", profileImage);
        editor.commit();
    }

    public static String getLocation(Context context) {
        return getSharedPreferences(context).getString("Location", null);
    }

    public static void setLocation(Context context, String location) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("Location", location);
        editor.commit();
    }
    public static String getLatitude(Context context) {
        return getSharedPreferences(context).getString("Latitude", null);
    }

    public static void setLatitude(Context context, String latitude) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("Latitude", latitude);
        editor.commit();
    }

    public static String getLongitude(Context context) {
        return getSharedPreferences(context).getString("Longitude", null);
    }

    public static void setLongitude(Context context, String longitude) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("Longitude", longitude);
        editor.commit();
    }

    public static String getOwnerName(Context context) {
        return getSharedPreferences(context).getString("OwnerName", null);
    }

    public static void setOwnerName(Context context, String longitude) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("OwnerName", longitude);
        editor.commit();
    }


}
