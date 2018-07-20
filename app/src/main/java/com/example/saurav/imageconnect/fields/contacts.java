package com.example.saurav.imageconnect.fields;

import java.io.Serializable;

/**
 * Created by saurav on 6/29/2018.
 */

public class contacts implements Serializable {
    String name;
    String ph_number;
    String image_thumb;
    String image;
    String date;

    public contacts(String name, String ph_number, String image_thumb, String image, String date) {
        this.name = name;
        this.ph_number = ph_number;
        this.image_thumb = image_thumb;
        this.image = image;
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPh_number() {
        return ph_number;
    }

    public void setPh_number(String ph_number) {
        this.ph_number = ph_number;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
