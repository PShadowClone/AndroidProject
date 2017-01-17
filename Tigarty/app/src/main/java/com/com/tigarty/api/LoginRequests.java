package com.com.tigarty.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.com.tigarty.api.Entities.User;
import com.example.amrsaidam.tigarty.Login;
import com.example.amrsaidam.tigarty.Menu;
import com.example.amrsaidam.tigarty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amr Saidam on 11/13/2016.
 */

public class LoginRequests {

  //  static boolean internetFlag;
    static String returnedResponse;
    public static boolean test = false;

    public LoginRequests() {
    }

    public static void checkUserAuthentication(String phoneNumber, String password, final Context context , final ProgressBar progressBar) throws JSONException, UnknownHostException {

        returnedResponse = null;
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNumber", phoneNumber);
        jsonObject.put("password", password);
        String userInfo = jsonObject.toString();

        System.out.println(Constants.LOGIN_URL + "?userInfo=" + userInfo);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.LOGIN_URL + "?userInfo=" + userInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // returnedResponse = response;
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            // JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("status");
                            System.out.println(jsonResponse);
                            if (status != 200) {
                                Toast.makeText(context,jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }
                            saveResponse(jsonResponse.getString("user_token"), jsonResponse.getJSONObject("user"));
                            progressBar.setVisibility(View.GONE);
                            System.out.println("Connection Response status : " + jsonResponse.getJSONObject("user"));
                            Intent intent = new Intent(context,Menu.class);
                            context.startActivity(intent);

                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Connection Response : " + response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                if (error.getClass().getSimpleName().equalsIgnoreCase("NoConnectionError"))
                        Toast.makeText(context, R.string.internetError , Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }

        };
        requestQueue.add(stringRequest);
    }



    public static void checkUserAuthenticationForTest( String phoneNumber, String password) throws JSONException, UnknownHostException {

        returnedResponse = null;
     //   progressBar.setVisibility(View.VISIBLE);

        Activity activity = new Login();
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getBaseContext());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNumber", phoneNumber);
        jsonObject.put("password", password);
        String userInfo = jsonObject.toString();

        System.out.println(Constants.LOGIN_URL + "?userInfo=" + userInfo);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.LOGIN_URL + "?userInfo=" + userInfo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // returnedResponse = response;
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            // JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("status");
                            System.out.println(jsonResponse);
                            if (status != 200) {
                              //  Toast.makeText(context,jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
                               // progressBar.setVisibility(View.GONE);
                                return;
                            }
                            test = true;
                            saveResponse(jsonResponse.getString("user_token"), jsonResponse.getJSONObject("user"));
                          //  progressBar.setVisibility(View.GONE);
                            System.out.println("Connection Response status : " + jsonResponse.getJSONObject("user"));
                          //  Intent intent = new Intent(context,Menu.class);
                          //  context.startActivity(intent);

                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Connection Response : " + response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                progressBar.setVisibility(View.GONE);
//                if (error.getClass().getSimpleName().equalsIgnoreCase("NoConnectionError"))
//                    Toast.makeText(context, R.string.internetError , Toast.LENGTH_LONG).show();
//
//            }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }

        };
        requestQueue.add(stringRequest);
    }

    public static void saveResponse(String token, JSONObject JsonUser) throws IOException, JSONException {

        //String id, String name, String address, String phone, String password, String email, String created_at, String updated_at
        User user = new User(""+JsonUser.getInt("id"),
                                JsonUser.getString("name"),
                                JsonUser.getString("address"),
                                JsonUser.getString("mobile"),
                                JsonUser.getString("email"),
                                JsonUser.getString("created_at"),
                                JsonUser.getString("updated_at"));

        File tigartyDir = new File(Environment.getExternalStorageDirectory(), "."+Constants.TIGARTY_DIR);
        tigartyDir.mkdir();
        File tokenFile = new File(tigartyDir, Constants.TOKEN_FILE+".txt");
        tokenFile.setWritable(false);
        FileWriter fileWriter = new FileWriter(tokenFile);
        fileWriter.write(token);
        fileWriter.flush();
        fileWriter.close();

        File userFile = new File(tigartyDir, Constants.USER_FILE+".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(userFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(user);
        userFile.setWritable(false);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();

    }

}


