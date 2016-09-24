package com.javinindia.citymalls.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ajit Gupta on 7/6/2016.
 */
public class FontRalewayBoldItalicSingleTonClass {

    private static FontRalewayBoldItalicSingleTonClass instance;
    private static Typeface typeface;

    public static FontRalewayBoldItalicSingleTonClass getInstance(Context context) {
        synchronized (FontRalewayBoldItalicSingleTonClass.class) {
            if (instance == null) {
                instance = new FontRalewayBoldItalicSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "raleway-bolditalic.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}

