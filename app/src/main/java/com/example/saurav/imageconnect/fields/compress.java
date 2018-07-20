package com.example.saurav.imageconnect.fields;

import android.widget.ImageView;

/**
 * Created by saurav on 6/27/2018.
 */

public class compress {

    String path;
    ImageView imageView;

    public compress(String path, ImageView imageView) {
        this.path = path;
        this.imageView = imageView;
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
