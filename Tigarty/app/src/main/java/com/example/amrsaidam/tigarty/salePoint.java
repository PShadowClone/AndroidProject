package com.example.amrsaidam.tigarty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.com.tigarty.api.Constants;
import com.com.tigarty.api.ProductNameOperations;
import com.com.tigarty.api.SalePointOperations;
import com.com.tigarty.api.TraderInvoiceOperations;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

public class salePoint extends AppCompatActivity {


    EditText productName;
    EditText productNumber;
    Button scanButton;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_point);

        productName = (EditText) findViewById(R.id.productName);
        productNumber = (EditText) findViewById(R.id.productNumber);
        scanButton = (Button) findViewById(R.id.scan_barcode_button);
        sendButton = (Button) findViewById(R.id.send_barcode_Button);
        setActionToTheScanButton(scanButton);
        setActionToTheSendButton(sendButton);
    }





    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            getProductName(scanContent);
            productNameAction();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }



    private void productNameAction() {

        productName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!productName.getText().toString().equalsIgnoreCase("")) {
                    System.out.println(productName.getText());
                    scanButton.setEnabled(true);
                    return;

                }
                System.out.println("after (" + productName.getText().toString() + ")");
                scanButton.setEnabled(false);

                System.out.println("onChange : " + productName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    private String getProductName(final String productBarcodeNumber) {
        resetInputs();
//        ProductNameOperations productNameOperations = new ProductNameOperations(this.getApplicationContext(), scanButton);
//        try {
//            productNameOperations.checkProductNameFromExternalAPI(Constants.GET_PRODUCT_NAME_URL, productBarcodeNumber, productName , true);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        productNumber.setText("1");
        return null;
    }


    private void setActionToTheScanButton(Button scanButtonAction)
    {
        scanButtonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.scan_barcode_button) {
                    IntentIntegrator scanIntegrator = new IntentIntegrator(salePoint.this);
                    scanIntegrator.initiateScan();


                }
            }
        });
    }

    private void setActionToTheSendButton(Button sendButtonAction)
    {


        sendButtonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (productName.getText().toString().equalsIgnoreCase("") || productNumber.getText().toString().equalsIgnoreCase("")) {
//                    Toast.makeText(getApplicationContext(), R.string.fillAllFields, Toast.LENGTH_LONG).show();
//                    return;
//                }
//                SalePointOperations salePointOperations = SalePointOperations.instance.getInstance();
//                try {
//                   // salePointOperations.broadcastToAPI(salePo,salePoint.this, productName.getText().toString());
//                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(),R.string.suddenlyError, Toast.LENGTH_LONG).show();
//                }


            }
        });
    }

    private void resetInputs() {
        productName.setText(null);
        productName.setEnabled(true);
        productNumber.setText(null);
        //scanButton.setVisibility(View.VISIBLE);
    }
}
