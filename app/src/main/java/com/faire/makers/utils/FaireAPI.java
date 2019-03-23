package com.faire.makers.utils;

import android.util.Log;

import com.faire.makers.model.Brand;
import com.faire.makers.model.Category;
import com.faire.makers.model.Product;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class responsable for connecting with Faire API,
 */
public class FaireAPI {

    private static final String TAG = "FaireAPI";
    private static final String BASE_URL = "https://www.faire.com";
    private static final String CATEGORIES_URL = BASE_URL + "/api/category/new";
    private static final String PRODUCTS_URL = BASE_URL + "/api/brand/%s/products";
    private static final String SEARCH_URL = BASE_URL + "/api/search/makers-with-filters";
    private static final String SEARCH_BODY = "{ SearchMakersWithFiltersRequest: \"%s\" }";
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

    public List<Brand> searchForMakers(String search){
        Log.d(TAG, "Search started. param = " + search);
        List<Brand> result = null;
        try {
            String json = String.format(SEARCH_BODY, search);
            RequestBody requestBody = RequestBody.create(JSON_TYPE, json);

            Request request = new Request.Builder()
                    .url(SEARCH_URL)
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) {
                String body = response.body().string();
                if (body != null) {
                    JSONObject obj = new JSONObject(body);
                    String brandsJson = obj.getJSONArray("brands").toString();
                    Brand[] brands = gson.fromJson(brandsJson, Brand[].class);
                    result = Arrays.asList(brands);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error loading brands: " + e.getMessage());
            result = new ArrayList<>();
        }

        Log.d(TAG, "Brands found: " + result.size());

        return result;
    }

    public List<Product> getProductsFromMaker(String token){
        Log.d(TAG, "Search products. token = " + token);
        List<Product> result = null;
        try {
            String url = String.format(PRODUCTS_URL, token);

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) {
                String body = response.body().string();
                if (body != null) {
                    Product[] products = gson.fromJson(body, Product[].class);
                    result = Arrays.asList(products);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Error loading products: " + e.getMessage());
            result = new ArrayList<>();
        }

        Log.d(TAG, "Products found: " + result.size());

        return result;
    }
}
