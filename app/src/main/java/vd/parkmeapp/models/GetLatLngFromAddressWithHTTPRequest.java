package vd.parkmeapp.models;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vd.parkmeapp.presenters.ParkMeAppPresenter;
import vd.parkmeapp.presenters.PresentersForActivitiesThaRequireInternetAccess;

public class GetLatLngFromAddressWithHTTPRequest implements RequiresDataFromWeb {
    private String url ;
    private JSONObject jsonObject;
    private Downloader myDownloader;
    private DataParser myDataParser;
    private ParkMeAppPresenter mPresenter;

    public GetLatLngFromAddressWithHTTPRequest(DirectionsUrl directionsUrl, String address, Downloader downloader, DataParser dataParser,
                                               ParkMeAppPresenter presenter){

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
    public void downloadCompeted(String result) {
        jsonObject = createJSONObject(result);
        LatLng latLngToParking = myDataParser.getLatLngOfLocation(jsonObject);
        mPresenter.moveCameraTo(latLngToParking);


    }



}
