package vd.parkmeapp.presenters;

import android.net.ConnectivityManager;

import java.util.ArrayList;

import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.HasInternetAccess;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.LoadingScreenActivity;

public class LoadingDataPresenter implements PresentersForActivitiesThaRequireInternetAccess {
    private LoadingScreenActivity mLds;
    private User currentUser;

    public LoadingDataPresenter(LoadingScreenActivity lds, User tenant){
        mLds = lds;
        currentUser = tenant;
    }
    @Override
    public void passMessage(String message) {
        mLds.showMessage(message);
    }


    public void getParkingList(){
        DbSingleton myDb = DbSingleton.getInstance();
        myDb.fetchData(this,currentUser);

    }

    public void resultsLoaded(ArrayList<Tenant> results){
        mLds.dataLoaded(results);
    }

    public void moveToInternetFailureActivity(){ mLds.moveToInternetFailureActivity();}

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
        mLds.hasConnection(result);
    }
}
