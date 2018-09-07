package vd.parkmeapp.views;

public interface UserProfileView extends ActivitiesThatNeedInternetAccess{

    void emptyUserInfo();

    void setUserInfo(String firstName, String lastName, String email, String password);

    void enableListeners();




}