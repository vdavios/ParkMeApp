package vd.parkmeapp.models;



import android.location.Location;
import android.util.Log;


import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalculateDistance {

    public Float returnDistance(double fromLat, double fromLng, double toLat, double toLng){
        Location fromLocation = new Location("From");
        fromLocation.setLatitude(fromLat);
        fromLocation.setLongitude(fromLng);
        Location toLocation = new Location("To");
        toLocation.setLatitude(toLat);
        toLocation.setLongitude(toLng);

        return fromLocation.distanceTo(toLocation);


    }

    public String distanceFormated(double fromLat, double fromLng, double toLat, double toLng) {
        Float result = returnDistance(fromLat, fromLng, toLat, toLng)/1000;
        DecimalFormat kiloFormat = new DecimalFormat("####.#");
        kiloFormat.setRoundingMode(RoundingMode.CEILING);
        DecimalFormat metersFormat = new DecimalFormat("###");
        metersFormat.setRoundingMode(RoundingMode.CEILING);
        if (result < 1) {
            //It's meters
            String fs = String.valueOf(result);
            Log.d("F: ", fs);
            float k = result * 1000;
            fs = String.valueOf(k);
            Log.d("F: ", fs);
            return (metersFormat.format(k) + " m");
        } else {
            //It's kilometers
            return (kiloFormat.format(result) + " Km");

        }
    }
}
