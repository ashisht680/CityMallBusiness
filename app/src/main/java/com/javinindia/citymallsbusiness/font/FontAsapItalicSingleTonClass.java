package com.javinindia.citymallsbusiness.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ashish on 20-09-2016.
 */
public class FontAsapItalicSingleTonClass {
    private static FontAsapItalicSingleTonClass instance;
    private static Typeface typeface;

    public static FontAsapItalicSingleTonClass getInstance(Context context) {
        synchronized (FontAsapItalicSingleTonClass.class) {
            if (instance == null) {
                instance = new FontAsapItalicSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "asap-italic.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
