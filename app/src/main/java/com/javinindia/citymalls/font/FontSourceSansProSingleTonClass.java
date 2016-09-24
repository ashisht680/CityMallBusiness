package com.javinindia.citymalls.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ajit Gupta on 7/6/2016.
 */
public class FontSourceSansProSingleTonClass {

    private static FontSourceSansProSingleTonClass instance;
    private static Typeface typeface;

    public static FontSourceSansProSingleTonClass getInstance(Context context) {
        synchronized (FontSourceSansProSingleTonClass.class) {
            if (instance == null) {
                instance = new FontSourceSansProSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "sourcesanspro-bolditalic.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}

