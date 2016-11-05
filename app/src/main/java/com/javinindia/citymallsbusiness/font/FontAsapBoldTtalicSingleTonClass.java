package com.javinindia.citymallsbusiness.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ashish on 20-09-2016.
 */
public class FontAsapBoldTtalicSingleTonClass {
    private static FontAsapBoldTtalicSingleTonClass instance;
    private static Typeface typeface;

    public static FontAsapBoldTtalicSingleTonClass getInstance(Context context) {
        synchronized (FontAsapBoldTtalicSingleTonClass.class) {
            if (instance == null) {
                instance = new FontAsapBoldTtalicSingleTonClass();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "asap-bolditalic.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
