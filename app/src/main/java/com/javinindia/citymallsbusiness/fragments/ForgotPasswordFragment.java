package com.javinindia.citymallsbusiness.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.font.FontRalewayMediumSingleTonClass;
import com.javinindia.citymallsbusiness.font.FontRalewayRegularSingleTonClass;
import com.javinindia.citymallsbusiness.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ForgotPasswordFragment extends BaseFragment implements View.OnClickListener {

    private EditText etEmailAddress;
    private RequestQueue requestQueue;
    private BaseFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity.getSupportActionBar().hide();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        initialize(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    private void initialize(View view) {
        ImageView imgBack=(ImageView)view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        TextView txtForgot = (TextView)view.findViewById(R.id.txtForgot);
        txtForgot.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        TextView txtForgotdiscription = (TextView)view.findViewById(R.id.txtForgotdiscription);
        txtForgotdiscription.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        AppCompatButton buttonResetPassword = (AppCompatButton) view.findViewById(R.id.btn_reset_password);
        buttonResetPassword.setTypeface(FontRalewayMediumSingleTonClass.getInstance(activity).getTypeFace());
        etEmailAddress = (EditText) view.findViewById(R.id.et_email_address);
        etEmailAddress.setTypeface(FontRalewayRegularSingleTonClass.getInstance(activity).getTypeFace());
        buttonResetPassword.setOnClickListener(this);
    }


    @Override
    protected int getFragmentLayout() {
        return R.layout.forgot_password_layout;
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
            case R.id.btn_reset_password:
                String email = etEmailAddress.getText().toString().trim();
                if (Utility.isEmailValid(email)) {
                    showLoader();
                    sendDataOnForgetPasswordApi(email);
                } else if (TextUtils.isEmpty(email)) {
                    etEmailAddress.setError("Enter your email id");
                    etEmailAddress.requestFocus();
                } else {
                    etEmailAddress.setError("Enter your correct email id");
                    etEmailAddress.requestFocus();
                }

                break;
            case R.id.imgBack:
                activity.onBackPressed();
                break;
        }
    }

    private void sendDataOnForgetPasswordApi(final String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.FORGET_PASSWORD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideLoader();
                        responseImplement(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideLoader();
                        volleyErrorHandle(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void responseImplement(String response) {
        JSONObject jsonObject = null;
        String status = null, userid = null, msg = null;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("status"))
                status = jsonObject.optString("status");
            if (jsonObject.has("userid"))
                userid = jsonObject.optString("userid");
            if (jsonObject.has("msg"))
                msg = jsonObject.optString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (status.equalsIgnoreCase("true") && !status.isEmpty()) {
            fragment = new LoginFragment();
            Toast.makeText(activity, "Congrats! Your password has been submitted.", Toast.LENGTH_SHORT).show();
            callFragmentMethod(fragment, this.getClass().getSimpleName(),R.id.navigationContainer);
        } else {
            if (!TextUtils.isEmpty(msg)) {
                showDialogMethod(msg);
            }
        }
    }

}
