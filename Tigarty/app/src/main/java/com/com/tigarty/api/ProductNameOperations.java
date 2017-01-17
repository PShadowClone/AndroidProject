package com.com.tigarty.api;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrsaidam.tigarty.MainActivity;
import com.example.amrsaidam.tigarty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Amr Saidam on 11/14/2016.
 */

public class ProductNameOperations {

    private Context activity;
    private String productName;
    private String productBarcodeNumber;
    private Button scanButton;

    public ProductNameOperations(Context activity , Button scanButton) {
        this.activity = activity;
        this.scanButton = scanButton;
        this.productName = null;
        this.productBarcodeNumber = "";
    }

    public void checkProductNameFromExternalAPI(String url, String productNumber, final EditText productName , final boolean flag , final ProgressBar progressBar) throws JSONException, IOException, ClassNotFoundException {
        //scanButton.setEnabled(false);
        final String tempProductNumber = productNumber;
        if (url.equalsIgnoreCase(Constants.GET_PRODUCT_NAME_URL)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productNumber", "" + productNumber);
            productNumber = null;
            productNumber = "?productNumber=" + jsonObject.toString()+"&userId="+Constants.getUserId();
        }

        //  this.productBarcodeNumber = productNumber;
        //System.out.println(Constants.PRODUCT_NAME_API+productNumber);
        System.out.println("url : " + url + productNumber);
        RequestQueue requestQueue = Volley.newRequestQueue(this.activity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + productNumber,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Here!@!@#!@#1");

                        System.out.println("Response is : " + response);

                        try {
                            if (new JSONObject(response).getInt("status") == 200) {
                                System.out.println("Status is 200");
                                productName.setText(new JSONObject(response).getString("name"));
                                productName.setEnabled(false);
                                progressBar.setVisibility(View.GONE);
                                return;
                            }else if(flag == false)
                                    checkProductNameFromExternalAPI(Constants.PRODUCT_NAME_API, tempProductNumber, productName, flag ,progressBar);
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            Toast.makeText(activity.getApplicationContext(),R.string.suddenlyError, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        contentTxt.setText(productNumber);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getClass().getSimpleName().equalsIgnoreCase("NoConnectionError")) {
                    Toast.makeText(activity.getApplicationContext(),R.string.internetError, Toast.LENGTH_LONG).show();
                    return;
                }

                Animation shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
                shake.setDuration(690);
                scanButton.startAnimation(shake);
              //  scanButton.setEnabled(false);

                Toast.makeText(activity.getApplicationContext(),R.string.productNameError, Toast.LENGTH_LONG).show();
                System.out.println("Connection Error : " + error.getClass().getSimpleName());
            }
        });


        requestQueue.add(stringRequest);
        requestQueue.start();


    }

}
