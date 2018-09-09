package vd.parkmeapp.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import vd.parkmeapp.R;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.LoadingParkingPresenter;

public class LoadingParkingActivity extends AppCompatActivity implements ActivitiesThatNeedInternetAccess {

    private LoadingParkingPresenter presenter;
    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_parking);
        currentUser = getIntent().getParcelableExtra("User");
        presenter = new LoadingParkingPresenter(this, currentUser);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Checking for active internet connection
        presenter.activeInternetConnection(connectivityManager);

    }

    @Override
    public void moveToInternetFailureActivity() {
        Intent intent = new Intent(LoadingParkingActivity.this, InternetFailureActivity.class);
        startActivity(intent);

    }

    @Override
    public void hasConnection(Boolean result) {
        presenter.loadLatLngOfParking();
    }

    public void parkingWasFound(){
        Intent intent = new Intent(LoadingParkingActivity.this, AddParkingActivity.class);
        intent.putExtra("User", currentUser);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG)
                .show();
    }
}
