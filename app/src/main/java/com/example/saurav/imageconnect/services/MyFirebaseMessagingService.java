package com.example.saurav.imageconnect.services;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.saurav.imageconnect.database.DatabaseHelper;
import com.example.saurav.imageconnect.database.models.Note;
import com.example.saurav.imageconnect.fields.message;
import com.example.saurav.imageconnect.fields.profile;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saurav on 6/20/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(  "onMessageReceived: ",remoteMessage.getData()+" " );
        Intent intent = new Intent("my.action.string");

      //  intent.putExtra("title", remoteMessage.getNotification().getTitle());

        intent.putExtra("body", String.valueOf(remoteMessage.getData()));
       // intent.putExtra("extra", phoneNo);
        sendBroadcast(intent);

    }
}
