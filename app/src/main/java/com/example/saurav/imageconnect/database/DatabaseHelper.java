package com.example.saurav.imageconnect.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.saurav.imageconnect.database.models.Note;
import com.example.saurav.imageconnect.database.models.users;
import com.example.saurav.imageconnect.fields.chat;
import com.example.saurav.imageconnect.fields.identify;
import com.example.saurav.imageconnect.fields.message_status;
import com.example.saurav.imageconnect.utils.TimeTeller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Note.NOTE;

/**
 * Created by saurav on 6/20/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "chat_database";
ArrayList<String> NameList = new ArrayList<>();
    ArrayList<String> ImAgeList = new ArrayList<>();
ArrayList<String> ConTactList = new ArrayList<>();
    ArrayList<String> FcmList = new ArrayList<>();
    String profile_contact = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SharedPreferences sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        profile_contact = sharedPreferences.getString("contact", null);

    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.CREATE_TABLE);
        db.execSQL(Note.CREATE_TABLE_USER);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Note.CREATE_TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public long insertMessage(Note note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.MESSAGE_FROM, note.getMessagefrom());
        values.put(Note.MESSAGE_TO, note.getMessageto());
        values.put(Note.CHAT_ID, note.getChatid());
        values.put(Note.SERVER_TIME_STUMP, note.getServertime());
        values.put(Note.MESSAGE_ID, note.getMessageid());
        values.put(Note.SEEN,note.getSeen());
        values.put(Note.MESSAGE, note.getMessage());
        values.put(Note.URL, note.getUrl());
        values.put(Note.TYPE, note.getType());



        // insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
/*    public Note getNote(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.MESSAGE_FROM, Note.MESSAGE_TO, Note.MESSAGE_ID
                ,Note.MESSAGE
                ,Note.URL
                ,Note.TYPE
                ,Note.SERVER_TIME_STUMP
                },
                Note.CHAT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.MESSAGE_FROM)),
                cursor.getString(cursor.getColumnIndex(Note.MESSAGE_TO)),
                cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID)),
                cursor.getString(cursor.getColumnIndex(Note.CHAT_ID)),
        cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID)),
        cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID)),
        cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID)),
                cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID)));
        cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID)));




        // close the db connection
        cursor.close();

        return note;
    }*/
public String getMessagestatus(String contact_enquired){
    String status= null;
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.query(  Note.USER_TABLE,
            new String[] { Note.MESSAGE_STATUS },
            Note.CONTACT +"="+contact_enquired,
            null, null, null, null, null);

    if(cursor.moveToFirst()){
        status = cursor.getString(cursor.getColumnIndex(Note.MESSAGE_STATUS));
    }
    return status;

}
public ArrayList<String> getChatidList(){
    ArrayList<String> notes = new ArrayList<>();

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.query(true,  Note.TABLE_NAME,
            new String[] { Note.MESSAGE_FROM, Note.MESSAGE_TO, Note.MESSAGE, Note.CHAT_ID,Note.MESSAGE_ID, Note.URL, Note.SEEN,Note.TYPE, Note.SERVER_TIME_STUMP },
            null,
            null, Note.CHAT_ID, null, null, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
             notes.add(cursor.getString(cursor.getColumnIndex(Note.CHAT_ID)));
        } while (cursor.moveToNext());
    }

    // close db connection
    db.close();

    // return notes list
    return notes;
}
public void messageStatusUpdate(String contact_info, String what_say ){
    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(Note.MESSAGE_STATUS, what_say);
    database.update(Note.USER_TABLE, cv,Note.CONTACT+" = "+contact_info,null);

    database.close();
}
public ArrayList<chat> getChatLastMessage(){
    ArrayList<chat> chatList = new ArrayList<>();
    for (int i=0;i< ConTactList.size();i++){
      chat chat =getUnitChatLastMessage(ConTactList.get(i), i);
      if(chat!=null){

          chatList.add(chat);

      }}
    return chatList;
}
public chat getUnitChatLastMessage(String chat_id_requested, int i){
 chat chat_d= null;
    SQLiteDatabase db = this.getWritableDatabase();
  /*        String[] whereArgs = {ConTactList.get(i), "unseen"};
     Cursor cursor = db.query(  Note.TABLE_NAME,
               new String[] { Note.MESSAGE,Note.SERVER_TIME_STUMP, Note.CHAT_ID, Note.SEEN },
               Note.CHAT_ID + "= ? AND "+ Note.SEEN +" = ?",
               whereArgs, null, null, null, null);
*/
       Cursor cursor = db.query(Note.TABLE_NAME,
               new String[]{Note.MESSAGE_FROM,
                       Note.MESSAGE_TO,
                       Note.MESSAGE,
                       Note.CHAT_ID,
                       Note.MESSAGE_ID
                       , Note.URL,
                       Note.SEEN,
                       Note.TYPE,
                       Note.SERVER_TIME_STUMP },
               Note.CHAT_ID + "=" + chat_id_requested.toString().trim(),
               null, null, null, null, null);
       String last_message = null;
       String real_from = null;
       String servertime = null;
       String last_parameter= null;
       String from_number = null;
     Log.e( "getChatLastMessage: ",cursor.getCount()+" size of cursor inevery check " );
       int count = 0;
       if (cursor.moveToLast()) {
           do {
               //   cursor.moveToLast();

               if (count == 0) {
                   real_from = cursor.getString(cursor.getColumnIndex(Note.MESSAGE_FROM));
                   if(real_from.equals(profile_contact)){
                       last_parameter= identify.SENDING;
                   }else {
                       last_parameter=identify.RECEVING;
                   }
                   last_message = cursor.getString(cursor.getColumnIndex(Note.MESSAGE));
                   servertime = cursor.getString(cursor.getColumnIndex(Note.SERVER_TIME_STUMP));
                   from_number = chat_id_requested;

               }

               String coutn_isseen = cursor.getString(cursor.getColumnIndex(Note.SEEN));
               if (coutn_isseen.equals("unseen")) {
                   count++;
                   if (cursor.isFirst()) {
                    chat_d=  new chat(last_message, NameList.get(i), servertime, String.valueOf(count), from_number, ImAgeList.get(i),last_parameter);
                   }
               } else {
                chat_d= new chat(last_message, NameList.get(i), servertime, String.valueOf(count), from_number, ImAgeList.get(i),last_parameter );
                   break;
               }

           } while (cursor.moveToPrevious());

    cursor.close();;

//}
   }

   db.close();
    return  chat_d;
}
public ArrayList<String> getName() {

    SQLiteDatabase db = this.getWritableDatabase();

        Cursor  cursor = db.query(  Note.USER_TABLE,
                new String[] { Note.CONTACT,Note.NAME,Note.IMAGE_LINK, Note.FCM_TOKEN },
                null,
                null, null, null, null, null);
    Log.e( "getName: ","cursor size "+cursor.getCount() );
        if(cursor.moveToFirst()){
            do{
                NameList.add(cursor.getString(cursor.getColumnIndex(Note.NAME)));
                Log.e( "getName: "," name "+cursor.getString(cursor.getColumnIndex(Note.NAME)) );
                ConTactList.add(cursor.getString(cursor.getColumnIndex(Note.CONTACT)));
                Log.e( "getName: "," contact   "+cursor.getString(cursor.getColumnIndex(Note.CONTACT)) );
                ImAgeList.add(cursor.getString(cursor.getColumnIndex(Note.IMAGE_LINK)));
                Log.e( "getName: "," imagelink "+cursor.getString(cursor.getColumnIndex(Note.IMAGE_LINK)) );
                FcmList.add(cursor.getString(cursor.getColumnIndex(Note.FCM_TOKEN)));

            }while (cursor.moveToNext());

        }

    Log.e( "getName: ","name size "+NameList.size() );
        Log.e( "getName: ","contact size "+ConTactList.size() );
    Log.e( "getName: ","image size "+ImAgeList.size() );
cursor.close();
    db.close();
    return NameList;
}
public void seenReportForMyMessages(String chat_number_report){
    SQLiteDatabase database = this.getWritableDatabase();
    String [] whereArgs = new String[]{ profile_contact, chat_number_report};
    ContentValues cv = new ContentValues();
    cv.put(Note.SEEN,"seen");
    database.update(Note.TABLE_NAME, cv,Note.MESSAGE_FROM+" = ?"+" AND"+" "+Note.CHAT_ID+" =?",whereArgs);

    database.close();
}

public void seeAllMessage(String chat_id_requested){
  SQLiteDatabase database = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(Note.SEEN,"seen");
    database.update(Note.TABLE_NAME, cv,Note.MESSAGE_FROM+" = "+chat_id_requested,null);

    database.close();
}

public String getUnseenCount (String chat_id){
       // Select All Query

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.query(  Note.TABLE_NAME,
            new String[] { Note.MESSAGE_ID, Note.SEEN },
            Note.CHAT_ID + "=" + chat_id,
            null, null, null, null, null);
    int count_useen =0;

    // looping through all rows and adding to list
   if(cursor!=null) {
       if (cursor.moveToFirst()) {
           do {
               String status = cursor.getString(cursor.getColumnIndex(Note.SEEN));
               if (status.equals("unseen")) {
                   count_useen++;

               }


           } while (cursor.moveToNext());
       }
   }
cursor.close();
    // close db connection
    db.close();

    // return notes list
    return String.valueOf(count_useen);

}
    public List<Note> getChat() {
        List<Note> notes = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(  Note.TABLE_NAME,
                new String[] { Note.MESSAGE_FROM, Note.MESSAGE_TO, Note.MESSAGE,Note.CHAT_ID, Note.MESSAGE_ID, Note.URL, Note.SEEN,Note.TYPE, Note.SERVER_TIME_STUMP },
                null,
                null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();

                note.setMessagefrom(cursor.getString(cursor.getColumnIndex(Note.MESSAGE_FROM)));
                note.setMessageto(cursor.getString(cursor.getColumnIndex(Note.MESSAGE_TO)));
                note.setSeen(cursor.getString(cursor.getColumnIndex(Note.SEEN)));
                note.setMessageid(cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID)));
                note.setServertime(cursor.getString(cursor.getColumnIndex(Note.SERVER_TIME_STUMP)));
                note.setMessage(cursor.getString(cursor.getColumnIndex(Note.MESSAGE)));
                note.setChatid(cursor.getString(cursor.getColumnIndex(Note.CHAT_ID)));
                note.setUrl(cursor.getString(cursor.getColumnIndex(Note.URL)));
                note.setType(cursor.getString(cursor.getColumnIndex(Note.TYPE)));






                notes.add(note);
            } while (cursor.moveToNext());
        }
cursor.close();
        // close db connection
        db.close();

        // return notes list
        return notes;
    }
    public List<Note> getChatWithIndevudual(String chat_id_v) {
        List<Note> notes = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(  Note.TABLE_NAME,
                new String[] { Note.MESSAGE_FROM, Note.MESSAGE_TO, Note.MESSAGE,Note.CHAT_ID, Note.MESSAGE_ID, Note.URL, Note.SEEN,Note.TYPE, Note.SERVER_TIME_STUMP },
                Note.CHAT_ID + "=" + chat_id_v,
                null, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();

                note.setMessagefrom(cursor.getString(cursor.getColumnIndex(Note.MESSAGE_FROM)));
                note.setMessageto(cursor.getString(cursor.getColumnIndex(Note.MESSAGE_TO)));
                note.setSeen(cursor.getString(cursor.getColumnIndex(Note.SEEN)));
                note.setMessageid(cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID)));
                note.setServertime(cursor.getString(cursor.getColumnIndex(Note.SERVER_TIME_STUMP)));
                note.setMessage(cursor.getString(cursor.getColumnIndex(Note.MESSAGE)));
                note.setChatid(cursor.getString(cursor.getColumnIndex(Note.CHAT_ID)));
                note.setUrl(cursor.getString(cursor.getColumnIndex(Note.URL)));
                note.setType(cursor.getString(cursor.getColumnIndex(Note.TYPE)));






                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // close db connection
        db.close();

        // return notes list
        return notes;
    }
    public String getUserSeen(String user_id){
    String status= null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(  Note.USER_TABLE,
                new String[] { Note.LAST_SEEN },
                Note.CONTACT + "=" +user_id,
                null, null, null, null, null);

        if(cursor.moveToLast()){
            status = cursor.getString(cursor.getColumnIndex(Note.LAST_SEEN));
        }
return status;
    }
 public void updatelastSeen(String fusion_id, long time){

     SQLiteDatabase database = this.getWritableDatabase();
     ContentValues cv = new ContentValues();

         cv.put(Note.LAST_SEEN,String.valueOf(time));

     database.update(Note.USER_TABLE, cv,Note.CONTACT+" = "+fusion_id,null);

     database.close();


 }

    public String getLastMessageId(String chat_id_V){

        String last_message_id="null";
        Log.e( "getLastMessageId: ",chat_id_V );
        String [] check_id = {chat_id_V.toString()};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(  Note.TABLE_NAME,
                new String[] { Note.MESSAGE_ID, Note.SEEN },
                Note.CHAT_ID+"="+chat_id_V ,
              null , null, null, null, null);

        // looping through all rows and adding to list
        if(cursor!= null) {
            if (cursor.moveToLast()) {
                do {

                    last_message_id = cursor.getString(cursor.getColumnIndex(Note.MESSAGE_ID));
                    break;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();

        // close db connection
        db.close();

        // return notes list

        return last_message_id;
    }
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public long seedUserTAble (users user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.NAME, user.getName());
        values.put(Note.CONTACT, user.getContact());
        values.put(Note.FCM_TOKEN, user.getFcmid());
        values.put(Note.IMAGE_LINK, user.getImagelink());
        values.put(Note.LAST_SEEN, user.getLast_seen());
        values.put(Note.MESSAGE_STATUS,user.getMessgae_status());
        // insert row
        long id = db.insert(Note.USER_TABLE, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;

    }
    public ArrayList<users> getFriends(){
        ArrayList<users> userArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Note.USER_TABLE,
                new String[]{Note.NAME, Note.FCM_TOKEN, Note.LAST_SEEN, Note.MESSAGE_STATUS,Note.CONTACT
                        ,Note.IMAGE_LINK
                },
                null,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                users users = new users(
                        cursor.getString(cursor.getColumnIndex(Note.NAME)),
                        cursor.getString(cursor.getColumnIndex(Note.CONTACT)),
                        cursor.getString(cursor.getColumnIndex(Note.FCM_TOKEN)),
                        cursor.getString(cursor.getColumnIndex(Note.IMAGE_LINK)),
                        cursor.getString(cursor.getColumnIndex(Note.LAST_SEEN)),
                        cursor.getString(cursor.getColumnIndex(Note.MESSAGE_STATUS))
                );





                userArrayList.add(users);
            } while (cursor.moveToNext());
        }






        // close the db connection
        cursor.close();
        db.close();
        return userArrayList;
    }
    public String getToken(String compare){
        int check=0;
        for (int i=0;i<ConTactList.size();i++){
           if(compare.equals(ConTactList.get(i))){
                check=i;
                break;

            }
        }
       return  FcmList.get(check);
    }
     public users getUserInfo(String contact){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.USER_TABLE,
                new String[]{Note.NAME, Note.FCM_TOKEN
                        ,Note.IMAGE_LINK
                },
                Note.CONTACT + "="+contact,
                new String[]{String.valueOf(contact)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        users users = new users(
                cursor.getString(cursor.getColumnIndex(Note.NAME)),
                contact,
                cursor.getString(cursor.getColumnIndex(Note.FCM_TOKEN)),
                cursor.getString(cursor.getColumnIndex(Note.IMAGE_LINK)),
                cursor.getString(cursor.getColumnIndex(Note.LAST_SEEN)),
                cursor.getString(cursor.getColumnIndex(Note.MESSAGE_STATUS))
                )


               ;





        // close the db connection
        cursor.close();
return  users;
    }
   /* public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
//
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }*/
    public void deleteusertable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.USER_TABLE, null, null);
        db.close();
    }


    public void deletemessagetable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, null, null);
        db.close();
    }
    public users getName_of_user(String chat_id_v) {
     int check=456222345;
    for (int i=0;i<ConTactList.size();i++){
        Log.e( "getName_of_user: ", String.valueOf(chat_id_v.compareTo(ConTactList.get(i))));
        Log.e( "getName_of_user: ",chat_id_v+" "+ConTactList.get(i) );
           if(chat_id_v.equals(ConTactList.get(i))){
                check=i;
                break;

           }
        }
        if(check !=456222345)
        {
            return  new users(NameList.get(check), ConTactList.get(check), FcmList.get(check), ImAgeList.get(check), "","");
        }else {
        return  new users(chat_id_v, chat_id_v, "","","0","");
        }

    }





}
