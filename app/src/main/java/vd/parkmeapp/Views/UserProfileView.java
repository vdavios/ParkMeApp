package vd.parkmeapp.Views;

/**
 * Created by vasileiosdavios on 8/5/17.
 */

public interface UserProfileView {

    void emptyUserInfo();

    void setUserInfo(String firstName, String lastName, String email, String password);

    void enableListeners();

    void showMessage(String s);


}