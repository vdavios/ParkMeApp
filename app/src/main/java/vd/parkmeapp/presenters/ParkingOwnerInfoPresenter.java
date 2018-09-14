package vd.parkmeapp.presenters;



import android.net.ConnectivityManager;

import com.google.android.gms.maps.model.LatLng;

import vd.parkmeapp.models.CalculateDistance;
import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.HasInternetAccess;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.ParkingOwnerInfoActivity;

public class ParkingOwnerInfoPresenter implements PresentersForActivitiesThaRequireInternetAccess{
    private ParkingOwnerInfoActivity mView;
    private User currentUser;


    public ParkingOwnerInfoPresenter(ParkingOwnerInfoActivity view, User currentUser){
        mView = view;
        this.currentUser = currentUser;
    }

    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }

    public void isNowRented(User parkingOwner){
        String parkingOwnerId = parkingOwner.getUid();
        double parkingOwnerLat = parkingOwner.getLatOfHisParking();
        double parkingOwnerLng = parkingOwner.getLngOfHisParking();
        currentUser.setIsHeRenting("yes");
        currentUser.setUsersIdParkingThatHeIsRenting(parkingOwnerId);
        currentUser.setLatOfParkingThatHeIsCurrentlyRenting(parkingOwnerLat);
        currentUser.setLngOfParkingThatHeIsCurrentlyRenting(parkingOwnerLng);
        mView.updateUser(currentUser);
        DbSingleton.getInstance().setLatOfTheParkingThatHeIsCurrentlyRenting(parkingOwnerLat);
        DbSingleton.getInstance().setLngOfTheParkingThatHeIsCurrentlyRenting(parkingOwnerLng);
        DbSingleton.getInstance().setUsersIdParkingThatHeIsRenting(parkingOwnerId);
        DbSingleton.getInstance().setParkingAvailableToRent(parkingOwnerId,"yes");
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

    public void parkingOwnersInfo(User parkingOwner){
        String name = parkingOwner.getFirstName() + " " + parkingOwner.getLastName();
        String address = parkingOwner.getStreetName() + " "+ parkingOwner.getHouseNumber();
        String price = parkingOwner.getPph() + " €/h";
        String distance = "15 min";
        mView.setParkingOwnerInfo(name,address,distance,price);
    }

    public String calculateDistanceToParking(LatLng usersLocation, double latToParking, double lngToParking){
        CalculateDistance calculateDistance = new CalculateDistance();
        return calculateDistance.distanceFormatted(usersLocation.latitude, usersLocation.longitude,
                latToParking,  lngToParking);
    }
}
