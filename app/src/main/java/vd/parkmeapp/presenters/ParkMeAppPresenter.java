package vd.parkmeapp.presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


import vd.parkmeapp.models.CalculateDistance;
import vd.parkmeapp.models.DataParser;
import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.DirectionsUrl;
import vd.parkmeapp.models.Downloader;
import vd.parkmeapp.models.FilterDataImpl;
import vd.parkmeapp.models.GetLocationWithHTTPRequest;
import vd.parkmeapp.models.GetPointsToLocation;
import vd.parkmeapp.models.HasInternetAccess;
import vd.parkmeapp.models.User;
import vd.parkmeapp.models.LocationRequests;
import vd.parkmeapp.views.ParkMeAppView;

/**
 * Presenter for main activity
 */

public class ParkMeAppPresenter implements PresentersForActivitiesThaRequireInternetAccess{

    private LocationRequests locationRequests;
    private ParkMeAppView mView;
    private User mCurrentUser;
    private ConnectivityManager connectivityManager;
    private LatLng locationWeAreLookingFor;



    public ParkMeAppPresenter(ParkMeAppView mView, Context context, User currentUser,
                              ConnectivityManager connectivityManager) {

        this.mView = mView;
        locationRequests = new LocationRequests(context, this);
        mCurrentUser = currentUser;
        this.connectivityManager = connectivityManager;

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
        if(locationRequests.isApiClientConnected()){
            Log.d("Pausing ", " Location updates");
            locationRequests.cancelLocationRequest();
        }

    }

    public void resumeRequests() {
        if(locationRequests.isApiClientConnected()){
            Log.d("Resuming ", " Location updates");
            locationRequests.customLocationRequest();
        } else {
            connectApiClient();
        }

    }

    public void setUserLocation(LatLng usersLatLng) {
        mView.setCamera(usersLatLng);
    }

    public void findLocation(Context context, String address){
        Log.d("I am here: ", "calling location request");
        LatLng parkingLocation = locationRequests.geoLocate(context, address);
        if(parkingLocation == null){
            geocodeFailed(new DirectionsUrl(), address, new Downloader(), new DataParser());
        } else {
            fetchData(parkingLocation);
        }
    }
    private void geocodeFailed(DirectionsUrl directionsUrl, String address, Downloader downloader, DataParser dataParser){
        GetLocationWithHTTPRequest GetLocationWithHTTPRequest
                = new GetLocationWithHTTPRequest(directionsUrl, address,
                downloader, dataParser,this);
    }
    public LatLng usersLocation(){return locationRequests.getDeviceLocation();}

    public void getRouteToLocation(double latToTheParkingThatHeIsRenting, double lngToTheParkingThatHeIsRenting){

        Log.d("Requesting ","route to the parking that the user is renting" );
        LatLng usersLocation = usersLocation();
        DirectionsUrl directionsUrl = new DirectionsUrl();
        String url = directionsUrl.getDirectionsUrl(usersLocation.latitude, usersLocation.longitude,
                latToTheParkingThatHeIsRenting, lngToTheParkingThatHeIsRenting);
        new GetPointsToLocation(this, url);

    }

    public void routesReady(ArrayList<LatLng> route){
        mView.addPolyline(route);
    }

    public void signOut() {
        DbSingleton.getInstance().signOut();
    }

    public void leaveParking(String uId, User mCurrentUser){
        mCurrentUser.setIsHeRenting("no");
        mCurrentUser.setUsersIdParkingThatHeIsRenting("0");
        mCurrentUser.setLatOfParkingThatHeIsCurrentlyRenting(0d);
        mCurrentUser.setLngOfParkingThatHeIsCurrentlyRenting(0d);
        mView.updateUser(mCurrentUser);
        DbSingleton.getInstance().setUsersIdParkingThatHeIsRenting("0");
        DbSingleton.getInstance().setParkingAvailableToRent(uId,"no");
        DbSingleton.getInstance().setIsHeRenting("no");
        DbSingleton.getInstance().setLatOfTheParkingThatHeIsCurrentlyRenting(0d);
        DbSingleton.getInstance().setLngOfTheParkingThatHeIsCurrentlyRenting(0d);
        mView.showMessage("User left parking");
    }


    @Override
    public void activeInternetConnection(ConnectivityManager connectivityManager) {

        HasInternetAccess hasInternetAccess = new HasInternetAccess();
        Object objects[] = new Object[2];
        objects[0] = connectivityManager;
        objects[1] = this;
        hasInternetAccess.execute(objects);

    }

    @Override
    public void connectionResults(Boolean result) {

        mView.hasConnection(result);

    }

    public void fetchData(LatLng location){
        locationWeAreLookingFor = location;
        DbSingleton.getInstance().fetchAvailableParkingNearUsersLocation(this,mCurrentUser,location, new CalculateDistance(), new FilterDataImpl());
    }

    public void parkingSpaceNearTheSelectedLocationResults(ArrayList<User> parkingOwner){
        ArrayList<LatLng> parkingArrayList= new ArrayList<>();
        for(User pOwner: parkingOwner){
            Log.d("Parking owner name: ", pOwner.getFirstName());
            parkingArrayList.add(new LatLng(pOwner.getLatOfHisParking(), pOwner.getLngOfHisParking()));
        }

        mView.moveCamera(locationWeAreLookingFor, 15f, parkingArrayList, parkingOwner);
    }

    public void apiConnected() {
        if(mCurrentUser.getIsHeRenting().equals("yes")){
            activeInternetConnection(connectivityManager);
        }
    }
}