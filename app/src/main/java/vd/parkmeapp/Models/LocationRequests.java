package vd.parkmeapp.Models;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;



import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import vd.parkmeapp.Presenters.ParkMeAppPresenter;
import vd.parkmeapp.Presenters.Presenter;


/**
 *
 */

public class LocationRequests implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private Presenter presenter;

    public LocationRequests(Context mContext, Presenter presenter){
        this.mContext = mContext;
        this.presenter = presenter;
        buildGoogleApiClient();
    }


    private synchronized void buildGoogleApiClient() {

        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    public void disconnectGoogleApiClient() {

        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

    }

    public void connectGoogleApiClient() {
        if(!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    public void customLocationRequest(){
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(mGoogleApiClient.isConnected()) {
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
        presenter.passMessage("edw");
        LatLng mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        ((ParkMeAppPresenter)presenter).setUserLocation(mLatLng);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        customLocationRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
