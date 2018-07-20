package com.example.saurav.imageconnect;

import android.*;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.saurav.imageconnect.Adapter.ContactAdapter;
import com.example.saurav.imageconnect.database.DatabaseHelper;
import com.example.saurav.imageconnect.database.models.users;
import com.example.saurav.imageconnect.fields.contacts;
import com.goodiebag.pinview.Pinview;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {
String contact = null;
Pinview pinview= null;
String name= null;
String image= null;
    String fcm_token;
    DatabaseHelper databaseHelper;
RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        contact = getIntent().getStringExtra("number");
        name = getIntent().getStringExtra("name");
        image = getIntent().getStringExtra("image");
        pinview = (Pinview) findViewById(R.id.pinview);
        SharedPreferences sharedPreferences = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        fcm_token = sharedPreferences.getString("token", null);

        Dexter.withActivity(OtpActivity.this).withPermissions(new String[]{
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_CONTACTS
        }).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                   // getContacts();

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
finish();
            }
        }).check();


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {

                Check_otp();

             }
        });
    }

    private void Check_otp() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://buy2hand.in/api/restaurant_api/config/checkotp.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e( "onResponse: ",response );
                if(response.toString().equals("insidego")){
                     SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
                  SharedPreferences.Editor editor = sharedPreferences.edit();
                  editor.putString("name",name);
                  editor.putString("contact", contact);
                  editor.putString("image_link",image);
                  editor.apply();
                 getContacts();
//
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
             Map<String,String> hashmap = new HashMap<>();
             hashmap.put("contact", contact);
             hashmap.put("otp", pinview.getValue());
                return hashmap;
            }
        };

        requestQueue.add(stringRequest);



    }
    public ArrayList<String> generateList(){

        ArrayList<String> contactLsist =new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                     int count =0;
                    while (pCur.moveToNext()) {
                        if(count ==0)
                        {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                            contactLsist.add(phoneNo);
                           count =1;
                        }else{
                            count =0;
                        }
}
                    pCur.close();
      }
            }
        }
        if(cur!=null){
            cur.close();
        }
        return contactLsist;
    }

    private ArrayList<String> getSimContact(){
        ArrayList<String> contactLsist =new ArrayList<>();

        Uri simUri = Uri.parse("content://icc/adn");

        Cursor cursorSim = this.getContentResolver().query(simUri, null, null,null, null);

        while (cursorSim.moveToNext()) {
         contactLsist.add(cursorSim.getString(cursorSim.getColumnIndex("number")));
        }
        return  contactLsist;
    }
    private void getContacts() {

      ArrayList<String> contact= generateList();

        Log.e( "getContacts: ",contact.size()+" " );
      for(int i=0;i<contact.size();i++){
          Log.e( "getContacts: ",contact.get(i) );
          fetchandsave(contact.get(i).replace(" ", ""));
          Log.e( "getContacts: ",contact.get(i).replace(" ", "") );
    if(contact.size()-1==i)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
      }



    }

    private void fetchandsave(final String s) {
        Log.e( "fetchandsave: ",s+" " );

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://buy2hand.in/api/restaurant_api/config/info.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.toString().length()!=0){
                    JsonDecode(response);
                }
                Log.e( "onResponse: ",response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "onErrorResponse: ", error.getCause() +" "+ error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hasmap = new HashMap<>();
                hasmap.put("contact", s);

                return hasmap;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void JsonDecode(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("posts");
            JSONObject json =  jsonArray.getJSONObject(0);
            String name = json.getString("name");
            String contact = json.getString("contact");
            String fcm = json.getString("fcm");
            String image_link= json.getString("image");
            String last_seen = json.getString("lastseen");
            databaseHelper.seedUserTAble(new users(name,contact,fcm,image_link,last_seen, "" ));
            Log.e( "JsonDecode: ",json.getString("name") );


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class SmsListener extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                SmsMessage[] msgs = null;
                String msg_from;
                if (bundle != null){
                    //---retrieve the SMS message received---
                    try{
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for(int i=0; i<msgs.length; i++){
                            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            msg_from = msgs[i].getOriginatingAddress();
                            if(msg_from.equals("7002773232")){
                                String msgBody = msgs[i].getMessageBody();
                                Toast.makeText(getApplicationContext(), msgBody,Toast.LENGTH_SHORT).show();
                                break;
                            }

                        }
                    }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                    }
                }
            }
        }
    }

}
