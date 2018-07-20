package com.example.saurav.imageconnect.fields;

/**
 * Created by saurav on 6/12/2018.
 */

        import android.net.Uri;

        import java.io.Serializable;

/**
 * Created by alegralabs on 08/03/18.
 */

public class gallary implements Serializable{
    String uri;
    String strUri;
    String nameuri;

    public gallary(String uri, String strUri, String nameuri) {
        this.uri = uri;
        this.strUri = strUri;
        this.nameuri = nameuri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getStrUri() {
        return strUri;
    }

    public void setStrUri(String strUri) {
        this.strUri = strUri;
    }

    public String getName() {
        return nameuri;
    }

    public void setName(String name) {
        this.nameuri = name;
    }
}
