package com.example.amrsaidam.tigarty;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.com.tigarty.api.Constants;
import com.com.tigarty.api.ProductNameOperations;
import com.com.tigarty.api.TraderInvoiceOperations;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button scanBtn, send_Button;
    private EditText formatTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        scanBtn = (Button) findViewById(R.id.scan_button);
        formatTxt = (EditText) findViewById(R.id.scan_format);
        contentTxt = (EditText) findViewById(R.id.scan_content);
        send_Button = (Button) findViewById(R.id.send_Button);


        scanBtn.setOnClickListener(this);
        send_ButtonAction();


        //getConnectionOn(contentTxt.getText().toString());


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scan_button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();


        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        // startActivity(intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            System.out.println(scanContent);
            //formatTxt.setText("FORMAT: " + scanFormat);
            // contentTxt.setText("CONTENT: " + scanContent);
            if (scanContent == null) {
                Toast.makeText(getApplicationContext(), R.string.scanEmpty, Toast.LENGTH_LONG).show();
            } else {
                getProductName(scanContent);
                productNameAction();
            }
            getProductName(scanContent);
            productNameAction();
            // getConnectionOn(contentTxt.getText().toString());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void getConnectionOn(String barcodeNumber) {


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        System.out.println(barcodeNumber);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.108:8000/API/test",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Connection Done");
                        Toast.makeText(getApplicationContext(), "Connection Done", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                System.out.println("Connection Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }

        };
        requestQueue.add(stringRequest);
//        APIConnection api = new APIConnection();
//        String result = "";
//         api.execute(barcodeNumber , result);
////        Toast toast = Toast.makeText(getApplicationContext(),
////                result, Toast.LENGTH_LONG);
////        toast.show();
    }

    private String getProductName(final String productNumber) {
        resetInputs();
        ProductNameOperations productNameOperations = new ProductNameOperations(this.getApplicationContext(), scanBtn);
//        try {
//           Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            //productNameOperations.checkProductNameFromExternalAPI(Constants.GET_PRODUCT_NAME_URL, productNumber, formatTxt, false,pr);
////            if(formatTxt.getText().toString().equalsIgnoreCase("")) {
////                System.out.println("External API");
////                productNameOperations.checkProductNameFromExternalAPI(Constants.PRODUCT_NAME_API, productNumber, formatTxt);
////            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        //  formatTxt.setText(productNameOperations.getProductName());
        contentTxt.setText(productNumber);
        return null;
    }


    public void productNameAction() {

        formatTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!formatTxt.getText().toString().equalsIgnoreCase("")) {
                    System.out.println(formatTxt.getText());
                    scanBtn.setEnabled(true);
                    return;

                }
                System.out.println("after (" + formatTxt.getText().toString() + ")");
                scanBtn.setEnabled(false);

                System.out.println("onChange : " + formatTxt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        formatTxt.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(!formatTxt.getText().toString().equalsIgnoreCase(""))
//                {
//                    System.out.println(formatTxt.getText());
//                    scanBtn.setEnabled(true);
//                    return true;
//                }
//                System.out.println("after ("+formatTxt.getText().toString()+")");
//                scanBtn.setEnabled(false);
//                return false;
//            }
//        });
    }


    private void send_ButtonAction() {
        send_Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formatTxt.getText().toString().equalsIgnoreCase("")
                        || contentTxt.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), R.string.fillAllFields, Toast.LENGTH_LONG).show();

                    return;
                }

                TraderInvoiceOperations traderInvoiceOperations = TraderInvoiceOperations.instance.getInstance();
                try {
                    traderInvoiceOperations.broadcastToAPI(MainActivity.this, formatTxt.getText().toString(), contentTxt.getText().toString());
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), R.string.suddenlyError, Toast.LENGTH_LONG).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }
//
//
//    public class APIConnection extends AsyncTask<String,String,String>
//    {
//
//
//        @Override
//        protected String doInBackground(String... params) {
//            String json = params[0];
//
//            URL url = null;
//            BufferedReader reader = null;
//            HttpURLConnection urlConnection = null;
//            try {
//                url = new URL("http://192.168.1.108:8000/API/test");
//                urlConnection  = (HttpURLConnection) url.openConnection();
//
//
//                urlConnection.setDoOutput(true);
//                urlConnection.setConnectTimeout(1000);
//               // urlConnection.setDoInput(true);
//                urlConnection.setChunkedStreamingMode(0);
//
//                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
//                out.write("Amrsaidam".getBytes());
//
//
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//
//                StringBuilder ste = new StringBuilder();
//
//                reader = new BufferedReader(new InputStreamReader(in));
////                StringBuilder out = new StringBuilder();
////                String line;
////                while ((line = reader.readLine()) != null) {
////                    out.append(line);
////                }
////                System.out.println(out.toString());
//               // Log.w("result :",out.toString());
//               // System.out.println(out.toString());   //Prints the string content read from input stream
//
////                try {
////                    reader.close();
////                } catch (IOException e) {
////                    Log.e("Error IO" , ""+e.getStackTrace());
////                }
//                // readStream(in);
//                out.close();
//            } catch (Exception e) {
//                Log.e("Error " , ""+e.getMessage());
//            }
//             finally {
//
//
//                urlConnection.disconnect();
//
//            }
//
////            SendRequest sendRequests = new SendRequest();
////
////            try {
////                String dataToBeSent = "?=AmrSaidam";
////                if(sendRequests.sendRequest());
////                    Log.d("Connection","Done");
////
////                HttpURLConnection httpUrlConnection = (HttpURLConnection) sendRequests.getConnection();
////                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
////                readStream(in);
//               ///OutputStream dataOutputStream = httpUrlConnection.getOutputStream();
//                //dataOutputStream.write(json.getBytes());
//                //byte[] outData = dataToBeSent.getBytes("UTF-8");
//                //dataOutputStream.write(outData);
//               // BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dataOutputStream, "UTF-8"));
//                //writer.write(dataToBeSent);
//                //writer.close();
//                //dataOutputStream.close();
//                //httpUrlConnection.disconnect();
////               Log.d("Connection field",httpUrlConnection.getHeaderField(2));
//              //  Log.d("Connection",httpUrlConnection.getContent().toString());
//
//          //     this.notify();
//
//              //  StringBuilder result = new StringBuilder();
//              // InputStream inputStream =  httpUrlConnection.getInputStream();
//               // result.append(httpUrlConnection.getInputStream().toString());
//            //    httpUrlConnection.getInputStream();
//              //  params[1] = " amr for andoris";
//
//                return "";
////            } catch (IOException e) {
////                Log.e("Error","Error");
////                Log.e("Connection",""+e.getStackTrace(),e);
////            }
//           // return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//        }
//    }

    private void resetInputs() {
        formatTxt.setText(null);
        formatTxt.setEnabled(true);
        contentTxt.setText(null);
        //scanBtn.setEnabled(true);
        //scanBtn.setVisibility(View.VISIBLE);

    }
}
