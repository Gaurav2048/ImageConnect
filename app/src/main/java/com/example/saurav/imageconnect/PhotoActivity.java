package com.example.saurav.imageconnect;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.saurav.imageconnect.Adapter.UploadGlryAdapter;
import com.example.saurav.imageconnect.Adapter.whpp_ge_recyclerAdapter;
import com.example.saurav.imageconnect.fields.gallary;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.OnSheetDismissedListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;


public class PhotoActivity extends AppCompatActivity {
    Camera camera;
    CameraView cameraView;
    boolean frontCam =false;
    RecyclerView glry_recycler_upload;
    BottomNavigationView navigationView;
    ImageView imageView_gallary;
    ImageView image_gly_up;
    int switchmode = 1;
    RecyclerView whatsapp_recyclerView;
    private Cursor cc = null;
    ArrayList<String> gallarylist ;

    BottomSheetLayout bottomSheet;
    ImageSwitcher image_flush;
    View gallaryView;
     CircleImageView button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        button = (CircleImageView) findViewById(R.id.cptrePhoto);
        cc =getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        gallarylist = getIntent().getStringArrayListExtra("gallaryList");
        gallaryView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.galary_lyout, bottomSheet, false);
        Toast.makeText(getApplicationContext(), "size"+gallarylist.size(), Toast.LENGTH_SHORT).show();
        bottomSheet = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        image_gly_up= (ImageView) findViewById(R.id.image_gly_up);
        cameraView = (CameraView)  findViewById(R.id.camera);
        cameraView.setFlash(CameraKit.Constants.FLASH_ON);
        whatsapp_recyclerView = (RecyclerView) findViewById(R.id.whtpp_rec_show);
        imageView_gallary = (ImageView) findViewById(R.id.image_galary);
        whatsapp_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false));
        image_gly_up.animate()
                .translationY(8)
                .setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(2000);
        image_flush= (ImageSwitcher) findViewById(R.id.image_flush);

        image_flush.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView view = new ImageView(getApplicationContext());

                return view;
            }
        });
        Animation in  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.inanim);
        Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.outanim);
        image_flush.setInAnimation(in);
        image_flush.setOutAnimation(out);
        image_flush.setImageResource(R.drawable.ic_flushon);
        image_flush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchmode==1){
                    cameraView.setFlash(CameraKit.Constants.FLASH_AUTO);
                    image_flush.setImageResource(R.drawable.ic_flashauto);
                    switchmode=2;
                }else if (switchmode==2){
                    cameraView.setFlash(CameraKit.Constants.FLASH_OFF);
                    image_flush.setImageResource(R.drawable.ic_flashoff);

                    switchmode=3;
                }else if(switchmode==3){
                    cameraView.setFlash(CameraKit.Constants.FLASH_ON);
                    image_flush.setImageResource(R.drawable.ic_flushon);

                    switchmode=1;
                }


            }
        });

        image_gly_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp_recyclerView.animate().alphaBy(-1.0f).setDuration(100);
                button.animate().alphaBy(-1.0f).setDuration(100);
                image_flush.animate().alphaBy(-1.0f).setDuration(100);
                imageView_gallary.animate().alphaBy(-1.0f).setDuration(100);
                bottomSheet.showWithSheetView(gallaryView);

            }
        });
        whatsapp_recyclerView.setAdapter(new whpp_ge_recyclerAdapter(getApplicationContext(), gallarylist,PhotoActivity.this));
        creatGlryview();
        bottomSheet.addOnSheetDismissedListener(new OnSheetDismissedListener() {
            @Override
            public void onDismissed(BottomSheetLayout bottomSheetLayout) {
                button.animate().alphaBy(1.0f).setDuration(100);
                gallaryView.animate().alphaBy(1.0f).setDuration(100);
            }
        });


        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {


            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Toast.makeText(getApplicationContext(), "taken"+cameraKitImage.getBitmap().getWidth(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });


        imageView_gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frontCam==false){
                    cameraView.setFacing(CameraKit.Constants.FACING_FRONT);
                    frontCam =true;
                    imageView_gallary.setImageResource(R.drawable.ic_bck);

                }else if (frontCam==true){
                    cameraView.setFacing(CameraKit.Constants.FACING_BACK);
                    frontCam =false;
                    imageView_gallary.setImageResource(R.drawable.ic_front);


                }


            }
        });


    }

    @Override
    protected void onStart() {
        cameraView.start();
        super.onStart();
    }

    @Override
    protected void onResume() {
        cameraView.start();
        super.onResume();
    }

    public void baggage(String baggage){
        Intent intent = new Intent();
        intent.putExtra("imageUri", baggage);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraView.stop();
        super.onStop();
    }



    private void creatGlryview() {
        glry_recycler_upload = (RecyclerView) gallaryView.findViewById(R.id.glry_recycler_upload);
        glry_recycler_upload.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        glry_recycler_upload.setAdapter(new UploadGlryAdapter(getApplicationContext(), gallarylist, PhotoActivity.this));
        ImageView  bck_cloase = (ImageView) gallaryView.findViewById(R.id.bck_cloase);
        bck_cloase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.dismissSheet();
            }
        });

    }
}
