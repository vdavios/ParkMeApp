package vd.parkmeapp.models;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vd.parkmeapp.presenters.ParkMeAppPresenter;


public class GetPointsToLocation implements RequiresDataFromWeb{

    private ParkMeAppPresenter parkMeAppPresenter;

    public GetPointsToLocation(ParkMeAppPresenter mParkMeAppPresenter,
                               String url){
        parkMeAppPresenter = mParkMeAppPresenter;
        DownloadTask downloadTask = new DownloadTask(new Downloader(),this);
        downloadTask.execute(url);
    }


    @Override
    public void downloadCompleted(String result){
        ParserTask parserTask = new ParserTask();
        parserTask.execute(result);
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>>{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(jsonData[0]);
                DataParser dataParser = new DataParser();
                routes = dataParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String,String>>>  results){
            ArrayList<LatLng> points;
            PointsCreator pointsCreator = new PointsCreator();
            //Traversing through all the routes
            for(int i=0; i<results.size(); i++){
                points = new ArrayList<>();
                List<HashMap<String,String>> path = results.get(i);

                //Fetching all the points in i-th route
                for (int j=0; j<path.size();j++){
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat,lng);
                    points.add(position);

                }
                pointsCreator.addPoints(points);


            }
            parkMeAppPresenter.routesReady(pointsCreator.getPoints());
        }
    }

    private class PointsCreator{
        private ArrayList<LatLng> al = new ArrayList<>();
        public void addPoints(ArrayList<LatLng> points){

            al.addAll(points);

        }

        public ArrayList<LatLng> getPoints(){
            return al;
        }
    }
}
