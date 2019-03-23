package com.faire.makers.utils;

import android.content.res.Resources;
import android.util.Log;

import com.faire.makers.R;
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

    private static LocationAPI instance;

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

    public static LocationAPI getInstance() {
        if (instance == null) {
            instance = new MockLocationAPI();
        }

        return instance;
    }

    /**
     * Loads and parse brands from a json file in assets folder.
     *
     * @return A list with all mock brands
     */
    private List<Brand> getAllBrands() {
        if (allBrands == null) {
            allBrands = new FaireAPI().searchForMakers(" ");
        }

        return allBrands;
    }

    @Override
    public List<Brand> getMakerNearby(double lat, double lon) {
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
                int iDelta = RandomGen.getRandomInt(DELTA_MIN, DELTA_MAX);
                double delta = (double) iDelta / 100.0;
                brand.latidude = lat + delta;
                brand.longitude = lon + delta;
            }
            result.add(brand);
        }
        return result;
    }
    
}
