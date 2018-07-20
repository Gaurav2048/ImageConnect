package com.example.saurav.imageconnect.fields;

/**
 * Created by saurav on 6/23/2018.
 */

public class panel {

    String name;
    String message ;
    String servertmstp;
    String unseen_count;

    public panel(String name, String message, String servertmstp, String unseen_count) {
        this.name = name;
        this.message = message;
        this.servertmstp = servertmstp;
        this.unseen_count = unseen_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServertmstp() {
        return servertmstp;
    }

    public void setServertmstp(String servertmstp) {
        this.servertmstp = servertmstp;
    }

    public String getUnseen_count() {
        return unseen_count;
    }

    public void setUnseen_count(String unseen_count) {
        this.unseen_count = unseen_count;
    }
}
