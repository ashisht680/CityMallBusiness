package com.javinindia.citymallsbusiness.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.javinindia.citymallsbusiness.R;
import com.javinindia.citymallsbusiness.apiparsing.allViewparsing.AllViewsUsersResponse;
import com.javinindia.citymallsbusiness.constant.Constants;
import com.javinindia.citymallsbusiness.font.FontAsapRegularSingleTonClass;
import com.javinindia.citymallsbusiness.preference.SharedPreferencesManager;
import com.javinindia.citymallsbusiness.recyclerview.AllUserAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashish on 18-11-2016.
 */
public class AllViewUserFragment extends BaseFragment implements AllUserAdapter.MyClickListener {
    private RecyclerView recyclerview;
    private int startLimit = 0;
    private int countLimit = 10;
    private boolean loading = true;
    private RequestQueue requestQueue;
    private AllUserAdapter adapter;
    ArrayList arrayList;
    String offerId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        offerId = getArguments().getString("offerId");
        getArguments().remove("offerId");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null){
            menu.findItem(R.id.action_changePass).setVisible(false);
            menu.findItem(R.id.action_feedback).setVisible(false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableTouchOfBackFragment(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initToolbar(view);
        initializeMethod(view);
        sendRequestOnAllViewFeed();
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
        AppCompatTextView textView =(AppCompatTextView)view.findViewById(R.id.tittle) ;
        textView.setText("Viewers");
        textView.setTextColor(activity.getResources().getColor(android.R.color.white));
        textView.setTypeface(FontAsapRegularSingleTonClass.getInstance(activity).getTypeFace());
    }

    private void sendRequestOnAllViewFeed() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.VIEWER_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AllViewsUsersResponse responseparsing = new AllViewsUsersResponse();
                        responseparsing.responseParseMethod(response);
                        if(responseparsing.getStatus()==1){
                            arrayList = responseparsing.getUsersInfoArrayList();
                            if(arrayList.size()>0){
                                if (adapter.getData() != null && adapter.getData().size() > 0) {
                                    adapter.getData().addAll(arrayList);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter.setData(arrayList);
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyErrorHandle(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //shopid=9&offerid=34
                Map<String, String> params = new HashMap<String, String>();
                params.put("shopid", SharedPreferencesManager.getUserID(activity));
                params.put("offerid",offerId);
                return params;
            }

        };
        stringRequest.setTag(this.getClass().getSimpleName());
        volleyDefaultTimeIncreaseMethod(stringRequest);
        requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void initializeMethod(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerviewList);
        adapter = new AllUserAdapter(activity);
        LinearLayoutManager layoutMangerDestination
                = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutMangerDestination);
       // recyclerview.addOnScrollListener(new replyScrollListener());
        recyclerview.setAdapter(adapter);
        adapter.setMyClickListener(AllViewUserFragment.this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.all_view_layout;
    }

    @Override
    public int getToolbarMenu() {
        return 0;
    }

    @Override
    public void onNetworkConnected() {

    }
}
