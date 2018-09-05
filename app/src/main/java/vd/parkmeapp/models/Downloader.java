package vd.parkmeapp.models;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vd.parkmeapp.R;
import vd.parkmeapp.views.ParkMeAppActivity;

public class Downloader {





    public String downloadUrl(String url) {
        String data = "";
        InputStream inputStream= null;
        HttpURLConnection urlConnection = null;
        try {

            //Creates the Url (We access the google location API through an HTTP interface)
            URL mUrl = new URL(url);
            //Creates an HttpURLConnection object
            urlConnection = (HttpURLConnection)mUrl.openConnection();
            //Open a communications link to the resource referenced by the URL
            urlConnection.connect();
            //Returns an InputStream (directions data) from the open connection
            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();


            String line = "";
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }

            data = stringBuilder.toString();
            bufferedReader.close();
            Log.d("Results: ", data);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert inputStream != null;
                inputStream.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return data;
    }
}
