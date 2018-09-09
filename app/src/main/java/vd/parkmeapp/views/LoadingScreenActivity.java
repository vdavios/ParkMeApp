package vd.parkmeapp.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import vd.parkmeapp.R;
import vd.parkmeapp.models.CalculateDistance;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.LoadingDataPresenter;

public class LoadingScreenActivity extends AppCompatActivity implements ActivitiesThatNeedInternetAccess {
    private LoadingDataPresenter mPresenter;
    private User currentUser;
    private LatLng currentUserLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        currentUser = getIntent().getParcelableExtra("User");
        currentUserLocation = getIntent().getParcelableExtra("UsersLocation");

        mPresenter = new LoadingDataPresenter(this,currentUser);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Checking for active internet connection
        mPresenter.activeInternetConnection(connectivityManager);

    }



    public void dataLoaded(ArrayList<Tenant> results){
        Intent intent = new Intent(LoadingScreenActivity.this, AvailableParkingListActivity.class);
        intent.putExtra("Results", results);
        intent.putExtra("User",currentUser);
        intent.putExtra("UsersLocation",currentUserLocation);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    public void moveToInternetFailureActivity(){
        Intent intent = new Intent(LoadingScreenActivity.this,
                InternetFailureActivity.class);
        startActivity(intent);
    }

    @Override
    public void hasConnection(Boolean result) {
        if(result){
            mPresenter.getParkingList(currentUserLocation);
        } else {
            moveToInternetFailureActivity();
        }
    }
}
