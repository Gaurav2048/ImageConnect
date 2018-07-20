package com.example.saurav.imageconnect.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saurav.imageconnect.R;
import com.example.saurav.imageconnect.database.DatabaseHelper;
import com.example.saurav.imageconnect.database.models.Note;
import com.example.saurav.imageconnect.fields.chat;
import com.example.saurav.imageconnect.fields.message_status;
import com.example.saurav.imageconnect.fields.profile;
import com.example.saurav.imageconnect.utils.TimeTeller;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurav on 6/27/2018.
 */

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Note> chatList;
    DatabaseHelper databaseHelper;
    String profile_contact;
    String data ="56";
    public ConversationAdapter(Context context, List<Note> chatList, String profile_contact,DatabaseHelper databaseHelper) {
        this.context =context;
        this.chatList=chatList;
        this.profile_contact=profile_contact;
        this.databaseHelper= databaseHelper;

    }

    @Override
    public int getItemViewType(int position) {
        if(chatList.get(position).getMessagefrom().equals(profile_contact)){
            return 0;

        }else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType==0){
           return new myTextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_here, parent, false));
       }else if (viewType==1){

           return new receiverViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_received, parent, false));
       }
        return null;
    }
public void messageStatusUpdate(String data){
    Toast.makeText(context, "ultimate method called",Toast.LENGTH_SHORT).show();
      if(data.equals("2"))
      {
          for (int i=chatList.size()-1;i>=0;i--){
              if(chatList.get(i).getMessagefrom().equals(profile_contact))
              {
                  if(chatList.get(i).getSeen().equals("unseen")){
                      Log.e( "messageStatusUpdate: ","cha notified" );
                      chatList.get(i).setSeen("seen");
                      notifyItemChanged(i);
                  }else if (chatList.get(i).getSeen().equals("seen")){
                      break;
                  }
              }
          }

      }

      else if (data.equals("1")){

          for (int i=chatList.size()-1;i>=0;i--){
              if(chatList.get(i).getSeen().equals("unseen")){
                  notifyItemChanged(i);
              }else if(chatList.get(i).getSeen().equals("seen"))
              {
                  break;
              }
          }

    }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Typeface typefac= Typeface.createFromAsset(context.getAssets(), "text/OpenSans-Regular.ttf");
        switch (holder.getItemViewType())
        {
            case 0:
                myTextViewHolder myTextViewHolder = (myTextViewHolder) holder;
               myTextViewHolder.text_send.setTypeface(typefac);
                myTextViewHolder.text_send.setText(chatList.get(position).getMessage());
                myTextViewHolder.time_send.setText(TimeTeller.getTimeAgo(Long.parseLong(chatList.get(position).getServertime())));
           if(chatList.get(position).getType().equals("image")){
               myTextViewHolder.imageView.setVisibility(View.VISIBLE);
               Picasso.get().load("http://buy2hand.in/api/restaurant_api/config/image/"+chatList.get(position).getUrl()).into(myTextViewHolder.imageView);
           }else  if(chatList.get(position).getType().equals("text")){
               myTextViewHolder.imageView.setVisibility(View.GONE);
           //    Picasso.get().load(chatList.get(position).getUrl()).into(myTextViewHolder.imageView);
           }
              if( chatList.get(position).getMessagefrom().equals(profile_contact))
              {
                  if(chatList.get(position).getSeen().equals("seen") ){
                      myTextViewHolder.time_send.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_seen_mark,0);

                  }else
                     if(databaseHelper.getMessagestatus(chatList.get(position).getChatid()).equals(message_status.MESSAGE_DELIVERED)) {
                          myTextViewHolder.time_send.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_delivered_mark,0);

                      }else if (databaseHelper.getMessagestatus(chatList.get(position).getChatid()).equals(message_status.MESSAGE_SEND)){
                          myTextViewHolder.time_send.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_send_mark,0);


                      }
              }

                break;

            case 1:
                receiverViewHolder receiverViewHolder = ( receiverViewHolder) holder;
                receiverViewHolder.text_recv.setText(chatList.get(position).getMessage());
                receiverViewHolder.text_recv.setTypeface(typefac);
                receiverViewHolder.time_recv.setText(TimeTeller.getTimeAgo(Long.parseLong(chatList.get(position).getServertime())));
                if(chatList.get(position).getType().equals("image")){
                    receiverViewHolder.imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load("http://buy2hand.in/api/restaurant_api/config/image/"+chatList.get(position).getUrl()).into(receiverViewHolder.imageView);
                }else  if(chatList.get(position).getType().equals("text")){
                    receiverViewHolder.imageView.setVisibility(View.GONE);
                    //    Picasso.get().load(chatList.get(position).getUrl()).into(myTextViewHolder.imageView);
                }
                break;
        }

    }
public void  add(Note note){
        chatList.add(note);
        notifyDataSetChanged();
}
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class myTextViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView text_send;
    TextView time_send;
        public myTextViewHolder(View itemView) {
            super(itemView);
            text_send= (TextView) itemView.findViewById(R.id.text_send);
            time_send= (TextView) itemView.findViewById(R.id.time_send);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }
    }
    public class receiverViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView text_recv;
        TextView time_recv;
        public receiverViewHolder(View itemView) {
            super(itemView);
            text_recv= (TextView) itemView.findViewById(R.id.text_recv);
            time_recv = (TextView) itemView.findViewById(R.id.time_recv);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}
