package vd.parkmeapp.Presenters;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import vd.parkmeapp.Models.DbSingleton;
import vd.parkmeapp.Models.User;
import vd.parkmeapp.Models.LocationRequests;
import vd.parkmeapp.Views.ParkMeAppView;

/**
 * Presenter for main activity
 */

public class ParkMeAppPresenter implements Presenter{

    private LocationRequests locationRequests;
    private ParkMeAppView mView;
    private Context context;

    public ParkMeAppPresenter(ParkMeAppView mView, Context context) {

        this.mView = mView;
        this.context = context;
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

    public User getUser() {

       return DbSingleton.getInstance().getUser();
    }


}
