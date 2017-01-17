package com.com.tigarty.api;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Amr Saidam on 10/21/2016.
 */

public class MyConnection {

    public final static String URL_FOR_TEST = "http://192.168.1.108:8000/API";
    private static URL url;
    private static HttpURLConnection httpUrlConnection;

    public MyConnection(){}


    private static URL getUrl() throws MalformedURLException {
        url = new URL(URL_FOR_TEST);
        return url;
    }

    public static HttpURLConnection createConnection() throws IOException {
        httpUrlConnection = (HttpURLConnection) getUrl().openConnection();
        Log.d("Permission",httpUrlConnection.getPermission().toString());
        httpUrlConnection.setConnectTimeout(1000);
        httpUrlConnection.setRequestMethod("GET");
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setDoOutput(true);

        return httpUrlConnection;
    }

    public static HttpURLConnection getConnection()
    {

        return httpUrlConnection;
    }
}
