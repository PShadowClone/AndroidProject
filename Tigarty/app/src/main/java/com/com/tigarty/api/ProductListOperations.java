package com.com.tigarty.api;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.com.tigarty.api.Entities.Product;
import com.com.tigarty.api.Entities.User;
import com.com.tigarty.api.RecycleViewAssets.RVAdapter;
import com.example.amrsaidam.tigarty.Fragments.MainFragment;
import com.example.amrsaidam.tigarty.Fragments.ProductList;
import com.example.amrsaidam.tigarty.MainActivity;
import com.example.amrsaidam.tigarty.Menu;
import com.example.amrsaidam.tigarty.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Amr Saidam on 12/1/2016.
 */

public class ProductListOperations {


    private FragmentManager fragmentManager;
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private View view;

    Context context;

    public ProductListOperations(){}
    public ProductListOperations(View view , Context context, RecyclerView recyclerView, ProgressBar progressBar, FragmentManager fragmentManager) {
        this.context = context;
        products = new ArrayList<>();
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
        this.fragmentManager = fragmentManager;
        this.view = view;

    }

    public void fetchProducts() {

        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        StringRequest stringRequest = null;
        try {
            stringRequest = new StringRequest(Request.Method.GET, Constants.LIST_OF_PRODUCTS+"?userId="+Constants.getUserId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println(response.toString());

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                convertToProducts(products, jsonArray);
                                RVAdapter rvAdapter = new RVAdapter(context, products);
                                recyclerView.setAdapter(rvAdapter);
                                progressBar.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {
                        ArrayList<Product> locallyProducts = new ArrayList<>();
                        locallyProducts = readProductsFromStorage();
                        System.out.println(locallyProducts);
                        if (locallyProducts != null){

                            RVAdapter rvAdapter = new RVAdapter(context, locallyProducts);
                            recyclerView.setAdapter(rvAdapter);
                            progressBar.setVisibility(View.GONE);

                        }

                        if (error.getClass().getSimpleName().equalsIgnoreCase("NoConnectionError")) {
                            Snackbar.make(view, R.string.internetError, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        requestQueue.add(stringRequest);
        requestQueue.start();


    }

    private void convertToProducts(ArrayList<Product> products, JSONArray response) throws JSONException, IOException {
        for (int counter = 0; counter < response.length(); counter++) {
            JSONObject jsonObject = new JSONObject(response.get(counter).toString());
            Product product = new Product(
                    jsonObject.getInt("Product_ID"),
                    jsonObject.getString("Name"),
                    (float) jsonObject.getDouble("WholeQuantity"),
                    jsonObject.getInt("numberOfUnitInCartoon"),
                    jsonObject.getInt("wholepricetrader"),
                    jsonObject.getInt("wholepricesale"),
                    jsonObject.getInt("SingleUnitPrice"),
                    jsonObject.getInt("SingleUnitAmount"),
                    jsonObject.getInt("total"),
                    jsonObject.getInt("numberOfSale"),
                    jsonObject.getString("Barcode"),
                    jsonObject.getString("Shop_ID"),
                    ""+jsonObject.getInt("userID"),
                    jsonObject.getString("Trader_ID"),
                    jsonObject.getString("created_at"),
                    jsonObject.getString("updated_at"));
            products.add(product);

        }

        saveProductsLocally(products);
    }


    private void initiateFragment(Fragment fragment, String tag) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, tag);
        fragmentTransaction.replace(R.id.content_menu, fragment);
        fragmentTransaction.commit();
    }

    private void saveProductsLocally(ArrayList<Product> products) throws IOException {

        File tigartyDir = new File(Environment.getExternalStorageDirectory(), "" + Constants.PRODUCT_DIR);
        if (!tigartyDir.exists())
            tigartyDir.mkdirs();

        File productFile = new File(tigartyDir, Constants.PRODUCT_LIST + ".dat");
        if(!productFile.exists())
            productFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(productFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(products);
        productFile.setWritable(false);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public ArrayList<Product> readProductsFromStorage() throws IOException, ClassNotFoundException {
        ArrayList<Product> locallyProducts = new ArrayList<>();
        File tigartyDir = new File(Environment.getExternalStorageDirectory(), "" + Constants.TIGARTY_DIR);
      //  System.out.println(Environment.getExternalStorageDirectory().getPath());
       // System.out.println(Environment.getExternalStorageDirectory());
        if (!tigartyDir.exists()) {
            System.out.println("Tigarty NONE");
            return null;
        }

        File productFile = new File(tigartyDir, Constants.PRODUCT_LIST+"//product" + ".dat");
        if (!productFile.exists()) {
            System.out.println("Product NONE");
            return null;
        }

        System.out.println(tigartyDir);
        System.out.println(productFile);
        FileInputStream fileInputStream = new FileInputStream(productFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        locallyProducts  = ( ArrayList<Product>) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();
        return locallyProducts;


    }
}
