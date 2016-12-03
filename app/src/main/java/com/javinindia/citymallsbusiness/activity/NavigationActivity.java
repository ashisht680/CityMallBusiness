package com.javinindia.citymallsbusiness.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.shoperprofileparsing.ShopViewResponse;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.fragments.BaseFragment;
import com.javinindia.citymallsbusiness.fragments.EditProfileFragment;
import com.javinindia.citymallsbusiness.fragments.ListShopProductCategoryFragment;
import com.javinindia.citymallsbusiness.fragments.LocationSearchFragment;
import com.javinindia.citymallsbusiness.fragments.LoginFragment;
import com.javinindia.citymallsbusiness.fragments.NavigationAboutFragment;
import com.javinindia.citymallsbusiness.fragments.OffersFragment;
import com.javinindia.citymallsbusiness.fragments.VisitFragment;
import com.javinindia.citymallsbusiness.picasso.CircleTransform;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.recyclerview.AboutAdaptar;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ashish on 12-09-2016.
 */
public class NavigationActivity extends BaseActivity implements LocationSearchFragment.OnCallBackListener,ListShopProductCategoryFragment.OnCallBackCategoryListListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public  String AVATAR_URL;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        initToolbar();
        setupDrawerLayout();
        final ImageView avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.avatar);
        final TextView txtShopName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.txtShopName);
        final TextView txtOwnerName = (TextView)navigationView.getHeaderView(0).findViewById(R.id.txtOwnerName);
        if(!TextUtils.isEmpty(SharedPreferencesManager.getProfileImage(getApplicationContext()))){
            Picasso.with(NavigationActivity.this).load(SharedPreferencesManager.getProfileImage(getApplicationContext())).transform(new CircleTransform()).into(avatar);
        }else {
            AVATAR_URL = "http://lorempixel.com/200/200/people/1/";
            Picasso.with(NavigationActivity.this).load(R.drawable.no_image_icon).transform(new CircleTransform()).into(avatar);
        }
        if(!TextUtils.isEmpty(SharedPreferencesManager.getUsername(getApplicationContext()))){
            txtShopName.setText(SharedPreferencesManager.getUsername(getApplicationContext()));
        }
        if (!TextUtils.isEmpty(SharedPreferencesManager.getOwnerName(getApplicationContext()))){
            txtOwnerName.setText(SharedPreferencesManager.getOwnerName(getApplicationContext()));
        }
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction().setCustomAnimations(0, 0, 0, 0);
        mFragmentTransaction.replace(R.id.navigationContainer, new NavigationAboutFragment()).commit();

    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle(null);

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                displayView(menuItem.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void displayView(CharSequence title) {
        if (title.equals("Home")) {
           // Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
            Intent refresh = new Intent(this, NavigationActivity.class);
            startActivity(refresh);
            finish();
            drawerLayout.closeDrawers();
        } else if (title.equals("Category List")) {
          //  Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
            ListShopProductCategoryFragment fragment = new ListShopProductCategoryFragment();
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .replace(R.id.navigationContainer, fragment).addToBackStack(this.getClass().getSimpleName()).commit();
            drawerLayout.closeDrawers();
        } else if (title.equals("Invite Amigo")) {
            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
           /* BaseFragment fragment = new ForgotPasswordFragment();
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .replace(R.id.navigationContainer, fragment).addToBackStack(Constants.NAVIGATION_DETAILS).commit();*/
            drawerLayout.closeDrawers();
        } else if (title.equals("more")) {
            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
            /*BaseFragment fragment = new SignUpAddressFragment();
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .replace(R.id.navigationContainer, fragment).addToBackStack(Constants.NAVIGATION_DETAILS).commit();*/
            drawerLayout.closeDrawers();
        } else if (title.equals("settings")) {
            /*BaseFragment fragment = new VisitFragment();
            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .replace(R.id.navigationContainer, fragment).addToBackStack(this.getClass().getSimpleName()).commit();*/
            drawerLayout.closeDrawers();
        } else if (title.equals("Logout")) {
          //  Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
            dialogBox();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Logout");
        alertDialogBuilder.setMessage("Thanks for visiting City mall business! Be back soon!");
        alertDialogBuilder.setPositiveButton("Ok!",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        SharedPreferencesManager.setUserID(getApplicationContext(), null);
                        SharedPreferencesManager.setUsername(getApplicationContext(), null);
                        SharedPreferencesManager.setPassword(getApplicationContext(), null);
                        SharedPreferencesManager.setEmail(getApplicationContext(), null);
                        SharedPreferencesManager.setLocation(getApplicationContext(), null);
                        SharedPreferencesManager.setLatitude(getApplicationContext(),null);
                        SharedPreferencesManager.setLongitude(getApplicationContext(),null);
                        SharedPreferencesManager.setProfileImage(getApplicationContext(),null);
                        SharedPreferencesManager.setOwnerName(getApplicationContext(),null);
                        Intent refresh = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(refresh);//Start the same Activity
                        finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
           /* case R.id.action_search:
                LocationSearchFragment fragment = new LocationSearchFragment();
                mFragmentManager = getSupportFragmentManager();
                fragment.setMyCallBackListener(this);
                mFragmentManager.beginTransaction()
                        .replace(R.id.navigationContainer, fragment).addToBackStack(Constants.NAVIGATION_DETAILS).commit();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public void onCallBack(String a) {
        Toast.makeText(getApplication(), a, Toast.LENGTH_LONG).show();
        SharedPreferencesManager.setLocation(getApplicationContext(), a);
        Intent refresh = new Intent(this, NavigationActivity.class);
        startActivity(refresh);//Start the same Activity
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("NavigationActivity", "resume");
    }


    @Override
    public void onCallBackCatList() {
        Intent refresh = new Intent(this, NavigationActivity.class);
        startActivity(refresh);
        finish();
    }
}
