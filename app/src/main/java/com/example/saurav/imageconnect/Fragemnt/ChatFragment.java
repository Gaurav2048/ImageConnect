package com.example.saurav.imageconnect.Fragemnt;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.saurav.imageconnect.Adapter.ChatAdapter;
import com.example.saurav.imageconnect.R;
import com.example.saurav.imageconnect.database.DatabaseHelper;
import com.example.saurav.imageconnect.database.models.Note;
import com.example.saurav.imageconnect.database.models.users;
import com.example.saurav.imageconnect.fields.chat;
import com.example.saurav.imageconnect.fields.gallary;
import com.example.saurav.imageconnect.fields.identify;
import com.example.saurav.imageconnect.fields.message;
import com.example.saurav.imageconnect.fields.message_status;
import com.example.saurav.imageconnect.fields.profile;
import com.example.saurav.imageconnect.utils.pushFCMNotification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    RecyclerView recycler_chat;

    ArrayList<chat> chatArrayList  ;
    DatabaseHelper databaseHelper;
    String profile_contact;
    String payload_tagret= null;
    ArrayList<String> gallaryArrayList;
    ChatAdapter chatAdapter;

       public ChatFragment() {
        // Required empty public constructor
    }

    public ChatFragment(ArrayList<String> gallaryArrayList) {
           this.gallaryArrayList=gallaryArrayList;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
          profile_contact = sharedPreferences.getString("contact", null);
        String name_ = sharedPreferences.getString("name", null);
        String image= sharedPreferences.getString("image", null);

        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.getName();
        recycler_chat = (RecyclerView) view.findViewById(R.id.recycler_chat);
        recycler_chat.setLayoutManager(new LinearLayoutManager(getContext()));

        // retriveChatList(kiuh);
        Receiver receiver = new Receiver();
        getContext().registerReceiver(receiver, new IntentFilter("my.action.string"));
    return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        chatArrayList = databaseHelper.getChatLastMessage();
        //  Collections.reverse(chatArrayList);
        Toast.makeText(getContext(),"chatArrayList"+chatArrayList.size(), Toast.LENGTH_SHORT).show();
        chatAdapter=  new ChatAdapter(getContext(), chatArrayList, gallaryArrayList, databaseHelper);

        recycler_chat.setAdapter(chatAdapter);

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.i("Receiver", "Broadcast received: " + action);

            if(action.equals("my.action.string")){
              //  String title = intent.getExtras().getString("title");

                String body = intent.getExtras().getString("body");
               String title = null;
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(body));
                   title= jsonObject.getString("title");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e( "onReceive: ",e.getCause()+" " );
                }
                Log.e( "onReceive: ",body.toString() );
              if(title!=null) {
                  if (title.equals("chat")) {
                      EnterMessageTODataBase(title, body);
                  } else if (title.equals("active")) {

                      operate(body);

                  }else if (title.equals("kassam")){
                      try {
                          JSONObject jsonObject = new JSONObject(body);
                          String sender_id = jsonObject.getString(message.SENDER_ID);
                          String message_status_report = jsonObject.getString(message.MESSAGE);
if(message_status_report.equals("delivered"))
{

    databaseHelper.messageStatusUpdate(sender_id, message_status.MESSAGE_DELIVERED);
    chatAdapter.notifyDataSetChanged();
}else if (message_status_report.equals("seen")){
    databaseHelper.seenReportForMyMessages(sender_id);
    databaseHelper.messageStatusUpdate(sender_id, message_status.MESSAGE_SEEN);
chatAdapter.notifyDataSetChanged();
}
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                    //  databaseHelper.messageStatusUpdate(String )
                  }
                  else {
                      Toast.makeText(getContext(), "Title null", Toast.LENGTH_SHORT).show();
                  }
              }


            }
        }
    }
    public class reportMessaegeSeen extends AsyncTask<String , Void, String> {
String number =null;
        @Override
        protected String  doInBackground(String... strings) {
           String result = "failed";
            try {
                pushFCMNotification.push_message_status_update("kassam", strings[0],profile_contact,"delivered");
            result = "success";
            } catch (Exception e) {
                result="failed";
                e.printStackTrace();
            }

            number = strings[1];

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("success")){
                databaseHelper.messageStatusUpdate(number, message_status.MESSAGE_DELIVERED);
            }
        }
    }
    private void operate(String body) {
        try {
            JSONObject jsonObject= new JSONObject(body);
            String active_person = jsonObject.getString(message.SENDER_ID);
            long active_time = Long.parseLong(jsonObject.getString(message.MESSAGE));

            databaseHelper.updatelastSeen(active_person,active_time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void EnterMessageTODataBase(String title, String body) {
        Note note = new Note();
        Log.e( "EnterMessaaBase: ",title );

        JSONObject jsonObject= null;
        try {
              jsonObject = new JSONObject(body);
            note.setMessagefrom(jsonObject.getString(message.SENDER_ID));
            note.setMessageto(profile_contact);//TODO need to alter with the account numberr
            note.setChatid(jsonObject.getString(message.SENDER_ID));
            note.setServertime(jsonObject.getString(message.SERVER_STAMP));
            note.setMessage(jsonObject.getString(message.MESSAGE));
            note.setType(jsonObject.getString(message.TYPE));
            note.setUrl(jsonObject.getString(message.URL));
            note.setSeen("unseen");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(databaseHelper.getLastMessageId(note.getChatid()).equals("null"))
        {
            note.setMessageid("0");
        }else {
            String last_message_id = databaseHelper.getLastMessageId(note.getChatid());
            int corrpond = Integer.parseInt(String.valueOf(last_message_id));
            note.setMessageid(String.valueOf(corrpond+1));


        }

      databaseHelper.insertMessage(note);
        new reportMessaegeSeen().execute(getFCM(note.getChatid()), note.getChatid());
        updateUI(title, note);
    }

    private String  getFCM(final String chatid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://buy2hand.in/api/restaurant_api/config/getfcm.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        payload_tagret= response.toString();
                     }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> hasmaMap = new HashMap<>();
                hasmaMap.put("contact", chatid);
                return hasmaMap;
            }

            ;

        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
return  payload_tagret;
    }

    private void updateUI(String title, Note note) {

        users name = databaseHelper.getName_of_user(note.getChatid());
        String count_unseen = databaseHelper.getUnseenCount(note.getChatid());
        Toast.makeText(getContext(), count_unseen+" ",Toast.LENGTH_SHORT).show();

       chat chat = new chat(note.getMessage(),name.getName(),note.getServertime(),count_unseen,note.getChatid(),name.getImagelink(), identify.RECEVING );
       chatAdapter.add(chat);
       chatAdapter.notifyDataSetChanged();

    }


}
