package com.javinindia.citymallsbusiness.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.font.FontAsapBoldSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;

/**
 * Created by Ashish on 14-12-2016.
 */

public class AboutAppFragments extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null){
            menu.findItem(R.id.action_changePass).setVisible(false);
            menu.findItem(R.id.action_feedback).setVisible(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initToolbar(view);
        AppCompatTextView txtTitle,txtQuestion,txtAns,txtPoints;
        txtTitle = (AppCompatTextView)view.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtQuestion = (AppCompatTextView)view.findViewById(R.id.txtQuestion);
        txtQuestion.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtAns = (AppCompatTextView)view.findViewById(R.id.txtAns);
        txtAns.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtPoints = (AppCompatTextView)view.findViewById(R.id.txtPoints);
        txtPoints.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
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
        textView.setText("About application");
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
    }
    @Override
    protected int getFragmentLayout() {
        return R.layout.abut_app_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }
}
