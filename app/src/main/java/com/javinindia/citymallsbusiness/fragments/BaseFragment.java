package com.javinindia.citymallsbusiness.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.activity.BaseActivity;
import com.javinindia.citymallsbusiness.volleycustomrequest.CustomJSONObjectRequest;
import com.javinindia.citymallsbusiness.volleycustomrequest.CustomVolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Ajit Gupta on 5/10/2016.
 */
public abstract class BaseFragment extends Fragment implements Response.Listener, Response.ErrorListener {

    private RequestQueue mQueue;
    public BaseActivity activity;
    // For No internet Connection
    private Snackbar snackbar;

    // BroadcastReceiver
    private NetworkConnected networkConnected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(android.R.color.black));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity)
            activity = (BaseActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void disableTouchOfBackFragment(Bundle savedInstanceState) {
        if (getView() != null && savedInstanceState == null) {
            getView().getParent().requestDisallowInterceptTouchEvent(true);
            BaseFragment.disableTouchTheft(getView());
        }
    }

    public static void disableTouchTheft(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return true;
            }
        });
    }


    protected abstract int getFragmentLayout();

    /**
     * Child fragments will return toolbar menu which it needs to inflate.
     *
     * @return int
     */
    public abstract int getToolbarMenu();

    /**
     * Fragments will override this method for all kinds of network call.
     * TODO - Will see how much I need to change this concept
     */
    public abstract void onNetworkConnected();


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int menuResId = getToolbarMenu();
        if (menuResId != 0) {
            inflater.inflate(menuResId, menu);

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Gets a reference to the global progressbar, which is part of the view
     * for fragment activity
     *
     * @return ProgressBar
     */
    private ProgressBar findLoader() {
        if (activity != null) {
            return (ProgressBar) activity.findViewById(R.id.loadProgress);
        } else {
            return null;
        }
    }

    /**
     * Makes global progressbar visible
     */
    public void showLoader() {
        ProgressBar progressBar = findLoader();
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hides global progressbar by setting its visibility to GONE
     */
    public void hideLoader() {
        ProgressBar progressBar = findLoader();
        if (progressBar != null) {
            findLoader().setVisibility(View.GONE);
        }
    }


    protected void callFragmentMethodDead(BaseFragment baseFragment, final String TAG) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(0, 0, 0, 0);
        fragmentTransaction.add(R.id.navigationContainer, baseFragment);
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    protected void callFragmentMethod(BaseFragment baseFragment, final String TAG, int container) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(0, 0, 0, 0);
        fragmentTransaction.add(container, baseFragment);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }

    protected void volleyGetRequestMethod(String url, final String REQUEST_TAG) {
        mQueue = CustomVolleyRequestQueue.getInstance(this.getActivity())
                .getRequestQueue();

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), this, this);

        jsonRequest.setTag(REQUEST_TAG);

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonRequest);
    }


    protected void cancelQueueMethod(final String REQUEST_TAG) {
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onResponse(Object response) {
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    protected void showDialogMethod(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setTitle(msg);
        alertDialogBuilder
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void volleyDefaultTimeIncreaseMethod(StringRequest stringRequest) {
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(180000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void onBackPressButton(Activity activity) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager != null) {
            int fragmentStackSize = fragmentManager.getBackStackEntryCount();
            if (fragmentStackSize > 1) {
                fragmentManager.popBackStack();
            } else {
                activity.finish();
            }
        }
    }

    public void onBack(Activity activity){

    }

    public void volleyErrorHandle(VolleyError error){
        NetworkResponse networkResponse = error.networkResponse;
        String facebookException = "net::ERR_INTERNET_DISCONNECTED";
        String errorMessage = "Unknown error";
        if (networkResponse == null) {
            if (error.getClass().equals(TimeoutError.class)) {
                registerNetworkListener();
                errorMessage = "Slow internet connection";
                showSnackBarMessage(errorMessage);
            } else if (error.getClass().equals(NoConnectionError.class)) {
                registerNetworkListener();
                errorMessage = "Check your internet connection";
                showSnackBarMessage(errorMessage);
            }
        } else {
            String result = new String(networkResponse.data);
            try {
                JSONObject response = new JSONObject(result);
                String status = response.getString("status");
                String message = response.getString("message");

                Log.e("Error Status", status);
                Log.e("Error Message", message);

                if (networkResponse.statusCode == 404) {
                    errorMessage = "Resource not found";
                } else if (networkResponse.statusCode == 401) {
                    errorMessage = message+" Please login again";
                } else if (networkResponse.statusCode == 400) {
                    errorMessage = message+ " Check your inputs";
                } else if (networkResponse.statusCode == 500) {
                    errorMessage = message+" Something is getting wrong";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("Error", errorMessage);
        error.printStackTrace();
    }

    public void showSnackBarMessage(String message) {

        snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message,
                Snackbar.LENGTH_INDEFINITE);

        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setGravity(Gravity.CENTER);
        snackbar.show();
    }

    public class NetworkConnected extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getExtras() != null) {
                final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

                if (ni != null && ni.isConnectedOrConnecting()) {
                    if (snackbar != null && snackbar.getView().isShown()) {
                        snackbar.dismiss();
                    }
                    unregisterNetworkListener();
                }
            }
        }
    }

    private void unregisterNetworkListener() {
        if (this.networkConnected != null) {
            try {
                activity.unregisterReceiver(this.networkConnected);

                this.networkConnected = null;

                if (snackbar != null && snackbar.getView().isShown()) {
                    snackbar.dismiss();
                }
            } catch (Exception e) {
                // consume
            }
        }
    }

    public void registerNetworkListener() {
        if (this.networkConnected == null) {
            networkConnected = new NetworkConnected();
            activity.registerReceiver(networkConnected,
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    public void onDestroy() {
        unregisterNetworkListener();
        super.onDestroy();
    }

    public void noInternetToast(VolleyError error){
        NetworkResponse networkResponse = error.networkResponse;
        String errorMessage = "Unknown error";
        if (networkResponse == null) {
            if (error.getClass().equals(TimeoutError.class)) {
                registerNetworkListener();
                errorMessage = "Slow internet connection";
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
                //showSnackBarMessage(errorMessage);
            } else if (error.getClass().equals(NoConnectionError.class)) {
                registerNetworkListener();
                errorMessage = "Check your internet connection";
                //showSnackBarMessage(errorMessage);
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
            }
        }
    }
}

