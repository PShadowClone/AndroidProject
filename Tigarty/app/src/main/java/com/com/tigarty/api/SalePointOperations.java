package com.com.tigarty.api;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrsaidam.tigarty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amr Saidam on 11/17/2016.
 */

public class SalePointOperations {

    public static SalePointOperations instance = getInstance();


    private SalePointOperations()
    {
    }

    public static SalePointOperations getInstance()
    {
        if(instance == null)
            instance = new SalePointOperations();
        return instance;
    }

    private String JsonConverter(String productName) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productName",productName);
        return jsonObject.toString();
    }

    public void broadcastToAPI(final View view , final Context context , String productName) throws JSONException, IOException, ClassNotFoundException {


        String variableToSend = "?productInfo="+JsonConverter(productName)+"&userId="+Constants.getUserId();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final StringRequest stringRequest;

        stringRequest = new StringRequest(Request.Method.GET, Constants.BROADCAST_TO_SALE_POINT+variableToSend,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response is Done");
                        Toast.makeText(context.getApplicationContext(), R.string.done , Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error.getClass().getSimpleName().equalsIgnoreCase("NoConnectionError"))
                         Snackbar.make(view, R.string.internetError, Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

}
