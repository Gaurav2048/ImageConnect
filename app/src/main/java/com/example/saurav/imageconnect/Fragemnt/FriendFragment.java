package com.example.saurav.imageconnect.Fragemnt;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saurav.imageconnect.Adapter.CheckAdapter;
import com.example.saurav.imageconnect.InvitationActivity;
import com.example.saurav.imageconnect.R;
import com.example.saurav.imageconnect.database.DatabaseHelper;
import com.example.saurav.imageconnect.database.models.users;
import com.example.saurav.imageconnect.utils.pushFCMNotification;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {

 DatabaseHelper databaseHelper ;
 int count;
  Button ui_chat;
 FloatingActionButton floatingActionButton;
 Button chatplus_ui;
 SharedPreferences sharedPreferences;
 RecyclerView recyclerView_friends;
 ArrayList<String> gallaryList;
    CheckAdapter adapter;
 ArrayList<users>usersArrayList;
    public FriendFragment(ArrayList<String> gallaryArrayList) {
        // Required empty public constructor
        this.gallaryList= gallaryArrayList;
    }

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_friend, container, false);
        Button chat_ui= (Button) view.findViewById(R.id.chat_ui);
        chatplus_ui= (Button) view.findViewById(R.id.chatplus_ui);
        databaseHelper = new DatabaseHelper(getContext());
        recyclerView_friends = (RecyclerView) view.findViewById(R.id.recyclerview_friends);
        recyclerView_friends.setLayoutManager(new LinearLayoutManager(getContext()));
     usersArrayList = databaseHelper.getFriends();
       adapter = new CheckAdapter(getContext(), usersArrayList,gallaryList);
     recyclerView_friends.setAdapter(adapter);
     Toast.makeText(getContext(), "user list "+usersArrayList.size(),Toast.LENGTH_SHORT).show();
      sharedPreferences = getContext().getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
      final String token =sharedPreferences.getString("token", null);
        Log.e( "onCreateView: ",token+" " );
        ui_chat= (Button) view.findViewById(R.id.ui_chat);
        count =0;
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.action_share);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), InvitationActivity.class));
                //bottomSheetLayout.showWithSheetView(relativeLayout);
            }
        });


        databaseHelper = new DatabaseHelper(getContext());

        chatplus_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new notification_two().execute(token);

            }
        });

        chat_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  /*              Note note = new Note();
                note.setMessagefrom("9898989898");
                note.setMessageto("9090909090");
                note.setMessage("Hi whats up");
                note.setServertime(String.valueOf(System.currentTimeMillis()));
                note.setChatid("9991");
                note.setUrl("http://www.gunjancool.info");
                note.setType("text");
                note.setMessageid(String.valueOf(count));
                databaseHelper.insertMessage(note);
                count++;
                Toast.makeText(getContext(), "size of convo "+databaseHelper.getNotesCount(),Toast.LENGTH_SHORT).show();
*/               // startActivity(new Intent(getContext(), ChatActivity.class));



                new notification().execute(token);

            }
        });
        ui_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new notification_one().execute(token);
            }
        });
    return view;
    }
  public class notification_two extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... voids) {
            String fcmToken = voids[0];
            try {
                pushFCMNotification.pushFCMNo_tification(fcmToken ,"chat","8822232690",
                        "I reaching. Get ready. ",
                        "text",
                        "null"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    public class notification_one extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... voids) {
            String fcmToken = voids[0];
            try {
                pushFCMNotification.pushFCMNo_tification(fcmToken,"chat",  "8822214250",
                        "I am on my way. ",
                        "text",
                        "null"
                        );
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class notification extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... voids) {
            String fcmToken = voids[0];
            try {
                pushFCMNotification.pushFCMNo_tification(fcmToken ,"chat","9864198664",
                        "I am on my way. ",
                        "text",
                        "null"
                        );
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
