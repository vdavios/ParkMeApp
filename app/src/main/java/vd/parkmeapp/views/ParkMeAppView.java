package vd.parkmeapp.views;

import com.google.android.gms.maps.model.LatLng;

import vd.parkmeapp.models.User;

/**
 *
 */

public interface ParkMeAppView extends View{


    void setCamera(LatLng mLatLng);
    void updateUser(User tenant);

}