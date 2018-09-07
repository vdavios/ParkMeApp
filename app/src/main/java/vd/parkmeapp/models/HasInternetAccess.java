package vd.parkmeapp.models;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import vd.parkmeapp.presenters.PresentersForActivitiesThaRequireInternetAccess;

/**
 * Class that checks if the user device has access to internet
 */
public class HasInternetAccess extends AsyncTask<Object, Integer, Boolean> {

    private ConnectivityManager connectivityManager;
    private PresentersForActivitiesThaRequireInternetAccess presenter;

    @Override
    protected Boolean doInBackground(Object... objects) {
        connectivityManager = (ConnectivityManager)objects[0];
        presenter = (PresentersForActivitiesThaRequireInternetAccess)objects[1];
        HttpsURLConnection urlConnection = null;
        if(isNetWorkAvailable()){
            //Users device can be connected to a network which doesn't have Internet access

            try {
                Log.d("Trying ", "to connect to internet");
                urlConnection =
                        (HttpsURLConnection) (new URL("https://clients3.google.com/generate_204").openConnection());
                urlConnection.setConnectTimeout(10000); //set to 10sec
                urlConnection.connect();
                return (urlConnection.getResponseCode() == 204 &&
                        urlConnection.getContentLength() == 0);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(urlConnection !=null){
                    urlConnection.disconnect();
                }
            }
        } else {
            Log.d("Network not available: ", "true");
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        presenter.connectionResults(result);
    }


    private boolean isNetWorkAvailable(){
        //Checks if the device is connected to a network

        NetworkInfo activeNetworkInfo=null;
        if(connectivityManager !=null){
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if(activeNetworkInfo!=null){
            Log.d("NetWorkAvailable: ", activeNetworkInfo.toString());
        }

        return activeNetworkInfo != null;
    }

}
