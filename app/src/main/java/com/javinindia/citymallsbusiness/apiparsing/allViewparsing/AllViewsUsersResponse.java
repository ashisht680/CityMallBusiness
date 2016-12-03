package com.javinindia.citymallsbusiness.apiparsing.allViewparsing;

import android.util.Log;

import com.javinindia.citymallsbusiness.apiparsing.offerListparsing.DetailsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish on 18-11-2016.
 */
public class AllViewsUsersResponse {
    private int status;
    private String msg;
    private ArrayList<UsersInfo> usersInfoArrayList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<UsersInfo> getUsersInfoArrayList() {
        return usersInfoArrayList;
    }

    public void setUsersInfoArrayList(ArrayList<UsersInfo> usersInfoArrayList) {
        this.usersInfoArrayList = usersInfoArrayList;
    }

    public void responseParseMethod(Object response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            setMsg(jsonObject.optString("msg"));
            setStatus(jsonObject.optInt("status"));
            if (jsonObject.optInt("status")==1 && jsonObject.has("UsersInfo") && jsonObject.optJSONArray("UsersInfo")!=null)
                setUsersInfoArrayList(getAllViewMethod(jsonObject.optJSONArray("UsersInfo")));

            Log.d("Response", this.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<UsersInfo> getAllViewMethod(JSONArray usersInfo) {
        ArrayList<UsersInfo> usersInfos = new ArrayList<>();
        for (int i = 0; i < usersInfo.length(); i++) {
            UsersInfo info = new UsersInfo();
            JSONObject jsonObject = usersInfo.optJSONObject(i);

            if (jsonObject.has("userid"))
                info.setUserid(jsonObject.optString("userid"));
            if (jsonObject.has("name"))
                info.setName(jsonObject.optString("name"));
            if (jsonObject.has("email"))
                info.setEmail(jsonObject.optString("email"));
            if (jsonObject.has("phone"))
                info.setPhone(jsonObject.optString("phone"));
            if (jsonObject.has("pic"))
                info.setPic(jsonObject.optString("pic"));
            if (jsonObject.has("date"))
                info.setDate(jsonObject.optString("date"));

            usersInfos.add(info);
            Log.d("Response", usersInfos.toString());
        }
        return usersInfos;
    }
}
