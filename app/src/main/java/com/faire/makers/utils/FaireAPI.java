package com.faire.makers.utils;

import com.faire.makers.model.Messages.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Class responsable for connecting with Faire API,
 */
public class FaireAPI {

    private static final String BASE_URL = "https://www.faire.com";
    private static final String CATGORORIES_URL = BASE_URL + "/api/category/new";
    private static final String SEARCH_URL = BASE_URL + "/api/category/new";
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
            Request request = new Request.Builder().url(CATGORORIES_URL).build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    Categories categories = Categories.parseFrom(responseBody.byteStream());
                    result = categories.getCategoriesList();
                }
            }

            if(result == null) {
                result = new ArrayList<>();
            }
        } catch (Exception e) {
            result = new ArrayList<>();
        }

        return result;
    }
}
