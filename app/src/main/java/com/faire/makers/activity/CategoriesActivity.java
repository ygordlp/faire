package com.faire.makers.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.faire.makers.R;
import com.faire.makers.adapter.CategoriesRecyclerViewAdapter;
import com.faire.makers.model.Category;
import com.faire.makers.utils.FaireAPI;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRecyclerViewAdapter.OnCategorySelectedListner, TextView.OnEditorActionListener {

    public static final String TAG = "CategoriesActivity";

    private RecyclerView recyclerView;
    private View progressBar;
    private CategoriesRecyclerViewAdapter adapter;
    private TextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.categoriesList);
        progressBar = findViewById(R.id.progressBar);
        txtSearch = findViewById(R.id.txtSearch);

        txtSearch.setOnEditorActionListener(this);

        adapter = new CategoriesRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);

        new LoadCategoriesTask().execute();
    }

    private void onCategoriesLoaded(List<Category> categories) {
        Log.d(TAG, "onCategoriesLoaded");
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.setCategories(categories);
        //TODO: Show message when no categories were found.
    }

    @Override
    public void onCategorySelected(Category category) {
        if (category == null || category.name == null || category.name.isEmpty()) {
            Log.e(TAG, "onCategoriesLoaded: invalid category name");
        }

        startSearch(category.name);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String searchParam = txtSearch.getText().toString().trim();
        if (!searchParam.isEmpty()) {
            startSearch(searchParam);
        }
        return true;
    }

    private void startSearch(String searchParam) {
        Log.d(TAG, "startSearch: " + searchParam);
        if (searchParam == null || searchParam.trim().isEmpty()) {
            Log.e(TAG, "Empty or null search parameter");
        } else {
            Intent intent = new Intent(this, MakersActivity.class);
            intent.putExtra(MakersActivity.EXTRA_SEARCH_PARAM, searchParam);
            startActivity(intent);
        }
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
