package vd.parkmeapp.models;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vd.parkmeapp.presenters.ParkMeAppPresenter;
import vd.parkmeapp.presenters.Presenter;

/**
 *
 */

public class LocationRequests implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private ParkMeAppPresenter presenter;

    public LocationRequests(Context mContext, ParkMeAppPresenter presenter){
        this.mContext = mContext;
        this.presenter = presenter;
        buildGoogleApiClient();
    }


    private synchronized void buildGoogleApiClient() {
        Log.d("Building ", "Google Api client");

        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("Google Api Client ", "connected");
        customLocationRequest();
        presenter.apiConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void disconnectGoogleApiClient() {

        if(isApiClientConnected()) {
            mGoogleApiClient.disconnect();
        }

    }

    public void connectGoogleApiClient() {
        if(!isApiClientConnected()) {
            mGoogleApiClient.connect();
        }
    }

    public boolean isApiClientConnected(){
        return mGoogleApiClient.isConnected();
    }

    public void customLocationRequest(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        Log.d("Location request: ", " Requesting location updates");
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(mGoogleApiClient.isConnected()){
                LocationServices.FusedLocationApi
                        .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }

        }
    }

    public void cancelLocationRequest(){
        if(mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("Location changed: ", " True");
        (presenter).setUserLocation(mLatLng);

    }


    //Last known Location
    public LatLng getDeviceLocation(){

        if(ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Location currentLocation =   LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


            Log.d("Get device location: ", "I am here");
            double lat = currentLocation.getLatitude();
            String latitude = Double.toString(lat);
            double lon = currentLocation.getLongitude();
            String longitude = Double.toString(lon);
            return new LatLng(lat,lon);
        } else {

            return null;
        }

    }




    //Returns the LatLng object of the desired location
    public LatLng geoLocate(Context context, String address){
        Geocoder geocoder = new Geocoder(context);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(address, 1);
        }catch (IOException e){
            Log.e("Test", "geoLocate: IOException: " + e.getMessage() );

        }
        if(list.size() > 0){
            Address addrss = list.get(0);

            Log.d("Test", "geoLocate: found a location: " + addrss.toString());


            return new LatLng(addrss.getLatitude(),addrss.getLongitude());

        } else {
            Log.d("In else statement: ", "Returning null");


            return null;
        }

    }


}