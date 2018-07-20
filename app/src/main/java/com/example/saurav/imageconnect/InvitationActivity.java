package com.example.saurav.imageconnect;

import android.*;
import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saurav.imageconnect.Adapter.ContactAdapter;
import com.example.saurav.imageconnect.fields.contacts;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;


public class InvitationActivity extends AppCompatActivity {
RecyclerView invitation_recycler;
BottomSheetLayout bottomSheetLayout;
    LinearLayout relativeLayout ;

    ArrayList<contacts> contactLsist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        invitation_recycler = (RecyclerView) findViewById(R.id.invitation_recycler);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);

invitation_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
       // stickyIndex.refresh();

        Dexter.withActivity(InvitationActivity.this).withPermissions(new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS
        }).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                    generateList();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();


    }
    public void generateList(){

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
                    while (pCur.moveToNext()) {
                        String Name = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String image_thumb = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                        String image = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                        String date = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DATA));

                        contacts contacts = new contacts(Name,phoneNo,image_thumb,image,date);

                        contactLsist.add(contacts);
                    }
                    pCur.close();

                    ContactAdapter adapter = new ContactAdapter(getApplicationContext(), contactLsist, bottomSheetLayout);
                    invitation_recycler.setAdapter(adapter);
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }


}
