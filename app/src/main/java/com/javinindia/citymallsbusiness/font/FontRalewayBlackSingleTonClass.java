package com.javinindia.citymallsbusiness.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ajit Gupta on 7/5/2016.
 */
public class FontRalewayBlackSingleTonClass {

    private static FontRalewayBlackSingleTonClass instance;
    private static Typeface typeface;

    public static FontRalewayBlackSingleTonClass getInstance(Context context) {
        synchronized (FontRalewayBlackSingleTonClass.class) {
            if (instance == null) {
                instance = new FontRalewayBlackSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "raleway-black.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
