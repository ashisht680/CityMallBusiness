package com.javinindia.citymallsbusiness.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.font.FontAsapBoldSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;

/**
 * Created by Ashish on 14-12-2016.
 */

public class FeedbackFragment extends BaseFragment {
    AppCompatEditText etFeedback;
    AppCompatButton btnFeedback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initToolbar(view);
        AppCompatTextView txtFeedback,txtTitle,txtPoints;
        txtFeedback = (AppCompatTextView)view.findViewById(R.id.txtFeedback);
        txtFeedback.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtTitle = (AppCompatTextView)view.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etFeedback = (AppCompatEditText)view.findViewById(R.id.etFeedback);
        etFeedback.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        btnFeedback = (AppCompatButton)view.findViewById(R.id.btnFeedback);
        btnFeedback.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        return view;
    }

    private void initToolbar(View view) {
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
        final ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(null);
        AppCompatTextView textView = (AppCompatTextView) view.findViewById(R.id.tittle);
        textView.setText("Feedback");
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.feedback_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }
}
