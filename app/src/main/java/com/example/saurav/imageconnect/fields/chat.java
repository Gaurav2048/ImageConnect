package com.example.saurav.imageconnect.fields;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by saurav on 6/21/2018.
 */

public class chat  implements Serializable {
    String message;
    String name_from;
    String from_number;
    String serverstamp;
    String identifier;
    String unread_count;
    String photo;


    public chat(String messge, String name_from, String serverstamp, String unread_count, String from_number, String photo, String identifier) {
        this.message = messge;
        this.name_from = name_from;
        this.serverstamp = serverstamp;
        this.unread_count = unread_count;
        this.from_number= from_number;
        this.identifier = identifier;

        this.photo=photo;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMessge_from() {
        return message;
    }

    public void setMessge_from(String messge_from) {
        this.message = messge_from;
    }

    public String getName_from() {
        return name_from;
    }

    public void setName_from(String name_from) {
        this.name_from = name_from;
    }

    public String getServerstamp() {
        return serverstamp;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom_number() {
        return from_number;
    }

    public void setFrom_number(String from_number) {
        this.from_number = from_number;
    }

    public void setServerstamp(String serverstamp) {
        this.serverstamp = serverstamp;
    }

    public String getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(String unread_count) {
        this.unread_count = unread_count;
    }
}
