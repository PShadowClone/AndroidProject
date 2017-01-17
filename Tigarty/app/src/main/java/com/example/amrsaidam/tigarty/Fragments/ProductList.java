package com.example.amrsaidam.tigarty.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.com.tigarty.api.Entities.Product;
import com.com.tigarty.api.ProductListOperations;
import com.com.tigarty.api.RecycleViewAssets.RVAdapter;
import com.example.amrsaidam.tigarty.R;

import java.util.ArrayList;


public class ProductList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        //this.registerReceiver(new Receiver(), filter);

        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        this.getActivity().setTitle(R.string.shopProducts);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        prepareRecyclerList(recyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        ProductListOperations productListOperations = new ProductListOperations(view,this.getContext() , recyclerView , progressBar , this.getActivity().getSupportFragmentManager());
        productListOperations.fetchProducts();

        return view;
    }

    private void prepareRecyclerList(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        recyclerView.setAdapter(new RVAdapter(this.getContext(), new ArrayList<Product>()));

    }

}