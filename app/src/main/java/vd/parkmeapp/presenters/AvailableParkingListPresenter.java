package vd.parkmeapp.presenters;


import com.google.android.gms.maps.model.LatLng;

import vd.parkmeapp.models.CalculateDistance;
import vd.parkmeapp.views.AvailableParkingListActivity;

public class AvailableParkingListPresenter implements Presenter{

    private AvailableParkingListActivity mView;


    public AvailableParkingListPresenter(AvailableParkingListActivity view) {

        mView = view;
    }

    @Override
    public void passMessage(String message) {

        mView.showMessage(message);

    }

    public String calculateDistanceToParking(LatLng usersLocation, double latToParking, double lngToParking){
        CalculateDistance calculateDistance = new CalculateDistance();
        return calculateDistance.distanceFormated(usersLocation.latitude, usersLocation.longitude,
                latToParking,  lngToParking);
    }



}
