package com.faire.makers.utils;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

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

    private OkHttpClient http = new OkHttpClient();
    private Gson gson = new Gson();

}
