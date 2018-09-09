package vd.parkmeapp.presenters;


import android.net.ConnectivityManager;

import vd.parkmeapp.models.DataParser;
import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.DirectionsUrl;
import vd.parkmeapp.models.Downloader;
import vd.parkmeapp.models.GetLocationWithHTTPRequest;
import vd.parkmeapp.models.HasInternetAccess;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.LoadingParkingActivity;

public class LoadingParkingPresenter implements PresentersForActivitiesThaRequireInternetAccess {
    private LoadingParkingActivity view;
    private User currentUser;

    public LoadingParkingPresenter(LoadingParkingActivity view, User currentUser){

        this.view = view;
        this.currentUser = currentUser;
    }


    public void loadLatLngOfParking() {
        String address = currentUser.getStreetName() + " " + currentUser.getHouseNumber() + " " + currentUser.getPostCode();
        GetLocationWithHTTPRequest getLocationWithHTTPRequest =
                new GetLocationWithHTTPRequest(new DirectionsUrl()
                        , address
                        ,new Downloader()
                        ,new DataParser()
                        ,this);

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
        if(result){
            loadLatLngOfParking();
        } else {
            view.moveToInternetFailureActivity();
        }
    }

    public void latLngFound(double lat, double lng){
        currentUser.setLatOfHisParking(lat);
        currentUser.setLngOfHisParking(lng);
        DbSingleton.getInstance().setLatOfUsersParking(lat);
        DbSingleton.getInstance().setLngOfUsersParking(lng);

        view.parkingWasFound();
    }

    @Override
    public void passMessage(String message) {
        view.showMessage(message);
    }
}
