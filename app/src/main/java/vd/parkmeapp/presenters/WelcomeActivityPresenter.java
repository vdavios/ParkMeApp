package vd.parkmeapp.presenters;

import android.net.ConnectivityManager;
import android.util.Log;

import vd.parkmeapp.models.DataParser;
import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.DirectionsUrl;
import vd.parkmeapp.models.Downloader;
import vd.parkmeapp.models.GetLocationWithHTTPRequest;
import vd.parkmeapp.models.HasInternetAccess;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.WelcomeActivity;

public class WelcomeActivityPresenter implements PresentersForActivitiesThaRequireInternetAccess {
    private WelcomeActivity mView;
    private DbSingleton myDb;
    private User currentUser;


    public WelcomeActivityPresenter(WelcomeActivity view){
        mView = view;
        myDb = DbSingleton.getInstance();
    }

    public void fetchCurrentUserData() {

        myDb.getUser(this);
    }

    public void loadingUsersDataCompleted(User currentUser){
        Log.d("Loading user: ", "completed");
        mView.loadingFinishedWithSuccess(currentUser);
    }

    public void passMessage(String message) { mView.showMessage(message);}

    @Override
    public void activeInternetConnection(ConnectivityManager connectivityManager) {
        HasInternetAccess hasInternetAccess = new HasInternetAccess();
        Object objects[] = new Object[2];
        objects[0] = connectivityManager;
        objects[1] = this;
        hasInternetAccess.execute(objects);
    }

    public void setLatLng(User currentUser){
        this.currentUser = currentUser;
        String address = currentUser.getStreetName()+ " "
                + currentUser.getHouseNumber()+ " "
                + currentUser.getPostCode();
        if(currentUser.getLatOfHisParking() == 0d && currentUser.getLngOfHisParking() == 0d){
            GetLocationWithHTTPRequest getParkingLocationWithHTTPRequest =
                    new GetLocationWithHTTPRequest(new DirectionsUrl(),
                            address,
                            new Downloader(),
                            new DataParser(),
                            this);
        } else {
            mView.mainActivity(currentUser);
        }
    }

    public void latLngFound(double lat, double lng){
        currentUser.setLatOfHisParking(lat);
        currentUser.setLngOfHisParking(lng);
        myDb.setLatOfUsersParking(lat);
        myDb.setLngOfUsersParking(lng);
        mView.mainActivity(currentUser);
    }

    @Override
    public void connectionResults(Boolean result) {
        mView.hasConnection(result);
    }
}
