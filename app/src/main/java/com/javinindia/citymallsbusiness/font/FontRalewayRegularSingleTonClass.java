package com.javinindia.citymallsbusiness.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ajit Gupta on 7/6/2016.
 */
public class FontRalewayRegularSingleTonClass {


    private static FontRalewayRegularSingleTonClass instance;
    private static Typeface typeface;

    public static FontRalewayRegularSingleTonClass getInstance(Context context) {
        synchronized (FontRalewayRegularSingleTonClass.class) {
            if (instance == null) {
                instance = new FontRalewayRegularSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "raleway-regular.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}

