package vd.parkmeapp.presenters;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import vd.parkmeapp.models.DbSingleton;
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

    public void getCurrentLocation(){
        locationRequests.getDeviceLocation();
    }

    public void moveToLocation(Context context, String address){
        locationRequests.geoLocate(context, address);
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