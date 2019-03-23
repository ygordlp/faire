package com.faire.makers.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.faire.makers.model.Brand;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JSON parse util.
 * To parse JSON from request results.
 */
public class Parser {
    private static final String TAG = "JSON Parser";

    private static Gson gson = new Gson();

    /**
     * Gets the list of brands from the JSON response for brands search.
     *
     * @param json JSON response from search api endpoint.
     * @return A List of brands. If an error occoured on parse, an empty list is return.
     */
    public static List<Brand> parseBrands(@NonNull String json) {
        List<Brand> result;
        try {
            JSONObject obj = new JSONObject(json);
            String brandsJson = null;
            brandsJson = obj.getJSONArray("brands").toString();
            Brand[] brands = gson.fromJson(brandsJson, Brand[].class);
            result = Arrays.asList(brands);
        } catch (JSONException e) {
            Log.e(TAG, "Error on parseBrands: " + e.getMessage());
            result = new ArrayList<>();
        }

        return result;
    }
}
