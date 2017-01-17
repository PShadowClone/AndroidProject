package com.example.amrsaidam.tigarty;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.com.tigarty.api.Constants;
import com.com.tigarty.api.LoginRequests;
import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Context loginContext  =null;
    ProgressBar progressBar;
    boolean flag = false;
    Button singIn;
    EditText phoneNumber , password;
    public static int check = 1;
//    public Context context = getBaseContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        check = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        getSupportActionBar().hide();// hide action bar without miss anything about title bar

        this.overridePendingTransition(0,0);
//        ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
//        animation.setDuration(1000);
//        animation.start();

//        LoginTest loginTest = new LoginTest();


        singIn = (Button) findViewById(R.id.singIn);
         phoneNumber = (EditText) findViewById(R.id.phoneNumber);
         password = (EditText) findViewById(R.id.password);
        progressBar =  (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        signInOperation(singIn, phoneNumber, password);

        loginContext = this;

    }


    public boolean signInOperation(Button singIn, final EditText phoneNumber, final EditText password) {


        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumber.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),R.string.fillAllFields, Toast.LENGTH_LONG).show();

                }


                try {
                    LoginRequests.checkUserAuthentication(phoneNumber.getText().toString(), password.getText().toString(), loginContext  ,progressBar);
                    flag = true;
                } catch (JSONException e) {
                  Toast.makeText(getApplicationContext(), "حدث خطأ أثناء ارسال البيانات", Toast.LENGTH_LONG).show();
                } catch (UnknownHostException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
              //  Toast.makeText(getApplicationContext(), "Done : " + phoneNumber.getText().toString() + " , " + password.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
        return flag;
    }

    public boolean signInOperation( String phoneNumberContent, String passwordContent) {

//        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
//        password = (EditText) findViewById(R.id.password);
        phoneNumber.setText(phoneNumberContent);
        password.setText(passwordContent);


        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumber.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(),R.string.fillAllFields, Toast.LENGTH_LONG).show();

                }


                try {
                    LoginRequests.checkUserAuthentication(phoneNumber.getText().toString(), password.getText().toString(), loginContext  ,progressBar);
                    flag = true;
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "حدث خطأ أثناء ارسال البيانات", Toast.LENGTH_LONG).show();
                } catch (UnknownHostException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                //  Toast.makeText(getApplicationContext(), "Done : " + phoneNumber.getText().toString() + " , " + password.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
        return flag;
    }

//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this.getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
//
//        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//    }


//    public Context getContext() {
//        return context;
//    }



    public void setUp()
    {
        Assert.assertTrue("",true);
    }


    public  void checkUserAuthenticationForTest( String phoneNumber, String password) throws JSONException, UnknownHostException {

       // returnedResponse = null;
        //   progressBar.setVisibility(View.VISIBLE);

       // Activity activity = new Login();
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
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
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            // JSONObject jsonResponse = new JSONObject(response);
//                            int status = jsonResponse.getInt("status");
//                            System.out.println(jsonResponse);
//                            if (status != 200) {
//                                //  Toast.makeText(context,jsonResponse.getString("message"), Toast.LENGTH_LONG).show();
//                                // progressBar.setVisibility(View.GONE);
//                                return;
//                            }
//                        //    test = true;
                          //  saveResponse(jsonResponse.getString("user_token"), jsonResponse.getJSONObject("user"));
                            //  progressBar.setVisibility(View.GONE);
                           // System.out.println("Connection Response status : " + jsonResponse.getJSONObject("user"));
                            //  Intent intent = new Intent(context,Menu.class);
                            //  context.startActivity(intent);

//                        } catch (JSONException e) {
//                            System.out.println(e.getMessage());
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
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
}
