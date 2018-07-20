package com.example.saurav.imageconnect.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by saurav on 6/19/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    String token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences =
                getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
        Log.e(  "onTokenRefresh: "," "+token );

    }
}
