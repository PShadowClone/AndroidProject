package com.com.tigarty.api;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrsaidam.tigarty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.com.tigarty.api.LoginRequests.returnedResponse;
import static com.com.tigarty.api.LoginRequests.saveResponse;

/**
 * Created by Amr Saidam on 11/15/2016.
 */

public class TraderInvoiceOperations {

    public static TraderInvoiceOperations instance = getInstance();

    private TraderInvoiceOperations()
    {
    }

    public static TraderInvoiceOperations getInstance()
    {
        if(instance == null)
            instance = new TraderInvoiceOperations();
        return instance;
    }


    public String JsonConverter(String productName , String productNumber) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productName",productName);
        jsonObject.put("productNumber",productNumber);
        System.out.println("Inside");
        return jsonObject.toString();
    }

    public void broadcastToAPI(final Context context , String productName , String productNumber) throws JSONException, IOException, ClassNotFoundException {


            String variableToSend = "?productInfo="+JsonConverter(productName , productNumber)+"&userId="+Constants.getUserId();
          RequestQueue requestQueue = Volley.newRequestQueue(context);
        final StringRequest stringRequest;

            stringRequest = new StringRequest(Request.Method.GET, Constants.BROADCAST_TO_TRADER_INVOICE+variableToSend,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          Toast.makeText(context, R.string.done,Toast.LENGTH_LONG).show();

                      }
                  }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {

                  Toast.makeText(context, R.string.serverPriorError,Toast.LENGTH_LONG).show();
                  System.out.println("Response is  Error : " + error.getClass().getSimpleName());
              }
          }) {
              @Override
              protected Map<String, String> getParams() {
                  return new HashMap<>();
              }

          };

            requestQueue.add(stringRequest);
            requestQueue.start();



    }

    private String getUserToken() throws IOException {
       FileInputStream fileInputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory()+"/"+Constants.TIGARTY_DIR+"/"+Constants.TOKEN_FILE+".txt"));
        InputStreamReader inputStream = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStream);
        String receiveString = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ( (receiveString = bufferedReader.readLine()) != null ) {
            stringBuilder.append(receiveString);
            System.out.println(receiveString);
        }

        inputStream.close();
        return stringBuilder.toString();

    }
}
