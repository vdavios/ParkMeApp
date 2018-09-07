package vd.parkmeapp.presenters;

import android.net.ConnectivityManager;

import vd.parkmeapp.models.HasInternetAccess;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.UserProfileView;

/**
 *
 */

public class UserProfilePresenter implements PresentersForActivitiesThaRequireInternetAccess{

    private UserProfileView mView;

    public UserProfilePresenter(UserProfileView mView) {

        this.mView = mView;

    }

    public void usersInfo(User tenant) {

        mView.setUserInfo(tenant.getFirstName(), tenant.getLastName(),
                tenant.getEmail(), tenant.getPassword());
        mView.enableListeners();

    }


    @Override
    public void passMessage(String message) {
        mView.showMessage(message);

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
}