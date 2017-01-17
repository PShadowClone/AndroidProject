package com.example.amrsaidam.tigarty.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.com.tigarty.api.Constants;
import com.com.tigarty.api.ProductNameOperations;
import com.com.tigarty.api.SalePointOperations;
import com.example.amrsaidam.tigarty.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalePointFragment extends Fragment {


    EditText productName;
    EditText productNumber;
    Button scanButton;
    Button sendButton;
    Button cancelButton;
    ProgressBar progressBar;

    public SalePointFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Tag : " + this.getTag());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sale_point2, container, false);
        this.getActivity().setTitle(R.string.navigationSalePoint);
        productName = (EditText) view.findViewById(R.id.productName);
        productNumber = (EditText) view.findViewById(R.id.productNumber);
        scanButton = (Button) view.findViewById(R.id.scan_barcode_button);
        sendButton = (Button) view.findViewById(R.id.send_barcode_Button);
        cancelButton = (Button) view.findViewById(R.id.cancel_Button);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        cancelButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        setActionToTheScanButton(scanButton);
        setActionToTheSendButton(sendButton);
        setActionToTheCancelButton(cancelButton);

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        return view;
    }

    public void Hi() {
        System.out.println("HI");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // System.out.println(intent.toString());
        //  startActivityForResult(intent,resultCode);
        super.onActivityResult(requestCode, resultCode, intent);
        System.out.println("On");
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            if (scanContent == null) {
                Toast.makeText(getContext(), R.string.scanEmpty, Toast.LENGTH_LONG).show();
                return;
            }
            getProductName(scanContent);
            productNameAction();
        } else {
            Toast toast = Toast.makeText(getContext(),
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
//        ProgressBar
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
               // scanButton.setEnabled(false);

                System.out.println("onChange : " + productName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private String getProductName(final String productBarcodeNumber) {
        resetInputs();
        progressBar.setVisibility(View.VISIBLE);
        ProductNameOperations productNameOperations = new ProductNameOperations(this.getContext(), scanButton);
        try {
            productNameOperations.checkProductNameFromExternalAPI(Constants.GET_PRODUCT_NAME_URL, productBarcodeNumber, productName, true  , progressBar);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productNumber.setText("1");
        scanButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.VISIBLE);
        return null;
    }


    private void setActionToTheScanButton(Button scanButtonAction) {
        scanButtonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.scan_barcode_button) {
                    IntentIntegrator scanIntegrator = new IntentIntegrator(SalePointFragment.this.getActivity());
                    scanIntegrator.initiateScan();


                }
            }
        });
    }


    private void setActionToTheSendButton(Button sendButtonAction) {
        sendButtonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productName.getText().toString().equalsIgnoreCase("") || productNumber.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), R.string.fillAllFields, Toast.LENGTH_LONG).show();
                    return;
                }
                SalePointOperations salePointOperations = SalePointOperations.instance.getInstance();
                try {
                    salePointOperations.broadcastToAPI(getView(),SalePointFragment.this.getContext(), productName.getText().toString());
                    resetInputs();
                } catch (JSONException e) {
                    Toast.makeText(getContext(), R.string.suddenlyError, Toast.LENGTH_LONG).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void setActionToTheCancelButton(Button cancelButtonAction)
    {
        cancelButtonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInputs();
            }
        });
    }
    private void resetInputs() {
        productName.setText(null);
        productName.setEnabled(true);
        productNumber.setText(null);
        progressBar.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
        scanButton.setVisibility(View.VISIBLE);
    }
}

