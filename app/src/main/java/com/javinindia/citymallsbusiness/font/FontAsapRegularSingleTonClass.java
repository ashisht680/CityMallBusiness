package com.javinindia.citymallsbusiness.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ashish on 20-09-2016.
 */
public class FontAsapRegularSingleTonClass {
    private static FontAsapRegularSingleTonClass instance;
    private static Typeface typeface;

    public static FontAsapRegularSingleTonClass getInstance(Context context) {
        synchronized (FontAsapRegularSingleTonClass.class) {
            if (instance == null) {
                instance = new FontAsapRegularSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "asap-regular.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
