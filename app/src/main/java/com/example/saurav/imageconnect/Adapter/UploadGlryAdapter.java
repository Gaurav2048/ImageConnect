package com.example.saurav.imageconnect.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


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

public class UploadGlryAdapter extends RecyclerView.Adapter<UploadGlryAdapter.viewHolder> {
   Context context;
   ArrayList <String> gallaryList;
   Activity activity;
   ArrayList<String> selectedUriList = new ArrayList<>();
    public UploadGlryAdapter(Context context, ArrayList<String> gallaryList, Activity activity) {

        this.context= context;
        this.gallaryList=gallaryList;
        this.activity =activity;
     }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_gry,parent,false));

    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {

        // holder.imageView.setImageBitmap(BitmapFactory.decodeFile(galaryList.get(position).getUri()));
new compressTask().execute(new compress(gallaryList.get(position), holder.glty_pic));
        holder.glty_pic.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("imageUri", gallaryList.get(position));
        activity.setResult(286, intent);
        activity.finish();
    }
});


    }
    public class compressTask extends AsyncTask<compress, Void, String>{
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
                        .setMaxHeight(200)
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

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView glty_pic;
        public viewHolder(View itemView) {
            super(itemView);
            glty_pic= (ImageView) itemView.findViewById(R.id.glty_pic);
        }
    }
}
