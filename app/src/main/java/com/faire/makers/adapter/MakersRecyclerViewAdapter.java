package com.faire.makers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.faire.makers.R;
import com.faire.makers.model.Brand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MakersRecyclerViewAdapter extends RecyclerView.Adapter<MakersRecyclerViewAdapter.ViewHolder> {

    public interface OnMakerSelectedListener {
        void onMakerSelected(Brand brand);
    }

    private List<Brand> makers = new ArrayList<>();
    private OnMakerSelectedListener listener;

    public MakersRecyclerViewAdapter(@NonNull OnMakerSelectedListener listener) {
        this.listener = listener;
    }

    public void setMakers(List<Brand> makers) {
        if (makers == null) {
            this.makers = new ArrayList<>();
        } else {
            this.makers = makers;
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.maker_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.brand = makers.get(i);
        viewHolder.updateUI();
    }

    @Override
    public int getItemCount() {
        return makers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Brand brand;
        private TextView txtName;
        private TextView txtCategories;
        private ImageView imgBrand;
        private Button btnProducts;

        private ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtCategories = view.findViewById(R.id.txtCategories);
            imgBrand = view.findViewById(R.id.imgBrand);
            btnProducts = view.findViewById(R.id.btnProducts);
        }

        void updateUI() {
            txtName.setText(brand.name);
            txtCategories.setText(brand.getCategories());
            if (brand.images != null && brand.images[0] != null) {
                Picasso.get()
                        .load(brand.images[0].url)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(imgBrand);
            }
            btnProducts.setOnClickListener(v -> listener.onMakerSelected(brand));

        }

    }
}
