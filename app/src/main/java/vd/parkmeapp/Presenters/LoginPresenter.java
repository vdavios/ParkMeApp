package vd.parkmeapp.Presenters;


import vd.parkmeapp.Models.DbSingleton;
import vd.parkmeapp.Views.LoginView;


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

    public void isLoggedIn() {
       if(myDb.isUserLoggedIn()){
           mView.mainActivity();
       }

    }

    public void userLoggedIn() {

       mView.mainActivity();
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
