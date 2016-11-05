package com.javinindia.citymallsbusiness.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Ashish on 20-09-2016.
 */
public class FontAsapBoldSingleTonClass {
    private static FontAsapBoldSingleTonClass instance;
    private static Typeface typeface;

    public static FontAsapBoldSingleTonClass getInstance(Context context) {
        synchronized (FontAsapBoldSingleTonClass.class) {
            if (instance == null) {
                instance = new FontAsapBoldSingleTonClass();
                if(context != null){
                    typeface = Typeface.createFromAsset(context.getResources().getAssets(), "asap-bold.ttf");
                }

            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}
