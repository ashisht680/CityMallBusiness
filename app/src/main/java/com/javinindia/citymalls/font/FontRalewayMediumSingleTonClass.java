package com.javinindia.citymalls.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ajit Gupta on 7/6/2016.
 */
public class FontRalewayMediumSingleTonClass {


    private static FontRalewayMediumSingleTonClass instance;
    private static Typeface typeface;

    public static FontRalewayMediumSingleTonClass getInstance(Context context) {
        synchronized (FontRalewayMediumSingleTonClass.class) {
            if (instance == null) {
                instance = new FontRalewayMediumSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "raleway-medium.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
