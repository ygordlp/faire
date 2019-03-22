package com.faire.makers.utils;

import android.util.Log;

import com.faire.makers.model.Messages.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class responsable for connecting with Faire API,
 */
public class FaireAPI {

    private static final String TAG = "FaireAPI";
    private static final String BASE_URL = "https://www.faire.com";
    private static final String CATEGORIES_URL = BASE_URL + "/api/category/new";
    private static final String SEARCH_URL = BASE_URL + "/api/search/makers-with-filters";
    private static final String SEARCH_BODY = "{ SearchMakersWithFiltersRequest: \"%s\" }";
    private static final String FILTER_RESPONSE = "SearchMakersWithFiltersResponse";
    private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient okHttpClient = new OkHttpClient();
    private Gson gson = new Gson();

    public FaireAPI(){

    }

    public List<Category> getAllCategories(){
        List<Category> result = null;
        try {
            Request request = new Request.Builder().url(CATEGORIES_URL).build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) {
                String body = response.body().string();
                if (body != null) {
                    Category[] categories  = gson.fromJson(body, Category[].class);
                    result = Arrays.asList(categories);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error loading categories: " + e.getMessage());
            result = new ArrayList<>();
        }

        Log.d(TAG, "Categories found: " + result.size());

        return result;
    }
}
