package com.example.saurav.imageconnect.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saurav.imageconnect.R;
import com.example.saurav.imageconnect.fields.contacts;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saurav on 6/29/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {
    ArrayList<contacts>contactList;
    Context context;
    String number;
    LinearLayout relativeLayout;
    BottomSheetLayout layout;
    public ContactAdapter(Context context, ArrayList<contacts> contactList, BottomSheetLayout layout) {
        relativeLayout= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.share_bottom_layout, layout, false);
        this.context=context;
        this.contactList=contactList;
        vieewAssociate();
        this.layout=layout;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_contact,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
//        if(!contactList.get(position).getImage_thumb().equals(null))
  //      Picasso.get().load(contactList.get(position).getImage_thumb()).into(holder.imageView);
        Picasso.get().load(contactList.get(position).getImage_thumb()).into(holder.imageView);
        holder.textview_date.setText(contactList.get(position).getDate());
        holder.textView.setText(contactList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.showWithSheetView(relativeLayout);
                number = contactList.get(position).getPh_number();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
CircleImageView imageView;
TextView textView;
TextView textview_date ;
        public viewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.contact_pic);
            textView= (TextView) itemView.findViewById(R.id.contact_name);
            textview_date= (TextView) itemView.findViewById(R.id.contact_date);
        }
    }

    private void vieewAssociate() {
        TextView Contact= (TextView) relativeLayout.findViewById(R.id.Contact);
        TextView SnapChat= (TextView) relativeLayout.findViewById(R.id.SnapChat);
        TextView Hike= (TextView) relativeLayout.findViewById(R.id.Hike);


        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"text/OpenSans-Regular.ttf");
        Contact.setTypeface(typeface);
        SnapChat.setTypeface(typeface);
        Hike.setTypeface(typeface);

        ImageView imageView_contact = (ImageView) relativeLayout.findViewById(R.id.one);
        ImageView imageView_messenger = (ImageView) relativeLayout.findViewById(R.id.two);
        ImageView imageView_three = (ImageView) relativeLayout.findViewById(R.id.three);

        imageView_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:"+number);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "Why donot you try the new image connect. See you there.");
                context.startActivity(it);
            }
        });
        imageView_messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Toast.makeText(context, "not configured yet", Toast.LENGTH_SHORT).show();
            }
        });
        imageView_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "not configured yet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
