package com.faire.makers.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.faire.makers.R;
import com.faire.makers.adapter.CategoriesRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    public static final String TAG = "MainActivity";

    private TextView txtSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSearch = findViewById(R.id.txtSearch);
        txtSearch.setOnEditorActionListener(this);

        findViewById(R.id.btnCategory).setOnClickListener(this);
        findViewById(R.id.btnNear).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCategory:
                showCategoriesActivity();
                break;
            case R.id.btnNear:
                showMapsActivity();
                break;
        }
    }

    private void showCategoriesActivity() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    private void showMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
