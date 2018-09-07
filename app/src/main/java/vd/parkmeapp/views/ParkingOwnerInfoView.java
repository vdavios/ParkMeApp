package vd.parkmeapp.views;

public interface ParkingOwnerInfoView extends ActivitiesThatNeedInternetAccess{


    void setParkingOwnerInfo(String name, String address, String distance, String pricePerHour);
}
