package com.faire.makers.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.faire.makers.R;
import com.faire.makers.adapter.MakersRecyclerViewAdapter;
import com.faire.makers.adapter.ProductsRecyclerViewAdapter;
import com.faire.makers.model.Brand;
import com.faire.makers.model.Product;
import com.faire.makers.utils.FaireAPI;

import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    public static final String TAG = "ProductsActivity";
    public static final String EXTRA_BRAND_TOKEN = "EXTRA_BRAND_TOKEN";

    private RecyclerView recyclerView;
    private View progressBar;
    private ProductsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        String token = getIntent().getStringExtra(EXTRA_BRAND_TOKEN);

        Log.d(TAG, "Brand token received: " + token);
        if (token == null || token.isEmpty()) {
            finish();
            Log.e(TAG, "Token empty or null.");
            return;
        }

        recyclerView = findViewById(R.id.productsList);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        adapter = new ProductsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);

        new SearchProductsTask().execute(token);
    }

    public void onProductsLoaded(List<Product> products) {
        if(products.size() == 0){
            Toast.makeText(this, getString(R.string.lbl_no_products_found), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.setProducts(products);
    }

    /**
     * Search for makers.
     */
    public class SearchProductsTask extends AsyncTask<String, Void, List<Product>> {

        @Override
        protected List<Product> doInBackground(String... token) {
            FaireAPI api = new FaireAPI();
            List<Product> makers = api.getProductsFromMaker(token[0]);
            return makers;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            onProductsLoaded(products);
        }
    }
}
