package com.example.saurav.imageconnect.Fragemnt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.saurav.imageconnect.R;
import com.example.saurav.imageconnect.database.DatabaseHelper;
import com.example.saurav.imageconnect.database.models.Note;
import com.example.saurav.imageconnect.database.models.users;
import com.example.saurav.imageconnect.fields.message_status;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
DatabaseHelper databaseHelper ;
Button data_helper;
Button clearMessgaeTable;
    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_group, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.getName();
        clearMessgaeTable= (Button) view.findViewById(R.id.clearMessgaeTable);
        data_helper= (Button) view.findViewById(R.id.data_helper);
        data_helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//databaseHelper.deleteusertable();
               List<Note> notelist = databaseHelper.getChat();
              for (int i=0;i<notelist.size();i++)
                //  Log.e( "onClick: ",Note.MESSAGE_FROM+" "+notelist.get(i).getImagelink()+" "+" "+Note.MESSAGE_FROM+" "+notelist.get(i).getContact()+" "+Note.MESSAGE_TO+" "+notelist.get(i).getLast_seen()+" "+Note.SEEN+" "+notelist.get(i).getMessgae_status()+" "+Note.MESSAGE_ID+ " "+notelist.get(i).getName() );
                  Log.e( "onClick: ",Note.CHAT_ID +notelist.get(i).getChatid()+" "+Note.SEEN +notelist.get(i).getSeen());
                // Log.e( "onClick: ",Note.MESSAGE_FROM+" "+notelist.get(i).getMessagefrom()+" "+" "+Note.MESSAGE_FROM+" "+notelist.get(i).getMessagefrom()+" "+Note.MESSAGE_TO+" "+notelist.get(i).getMessageto()+" "+Note.SEEN+" "+notelist.get(i).getSeen()+" "+Note.MESSAGE_ID+" "+notelist.get(i).getMessageid()+" "+Note.SERVER_TIME_STUMP+" "+notelist.get(i).getServertime()+" "+Note.MESSAGE+" "+notelist.get(i).getMessage()+" "+Note.CHAT_ID+" "+notelist.get(i).getChatid()+" "+Note.URL+" "+notelist.get(i).getUrl()+" "+Note.TYPE+" "+notelist.get(i).getType()+" " );
            }
        });
     //   databaseHelper.deleteusertable();
         users users = new users("Julia Shine", "9864198664", "fcm","https://imagesvc.timeincapp.com/v3/mm/image?url=https%3A%2F%2Fpeopledotcom.files.wordpress.com%2F2016%2F12%2Fkim-k-660.jpeg%3Fw%3D660&w=700&q=85","active", message_status.MESSAGE_SEND);
        users users1 = new users("Kattie Stark","8822214250", "fcm","http://1.bp.blogspot.com/-B8Ja8frvLm0/UzBFrmarSkI/AAAAAAAAKnI/U9NuQANxYm4/s1600/Indian+Beautiful+Girls+Wallpapers+Free+Download+15.jpg","active", message_status.MESSAGE_SEND);
        users users2 = new users("Tapsi Pannu", "8822232690", "fcm","http://www.garage-reybert.com/wp-content/uploads/2016/06/beautiful-short-haircuts-for-fat-faces-women-with-fat-face-shape-usually-confused-with-what-hairstyles-that-match-with-her-face-shape-fat-girl-short-hairstyles.jpg","active", message_status.MESSAGE_SEND);
        users users3 = new users("Gunjan kalita", "7896411509", "eENZddINalY:APA91bFaXFeHrvFrNv9J5zgqsBHZ7l32AmSVIRCqObHdZNNYrbeBdf2r74hY7LbGLiU8s_3ae-ix6EG4voPIDyn7I_3RFu-1NkxBhWL54OuwPuxzH6nYHKLhXmmUUntSVQieVt1O9DmOIUzqF2MC9PM9B0fgGegtwg","https://qph.fs.quoracdn.net/main-thumb-267425359-200-ujgdvgscwqhcupebeigryyzaxbrwgruu.jpeg","active", message_status.MESSAGE_SEND);
        users users4 = new users("Trishna Kalita", "8473926865", "eQ8yV0orxT8:APA91bH65kMrcAeByQTCtWj3n_DjCPLKEjrkavPMUV92lTaedtwjQuPceB_sAKtAajxyibmMXAlaUQyn5nNq8JgmNWlEhV9iIJ5dMrfOIf0aEIzExe47AaGpq44OXXATmIRD3Dj9aClHFwDPH5eoOjI4NPp5-IsZBg","https://thumb9.shutterstock.com/display_pic_with_logo/1054231/347687978/stock-photo-beautiful-girl-with-long-wavy-hair-brunette-with-curly-hairstyle-347687978.jpg","active", message_status.MESSAGE_SEND);
   /*    databaseHelper.seedUserTAble(users);
        databaseHelper.seedUserTAble(users1);
     databaseHelper.seedUserTAble(users3);
        databaseHelper.seedUserTAble(users4);
        databaseHelper.seedUserTAble(users2);
 */ clearMessgaeTable.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          databaseHelper.deletemessagetable();
      }
  });
        return  view;
    }

}
