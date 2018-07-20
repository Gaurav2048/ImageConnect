package com.example.saurav.imageconnect.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by saurav on 6/20/2018.
 */

public class pushFCMNotification {
    public final static String AUTH_KEY_FCM = "AAAABzvuuWg:APA91bHv5JMaLmCgXIi7HPB4ZsiwiEpKS4QEpw3Ha-13_qUmvjn1iNDs1uxHL9_AE8lun5O5eB3eZcMcV25cH_tcoMJLL3Hx-4KuIOel8UYcspfBkY6poyGWPKZI_ymTih4UorGweICq";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

// userDeviceIdKey is the device id you will query from your database

    public static void pushFCMNo_tification(String userDeviceIdKey,
                                            String title,
                                            String sender,
                                            String message,
                                            String type,
                                            String url_to_pass
                                            ) throws Exception{

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization","key="+authKey);
        conn.setRequestProperty("Content-Type","application/json");

        JSONObject json = new JSONObject();
        json.put("to",userDeviceIdKey.trim());
      //  JSONObject info = new JSONObject();js
      //  info.put("title", title); // Notification title
        JSONObject body = new JSONObject();
      body.put("title", title);
        body.put("type", type);
        body.put("url", url_to_pass);
        body.put("sender_id", sender);
        body.put("message", message);
        body.put("servertime", String.valueOf(System.currentTimeMillis()));
      //  info.put("body", body.toString()); // Notification body
        json.put("data", body);
        Log.e("pushFCMNo_tification: " ," "+json.toString() );

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();
        conn.getInputStream();
    }

    public static void pushToActiveTopic(   String title,
                                            String sender,
                                            String message
    ) throws Exception{

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization","key="+authKey);
        conn.setRequestProperty("Content-Type","application/json");

        JSONObject json = new JSONObject();
        json.put("to","/topics/active");
       // JSONObject info = new JSONObject();
        JSONObject body = new JSONObject();
        body.put("title", title); // Notification title
        body.put("sender_id", sender);
        body.put("message", message);
        // info.put("body", body.toString()); // Notification body
        json.put("data", body);
        Log.e("pushFCMNo_tification: " ," "+json.toString() );

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();
        conn.getInputStream();
    }

    public static void pushToActiveTyping(   String title,
                                            String toSend,
                                            String sender,
                                            String message
    ) throws Exception{

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization","key="+authKey);
        conn.setRequestProperty("Content-Type","application/json");

        JSONObject json = new JSONObject();
        json.put("to","/topics/typing");
        // JSONObject info = new JSONObject();
        JSONObject body = new JSONObject();
        body.put("title", title); // Notification title
        body.put("toSend", toSend); // Notification title
        body.put("sender_id", sender);
        body.put("message", message);
        // info.put("body", body.toString()); // Notification body
        json.put("data", body);
        Log.e("pushFCMNo_tification: " ," "+json.toString() );

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();
        conn.getInputStream();
    }
    public static void push_message_status_update(   String title,
                                             String fcm_adddress
                                             ,String sender,
                                             String message
    ) throws Exception{

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization","key="+authKey);
        conn.setRequestProperty("Content-Type","application/json");

        JSONObject json = new JSONObject();
        json.put("to",fcm_adddress.trim());
        // JSONObject info = new JSONObject();
        JSONObject body = new JSONObject();
        body.put("title", title); // Notification title
         body.put("sender_id", sender);
        body.put("message", message);
        // info.put("body", body.toString()); // Notification body
        json.put("data", body);
        Log.e("pushFCMNo_tification: " ," "+json.toString() );

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(json.toString());
        wr.flush();
        conn.getInputStream();
    }

}
