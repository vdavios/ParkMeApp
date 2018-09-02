package vd.parkmeapp.views;

/**
 * Created by vasileiosdavios on 8/5/17.
 */

public interface UserProfileView extends View{

    void emptyUserInfo();

    void setUserInfo(String firstName, String lastName, String email, String password);

    void enableListeners();




}