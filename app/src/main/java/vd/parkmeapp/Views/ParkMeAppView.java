package vd.parkmeapp.Views;

import com.google.android.gms.maps.model.LatLng;

/**
 *
 */

public interface ParkMeAppView {

    void showMessage(String message);
    void setCamera(LatLng mLatLng);

}
