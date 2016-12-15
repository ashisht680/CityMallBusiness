package com.javinindia.citymallsbusiness.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class TermAndConditionFragment extends BaseFragment {

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
        AppCompatTextView txtTerm,txtPolicy,txtProperty;
        AppCompatTextView txtHead1,txtHead2,txtHead3,txtHead4,txtHead5,txtHead6,txtHead7,txtHead8,txtHead10;
        AppCompatTextView txtOne,txtTwo,txtThree,txtFour,txtFive,txtSix,txtSeven,txtEight,txtTen;
        txtTerm = (AppCompatTextView)view.findViewById(R.id.txtTerm);
        txtTerm.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtPolicy = (AppCompatTextView)view.findViewById(R.id.txtPolicy);
        txtPolicy.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtProperty = (AppCompatTextView)view.findViewById(R.id.txtProperty);
        txtProperty.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());

        txtHead1 = (AppCompatTextView)view.findViewById(R.id.txtHead1);
        txtHead1.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtHead2 = (AppCompatTextView)view.findViewById(R.id.txtHead2);
        txtHead2.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtHead3 = (AppCompatTextView)view.findViewById(R.id.txtHead3);
        txtHead3.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtHead4 = (AppCompatTextView)view.findViewById(R.id.txtHead4);
        txtHead4.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtHead5 = (AppCompatTextView)view.findViewById(R.id.txtHead5);
        txtHead5.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtHead6 = (AppCompatTextView)view.findViewById(R.id.txtHead6);
        txtHead6.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtHead7 = (AppCompatTextView)view.findViewById(R.id.txtHead7);
        txtHead7.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtHead8 = (AppCompatTextView)view.findViewById(R.id.txtHead8);
        txtHead8.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());
        txtHead10 = (AppCompatTextView)view.findViewById(R.id.txtHead10);
        txtHead10.setTypeface(FontAsapBoldSingleTonClass.getInstance(activity).getTypeFace());

        txtOne = (AppCompatTextView)view.findViewById(R.id.txtOne);
        txtOne.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtTwo = (AppCompatTextView)view.findViewById(R.id.txtTwo);
        txtTwo.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtThree = (AppCompatTextView)view.findViewById(R.id.txtThree);
        txtThree.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtFour = (AppCompatTextView)view.findViewById(R.id.txtFour);
        txtFour.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtFive = (AppCompatTextView)view.findViewById(R.id.txtFive);
        txtFive.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtSix = (AppCompatTextView)view.findViewById(R.id.txtSix);
        txtSix.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtSeven = (AppCompatTextView)view.findViewById(R.id.txtSeven);
        txtSeven.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtEight = (AppCompatTextView)view.findViewById(R.id.txtEight);
        txtEight.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        txtTen = (AppCompatTextView)view.findViewById(R.id.txtTen);
        txtTen.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());


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
        textView.setText("Term & Conditions");
        textView.setTextColor(activity.getResources().getColor(android.R.color.white));
        textView.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.term_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }
}
