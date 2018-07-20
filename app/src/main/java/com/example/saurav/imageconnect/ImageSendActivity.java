package com.example.saurav.imageconnect;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.saurav.imageconnect.utils.TouchImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageSendActivity extends AppCompatActivity {
String uri;
TouchImageView touch_view;
EditText editText;
CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_send);
    uri = getIntent().getStringExtra("uri");
    editText = (EditText) findViewById(R.id.message_binded);
        touch_view= (TouchImageView) findViewById(R.id.touch_view);
        touch_view.setImageURI(Uri.parse(uri));

        imageView = (CircleImageView) findViewById(R.id.image_message_send);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("image", uri);
                intent.putExtra("message", editText.getText().toString()+" ");
                 setResult(555, intent);
                 finish();
            }
        });

    }
}
