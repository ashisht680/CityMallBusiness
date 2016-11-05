package com.javinindia.citymallsbusiness.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 05-10-2016.
 */
public class VisitFragment extends BaseFragment implements View.OnClickListener {
    private RequestQueue requestQueue;
    AppCompatEditText etName, etMobile, etEmail, etDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        etName = (AppCompatEditText) view.findViewById(R.id.etName);
        etMobile = (AppCompatEditText) view.findViewById(R.id.etMobile);
        etEmail = (AppCompatEditText) view.findViewById(R.id.etEmail);
        etDescription = (AppCompatEditText) view.findViewById(R.id.etDescription);

        AppCompatButton btnSave = (AppCompatButton) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.visit_layout;
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
            case R.id.btnSave:
                methodSubmit();
                break;
        }
    }

    private void methodSubmit() {
        if (!etName.getText().toString().equals("")) {
            if (!etMobile.getText().toString().equals("") && etMobile.getText().toString().length() == 10) {
                if (!etEmail.getText().toString().equals("")&& Utility.isEmailValid(etEmail.getText().toString())) {
                    if (!etDescription.getText().toString().equals("")) {
                        methodSave(etName.getText().toString(),etMobile.getText().toString(),etEmail.getText().toString(),etDescription.getText().toString());
                    } else {
                        Toast.makeText(activity, "write description please", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity, "write email address please", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(activity, "write correct mobile number please", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(activity, "write your name please", Toast.LENGTH_LONG).show();
        }
    }

    private void methodSave(final String name, final String molile, final String email, final String desc) {
        final ProgressDialog loading = ProgressDialog.show(activity, "Loading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.VISIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("response", response);
                        loading.dismiss();
                        JSONObject jsonObject = null;
                        String status = null, userId = null, socialId = null, msg = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.has("status"))
                                status = jsonObject.optString("status");
                            if (jsonObject.has("msg"))
                                msg = jsonObject.optString("msg");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (status.equals("true") && !TextUtils.isEmpty(status)) {
                            activity.onBackPressed();
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
                        volleyErrorHandle(error);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",name);
                params.put("phone", molile);
                params.put("email", email);
                params.put("description", desc);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }
}
