package com.faire.makers.utils;

import android.content.res.Resources;
import android.util.Log;

import com.faire.makers.model.Brand;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock location API.
 * Mocks the service to get Brands base on location.
 * <p>
 * Random location will be used to return random brands.
 */
public class MockLocationAPI extends LocationAPI {

    private static final String TAG = "MockLocationAPI";

    /**
     * To generate a random distance from the given location.
     **/
    private static final int DELTA_MIN = 10;
    private static final int DELTA_MAX = 100;

    private List<Brand> allBrands;


    /**
     * Private constructor to make it singleton.
     */
    private MockLocationAPI() {

    }

    @Override
    public LocationAPI getInstance() {
        if (this.instance == null) {
            this.instance = new MockLocationAPI();
        }

        return this.instance;
    }

    /**
     * Loads and parse brands from a json file in assets folder.
     *
     * @return A list with all mock brands
     */
    private List<Brand> getAllBrands() {
        if (allBrands == null) {
            //Loads all brands from local JSON in assets;
            String json = "";
            try {
                InputStream is = Resources.getSystem().getAssets().open("brands.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                Log.e(TAG, "Error loading mock brands from json: " + ex.getMessage());
            }

            if (json == null || json.isEmpty()) {
                Log.e(TAG, "Null or empty mock brands json");
                allBrands = new ArrayList<>();
            } else {
                allBrands = Parser.parseBrands(json);
            }
        }

        return allBrands;
    }

    @Override
    public List<Brand> getMakerNearby(long lat, long lon) {
        List<Brand> all = getAllBrands();
        int h = all.size() / 2;
        int s = (h > 10) ? 10 : h;

        int count = RandomGen.getRandomInt(1, s);

        int[] randomIndexes = RandomGen.getRandomIntsNoRep(count, 0, all.size() - 1);
        List<Brand> result = new ArrayList<>();
        for (int i : randomIndexes) {
            Brand brand = all.get(i);

            //Mock brand location if not mocked yet
            if (brand.latidude == 0) {
                long delta = RandomGen.getRandomInt(DELTA_MIN, DELTA_MAX);
                brand.latidude = lat + delta;
                brand.longitude = lon + delta;
            }
            result.add(brand);
        }
        return result;
    }
    
}
