package com.javinindia.citymallsbusiness.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class BaseActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private NetworkConnected networkConnected;
    private Snackbar snackbar;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected abstract int getLayoutResourceId();
    public void volleyErrorHandle(VolleyError error) {
        NetworkResponse networkResponse = error.networkResponse;
        String facebookException = "net::ERR_INTERNET_DISCONNECTED";
        String errorMessage = "Unknown error";
        if (networkResponse == null) {
            if (error.getClass().equals(TimeoutError.class)) {
                registerNetworkListener();
                errorMessage = "Slow internet connection";
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                //showSnackBarMessage(errorMessage);
            } else if (error.getClass().equals(NoConnectionError.class)) {
                registerNetworkListener();
                errorMessage = "Check your internet connection";
                //showSnackBarMessage(errorMessage);
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
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
                    errorMessage = message + " Please login again";
                } else if (networkResponse.statusCode == 400) {
                    errorMessage = message + " Check your inputs";
                } else if (networkResponse.statusCode == 500) {
                    errorMessage = message + " Something is getting wrong";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("Error", errorMessage);
        error.printStackTrace();
    }

    public void showSnackBarMessage(String message) {

        snackbar = Snackbar.make(this.findViewById(android.R.id.content), message,
                Snackbar.LENGTH_INDEFINITE);

        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setGravity(Gravity.CENTER);
        snackbar.show();
    }

    public void registerNetworkListener() {
        if (this.networkConnected == null) {
            networkConnected = new NetworkConnected();
            this.registerReceiver(networkConnected,
                    new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
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
                this.unregisterReceiver(this.networkConnected);

                this.networkConnected = null;

                if (snackbar != null && snackbar.getView().isShown()) {
                    snackbar.dismiss();
                }
            } catch (Exception e) {
                // consume
            }
        }
    }

    @Override
    public void onDestroy() {
        unregisterNetworkListener();
        super.onDestroy();
    }

}
