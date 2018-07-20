package com.example.saurav.imageconnect.database.models;

/**
 * Created by saurav on 6/20/2018.
 */

public class users  {

    private String name;
    private String contact;
    private String fcmid;
    private String imagelink;
    private String last_seen;
    private String messgae_status;



    public users(String name, String contact, String fcmid, String imagelink, String last_seen, String message_status) {
        this.name = name;
        this.contact = contact;
        this.fcmid = fcmid;
        this.imagelink = imagelink;
        this.last_seen=last_seen;
        this.messgae_status=message_status;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getMessgae_status() {
        return messgae_status;
    }

    public void setMessgae_status(String messgae_status) {
        this.messgae_status = messgae_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFcmid() {
        return fcmid;
    }

    public void setFcmid(String fcmid) {
        this.fcmid = fcmid;
    }

    public String getImagelink() {
        return imagelink;
    }


    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }
}
