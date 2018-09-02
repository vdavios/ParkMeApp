package vd.parkmeapp.presenters;


import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.LoginView;


/**
 *
 */

public class LoginPresenter implements Presenter {


    private LoginView mView;
    private DbSingleton myDb;


    public LoginPresenter(LoginView mView) {
        this.mView = mView;
        myDb = DbSingleton.getInstance();

    }


    public void logIn(String email, String password) {
        if (email.isEmpty()) {
            passMessage("Please enter your Email address");
            return;
        }
        if (password.isEmpty()) {
            passMessage("Please enter your password");
            return;
        }
        myDb.signInUser(email,password,this);


    }

    public Boolean isLoggedIn() {
        return myDb.isUserLoggedIn();

    }

    public void userLoggedIn() {
       mView.welcomeActivity();
    }


    public void resumeAuthListener() {
        myDb.setAuthListener();
    }

    public void stopAuthListener() {
        myDb.cancelAuthListener();
    }

    public void logInFailReason(String s) {
        passMessage(s);
    }

    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }
}