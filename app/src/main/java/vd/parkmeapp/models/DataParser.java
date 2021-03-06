package vd.parkmeapp.models;





import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Parses the JSONObject that we received as response from google directions API
 */
public class DataParser {

    public  List<List<HashMap<String,String>>> parse(JSONObject jsonObject){
        List<List<HashMap<String,String>>> routes = new ArrayList<>();
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jsonObject.getJSONArray("routes");

            //Travers all routes
            for(int i=0; i<jRoutes.length(); i++){
                jLegs = ((JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String,String>>();

                //Travers all legs
                for(int j=0; j<jLegs.length(); j++){
                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                    //Traversing all steps
                    for(int k=0; k<jSteps.length(); k++){
                        String polyline;
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> decodedPolylinePoints = decodePoly(polyline);

                        //Travers all points
                        for(int l=0; l<decodedPolylinePoints.size(); l++){
                            HashMap<String, String> latLngHm = new HashMap<>();
                            latLngHm.put("lat", Double.toString((decodedPolylinePoints.get(l)).latitude));
                            latLngHm.put("lng", Double.toString((decodedPolylinePoints.get(l)).longitude));
                            path.add(latLngHm);
                        }
                    }
                    routes.add(path);
                }
            }
        } catch (JSONException e) {


            e.printStackTrace();
        }
        return routes;
    }

    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public LatLng getLatLngOfLocation(JSONObject jsonObject){
        Double lat = 0d;
        Double lng = 0d;
        try {

            // JSON -> Results -> Geometry -> Location (lat, lng)

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");
            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new LatLng(lat,lng);
    }

}
