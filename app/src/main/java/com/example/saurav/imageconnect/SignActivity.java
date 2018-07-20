package com.example.saurav.imageconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignActivity extends AppCompatActivity {
Button button;
TextInputLayout text_input_namelayout;
ArrayList<String> imageList= new ArrayList<>();
String image= null;
    TextInputLayout text_input_numberlayout;
RequestQueue requestQueue;
String fcm_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        text_input_namelayout = (TextInputLayout) findViewById(R.id.text_input_namelayout);
        text_input_numberlayout = (TextInputLayout) findViewById(R.id.text_input_numberlayout);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        list();
        image=imageList.get(new Random().nextInt(25));
        button = (Button) findViewById(R.id.button);

        SharedPreferences sharedPreferences = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        fcm_token = sharedPreferences.getString("token", null);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(text_input_namelayout.getEditText().getText().toString())&& TextUtils.isEmpty(text_input_numberlayout.getEditText().getText().toString()) && TextUtils.isEmpty(fcm_token)){

                    Toast.makeText(getApplicationContext(), "Enter all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    SereverSide();

                }
            }
        });

    }
    private void list()
    {
        imageList.add("http://www.gunjancool.info/image/medium_01.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_02.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_03.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_04.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_05.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_06.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_07.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_08.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_08.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_11.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_12.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_13.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_14.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_15.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_16.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_17.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_18.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_19.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_20.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_21.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_22.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_23.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_24.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_25.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_26.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_27.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_27.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_27.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_01.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_01.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_01.jpg");
        imageList.add("http://www.gunjancool.info/image/medium_01.jpg");
    }
    private void SereverSide() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(text_input_namelayout.getEditText().getText().toString()+"@gmail.com", text_input_numberlayout.getEditText().getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                }else {

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(text_input_namelayout.getEditText().getText().toString()+"@gmail.com", text_input_numberlayout.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            Map<String, String> value_data = new HashMap<>();
                            value_data.put("name", text_input_namelayout.getEditText().getText().toString());
                            value_data.put("contact", text_input_numberlayout.getEditText().getText().toString());
                            value_data.put("fcm", fcm_token);
                            value_data.put("lastseen", String.valueOf(System.currentTimeMillis()));
                            value_data.put("image", "null");

                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(value_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        startActivity(new Intent(getApplicationContext(), OtpActivity.class));
                                        finish();
                                    }
                                }
                            });

                        }
                    });

                }
            }
        });

    }
}
