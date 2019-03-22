package com.faire.makers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faire.makers.R;
import com.faire.makers.model.Brand;

import java.util.ArrayList;
import java.util.List;

public class MakersRecyclerViewAdapter extends RecyclerView.Adapter<MakersRecyclerViewAdapter.ViewHolder> {

    public interface OnMakerSelectedListner {
        void onCategorySelected(Brand brand);
    }

    private List<Brand> makers = new ArrayList<>();
    private OnMakerSelectedListner listener;

    public MakersRecyclerViewAdapter(@NonNull OnMakerSelectedListner listener) {
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

        private ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            view.setOnClickListener(v -> listener.onCategorySelected(brand));
        }

        void updateUI() {
            txtName.setText(brand.name);
        }

    }
}
