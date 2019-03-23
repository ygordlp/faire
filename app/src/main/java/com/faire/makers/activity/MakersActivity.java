package com.faire.makers.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.faire.makers.R;
import com.faire.makers.adapter.MakersRecyclerViewAdapter;
import com.faire.makers.model.Brand;
import com.faire.makers.utils.FaireAPI;

import java.util.List;

public class MakersActivity extends AppCompatActivity implements MakersRecyclerViewAdapter.OnMakerSelectedListener {

    public static final String TAG = "MakersActivity";
    public static final String EXTRA_SEARCH_PARAM = "EXTRA_SEARCH_PARAM";

    private RecyclerView recyclerView;
    private View progressBar;
    private MakersRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makers);

        String searchParam = getIntent().getStringExtra(EXTRA_SEARCH_PARAM);

        Log.d(TAG, "Search param received: " + searchParam);
        if(searchParam == null || searchParam.isEmpty()){
            finish();
            Log.e(TAG, "Search param empty or null.");
            return;
        }

        recyclerView = findViewById(R.id.makersList);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        adapter = new MakersRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);

        new SearchTask().execute(searchParam);
    }

    public void onSearchResults(List<Brand> makers){
        if(makers.size() == 0){
            Toast.makeText(this, getString(R.string.msg_no_makers_found), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.setMakers(makers);
    }

    @Override
    public void onMakerSelected(Brand brand) {
        Log.d(TAG, "onMakerSelected: " + brand);
        if (brand == null || brand.token == null) {
            Log.e(TAG, "Brand or token is null");
        } else {
            Intent intent = new Intent(this, ProductsActivity.class);
            intent.putExtra(ProductsActivity.EXTRA_BRAND_TOKEN, brand.token);
            startActivity(intent);
        }
    }

    /**
     * Search for makers.
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
