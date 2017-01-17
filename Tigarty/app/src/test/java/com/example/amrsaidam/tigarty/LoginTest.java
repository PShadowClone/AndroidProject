package com.example.amrsaidam.tigarty;
//import org.apache.http.*;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.com.tigarty.api.Constants;
import com.com.tigarty.api.LoginRequests;
import com.com.tigarty.api.ProductListOperations;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Amr Saidam on 12/12/2016.
 */

public class LoginTest{

    ProductListOperations productListOperations;
    Context context;
    public LoginTest(Context context)
    {
        this.context = context;
       // super();
       // super();

    }

//    public TestRule onActivty()
//    {
//       return Login.class;
//    }
@Before
public void setUp()
{
    productListOperations = new ProductListOperations();
}

    @Test
    public void testSignInOperation() throws Exception {
//
////        EditText phoneNumber = new EditText(login.loginContext);
////        phoneNumber.setText("592872971");
////        EditText password = new EditText(login.loginContext);
////        password.setText("amrSaidam123");
////        signInOperation("592872971" , "amrSaidam123");;
////        Login login = new Login();
////        login.startActivity(new Intent());
////        Intent intent = new Intent();
//
//        Login login = new Login();
//        Intent intent = new Intent(String.valueOf(login));
//       // login.startActivity(intent);
////        AndroidTestCase.
//        //login.startActivity(intent);
//
//        login.checkUserAuthenticationForTest("592872971","amrSaidam123" );
//       // Thread.sleep(100);
//        System.out.println(Login.check);
//        assertFalse("SignInOperation" ,LoginRequests.test);
        checkUserAuthenticationForTest("dsd","ds");
        assert(true);

    }

    public  void checkUserAuthenticationForTest(String phoneNumber, String password) throws JSONException, UnknownHostException {

        // returnedResponse = null;
        //   progressBar.setVisibility(View.VISIBLE);

        // Activity activity = new Login();
        RequestQueue requestQueue = Volley.newRequestQueue(null);
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

                System.out.println("Error");
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