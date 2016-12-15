package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.activity.NavigationActivity;
import com.javinindia.citymallsbusiness.apiparsing.loginsignupparsing.LoginSignupResponseParsing;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.utility.Utility;

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private RequestQueue requestQueue;
    private EditText etUsername;
    private EditText etPassword;
    private BaseFragment fragment;
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    private CheckBox checkShowPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity.getSupportActionBar().hide();
        Log.e("device", SharedPreferencesManager.getDeviceToken(activity));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        initialize(view);
        return view;
    }


    private void initialize(View view) {
        AppCompatButton buttonLogin = (AppCompatButton) view.findViewById(R.id.btn_login);
        buttonLogin.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        TextView txtForgotPass = (TextView) view.findViewById(R.id.forgot_password);
        txtForgotPass.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etUsername = (EditText) view.findViewById(R.id.et_username);
        etUsername.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etPassword = (EditText) view.findViewById(R.id.et_password);
        etPassword.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        etUsername.setText(SharedPreferencesManager.getEmail(activity));
        etPassword.setText(SharedPreferencesManager.getPassword(activity));
        TextView txtRegistration = (TextView) view.findViewById(R.id.txtRegistration);
        txtRegistration.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        buttonLogin.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        txtRegistration.setOnClickListener(this);
        checkShowPassword = (CheckBox) view.findViewById(R.id.checkShowPassword);
        checkShowPassword.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
        checkShowPassword.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.login_layout;
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
        BaseFragment baseFragment;
        switch (v.getId()) {
            case R.id.btn_login:
                Utility.hideKeyboard(activity);
                loginMethod();
                break;
            case R.id.forgot_password:
                baseFragment = new ForgotPasswordFragment();
                callFragmentMethod(baseFragment, this.getClass().getSimpleName(), R.id.container);
                break;
            case R.id.txtRegistration:
                baseFragment = new SignUpFragment();
                callFragmentMethod(baseFragment, this.getClass().getSimpleName(), R.id.container);
                break;
            case R.id.checkShowPassword:
                if (checkShowPassword.isChecked()) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loginMethod() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        SharedPreferencesManager.setPassword(activity, password);

        if (validation(username, password)) {
            sendDataOnLoginApi(username, password);
        }

    }

    private void sendDataOnLoginApi(final String username, final String password) {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.e("response", response);
                        String status = null, sID = null, msg = null, sPic = null;
                        String sName, oName, sEmail, sMobileNum, sLandline, sState, sCity, sAddress, mName, mAddress, mLat, mLong, banner;
                        hideLoader();
                        LoginSignupResponseParsing loginSignupResponseParsing = new LoginSignupResponseParsing();
                        loginSignupResponseParsing.responseParseMethod(response);

                        status = loginSignupResponseParsing.getStatus().trim();
                        msg = loginSignupResponseParsing.getMsg();

                        if (status.equalsIgnoreCase("true") && !status.isEmpty()) {
                            sID = loginSignupResponseParsing.getShopid();
                            sPic = loginSignupResponseParsing.getProfilepic();
                            banner = loginSignupResponseParsing.getBanner().trim();
                            sName = loginSignupResponseParsing.getStoreName();
                            oName = loginSignupResponseParsing.getOwnerName();
                            sEmail = loginSignupResponseParsing.getEmail();
                            sMobileNum = loginSignupResponseParsing.getMobile();
                            sLandline = loginSignupResponseParsing.getLandline();
                            sState = loginSignupResponseParsing.getState();
                            sCity = loginSignupResponseParsing.getCity();
                            sAddress = loginSignupResponseParsing.getAddress();
                            mName = loginSignupResponseParsing.getMallName();
                            mAddress = loginSignupResponseParsing.getMallAddress();
                            mLat = loginSignupResponseParsing.getMallLat();
                            mLong = loginSignupResponseParsing.getMallLong();
                            Log.e("sign up detail", sName + "\t" + oName + "\t" + sEmail + "\t" + sMobileNum + "\t" + sLandline + "\t" + sState + "\t" + sCity + "\t" + sAddress + "\t" + mName + "\t" + mAddress + "\t" + mLat + "\t" + mLong + "\t" + sPic);
                            saveDataOnPreference(sEmail, sName, mLat, mLong, sID, banner, oName);
                            Intent refresh = new Intent(activity, NavigationActivity.class);
                            startActivity(refresh);//Start the same Activity
                            activity.finish();
                            Log.e("image", SharedPreferencesManager.getProfileImage(activity));
                           /* fragment = new OffersFragment();
                            callFragmentMethodDead(fragment, this.getClass().getSimpleName());*/
                        } else {
                            if (!TextUtils.isEmpty(msg)) {
                                showDialogMethod("Invalid username/password.");
                            }
                        }
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
                params.put("email", username);
                params.put("password", password);
                //  String deviceToken = SharedPreferencesManager.getDeviceToken(activity);
                //   params.put("deviceToken", deviceToken);
                //  params.put("type", "manual");
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
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

    private boolean validation(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Mobile number entered is invalid");
            etUsername.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter Password.");
            etPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

}

