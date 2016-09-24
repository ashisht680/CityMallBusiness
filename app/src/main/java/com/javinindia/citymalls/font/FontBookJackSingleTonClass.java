package com.javinindia.citymalls.font;

import android.content.Context;
import android.graphics.Typeface;

public class FontBookJackSingleTonClass {
    private static FontBookJackSingleTonClass instance;
    private static Typeface typeface;

    public static FontBookJackSingleTonClass getInstance(Context context) {
        synchronized (FontRalewayBlackSingleTonClass.class) {
            if (instance == null) {
                instance = new FontBookJackSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "bookjack.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
