package vd.parkmeapp.presenters;

import java.util.ArrayList;

import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.LoadingScreenActivity;

public class LoadingDataPresenter implements Presenter {
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
}
