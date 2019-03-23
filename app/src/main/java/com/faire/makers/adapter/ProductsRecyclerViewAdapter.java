package com.faire.makers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.faire.makers.R;
import com.faire.makers.model.Brand;
import com.faire.makers.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ViewHolder> {

    private List<Product> products = new ArrayList<>();

    public void setProducts(List<Product> products) {
        if (products == null) {
            this.products = new ArrayList<>();
        } else {
            this.products = products;
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.product = products.get(i);
        viewHolder.updateUI();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Product product;
        private TextView txtName;
        private TextView txtDescription;
        private ImageView imgProduct;

        private ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            imgProduct = view.findViewById(R.id.imgProduct);
            txtDescription = view.findViewById(R.id.txtDescription);
        }

        void updateUI() {
            txtName.setText(product.name);
            txtDescription.setText(product.short_description);
            if (product.images != null && product.images[0] != null) {
                Picasso.get()
                        .load(product.images[0].url)
                        .error(R.drawable.placeholder)
                        .into(imgProduct);
            }
        }

    }
}
