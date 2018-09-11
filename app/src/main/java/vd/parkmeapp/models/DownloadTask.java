package vd.parkmeapp.models;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadTask extends AsyncTask<String, Void, String> {

    private Downloader downloader;
    private RequiresDataFromWeb requiresDataFromWeb;

    public DownloadTask(Downloader downloader,RequiresDataFromWeb requiresDataFromWeb){
        this.downloader = downloader;
        this.requiresDataFromWeb = requiresDataFromWeb;
    }
    @Override
    protected String doInBackground(String... url) {
        Log.d("DownloadTask:", " true");
        String data = "";
        data = downloader.downloadUrl(url[0]);
        return data;
    }

    @Override
    protected void onPostExecute(String result){

        requiresDataFromWeb.downloadCompleted(result);
    }
}
