package vd.parkmeapp.models;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import vd.parkmeapp.presenters.LoadingParkingPresenter;
import vd.parkmeapp.presenters.ParkMeAppPresenter;
import vd.parkmeapp.presenters.PresentersForActivitiesThaRequireInternetAccess;
import vd.parkmeapp.presenters.WelcomeActivityPresenter;


public class GetLocationWithHTTPRequest implements RequiresDataFromWeb {
    private String url ;
    private JSONObject jsonObject;
    private Downloader myDownloader;
    private DataParser myDataParser;
    private PresentersForActivitiesThaRequireInternetAccess mPresenter;

    public GetLocationWithHTTPRequest(DirectionsUrl directionsUrl, String address, Downloader downloader, DataParser dataParser,
                                      PresentersForActivitiesThaRequireInternetAccess presenter){
        url = directionsUrl.getLocationLatLngUrl(address);
        myDownloader = downloader;
        myDataParser = dataParser;
        mPresenter = presenter;
        DownloadTask downloadTask = new DownloadTask(myDownloader, this);
        downloadTask.execute(url);
    }

    private JSONObject createJSONObject(String url){
        try {
            jsonObject = new JSONObject(url);
            return jsonObject;
        } catch (JSONException e) {
            Log.d("Exception: ", "While creating JSONObject"+e.toString());
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void downloadCompleted(String result) {
        JSONObject jObject = createJSONObject(result);
        LatLng parkingLatLng = myDataParser.getLatLngOfLocation(jObject);
        String presenterClass = mPresenter.getClass().getSimpleName();
        switch (presenterClass) {
            case "WelcomeActivityPresenter":
                ((WelcomeActivityPresenter) mPresenter).latLngFound(parkingLatLng.latitude, parkingLatLng.longitude);
                break;
            case "ParkMeAppPresenter":
                ((ParkMeAppPresenter) mPresenter).fetchData(parkingLatLng);
                break;
            case "LoadingParkingPresenter":
                ((LoadingParkingPresenter)mPresenter).latLngFound(parkingLatLng.latitude, parkingLatLng.longitude);

        }
    }
}
