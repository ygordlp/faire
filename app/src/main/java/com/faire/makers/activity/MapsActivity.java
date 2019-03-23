package com.faire.makers.activity;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.faire.makers.R;
import com.faire.makers.model.Brand;
import com.faire.makers.utils.LocationAPI;
import com.faire.makers.utils.MockLocationAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //TODO: get real location
        double lat = -23.5866628;
        double lon = -46.7043541;
        // Add a marker in the Hackthon location and move the camera
        LatLng loc = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(loc).title("You"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

        //Get Makers nearby
        new LoadMakersTask().execute(lat, lon);
    }

    private void createMarkerAt(double lat, double lon, String label){

        LatLng loc = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(loc).title(label).icon(BitmapDescriptorFactory
                .defaultMarker(120)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }

    private void onMakersLoaded(List<Brand> brands) {

        for(Brand b : brands){
            createMarkerAt(b.latidude, b.longitude, b.name);
        }

    }

    public class LoadMakersTask extends AsyncTask<Double, Void, List<Brand>>{

        @Override
        protected List<Brand> doInBackground(Double... coord) {
            LocationAPI api = MockLocationAPI.getInstance();


            return api.getMakerNearby(coord[0], coord[1]);
        }

        @Override
        protected void onPostExecute(List<Brand> brands) {
            onMakersLoaded(brands);
        }
    }
}
