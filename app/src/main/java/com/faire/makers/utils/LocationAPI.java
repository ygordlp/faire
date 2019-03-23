package com.faire.makers.utils;

import com.faire.makers.model.Brand;

import java.util.List;

/**
 * Abstract for Location API.
 * Classes that implement this must be singleton.
 */
public abstract class LocationAPI {

    protected LocationAPI instance;

    /**
     * Location API must be a singleton.
     *
     * @return Location API instance.
     */
    public abstract LocationAPI getInstance();

    /**
     * Gets the makers nearby the given GPS coordinates.
     *
     * @param lat Latitude
     * @param lon Longitude
     * @return The list of brands found nearby.
     */
    public abstract List<Brand> getMakerNearby(long lat, long lon);
}
