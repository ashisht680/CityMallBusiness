package com.javinindia.citymalls.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.javinindia.citymalls.R;


public class OfferPostFragment extends BaseFragment implements View.OnClickListener {
    //  private ArrayList<PostImage> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView txtShopeName, txtOffer, txtDiscription, txtOfferTime;
    private int selectedPosition = 0;
    String shopeName, offer, discription, offerTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   images = (ArrayList<PostImage>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");
        shopeName = getArguments().getString("mallname");
        offer = getArguments().getString("offer");
        discription = getArguments().getString("discription");
        offerTime = getArguments().getString("timer");
        // setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(getFragmentLayout(), container, false);
        initialize(v);
        setCurrentItem(selectedPosition);
        return v;
    }

    private void initialize(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        txtShopeName = (TextView) v.findViewById(R.id.txtShopNameAbout);
        txtDiscription = (TextView) v.findViewById(R.id.txtDiscription);
        txtOffer = (TextView) v.findViewById(R.id.txtOffer);
        txtOfferTime = (TextView) v.findViewById(R.id.txtOfferTime);

        txtShopeName.setText(shopeName);
        txtOffer.setText(offer);
        txtDiscription.setText(discription);
        txtOfferTime.setText(offerTime);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        //   txtShopeName.setText((position + 1) + " of " + images.size());

        //   PostImage image = images.get(position);
        //  lblTitle.setText(image.getName());
        //     txtOffer.setText(Utility.getDateFormatAmAndPm(image.getCreateDate()));
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.offer_post_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
            //   PostImage image = images.get(position);

            //   Utility.imageLoadGlideLibrary(getActivity(), progressBar, imageViewPreview, image.getPostUrl());
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            //  return images.size();
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
