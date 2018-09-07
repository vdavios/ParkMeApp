package vd.parkmeapp.presenters;

import android.net.ConnectivityManager;

import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.HasInternetAccess;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.WelcomeActivity;

public class WelcomeActivityPresenter implements PresentersForActivitiesThaRequireInternetAccess {
    private WelcomeActivity mView;
    private DbSingleton myDb;


    public WelcomeActivityPresenter(WelcomeActivity view){
        mView = view;
        myDb = DbSingleton.getInstance();
    }

    public void fetchCurrentUserData() {

        myDb.getUser(this);
    }

    public void loadingUsersDataCompleted(User currentUser){
        mView.mainActivity(currentUser);
    }

    public void moveToInternetFailureActivity(){ mView.moveToInternetFailureActivity();}

    public void passMessage(String message) { mView.showMessage(message);}

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
}
