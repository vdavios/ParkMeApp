package vd.parkmeapp.models;

public class DirectionsUrl {


    private static final String KEY = "AIzaSyD9N369yrrRunarmGgyuY-Qo3a1wPoLNcY";

    public String getDirectionsUrl(Double startLatitude, Double startLongitude, Double endLat, Double endLong){
        String latitude = startLatitude.toString();
        String longitude = startLongitude.toString();
        String endLatitude = endLat.toString();
        String endLongitude = endLong.toString();
        return "https://maps.googleapis.com/maps/api/directions/json?" + "origin=" + latitude + "," + longitude +
                "&destination=" + endLatitude + "," + endLongitude +
                "&key=" + KEY;
    }
}
