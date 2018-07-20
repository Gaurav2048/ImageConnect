package com.example.saurav.imageconnect.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.saurav.imageconnect.PhotoActivity;
import com.example.saurav.imageconnect.R;
import com.example.saurav.imageconnect.fields.compress;
import com.example.saurav.imageconnect.fields.gallary;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

/**
 * Created by saurav on 6/22/2018.
 */

public class whpp_ge_recyclerAdapter extends RecyclerView.Adapter<whpp_ge_recyclerAdapter.viewHolder> {
   Context context;
   ArrayList<String> galaryList;
   Activity activity;
    public whpp_ge_recyclerAdapter(Context context, ArrayList<String> galaryList, Activity activity) {
    this.context=context;
    this.galaryList=galaryList;
    this.activity=activity;
    }
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.whtpp_hr_ig,parent,false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        new  compressTask().execute(new compress(galaryList.get(position), holder.imageView));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PhotoActivity)activity).baggage(galaryList.get(position));

            }
        });
        // holder.imageView.setImageBitmap(BitmapFactory.decodeFile(galaryList.get(position).getUri()));
    }

    @Override
    public int getItemCount() {
        return galaryList.size();
    }
    public class compressTask extends AsyncTask<compress, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        ImageView imageView;

        @Override
        protected String doInBackground(compress... strings) {
            compress uri = strings[0];
            imageView =uri.getImageView();
            File processedImage = null;
            try {
                processedImage = new Compressor(context)
                        .setMaxWidth(200)
                        .setMaxHeight(100)
                        .setQuality(50)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)

                        .compressToFile(new File(Uri.parse(uri.getPath()).getPath()));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return Uri.fromFile(processedImage).getPath();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Picasso.get().load(new File(s)).resize(200,200).into(imageView);


        }
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public viewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.whpp_ge);
        }
    }
}
