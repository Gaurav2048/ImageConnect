package com.example.saurav.imageconnect;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.saurav.imageconnect.Adapter.ConversationAdapter;
import com.example.saurav.imageconnect.database.DatabaseHelper;
import com.example.saurav.imageconnect.database.models.Note;
import com.example.saurav.imageconnect.fields.chat;
import com.example.saurav.imageconnect.fields.message;
import com.example.saurav.imageconnect.fields.message_status;
import com.example.saurav.imageconnect.fields.profile;
import com.example.saurav.imageconnect.utils.TimeTeller;
import com.example.saurav.imageconnect.utils.pushFCMNotification;
import com.squareup.picasso.Picasso;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class ChatActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    TabLayout chat_tablayout;
    chat Chat_id = null;
    ViewPager tab_chat_pager;
    Toolbar toolbar_custom;
    ImageView gallary;
    RecyclerView chat_recyclerview;
    List<Note> NoteList;
    LinearLayout displaygkj;
    int activeCount=0;
     ImageView send_message;
    ConversationAdapter adapter;
    int backgroundCheck =0;
    int forgroundresponsibility= 1;
    EditText messsage_edittext;
    TextView typic_indicator;
    private static final int DIALOG_ID = 0;
    String profile_contact;
    CircleImageView pic_profile;
    ArrayList<String> gallaryList;
    String payload_tagret = null;
    TextView name__;
TextView activity_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        gallaryList = getIntent().getStringArrayListExtra("gallaryList");
        Chat_id = (chat) getIntent().getSerializableExtra("chat_id");
        displaygkj= (LinearLayout) findViewById(R.id.displaygkj);
        getFCM_TOKEN(Chat_id.getFrom_number());
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        profile_contact = sharedPreferences.getString("contact", null);
        String name_ = sharedPreferences.getString("name", null);
        String image= sharedPreferences.getString("image", null);

        pic_profile = (CircleImageView) findViewById(R.id.pic_profile);
        typic_indicator = (TextView) findViewById(R.id.typic_indicator);
        name__= (TextView) findViewById(R.id.name__);
        activity_status = (TextView) findViewById(R.id.activity_status);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "text/OpenSans-Regular.ttf");
        name__.setTypeface(typeface);
        activity_status.setTypeface(typeface);
        name__.setText(Chat_id.getName_from());
//        Picasso.get().load(Chat_id.getPhoto()).into(pic_profile);

        send_message = (ImageView) findViewById(R.id.send_message);
        //messagesList=(MessagesList) findViewById(R.id.messagesList);
        //  messageInput = (MessageInput) findViewById(R.id.input);
        toolbar_custom = (Toolbar) findViewById(R.id.toolbar_custom);
        messsage_edittext = (EditText) findViewById(R.id.messsage_edittext);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.getName();
       long lonnumber = Long.parseLong(databaseHelper.getUserSeen(Chat_id.getFrom_number()));
        if(lonnumber==0){
            activity_status.setText("Online");
        }else
        {
            activity_status.setText("Active" + TimeTeller.getTimeAgo(lonnumber));
        }
       SeeAllMessage(Chat_id.getFrom_number());

        gallary = (ImageView) findViewById(R.id.gallary);

        chat_recyclerview = (RecyclerView) findViewById(R.id.chat_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        chat_recyclerview.setLayoutManager(layoutManager);
        toolbar_custom = (Toolbar) findViewById(R.id.toolbar_custom);
     send_message.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(!TextUtils.isEmpty(messsage_edittext.getText().toString()))
             {
                 ArrayList<String> arrayList = new ArrayList<>();
                 arrayList.add(payload_tagret);
                 arrayList.add(Chat_id.getFrom_number());
                 arrayList.add(messsage_edittext.getText().toString());
                 arrayList.add("text");
                 arrayList.add("null");
             sendMessage(arrayList);
             }
         }
     });
      gallary.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
        intent.putExtra("gallaryList", gallaryList);
        startActivityForResult(intent, 286);}
});
        Receiver_chat receiver = new Receiver_chat();
        registerReceiver(receiver, new IntentFilter("my.action.string"));
        KeyboardVisibilityEvent.setEventListener(
                ChatActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if (isOpen) {
chat_recyclerview.scrollToPosition(NoteList.size()-1);
                            sendTypingInfo("1");
                        } else {
                            sendTypingInfo("0");

                        }
                    }
                });

toolbar_custom.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.putExtra("chat_id", Chat_id);
        startActivity(intent);
    }
});
    }

    private void getChats() {
        NoteList = databaseHelper.getChatWithIndevudual(Chat_id.getFrom_number());
         adapter = new ConversationAdapter(getApplicationContext(), NoteList,profile_contact,databaseHelper);
        chat_recyclerview.setAdapter(adapter);
        if(NoteList.size()!=0)
        {
            chat_recyclerview.scrollToPosition(NoteList.size()-1);
        }

    }

    public class sendTypingInfo extends AsyncTask<String , Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {

            try {
                pushFCMNotification.pushToActiveTyping("typing" ,Chat_id.getFrom_number(), profile_contact,strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

    }
    private void sendTypingInfo(String s) {
        new sendTypingInfo().execute(s);

    }

    private void getFCM_TOKEN(final String from_number) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://buy2hand.in/api/restaurant_api/config/getfcm.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        payload_tagret= response.toString();
                        Log.e( "onResponse: ",payload_tagret+" " );
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
          Map<String,String> hasmaMap = new HashMap<>();
          hasmaMap.put("contact", from_number);
                return hasmaMap;
            }

            ;

        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public class reportMessaegeSeen extends  AsyncTask<String , Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            try {
                pushFCMNotification.push_message_status_update("kassam", payload_tagret,profile_contact,strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private void SeeAllMessage(String chat_id) {

        new reportMessaegeSeen().execute("seen");
        databaseHelper.seeAllMessage(chat_id);

    }


    public class Receiver_chat extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.i("Receiver", "Broadcast received: " + action);

            if(action.equals("my.action.string")){
              //  String title = intent.getExtras().getString("title");

                String body = intent.getExtras().getString("body");
                String title = null;
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    Log.e( "onReceive: ",jsonObject.toString() );
                    title= jsonObject.getString("title");
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                if(title!=null)
                {
                    if(title.equals("chat")){
                        Note note = new Note();
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

                        Log.e( "EnterMessaaBase: ",title );
                        String last_message_id = databaseHelper.getLastMessageId(note.getChatid());
                        int corrpond = Integer.parseInt(String.valueOf(last_message_id));
                        note.setMessageid(String.valueOf(corrpond+1));


                        if(Chat_id.getFrom_number().equals(note.getMessagefrom()))
                        {
                            adapter.add(note);
                        }
                        chat_recyclerview.scrollToPosition(NoteList.size()-1);

                        if(backgroundCheck==0)
                        {
                            new reportMessaegeSeen().execute("seen");
                        }else {
                            forgroundresponsibility =1;
                        }

                    }else if (title.equals("active")){
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            String active_person = jsonObject.getString(message.SENDER_ID);
                            if(active_person.equals(Chat_id.getFrom_number())) {
                                long active_time = Long.parseLong(jsonObject.getString(message.MESSAGE));
                                if (active_time == 0) {
                                    if(!activity_status.getText().equals("Online"))
                                    {
                                        activity_status.setText("Online");
                                    }
                                } else if (active_time != 0) {
                                    activity_status.setText("Active " + TimeTeller.getTimeAgo(active_time));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }else if(title.equals("typing")){
                        try {
                            JSONObject jsonObject= new JSONObject(body);
                            String sender_id=  jsonObject.getString(message.SENDER_ID);
                            String sender_to = jsonObject.getString("toSend");
                            String message = jsonObject.getString("message");
                            if(sender_to.equals(profile_contact) && sender_id.equals(Chat_id.getFrom_number())){
                                if(message.equals("1")){
                                    typic_indicator.setVisibility(View.VISIBLE);
                                }else{

                                    typic_indicator.setVisibility(View.INVISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(title.equals("kassam")){
                        try {
                            JSONObject jsonObject= new JSONObject(body);
                            String number = jsonObject.getString(message.SENDER_ID);
                            String message_status_reporty = jsonObject.getString(message.MESSAGE);
                            if(number.equals(Chat_id.getFrom_number())) {
                                if (message_status_reporty.equals("delivered")) {
                                    Toast.makeText(getApplicationContext(), "delivery reported", Toast.LENGTH_SHORT).show();
                                    //TODO delivery icon
                                    adapter.messageStatusUpdate("1");
                                    databaseHelper.messageStatusUpdate(Chat_id.getFrom_number(), message_status.MESSAGE_DELIVERED);
                                } else if (message_status_reporty.equals("seen")) {
                                    //TODO seen icon
                                    Toast.makeText(getApplicationContext(), "seen reported", Toast.LENGTH_SHORT).show();
                                    adapter.messageStatusUpdate("2");
                                    databaseHelper.seenReportForMyMessages(Chat_id.getFrom_number());
                                    databaseHelper.messageStatusUpdate(Chat_id.getFrom_number(), message_status.MESSAGE_SEEN);


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }
    }

    @Override
    protected void onPause() {
        backgroundCheck =1;
        super.onPause();
    }

    @Override
    protected void onStart() {

        if(forgroundresponsibility==1){
            new reportMessaegeSeen().execute("seen");
        }
        backgroundCheck=0;
        if(activeCount==0){
getChats();
            new ActiveReport().execute("0");
            activeCount=1;

        }super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
        backgroundCheck=1;

        if(activeCount==0){
            sendTypingInfo(String.valueOf(System.currentTimeMillis()));
            new ActiveReport().execute(String.valueOf(System.currentTimeMillis()));

            activeCount=0;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        backgroundCheck=1;
       super.onDestroy();
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

    private void sendMessage(ArrayList<String> arrayList) {
new Messageng().execute(arrayList);
    }
    public class Messageng extends AsyncTask<ArrayList<String>, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        String type= null;
        String url=null;
        String message = null;

        @Override
        protected String doInBackground(ArrayList<String>... voids) {
            ArrayList<String> map=voids[0];
            String fcmToken = map.get(0);
              message = map.get(2);
              type = map.get(3);
              url = map.get(4);

            String status = "success";
            try {
                pushFCMNotification.pushFCMNo_tification(fcmToken,"chat",profile_contact,
                        message,
                        type,
                        url
                );
                Log.e( "doInBackground: ",type +" "+url );
            } catch (Exception e) {
                e.printStackTrace();
                status="failed";
            }
            return status;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if(aVoid.equals("success")){
                int count=1;
                if(NoteList.size()>0){
                   count = Integer.valueOf(NoteList.get(NoteList.size()-1).getMessageid());
              count++;
                }
                Note note =  new Note(profile_contact, Chat_id.getFrom_number(),Chat_id.getFrom_number(),String.valueOf(System.currentTimeMillis()),
                        String.valueOf(count),
                        message,"unseen",
                        url,
                        type

                );
                adapter.add(note);
              chat_recyclerview.scrollToPosition(NoteList.size()-1);
              databaseHelper.messageStatusUpdate(Chat_id.getFrom_number(), message_status.MESSAGE_SEND);
                databaseHelper.insertMessage(note);
                messsage_edittext.setText("");

            }else {
                Toast.makeText(getApplicationContext(), "unable to deliver message", Toast.LENGTH_SHORT).show();
                messsage_edittext.setText("");

            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 286) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String imageUri = data.getStringExtra("imageUri");

                        //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                        Intent intent = new Intent(getApplicationContext(), ImageSendActivity.class);
                        intent.putExtra("uri", imageUri);
                        startActivityForResult(intent, 555);

                }
            }
        }else if (resultCode==555){
                String imageUri = data.getStringExtra("image");
                String message_bind = data.getStringExtra("message");

                try {
                    Bitmap compressedImage = new Compressor(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(75)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .compressToBitmap(new File(imageUri));

                    SendImageToServer(compressedImage, message_bind);


             } catch (IOException e) {
                    e.printStackTrace();

                }


        }else {

        }
    }

    private void SendImageToServer(Bitmap compressedImage, final String message_bind) {

        final String fileName = String.valueOf(System.currentTimeMillis())+".jpg";
        final String imageString = getStringImage(compressedImage);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://buy2hand.in/api/restaurant_api/config/upoad.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ArrayList<String> list = new ArrayList<>();
                list.add(payload_tagret);
                list.add(Chat_id.getFrom_number());
                list.add(message_bind);
                list.add("image");
                list.add(fileName);
                sendMessage(list);
                Toast.makeText(getApplicationContext(), "iamge uploaded and saveing ", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> hasmap = new HashMap<>();
                hasmap.put("stringFile", imageString);
                hasmap.put("filename",fileName);
                return hasmap;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
    private void createTabs() {

        for (int i = 0; i < 5; i++) {
            CircleImageView circleImageView = (CircleImageView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.ornge_tab, null);
            TabLayout.Tab tab = chat_tablayout.newTab();
            Bitmap bitmap = getABitmap(40, 40);
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.profile);
            bitmap = overlay(bitmap, icon);
            circleImageView.setImageBitmap(bitmap);
            tab.setCustomView(circleImageView);
            chat_tablayout.addTab(tab);
        }
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }

    public Bitmap getABitmap(int height, int weidth) {
        //int width = 200;
        //int height = 300;
        Bitmap bitmap = Bitmap.createBitmap(weidth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.argb(130, 144, 144, 153));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setAntiAlias(true);
        paint.setTextSize(12.f);
        paint.setTextAlign(Paint.Align.CENTER);
        return bitmap;
    }
}