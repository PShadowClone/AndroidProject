package com.example.amrsaidam.tigarty.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.com.tigarty.api.TraderInvoiceOperations;
import com.example.amrsaidam.tigarty.MainActivity;
import com.example.amrsaidam.tigarty.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class TraderInvoiceFragment extends Fragment   {

    private Button scanBtn, send_Button ,cancelButton;
    private EditText formatTxt, contentTxt;

    private ProgressBar progressBar;
    public TraderInvoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_trader_invoice, container, false);
        this.getActivity().setTitle(R.string.navigationTraderInvoice);

        scanBtn = (Button) view.findViewById(R.id.scan_button);
        formatTxt = (EditText) view.findViewById(R.id.scan_format);
        contentTxt = (EditText) view.findViewById(R.id.scan_content);
        send_Button = (Button) view.findViewById(R.id.send_Button);
        cancelButton = (Button) view.findViewById(R.id.cancel_Button);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);

        //scanBtn.setOnClickListener(this);
        setActionForScanBtn(scanBtn);
        setActionToTheCancelButton(cancelButton);
        send_ButtonAction();



        return view;
    }



@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    System.out.println("Here in Trade Invoice");
        super.onActivityResult(requestCode,resultCode,intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        // startActivity(intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            System.out.println(scanContent);
            //formatTxt.setText("FORMAT: " + scanFormat);
            // contentTxt.setText("CONTENT: " + scanContent);
            if (scanContent == null) {
                Toast.makeText(getContext(), R.string.scanEmpty, Toast.LENGTH_LONG).show();
            } else {
                getProductName(scanContent);
                productNameAction();
            }
//            getProductName(scanContent);
//            productNameAction();
            // getConnectionOn(contentTxt.getText().toString());
        } else {
            Toast toast = Toast.makeText(getContext(),
                    "No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    private void setActionForScanBtn(Button button)
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.scan_button) {
                    IntentIntegrator scanIntegrator = new IntentIntegrator(TraderInvoiceFragment.this.getActivity());
                    scanIntegrator.initiateScan();


                }

            }
        });
    }
    private String getProductName(final String productNumber) {
        resetInputs();
        progressBar.setVisibility(View.VISIBLE);
        ProductNameOperations productNameOperations = new ProductNameOperations(this.getContext(), scanBtn);
//        try {
//           Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            productNameOperations.checkProductNameFromExternalAPI(Constants.GET_PRODUCT_NAME_URL, productNumber, formatTxt, false ,progressBar);
//            if(formatTxt.getText().toString().equalsIgnoreCase("")) {
//                System.out.println("External API");
//                productNameOperations.checkProductNameFromExternalAPI(Constants.PRODUCT_NAME_API, productNumber, formatTxt);
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //  formatTxt.setText(productNameOperations.getProductName());
        contentTxt.setText(productNumber);
        scanBtn.setVisibility(View.GONE);
        cancelButton.setVisibility(View.VISIBLE);
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

    private void send_ButtonAction() {
        send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formatTxt.getText().toString().equalsIgnoreCase("")
                        || contentTxt.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), R.string.fillAllFields, Toast.LENGTH_LONG).show();

                    return;
                }

                TraderInvoiceOperations traderInvoiceOperations = TraderInvoiceOperations.instance.getInstance();
                try {
                    traderInvoiceOperations.broadcastToAPI(getContext(), formatTxt.getText().toString(), contentTxt.getText().toString());
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
    private void resetInputs() {
        formatTxt.setText(null);
        formatTxt.setEnabled(true);
        contentTxt.setText(null);
        scanBtn.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        //scanBtn.setEnabled(true);
        //scanBtn.setVisibility(View.VISIBLE);

    }
}
