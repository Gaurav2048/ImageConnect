package com.example.saurav.imageconnect;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
AppBarLayout appBarLayout;
CircleImageView imageView;
CircleImageView replace_profile;
    int maximum;
     int newheight=0, newweidth =0;
CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        replace_profile= (CircleImageView) findViewById(R.id.replace_profile);
imageView = (CircleImageView) findViewById(R.id.profile_pic);
        replace_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Gallary", "Camera"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Select");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                      if(item==0){
                          Intent intent = new Intent();
                          intent.setType("image/*");
                          intent.setAction(Intent.ACTION_GET_CONTENT);//
                          startActivityForResult(Intent.createChooser(intent, "Select Picture"),101);

                      }else if (item==1){

                      }
                        dialog.dismiss();
                    }
                });
                builder.show();

              }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), "please try again", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode==101){
            Uri uri = data.getData();
            CropImage.activity(uri)
                    .start(this);


        }



        super.onActivityResult(requestCode, resultCode, data);
        }

}
