package com.example.saurav.imageconnect.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saurav.imageconnect.ChatActivity;
import com.example.saurav.imageconnect.R;
import com.example.saurav.imageconnect.database.models.users;
import com.example.saurav.imageconnect.fields.chat;
import com.example.saurav.imageconnect.fields.identify;
import com.santalu.diagonalimageview.DiagonalImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.viewHolder>  {
    Context context;
    ArrayList<users> usersArrayList;
    ArrayList<String> gallaryArrayList;
    public CheckAdapter(Context context, ArrayList<users> usersArrayList,ArrayList<String> gallaryArrayList) {
         this.context=context;
         this.usersArrayList=usersArrayList;
         this.gallaryArrayList=gallaryArrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_friends,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "text/OpenSans-Regular.ttf");
        holder.name_friend.setTypeface(typeface);
        holder.status.setTypeface(typeface);
        Picasso.get().load(usersArrayList.get(position).getImagelink()).into(holder.image_friend);
        holder.name_friend.setText(usersArrayList.get(position).getName());
        holder.status.setText(usersArrayList.get(position).getMessgae_status());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("chat_id", new chat("", usersArrayList.get(position).getName(),
                "","",usersArrayList.get(position).getContact(), usersArrayList.get(position).getImagelink(),
                identify.RECEVING ));
        intent.putExtra("gallaryList", gallaryArrayList);
        context.startActivity(intent);

    }
});
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        DiagonalImageView image_friend;
        TextView name_friend;
        CircleImageView active;
        TextView status;
        public viewHolder(View itemView) {
            super(itemView);
            image_friend= (DiagonalImageView) itemView.findViewById(R.id.image_friend);
            name_friend = (TextView) itemView.findViewById(R.id.name_friend);
            active= (CircleImageView) itemView.findViewById(R.id.active);
            status= (TextView) itemView.findViewById(R.id.status);

        }
    }
}
