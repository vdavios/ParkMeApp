package vd.parkmeapp.views;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import vd.parkmeapp.models.User;


public interface ParkMeAppView extends ActivitiesThatNeedInternetAccess{


    void setCamera(LatLng mLatLng);
    void updateUser(User tenant);
    void addPolyline(ArrayList<LatLng> points);
    void moveCamera(LatLng latLng, float zoom);

}