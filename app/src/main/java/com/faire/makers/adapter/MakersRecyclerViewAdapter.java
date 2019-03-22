package com.faire.makers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faire.makers.R;
import com.faire.makers.model.Messages.Category;

import java.util.ArrayList;
import java.util.List;

public class MakersRecyclerViewAdapter extends RecyclerView.Adapter<MakersRecyclerViewAdapter.ViewHolder> {

    public  interface OnCategorySelectedListner {
        void onCategorySelected(Category category);
    }
    private List<Category> categories = new ArrayList<>();
    private OnCategorySelectedListner listener;

    public MakersRecyclerViewAdapter(@NonNull OnCategorySelectedListner listener) {
        this.listener = listener;
    }

    public void setCategories(List<Category> categories) {
        if(categories == null) {
            this.categories = new ArrayList<>();
        } else {
            this.categories = categories;
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.category = categories.get(i);
        viewHolder.updateUI();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Category category;
        private TextView txtCategory;

        private ViewHolder(View view) {
            super(view);
            txtCategory = view.findViewById(R.id.txtCategory);
            view.setOnClickListener(v -> listener.onCategorySelected(category));
        }

        void updateUI(){
            txtCategory.setText(category.getName());
        }

    }
}
