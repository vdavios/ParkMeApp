package vd.parkmeapp.presenters;



import android.net.ConnectivityManager;

import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.HasInternetAccess;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.views.ParkingOwnerInfoActivity;

public class ParkingOwnerInfoPresenter implements PresentersForActivitiesThaRequireInternetAccess{
    private ParkingOwnerInfoActivity mView;
    private Tenant currentUser;


    public ParkingOwnerInfoPresenter(ParkingOwnerInfoActivity view, Tenant currentUser){
        mView = view;
        this.currentUser = currentUser;
    }

    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }

    public void isNowRented(String ownerId, String parkingAddress){
        currentUser.setIsHeRenting("yes");
        currentUser.setUsersIdParkingThatHeIsRenting(ownerId);
        currentUser.setAddressOfTheParkingThatHeIsCurrentlyRenting(parkingAddress);
        mView.updateUser(currentUser);
        DbSingleton.getInstance().setAddressOfTheParkingThatHeIsCurrentlyRenting(parkingAddress);
        DbSingleton.getInstance().setUsersIdParkingThatHeIsRenting(ownerId);
        DbSingleton.getInstance().setValue(ownerId,"yes");
        DbSingleton.getInstance().setIsHeRenting("yes");
        mView.showMessage("Is rented now");
    }

    @Override
    public void activeInternetConnection(ConnectivityManager connectivityManager) {
        HasInternetAccess hasInternetAccess = new HasInternetAccess();
        Object objects[] = new Object[2];
        objects[0] = connectivityManager;
        objects[1] = this;
        hasInternetAccess.execute(objects);
    }

    @Override
    public void connectionResults(Boolean result) {
        mView.hasConnection(result);
    }

    public void parkingOwnersInfo(Tenant parkingOwner){
        String name = parkingOwner.getFirstName() + " " + parkingOwner.getLastName();
        String address = parkingOwner.getStreetName() + " "+ parkingOwner.getHouseNumber();
        String price = parkingOwner.getPph() + " P/h";
        String distance = "15 min";
        mView.setParkingOwnerInfo(name,address,distance,price);
    }
}
