package com.com.tigarty.api.RecycleViewAssets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.com.tigarty.api.Entities.Product;
import com.example.amrsaidam.tigarty.R;

import java.util.List;

/**
 * Created by Amr Saidam on 12/1/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Product> products;
    public RVAdapter(Context context , List<Product> products) {
        layoutInflater = LayoutInflater.from(context);
        this.products = products;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


       // products.get(position)
//        String numberOSale = String.valueOf(R.string.numberOfSale);
//        String restAmount = String.valueOf(R.string.restAmount);
//        String cartoon = String.valueOf(R.string.cartoon);

        holder.mainTitle.setText(""+products.get(position).getName());
        holder.subTitleRestAmountNumberOfSale.setText(holder.subTitleRestAmountNumberOfSale.getText()+""+products.get(position).getNumberOfSale());
        holder.subTitleRestAmount.setText(holder.subTitleRestAmount.getText()+""+  products.get(position).getWholeQuantity()+"C");
        holder.productPrice.setText(products.get(position).getSingleUnitPrice()+"$");


    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mainTitle, subTitleRestAmount ,subTitleRestAmountNumberOfSale, productPrice;


        public MyViewHolder(View itemView) {
            super(itemView);
            mainTitle = (TextView) itemView.findViewById(R.id.mainTitle);
            subTitleRestAmountNumberOfSale = (TextView) itemView.findViewById(R.id.numberOfSale);
            subTitleRestAmount = (TextView) itemView.findViewById(R.id.restAmount);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position

            System.out.println(position);
//            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
////                User user = users.get(position);
////                // We can access the data within the views
////                Toast.makeText(context, tvName.getText(), Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
