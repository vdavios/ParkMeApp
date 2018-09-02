package vd.parkmeapp.presenters;

import vd.parkmeapp.models.User;
import vd.parkmeapp.views.UserProfileView;

/**
 *
 */

public class UserProfilePresenter implements Presenter{

    private UserProfileView mView;

    public UserProfilePresenter(UserProfileView mView) {

        this.mView = mView;

    }

    public void usersInfo(User tenant) {

        if(tenant.getFirstName() == null || tenant.getLastName() == null
                || tenant.getEmail() == null || tenant.getPassword() == null) {
            mView.emptyUserInfo();
            passMessage("Please check your internet connection");
        } else {
            mView.setUserInfo(tenant.getFirstName(), tenant.getLastName(),
                    tenant.getEmail(), tenant.getPassword());
            mView.enableListeners();
        }
    }


    @Override
    public void passMessage(String message) {
        mView.showMessage(message);

    }
}