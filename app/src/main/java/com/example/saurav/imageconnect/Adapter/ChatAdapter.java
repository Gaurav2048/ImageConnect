package com.example.saurav.imageconnect.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saurav.imageconnect.ChatActivity;
import com.example.saurav.imageconnect.R;
import com.example.saurav.imageconnect.database.DatabaseHelper;
import com.example.saurav.imageconnect.fields.chat;
import com.example.saurav.imageconnect.fields.gallary;
import com.example.saurav.imageconnect.fields.identify;
import com.example.saurav.imageconnect.fields.message_status;
import com.example.saurav.imageconnect.fields.panel;
import com.example.saurav.imageconnect.utils.TimeTeller;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saurav on 6/21/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.viewHolder> {
    ArrayList<chat> chatList;
    ArrayList<String> ImageList;
    Context context;
     ArrayList<String> gallaryArrayList;
     DatabaseHelper databaseHelper;
     public ChatAdapter(Context context, ArrayList<chat> chatList, ArrayList<String> gallaryArrayList, DatabaseHelper databaseHelper) {

        this.chatList=chatList;
        this.context=context;
        this.gallaryArrayList=gallaryArrayList;
        this.ImageList=ImageList;

        this.databaseHelper=databaseHelper;

     }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_chat,parent, false));
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
holder.message.setText(chatList.get(position).getMessage());
//        Picasso.get().load(chatList.get(position).getPhoto()).resize(150, 150).into(holder.circleImageView);
        holder.message_time.setText(TimeTeller.getTimeAgo(Long.parseLong(chatList.get(position).getServerstamp())));
        holder.friend_name.setText(chatList.get(position).getName_from());


        if(chatList.get(position).getIdentifier().equals(identify.RECEVING)){
            holder.no_of_message.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            holder.no_of_message.setBackgroundResource(R.drawable.circular_text);
            holder.no_of_message.setText(chatList.get(position).getUnread_count());
            int num_unread = Integer.valueOf(chatList.get(position).getUnread_count());
        if(num_unread==0){
            Log.e( "updateUI: ",num_unread +" ");
            holder.no_of_message.setVisibility(View.INVISIBLE);
        holder.message_time.setTextColor(Color.argb(255, 203, 203,203));
        }else {
            holder.no_of_message.setVisibility(View.VISIBLE);
            holder.message_time.setTextColor(Color.argb(255, 255, 152,0));

        }
            Log.e( "updateUI: ",num_unread +" ");
        }
        else if (chatList.get(position).getIdentifier().equals(identify.SENDING)) {
//TODO ui behaviour when message is send.
          if(databaseHelper.getMessagestatus(chatList.get(position).getFrom_number()).equals(message_status.MESSAGE_DELIVERED)){
              holder.no_of_message.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.ic_delivered_mark);

          }else if (databaseHelper.getMessagestatus(chatList.get(position).getFrom_number()).equals(message_status.MESSAGE_SEND)){
              holder.no_of_message.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.ic_send_mark);

          }else if (databaseHelper.getMessagestatus(chatList.get(position).getFrom_number()).equals(message_status.MESSAGE_SEEN)){
              holder.no_of_message.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.ic_seen_mark);

          }
            holder.no_of_message.setBackground(null);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if(chatList.get(position).getIdentifier().equals(identify.RECEVING)) {
               showuiupdate(holder, position);
                 }else {

           }
                Intent intent = new Intent(context, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("chat_id", chatList.get(position));
                intent.putExtra("gallaryList", gallaryArrayList);
                context.startActivity(intent);

            }
        });
    }

    private void showuiupdate(viewHolder holder, int position) {
        holder.no_of_message.setVisibility(View.INVISIBLE);
        holder.message_time.setTextColor(Color.argb(255, 203, 203,203));
        chatList.get(position).setUnread_count("0");
     }

    public void add(chat chat){
        boolean added = false;
        for (int i=0;i<chatList.size();i++){
            if(chat.getFrom_number().equals(chatList.get(i).getFrom_number())){
                chatList.remove(i);
                chatList.add(chat);
                added =true;
                break;

            }

        }

        if(added==false){
            chatList.add(chat);
        }

        Collections.reverse(chatList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView friend_name;
        TextView message_time;
        TextView message;
        TextView no_of_message;


        public viewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.friend_pic_front);
            friend_name = (TextView) itemView.findViewById(R.id.friend_name);
            message_time = (TextView) itemView.findViewById(R.id.message_sendtime);
            message= (TextView) itemView.findViewById(R.id.message);
            no_of_message= (TextView) itemView.findViewById(R.id.no_of_message);
        }
    }
}
