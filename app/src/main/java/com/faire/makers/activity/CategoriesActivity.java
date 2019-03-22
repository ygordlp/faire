package com.faire.makers.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.faire.makers.R;
import com.faire.makers.adapter.CategoriesRecyclerViewAdapter;
import com.faire.makers.adapter.MakersRecyclerViewAdapter;
import com.faire.makers.model.Category;
import com.faire.makers.utils.FaireAPI;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRecyclerViewAdapter.OnCategorySelectedListner {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CategoriesRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.categoriesList);
        progressBar = findViewById(R.id.progressBar);

        adapter = new CategoriesRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);

        new LoadCategoriesTask().execute();
    }

    private void onCategoriesLoaded(List<Category> categories) {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.setCategories(categories);
        //TODO: Show message when no categories were found.
    }

    @Override
    public void onCategorySelected(Category category) {
        //TODO: Use category for search;
        Toast.makeText(this, category.name, Toast.LENGTH_SHORT).show();
    }

    /**
     * Loads all categories.
     */
    public class LoadCategoriesTask extends AsyncTask<Void, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Void... voids) {
            FaireAPI api = new FaireAPI();
            List<Category> categories = api.getAllCategories();
            return categories;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            onCategoriesLoaded(categories);
        }
    }
}
