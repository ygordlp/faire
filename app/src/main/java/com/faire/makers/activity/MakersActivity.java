package com.faire.makers.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.faire.makers.R;
import com.faire.makers.adapter.MakersRecyclerViewAdapter;
import com.faire.makers.model.Brand;
import com.faire.makers.model.Category;
import com.faire.makers.utils.FaireAPI;

import java.util.List;

public class MakersActivity extends AppCompatActivity implements MakersRecyclerViewAdapter.OnMakerSelectedListner {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MakersRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makers);

        recyclerView = findViewById(R.id.categoriesList);
        progressBar = findViewById(R.id.progressBar);

        adapter = new MakersRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);
    }

    public void onSearchResults(List<Brand> makers){
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.setMakers(makers);
    }

    @Override
    public void onCategorySelected(Brand brand) {

    }

    /**
     * Loads all categories.
     */
    public class SearchTask extends AsyncTask<String, Void, List<Brand>> {

        @Override
        protected List<Brand> doInBackground(String... searchParams) {
            FaireAPI api = new FaireAPI();
            List<Brand> makers = api.searchForMakers(searchParams[0]);
            return makers;
        }

        @Override
        protected void onPostExecute(List<Brand> makers) {
            onSearchResults(makers);
        }
    }
}
