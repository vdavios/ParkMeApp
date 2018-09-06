package vd.parkmeapp.models;

public class DirectionsUrl {

    private String latitude ;
    private String longitude ;
    private String endLatitude ;
    private String endLongitude ;

    private static final String KEY = "AIzaSyD9N369yrrRunarmGgyuY-Qo3a1wPoLNcY";
    private static final String GEOKEY = "AIzaSyDiawE-Dcq1wNpyGjjv83fDDleft2k7HDQ";

    public String getDirectionsUrl(Double startLatitude, Double startLongitude, Double endLat, Double endLong){
        latitude = startLatitude.toString();
        longitude = startLongitude.toString();
        endLatitude = endLat.toString();
        endLongitude = endLong.toString();
        return "https://maps.googleapis.com/maps/api/directions/json?" + "origin=" + latitude + "," + longitude +
                "&destination=" + endLatitude + "," + endLongitude +
                "&key=" + KEY;
    }


    public String getLocationLatLngUrl(String address){


        return "https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key="+GEOKEY;
    }
}
