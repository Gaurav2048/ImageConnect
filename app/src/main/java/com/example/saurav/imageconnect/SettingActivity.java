package com.example.saurav.imageconnect;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
TextView name_user,status_user,account,chat,notification,data,friend_iv,gelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        name_user= (TextView) findViewById(R.id.name_user);
        status_user= (TextView) findViewById(R.id.status_user);
        account= (TextView) findViewById(R.id.account);
        chat= (TextView) findViewById(R.id.chat);
        notification= (TextView) findViewById(R.id.notification);
        data= (TextView) findViewById(R.id.data);
        friend_iv= (TextView) findViewById(R.id.friend_iv);
        gelp= (TextView) findViewById(R.id.gelp);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "text/OpenSans-Regular.ttf");
        name_user.setTypeface(typeface);
        status_user.setTypeface(typeface);
        account.setTypeface(typeface);
        notification.setTypeface(typeface);
        data.setTypeface(typeface);
        friend_iv.setTypeface(typeface);
        gelp.setTypeface(typeface);

    }
}
