package vd.parkmeapp.presenters;

import android.net.ConnectivityManager;

public interface PresentersForActivitiesThaRequireInternetAccess extends Presenter{

    void activeInternetConnection(ConnectivityManager connectivityManager);
    void connectionResults(Boolean result);
}
