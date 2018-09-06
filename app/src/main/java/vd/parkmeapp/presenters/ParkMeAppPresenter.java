package vd.parkmeapp.presenters;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import vd.parkmeapp.models.DataParser;
import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.DirectionsUrl;
import vd.parkmeapp.models.Downloader;
import vd.parkmeapp.models.GetLatLngWithHTTPRequest;
import vd.parkmeapp.models.GetPointsToLocation;
import vd.parkmeapp.models.User;
import vd.parkmeapp.models.LocationRequests;
import vd.parkmeapp.views.ParkMeAppActivity;
import vd.parkmeapp.views.ParkMeAppView;

/**
 * Presenter for main activity
 */

public class ParkMeAppPresenter implements Presenter{

    private LocationRequests locationRequests;
    private ParkMeAppView mView;


    public ParkMeAppPresenter(ParkMeAppView mView, Context context) {

        this.mView = mView;
        locationRequests = new LocationRequests(context, this);

    }

    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }

    public void connectApiClient() {
        locationRequests.connectGoogleApiClient();
    }

    public void disconnectApiClient(){
        locationRequests.disconnectGoogleApiClient();
    }

    public void pauseRequests() {
        locationRequests.cancelLocationRequest();
    }

    public void resumeRequests() {
        locationRequests.customLocationRequest();
    }

    public void setUserLocation(LatLng usersLatLng) {
        mView.setCamera(usersLatLng);
    }

    public LatLng getCurrentLocation(){
       return locationRequests.getDeviceLocation();
    }

    private LatLng getParkingLocation(Context context, String address){
        Log.d("I am here: ", "calling location request");

        return locationRequests.geoLocate(context, address);
    }

    public void routeToParking(Context context, String address){
        LatLng parkingLocation = getParkingLocation(context,address);
        if(parkingLocation == null){
            Log.d("result null: ", "Getting latlng via http request");
            geocodeFailed(new DirectionsUrl(), address, new Downloader(),
                    new DataParser());
        } else {
           getRouteToLocation(parkingLocation);
        }

    }

    public void getRouteToLocation(LatLng parkingLocation){
        LatLng currentLocation = getCurrentLocation();
        DirectionsUrl directionsUrl = new DirectionsUrl();
        String url = directionsUrl.getDirectionsUrl(currentLocation.latitude, currentLocation.longitude,
                parkingLocation.latitude, parkingLocation.longitude);
        new GetPointsToLocation(this, url);
    }

    private void geocodeFailed(DirectionsUrl directionsUrl, String address, Downloader downloader, DataParser dataParser){
        GetLatLngWithHTTPRequest getLatLngWithHTTPRequest
                = new GetLatLngWithHTTPRequest(directionsUrl, address,
                downloader, dataParser,this);
    }

    public void routesReady(ArrayList<LatLng> route){
        mView.addPolyline(route);
    }

    public void moveCameraTo(LatLng location){
        ((ParkMeAppActivity)mView).moveCamera(location,15f);
    }

    public void signOut() {
        DbSingleton.getInstance().signOut();
    }

    public void leaveParking(String uId, User mCurrentUser){
        mCurrentUser.setIsHeRenting("no");
        mCurrentUser.setUsersIdParkingThatHeIsRenting("0");
        mView.updateUser(mCurrentUser);
        DbSingleton.getInstance().setUsersIdParkingThatHeIsRenting("0");
        DbSingleton.getInstance().setValue(uId,"no");
        DbSingleton.getInstance().setIsHeRenting("no");
        mView.showMessage("User left parking");
    }


}