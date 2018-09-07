package vd.parkmeapp.views;

interface ActivitiesThatNeedInternetAccess extends View{

    void moveToInternetFailureActivity();
    void hasConnection(Boolean result);
}
