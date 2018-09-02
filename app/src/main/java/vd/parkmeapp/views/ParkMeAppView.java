package vd.parkmeapp.views;

import com.google.android.gms.maps.model.LatLng;

/**
 *
 */

public interface ParkMeAppView extends View{


    void setCamera(LatLng mLatLng);

}