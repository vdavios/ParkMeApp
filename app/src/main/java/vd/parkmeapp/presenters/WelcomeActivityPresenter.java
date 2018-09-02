package vd.parkmeapp.presenters;

import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.WelcomeActivity;

public class WelcomeActivityPresenter {
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
}
