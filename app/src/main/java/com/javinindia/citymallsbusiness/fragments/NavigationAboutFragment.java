package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.activity.LoginActivity;
import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.DetailsList;
import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.OfferListResponseparsing;
import com.javinindia.citymallsbusiness.apiparsing.shoperprofileparsing.ShopViewResponse;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.picasso.CircleTransform;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.recyclerview.AboutAdaptar;
import com.javinindia.citymallsbusiness.utility.CheckConnection;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 09-09-2016.
 */
public class NavigationAboutFragment extends BaseFragment implements View.OnClickListener, AboutAdaptar.MyClickListener, AddNewOfferFragment.OnCallBackAddOfferListener, EditProfileFragment.OnCallBackEditProfileListener, UpdateOfferFragment.OnCallBackUpdateOfferListener, AllOffersFragment.OnCallBackRefreshListener, AddSubCatFragment.OnCallBackCAtegoryListener, CheckConnectionFragment.OnCallBackInternetListener {
    private RecyclerView recyclerview;
    private AboutAdaptar adapter;
    private RequestQueue requestQueue;
    ArrayList catArray;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initToolbar(view);
        setupDrawerLayout(view);
        initialize(view);
        sendDataOnRegistrationApi();
        return view;
    }

    private void initToolbar(View view) {
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarNav);
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        final ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(null);

    }

    private void setupDrawerLayout(View view) {
        drawerLayout = (DrawerLayout) view.findViewById(R.id.DrawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                displayView(menuItem.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });

        final ImageView avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);
        final TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtOwnerName);
        final TextView txtOwnerName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtShopName);
        if (!TextUtils.isEmpty(SharedPreferencesManager.getProfileImage(activity))) {
            Picasso.with(activity).load(SharedPreferencesManager.getProfileImage(activity)).transform(new CircleTransform()).into(avatar);
        } else {
            Picasso.with(activity).load(R.drawable.no_image_icon).transform(new CircleTransform()).into(avatar);
        }
        if (!TextUtils.isEmpty(SharedPreferencesManager.getUsername(activity))) {
            txtOwnerName.setText(SharedPreferencesManager.getUsername(activity));
        }
        if (!TextUtils.isEmpty(SharedPreferencesManager.getEmail(activity))) {
            email.setText(SharedPreferencesManager.getEmail(activity));
        }
    }

    private void displayView(CharSequence title) {
        if (CheckConnection.haveNetworkConnection(activity)) {
            if (title.equals("Home")) {
                drawerLayout.closeDrawers();
                Intent refresh = new Intent(activity, LoginActivity.class);
                startActivity(refresh);
                activity.finish();
            } else if (title.equals("Add Category")) {
                drawerLayout.closeDrawers();
                AddSubCatFragment categoryFragment = new AddSubCatFragment();
                categoryFragment.setMyCallBackCategoryListener(this);
                callFragmentMethod(categoryFragment, this.getClass().getSimpleName(), R.id.container);
            } else if (title.equals("Add Offer")) {
                drawerLayout.closeDrawers();
                AddNewOfferFragment fragment = new AddNewOfferFragment();
                fragment.setMyCallBackOfferListener(this);
                callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.container);
            } else if (title.equals("Category List")) {
                drawerLayout.closeDrawers();
                ListShopProductCategoryFragment fragment = new ListShopProductCategoryFragment();
                callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.container);
            } else if (title.equals("Looking For(Up coming)")) {
                drawerLayout.closeDrawers();
                looking_for_fragment fragment = new looking_for_fragment();
                callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.container);
            } else if (title.equals("About App")) {
                drawerLayout.closeDrawers();
                AboutAppFragments fragment = new AboutAppFragments();
                callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.container);
            } else if (title.equals("Invite")) {
                drawerLayout.closeDrawers();
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Ample App");
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.javinindia.citymallsbusiness\n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch (Exception e) {

                }

            } else if (title.equals("Rate us")) {
                drawerLayout.closeDrawers();
                final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            } else if (title.equals("Logout")) {
                drawerLayout.closeDrawers();
                dialogBox();
            }
        } else {
            methodCallCheckInternet();
        }
    }

    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Logout");
        alertDialogBuilder.setMessage("Thanks for visiting Ample Partner! Be back soon!");
        alertDialogBuilder.setPositiveButton("Ok!",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        sendDataOnLogOutApi();
                    }
                });

        alertDialogBuilder.setNegativeButton("Take me back",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void sendDataOnLogOutApi() {
        final ProgressDialog loading = ProgressDialog.show(activity, "Logging out...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGOUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        responseImplement(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        volleyErrorHandle(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", SharedPreferencesManager.getUserID(activity));
                params.put("type", "shop");
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void responseImplement(String response) {
        JSONObject jsonObject = null;
        String msg = null;
        int status = 0;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("status"))
                status = jsonObject.optInt("status");
            if (jsonObject.has("msg"))
                msg = jsonObject.optString("msg");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (status == 1) {
            SharedPreferencesManager.setUserID(activity, null);
            SharedPreferencesManager.setUsername(activity, null);
            SharedPreferencesManager.setPassword(activity, null);
            SharedPreferencesManager.setEmail(activity, null);
            SharedPreferencesManager.setLocation(activity, null);
            SharedPreferencesManager.setLatitude(activity, null);
            SharedPreferencesManager.setLongitude(activity, null);
            SharedPreferencesManager.setProfileImage(activity, null);
            SharedPreferencesManager.setOwnerName(activity, null);
            SharedPreferencesManager.setDeviceToken(activity, null);
            Intent refresh = new Intent(activity, LoginActivity.class);
            startActivity(refresh);//Start the same Activity
            activity.finish();
        } else {
            if (!TextUtils.isEmpty(msg)) {
                showDialogMethod("Try again");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
            case R.id.action_changePass:
                ChangePasswordFragment fragment = new ChangePasswordFragment();
                callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.container);
                drawerLayout.closeDrawers();
                return true;
            case R.id.action_feedback:
                FeedbackFragment fragment1 = new FeedbackFragment();
                callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.container);
                drawerLayout.closeDrawers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        activity.getMenuInflater().inflate(R.menu.navigation_menu, menu);
    }


    private void sendDataOnRegistrationApi() {
        progressBar.setVisibility(View.VISIBLE);
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOP_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        String status = null, sID = null, msg = null, sPic = null, banner;
                        String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong;
                        String shopCategory, shopSubCategory, country, pincode, rating, openTime, closeTime, distance, shopfavCount, sFloor, sNo;
                        int offerCount;
                        loading.dismiss();
                        ShopViewResponse shopViewResponse = new ShopViewResponse();
                        shopViewResponse.responseParseMethod(response);

                        status = shopViewResponse.getStatus().trim();

                        if (status.equalsIgnoreCase("true")) {
                            sID = shopViewResponse.getShopid().trim();
                            sPic = shopViewResponse.getProfilepic().trim();
                            sName = shopViewResponse.getStoreName().trim();
                            oName = shopViewResponse.getOwnerName().trim();
                            sEmail = shopViewResponse.getEmail().trim();
                            sMobileNum = shopViewResponse.getMobile().trim();
                            sLandline = shopViewResponse.getLandline().trim();
                            sState = shopViewResponse.getState().trim();
                            sCity = shopViewResponse.getCity().trim();
                            sAddress = shopViewResponse.getAddress().trim();
                            mName = shopViewResponse.getMallName().trim();
                            mAddress = shopViewResponse.getMallAddress();
                            mLat = shopViewResponse.getMallLat();
                            mLong = shopViewResponse.getMallLong();
                            country = shopViewResponse.getCountry().trim();
                            pincode = shopViewResponse.getPincode().trim();
                            rating = shopViewResponse.getRating().trim();
                            openTime = shopViewResponse.getShopOpenTime().trim();
                            closeTime = shopViewResponse.getShopCloseTime().trim();
                            distance = shopViewResponse.getDistance().trim();
                            offerCount = shopViewResponse.getOfferCount();
                            shopfavCount = shopViewResponse.getShopfavCount().trim();
                            banner = shopViewResponse.getBanner().trim();
                            sFloor = shopViewResponse.getFloor().trim();
                            sNo = shopViewResponse.getShopNum().trim();
                            saveDataOnPreference(sEmail, sName, mLat, mLong, sID, sPic, oName);
                            methodSetNavData();
                            catArray = shopViewResponse.getShopCategoryDetailsArrayList();
                            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                            recyclerview.setLayoutManager(layoutManager);
                            adapter = new AboutAdaptar(activity, sFloor, sNo, shopfavCount, sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong, sPic, country, pincode, rating, openTime, closeTime, distance, offerCount, banner, catArray);
                            recyclerview.setAdapter(adapter);
                            adapter.setMyClickListener(NavigationAboutFragment.this);
                            recyclerview.setItemAnimator(new DefaultItemAnimator());

                            offerRequestApi();
                        } else {
                            if (!TextUtils.isEmpty(msg)) {
                                showDialogMethod(msg);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        noInternetToast(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", SharedPreferencesManager.getUserID(activity));
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void methodSetNavData() {
        final ImageView avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);
        final TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtOwnerName);
        final TextView txtOwnerName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtShopName);
        if (!TextUtils.isEmpty(SharedPreferencesManager.getProfileImage(activity))) {
            Picasso.with(activity).load(SharedPreferencesManager.getProfileImage(activity)).transform(new CircleTransform()).into(avatar);
        } else {
            Picasso.with(activity).load(R.drawable.no_image_icon).transform(new CircleTransform()).into(avatar);
        }
        if (!TextUtils.isEmpty(SharedPreferencesManager.getUsername(activity))) {
            txtOwnerName.setText(SharedPreferencesManager.getUsername(activity));
        }
        if (!TextUtils.isEmpty(SharedPreferencesManager.getEmail(activity))) {
            email.setText(SharedPreferencesManager.getEmail(activity));
        }
    }

    private void saveDataOnPreference(String sEmail, String sName, String mLat, String mLong, String sID, String profilepic, String oName) {
        SharedPreferencesManager.setUserID(activity, sID);
        SharedPreferencesManager.setEmail(activity, sEmail);
        SharedPreferencesManager.setUsername(activity, sName);
        SharedPreferencesManager.setLatitude(activity, mLat);
        SharedPreferencesManager.setLongitude(activity, mLong);
        SharedPreferencesManager.setProfileImage(activity, profilepic);
        SharedPreferencesManager.setOwnerName(activity, oName);
    }

    private void offerRequestApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.OFFER_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        OfferListResponseparsing responseparsing = new OfferListResponseparsing();
                        responseparsing.responseParseMethod(response);
                        String status = responseparsing.getStatus().trim();
                        if (status.equals("true")) {
                            if (responseparsing.getDetailsListArrayList().size() > 0) {
                                ArrayList arrayList = responseparsing.getDetailsListArrayList();
                                if (arrayList.size() > 0) {
                                    if (adapter.getData() != null && adapter.getData().size() > 0) {
                                        adapter.getData().addAll(arrayList);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        adapter.setData(arrayList);
                                        adapter.notifyDataSetChanged();

                                    }
                                }
                            } else {
                                //  Toast.makeText(activity, "No offers", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            // Toast.makeText(activity, "No offers", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        noInternetToast(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", SharedPreferencesManager.getUserID(activity));
                params.put("startlimit", "0");
                params.put("countlimit", "5");
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void initialize(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        AppCompatButton btnAddOffer = (AppCompatButton) view.findViewById(R.id.btnAddOffer);
        btnAddOffer.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        btnAddOffer.setOnClickListener(this);
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.about_layout;
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
            case R.id.btnAddOffer:
                if (CheckConnection.haveNetworkConnection(activity)) {
                    AddNewOfferFragment fragment = new AddNewOfferFragment();
                    fragment.setMyCallBackOfferListener(this);
                    callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.container);
                } else {
                    methodCallCheckInternet();
                }
                break;
        }
    }

    public void methodCallCheckInternet() {
        CheckConnectionFragment fragment = new CheckConnectionFragment();
        fragment.setMyCallBackInternetListener(this);
        callFragmentMethod(fragment, this.getClass().getSimpleName(), R.id.container);
    }

    @Override
    public void onEditClick(int position) {
        if (CheckConnection.haveNetworkConnection(activity)) {
            EditProfileFragment fragment1 = new EditProfileFragment();
            fragment1.setMyCallBackOfferListener(this);
            callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.container);
        } else {
            methodCallCheckInternet();
        }
    }

    @Override
    public void onAllOffers(int position) {
        if (CheckConnection.haveNetworkConnection(activity)) {
            AllOffersFragment fragment1 = new AllOffersFragment();
            fragment1.setMyCallBackRefreshListener(this);
            callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.container);
        } else {
            methodCallCheckInternet();
        }
    }

    @Override
    public void onAddCategory(int position) {
        AddSubCatFragment categoryFragment = new AddSubCatFragment();
        callFragmentMethod(categoryFragment, this.getClass().getSimpleName(), R.id.container);
    }

    @Override
    public void onOfferClick(int position, DetailsList detailsList) {
        String shopNewAddress = "";
        String shopPic = detailsList.getOfferShopDetails().getShopProfilePic().trim();
        String brandName = detailsList.getOfferBrandDetails().getBrandName().trim();
        String brandPic = detailsList.getOfferBrandDetails().getBrandLogo().trim();
        String shopName = detailsList.getOfferShopDetails().getShopName().trim();
        String mallName = detailsList.getOfferMallDetails().getMallName().trim();
        String offerRating = "4";
        String offerPic = detailsList.getOfferDetails().getOfferBanner().trim();
        String offerTitle = detailsList.getOfferDetails().getOfferTitle().trim();
        String offerCategory = detailsList.getOfferDetails().getOfferCategory();
        String offerSubCategory = detailsList.getOfferDetails().getOfferSubcategory().trim();
        String offerPercentType = detailsList.getOfferDetails().getOfferPercentageType().trim();
        String offerPercentage = detailsList.getOfferDetails().getOfferPercentage().trim();
        String offerActualPrice = detailsList.getOfferDetails().getOfferActualPrice().trim();
        String offerDiscountPrice = detailsList.getOfferDetails().getOfferDiscountedPrice().trim();
        String offerStartDate = detailsList.getOfferDetails().getOfferOpenDate().trim();
        String offerCloseDate = detailsList.getOfferDetails().getOfferCloseDate().trim();
        String offerDescription = detailsList.getOfferDetails().getOfferDescription().trim();
        String shopOpenTime = detailsList.getOfferShopDetails().getShopOpenTime().trim();
        String shopCloseTime = detailsList.getOfferShopDetails().getShopCloseTime().trim();
        String shopNo = detailsList.getOfferShopDetails().getShopNo().trim();
        String floor = detailsList.getOfferShopDetails().getShopFloorNo().trim();
        String favCount = detailsList.getFavCount().trim();
        final ArrayList<String> data = new ArrayList<>();
        if (!TextUtils.isEmpty(shopNo)) {
            data.add(shopNo);
        }
        if (!TextUtils.isEmpty(floor)) {
            data.add(floor);
        }

        if (data.size() > 0) {
            String str = Arrays.toString(data.toArray());
            String test = str.replaceAll("[\\[\\](){}]", "");
            shopNewAddress = test;
        }

        OfferPostFragment fragment1 = new OfferPostFragment();

        Bundle bundle = new Bundle();
        bundle.putString("shopNewAddress", shopNewAddress);
        bundle.putString("shopPic", shopPic);
        bundle.putString("favCount", favCount);
        bundle.putString("brandName", brandName);
        bundle.putString("brandPic", brandPic);
        bundle.putString("shopName", shopName);
        bundle.putString("mallName", mallName);
        bundle.putString("offerRating", offerRating);
        bundle.putString("offerPic", offerPic);
        bundle.putString("offerTitle", offerTitle);
        bundle.putString("offerCategory", offerCategory);
        bundle.putString("offerSubCategory", offerSubCategory);
        bundle.putString("offerPercentType", offerPercentType);
        bundle.putString("offerPercentage", offerPercentage);
        bundle.putString("offerActualPrice", offerActualPrice);
        bundle.putString("offerDiscountPrice", offerDiscountPrice);
        bundle.putString("offerStartDate", offerStartDate);
        bundle.putString("offerCloseDate", offerCloseDate);
        bundle.putString("offerDescription", offerDescription);
        bundle.putString("shopOpenTime", shopOpenTime);
        bundle.putString("shopCloseTime", shopCloseTime);
        fragment1.setArguments(bundle);
        callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.container);
    }

    @Override
    public void onOfferEditClick(int position, DetailsList detailsList) {
        String brandId = detailsList.getOfferBrandDetails().getBrandId().trim();
        String brandName = detailsList.getOfferBrandDetails().getBrandName().trim();
        String brandPic = detailsList.getOfferBrandDetails().getBrandLogo().trim();
        String shopName = detailsList.getOfferShopDetails().getShopName().trim();
        String mallName = detailsList.getOfferMallDetails().getMallName().trim();
        String offerId = detailsList.getOfferDetails().getOfferId().trim();
        String offerRating = "4";
        String offerPic = detailsList.getOfferDetails().getOfferBanner().trim();
        String offerTitle = detailsList.getOfferDetails().getOfferTitle().trim();
        String offerCatId = detailsList.getOfferBrandDetails().getBrandOfferCategory().trim();
        String offerCategory = detailsList.getOfferDetails().getOfferCategory();
        String offerSubCatId = detailsList.getOfferBrandDetails().getBrandOfferSubCategory().trim();
        String offerSubCategory = detailsList.getOfferDetails().getOfferSubcategory().trim();
        String offerPercentType = detailsList.getOfferDetails().getOfferPercentageType().trim();
        String offerPercentage = detailsList.getOfferDetails().getOfferPercentage().trim();
        String offerActualPrice = detailsList.getOfferDetails().getOfferActualPrice().trim();
        String offerDiscountPrice = detailsList.getOfferDetails().getOfferDiscountedPrice().trim();
        String offerStartDate = detailsList.getOfferDetails().getOfferOpenDate().trim();
        String offerCloseDate = detailsList.getOfferDetails().getOfferCloseDate().trim();
        String offerDescription = detailsList.getOfferDetails().getOfferDescription().trim();
        String shopOpenTime = detailsList.getOfferShopDetails().getShopOpenTime().trim();
        String shopCloseTime = detailsList.getOfferShopDetails().getShopCloseTime().trim();

        UpdateOfferFragment fragment1 = new UpdateOfferFragment();

        Bundle bundle = new Bundle();
        //    bundle.putSerializable("images", postImage);
        bundle.putString("brandId", brandId);
        bundle.putString("offerCatId", offerCatId);
        bundle.putString("offerSubCatId", offerSubCatId);
        bundle.putString("offerId", offerId);
        bundle.putString("brandName", brandName);
        bundle.putString("brandPic", brandPic);
        bundle.putString("shopName", shopName);
        bundle.putString("mallName", mallName);
        bundle.putString("offerRating", offerRating);
        bundle.putString("offerPic", offerPic);
        bundle.putString("offerTitle", offerTitle);
        bundle.putString("offerCategory", offerCategory);
        bundle.putString("offerSubCategory", offerSubCategory);
        bundle.putString("offerPercentType", offerPercentType);
        bundle.putString("offerPercentage", offerPercentage);
        bundle.putString("offerActualPrice", offerActualPrice);
        bundle.putString("offerDiscountPrice", offerDiscountPrice);
        bundle.putString("offerStartDate", offerStartDate);
        bundle.putString("offerCloseDate", offerCloseDate);
        bundle.putString("offerDescription", offerDescription);
        bundle.putString("shopOpenTime", shopOpenTime);
        bundle.putString("shopCloseTime", shopCloseTime);
        fragment1.setArguments(bundle);
        fragment1.setMyCallBackUpdateOfferListener(this);
        callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.container);
    }

    @Override
    public void onViewClick(int position, DetailsList detailsList) {
        if (CheckConnection.haveNetworkConnection(activity)) {
            String offerId = detailsList.getOfferDetails().getOfferId().trim();
            String numView = detailsList.getOfferViewCount().toString().trim();
            if (!TextUtils.isEmpty(numView) && !numView.equals("0")) {
                AllViewUserFragment fragment1 = new AllViewUserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("offerId", offerId);
                fragment1.setArguments(bundle);
                callFragmentMethod(fragment1, this.getClass().getSimpleName(), R.id.container);
            } else {
                Toast.makeText(activity, "No views now", Toast.LENGTH_LONG).show();
            }
        } else {
            methodCallCheckInternet();
        }
    }


    @Override
    public void OnCallBackAddOffer() {
        sendDataOnRegistrationApi();
    }

    @Override
    public void OnCallBackEditProfile() {
        Intent refresh = new Intent(activity, LoginActivity.class);
        startActivity(refresh);
        activity.finish();
    }

    @Override
    public void OnCallBackUpdateOffer() {
        sendDataOnRegistrationApi();
    }

    @Override
    public void OnCallBackRefreshOffer() {
        sendDataOnRegistrationApi();
    }

    @Override
    public void onCallBackCat() {

    }

    @Override
    public void OnCallBackInternet() {
        Intent refresh = new Intent(activity, LoginActivity.class);
        startActivity(refresh);//Start the same Activity
        activity.finish();
    }
}
