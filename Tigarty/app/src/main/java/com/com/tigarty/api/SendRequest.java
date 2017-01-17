package com.com.tigarty.api;

import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Amr Saidam on 10/21/2016.
 */

public class SendRequest implements Abstracts {


    @Override
    public boolean sendRequest() throws IOException {
        MyConnection.createConnection().connect();
        return true;
    }

    public HttpURLConnection getConnection()
    {
        return MyConnection.getConnection();
    }




    @Override
    public String reciveResponse() {

        return null;
    }
}
