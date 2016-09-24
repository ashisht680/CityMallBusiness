package com.javinindia.citymallsbusiness.font;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Ajit Gupta on 7/5/2016.
 */
public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        setTypeface(FontRalewayBlackSingleTonClass.getInstance(context).getTypeFace());
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontRalewayBlackSingleTonClass.getInstance(context).getTypeFace());
    }

    public CustomTextView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(FontRalewayBlackSingleTonClass.getInstance(context).getTypeFace());
    }

}

