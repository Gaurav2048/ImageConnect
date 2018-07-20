package com.example.saurav.imageconnect;

import android.*;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saurav.imageconnect.Fragemnt.ChatFragment;
import com.example.saurav.imageconnect.Fragemnt.FriendFragment;
import com.example.saurav.imageconnect.Fragemnt.GroupFragment;
import com.example.saurav.imageconnect.fields.gallary;
import com.example.saurav.imageconnect.fields.profile;
import com.example.saurav.imageconnect.utils.pushFCMNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView cancel_toswipe;
    RelativeLayout card_info;
    CardView display_view;
    ImageView settings;
    String profile_contact;
    RelativeLayout measeure;
    TextView name,status;
    CircleImageView profile_image;
int activeCount =0;
    ArrayList<String> gallaryList = new ArrayList<>();
    private Cursor cc = null;
    int gone;
    CoordinatorLayout main_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("active");
        FirebaseMessaging.getInstance().subscribeToTopic("typing");
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
      SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        profile_contact = sharedPreferences.getString("contact", null);
String name_ = sharedPreferences.getString("name", null);
String image= sharedPreferences.getString("image", null);

      //  Picasso.get().load(image).into(profile_image);

        if(profile_contact==null){
          startActivity(new Intent(getApplicationContext(), StartScreenActivity.class));
          finish();
      }

        settings = (ImageView) findViewById(R.id.settings);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        seekPermission();
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        measeure = (RelativeLayout) findViewById(R.id.measure);
        status= (TextView) findViewById(R.id.status);
        name = (TextView) findViewById(R.id.name_prfile);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "text/OpenSans-Regular.ttf");
        name.setTypeface(typeface);
        status.setTypeface(typeface);
        main_view = (CoordinatorLayout) findViewById(R.id.main_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        cancel_toswipe = (ImageView) findViewById(R.id.cancel_toswipe);
        display_view = (CardView) findViewById(R.id.display_view);


         card_info = (RelativeLayout) findViewById(R.id.card_info);
        createViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();

        tabLayout.getTabAt(0).select();
        highlIghtTab(tabLayout.getTabAt(0), 0);
        // gone = display_view.getHeight();

        cancel_toswipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display_view.animate().translationX(main_view.getWidth()).setDuration(240);
                cancel_toswipe.animate().rotation(68).setDuration(58);
                final int operte = getHeightOfView(display_view) - getHeightOfView(card_info) - getHeightOfView(display_view) + getHeightOfView(measeure);

                tabLayout.animate().translationY((float) (-operte)).setStartDelay(240).setDuration(140);
                // viewPager.animate().translationY(-display_view.getHeight()).setStartDelay(240).setDuration(140);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                        params.height = (int) (viewPager.getHeight() + operte);
                        viewPager.setLayoutParams(params);
                    }
                }, 240);

            }
        });
        String fcmToken = "dphTwEt17-k:APA91bGK3znAb2QpzYI22z9rC5vpzbguZ-0xX5eKO7nYIfm-r2muhLvz5zfGmruYSK0Ut-oNpCb2LYs0wsqQ6PHbSW2LI_s25FlFQg-kNwn7-fTBgf0Ng7SMU89PSv-FWO1YrsJG5Ffd-VIeNhsgv3ycWXtd_CpGJg";

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                highlIghtTab(tab, tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                shlwTab(tab);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
     //   new notification().execute(fcmToken);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(tabLayout.getSelectedTabPosition()==2){
            tabLayout.getTabAt(1).select();
        }else if (tabLayout.getSelectedTabPosition()==1){
            tabLayout.getTabAt(0).select();
        }else if (tabLayout.getSelectedTabPosition()==0){
            super.onBackPressed();

        }
    }

    private void seekPermission() {
        Dexter.withActivity(MainActivity.this).withPermissions(
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted())
                {
                    cc =getContentResolver().query(
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                            null);
                    if(cc!=null) {
                        new taskSync().execute(cc);
                    }
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();
    }

    private int getHeightOfView(View contentview) {
        contentview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //contentview.getMeasuredWidth();
        return contentview.getMeasuredHeight();
    }

    private void shlwTab(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        int i = tab.getPosition();
        TextView name = view.findViewById(R.id.text);
        view.setBackground(null);
        view.setElevation(0);
        if (i == 0) {
            name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friend, 0, 0, 0);
        } else if (i == 1) {
            name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_chat, 0, 0, 0);

        } else if (i == 2) {
            name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group, 0, 0, 0);

        }
        name.setTextColor(Color.argb(255, 187, 192, 198));
        ImageView front = view.findViewById(R.id.front);
        ImageView back = view.findViewById(R.id.back);
        front.setEnabled(false);
        back.setEnabled(false);
        front.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
    }

    private void highlIghtTab(final TabLayout.Tab tab, final int i) {
        View view = tab.getCustomView();
        TextView name = view.findViewById(R.id.text);
        view.setBackgroundResource(R.drawable.tab_back);
        name.setTextColor(Color.argb(255, 255, 152, 0));
        if (i == 0) {
            name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_act_friends, 0, 0, 0);
        } else if (i == 1) {
            name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_act_chat, 0, 0, 0);

        } else if (i == 2) {
            name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_act_group, 0, 0, 0);

        }
        ImageView front = view.findViewById(R.id.front);
        ImageView back = view.findViewById(R.id.back);
        front.setEnabled(true);
        back.setEnabled(true);
        front.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toast
                if (i != 2) {
                    tabLayout.getTabAt(i + 1).select();
                    shlwTab(tab);
                    highlIghtTab(tabLayout.getTabAt(i + 1), i + 1);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != 0) {
                    tabLayout.getTabAt(i - 1).select();
                    shlwTab(tab);
                    highlIghtTab(tabLayout.getTabAt(i - 1), i - 1);
                }
            }
        });
        // click listner
    }

    @Override
    protected void onStart() {
        if(activeCount==0){

            new ActiveReport().execute("0");
activeCount=1;
        }super.onStart();
    }

    @Override
    protected void onPause() {
        if(activeCount==1){

            new ActiveReport().execute(String.valueOf(System.currentTimeMillis()));
activeCount=0;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        if(activeCount==0){

            new ActiveReport().execute(String.valueOf(System.currentTimeMillis()));

            activeCount=0;
        }    super.onDestroy();
    }

    public class ActiveReport extends  AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                pushFCMNotification.pushToActiveTopic("active" , profile_contact,strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void createTabIcons() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "text/OpenSans-Regular.ttf");
        RelativeLayout tabOne = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.amaze_tab, null);
        tabOne.setBackground(null);
        TextView tv_ = (TextView) tabOne.findViewById(R.id.text);
        tv_.setTypeface(typeface);
        tv_.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friend, 0, 0, 0);
        tv_.setCompoundDrawablePadding(6);
        tv_.setText("Friends");
        ImageView im_back = (ImageView) tabOne.findViewById(R.id.back);
        im_back.setVisibility(View.INVISIBLE);
        im_back.setImageResource(R.drawable.ic_gob);
        ImageView im_front = (ImageView) tabOne.findViewById(R.id.front);
        im_front.setVisibility(View.INVISIBLE);
        im_front.setImageResource(R.drawable.ic_gof);
        im_back.setEnabled(false);
        im_front.setEnabled(false);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        RelativeLayout tabOne1 = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.amaze_tab, null);
        tabOne1.setBackground(null);
        TextView tv_1 = (TextView) tabOne1.findViewById(R.id.text);
        tv_1.setTypeface(typeface);
        tv_1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_chat, 0, 0, 0);
        tv_1.setCompoundDrawablePadding(6);
        tv_1.setText("Chats");
        ImageView im_back1 = (ImageView) tabOne1.findViewById(R.id.back);
        im_back1.setVisibility(View.INVISIBLE);
        ImageView im_front1 = (ImageView) tabOne1.findViewById(R.id.front);
        im_front1.setVisibility(View.INVISIBLE);
        im_back1.setEnabled(false);
        im_front1.setEnabled(false);
        im_back1.setImageResource(R.drawable.ic_gob);
        im_front1.setImageResource(R.drawable.ic_gof);
        tabLayout.getTabAt(1).setCustomView(tabOne1);

        RelativeLayout tabOne2 = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.amaze_tab, null);
        tabOne2.setBackground(null);
        TextView tv_2 = (TextView) tabOne2.findViewById(R.id.text);
        tv_2.setTypeface(typeface);
        tv_2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_group, 0, 0, 0);
        tv_2.setCompoundDrawablePadding(6);
        tv_2.setText("Group");
        ImageView im_back2 = (ImageView) tabOne2.findViewById(R.id.back);
        im_back2.setVisibility(View.INVISIBLE);
        ImageView im_front2 = (ImageView) tabOne2.findViewById(R.id.front);
        im_front2.setVisibility(View.INVISIBLE);
        im_back2.setEnabled(false);
        im_front2.setEnabled(false);

        im_back2.setImageResource(R.drawable.ic_gob);
        im_front2.setImageResource(R.drawable.ic_gof);
        tabLayout.getTabAt(2).setCustomView(tabOne2);
    }


    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag( new FriendFragment(gallaryList), "friends");
        adapter.addFrag(new ChatFragment(gallaryList), "Chats");
        adapter.addFrag( new GroupFragment(), "Groups");
        viewPager.setAdapter(adapter);
    }
    public class taskSync extends AsyncTask<Cursor , Void , ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(Cursor... cursors) {
            Cursor cursor = cursors[0];
            cursor.moveToFirst();
            for (int i = 0; i < 15; i++) {
                cursor.moveToPosition(i);
                 gallaryList.add(cursor.getString(1));
            }
            return gallaryList;
        }
        @Override
        protected void onPostExecute(ArrayList<String> gallaries) {
            //   UploadGlryAdapter adapter = new UploadGlryAdapter(getContext(), gallaries);
            //   glry_recycler_upload.setAdapter(adapter);
            Toast.makeText(getApplicationContext(), "size"+gallaries.size(), Toast.LENGTH_SHORT).show();

            Log.e(  "onPostExecute: ","finished" );
            super.onPostExecute(gallaries);
        }
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
