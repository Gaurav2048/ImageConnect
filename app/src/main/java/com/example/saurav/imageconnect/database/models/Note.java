package com.example.saurav.imageconnect.database.models;

/**
 * Created by saurav on 6/20/2018.
 */

public class Note {
    public static final String TABLE_NAME = "message";

    public static final String MESSAGE_FROM = "message_from";
    public static final String MESSAGE_TO = "message_to";
    public static final String CHAT_ID = "chat_id";
    public static final String SERVER_TIME_STUMP = "server_time";
    public static final String MESSAGE_ID = "message_id";
    public static final String MESSAGE = "message";
    public static final String SEEN ="seen";
    public static final String URL = "url";
    public static final String TYPE = "type";


    public static final String USER_TABLE = "user";

    public static final String NAME = "name";
    public static final String CONTACT = "contact";
    public static final String LAST_SEEN= "last_seen";
    public static final String FCM_TOKEN = "fcm_token";
    public static final String MESSAGE_STATUS = "message_status";
    public static final String IMAGE_LINK = "image_link";

    private String messagefrom;
    private String messageto;
    private String chatid;
    private String servertime;
    private String messageid;
    private String message;
    private String seen;
    private String url;

    public Note(String messagefrom, String messageto, String chatid, String servertime, String messageid, String message, String seen, String url, String type) {
        this.messagefrom = messagefrom;
        this.messageto = messageto;
        this.chatid = chatid;
        this.servertime = servertime;
        this.messageid = messageid;
        this.message = message;
        this.seen = seen;
        this.url = url;
        this.type = type;
    }

    public void setMessagefrom(String messagefrom) {
        this.messagefrom = messagefrom;
    }

    public void setMessageto(String messageto) {
        this.messageto = messageto;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessagefrom() {
        return messagefrom;
    }

    public String getMessageto() {
        return messageto;
    }

    public String getChatid() {
        return chatid;
    }

    public String getServertime() {
        return servertime;
    }

    public String getMessageid() {
        return messageid;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    private String type;








    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + MESSAGE_FROM + " TEXT,"
                    + MESSAGE_TO + " TEXT,"
                    + SERVER_TIME_STUMP + " Text,"
                    + CHAT_ID +" TEXT,"
                    + MESSAGE_ID + " TEXT,"
                    + SEEN + " TEXT,"
                    +MESSAGE + " TEXT,"
                    +URL + " TEXT,"
                    +TYPE + " TEXT"
                    + ")";


    public static final String CREATE_TABLE_USER =
            "CREATE TABLE "+ USER_TABLE + "("
                    + NAME + " TEXT,"
                    + CONTACT + " TEXT,"
                    + FCM_TOKEN + " TEXT,"
                    + LAST_SEEN + " TEXT,"
                    + MESSAGE_STATUS + " TEXT,"
                    + IMAGE_LINK +" TEXT"
                    + ")";

    public Note() {
    }


}