package vd.parkmeapp.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import vd.parkmeapp.R;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.ParkingOwnerInfoPresenter;

public class ParkingOwnerInfoActivity extends AppCompatActivity implements ParkingOwnerInfoView{
    private User mCurrentUser, parkingOwner;
    private TextView parkingOwnerName, parkingAddress, parkingPrice, distanceFromParking;
    private ArrayList<Tenant> owners;
    private ParkingOwnerInfoPresenter parkingOwnerInfoPresenter;
    private ConnectivityManager connectivityManager;
    private LatLng usersCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_owner_info);
        //Getting data that are passed through the activities
        mCurrentUser = getIntent().getParcelableExtra("User");
        parkingOwner = getIntent().getParcelableExtra("ParkingOwner");
        owners = getIntent().getParcelableArrayListExtra("Results");
        usersCurrentLocation = getIntent().getParcelableExtra("UsersLocation");
        parkingOwnerInfoPresenter = new ParkingOwnerInfoPresenter(this, mCurrentUser);

        //Creating a connectivity manager
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        parkingOwnerName = findViewById(R.id.ParkingOwnerNameText);
        parkingAddress = findViewById(R.id.AddressText);
        parkingPrice = findViewById(R.id.PricePerHourParkingOwnerText);
        distanceFromParking = findViewById(R.id.DistanceText);

        parkingOwnerInfoPresenter.parkingOwnersInfo(parkingOwner);

        Button rentButton = findViewById(R.id.RentParking);
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               parkingOwnerInfoPresenter.activeInternetConnection(connectivityManager);
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ParkingOwnerInfoActivity.this, AvailableParkingListActivity.class);
        intent.putExtra("User", mCurrentUser);
        intent.putExtra("Results", owners);
        intent.putExtra("UsersLocation", usersCurrentLocation);
        startActivity(intent);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();

    }

    public void updateUser(User tenant){
        mCurrentUser = tenant;
    }

    @Override
    public void moveToInternetFailureActivity() {
        Intent intent = new Intent(ParkingOwnerInfoActivity.this,
                InternetFailureActivity.class);
        startActivity(intent);
    }

    @Override
    public void hasConnection(Boolean result) {
        if(result){
            parkingOwnerInfoPresenter.isNowRented(parkingOwner);
            Log.d("Going to: ", "ParkMeAppActivity");
            Intent intent = new Intent(ParkingOwnerInfoActivity.this, ParkMeAppActivity.class);
            intent.putExtra("User",mCurrentUser);
            startActivity(intent);
        } else {
            moveToInternetFailureActivity();
        }
    }

    @Override
    public void setParkingOwnerInfo(String name, String address, String distance, String pricePerHour) {
        parkingOwnerName.setText(name);
        parkingAddress.setText(address);
        String distanceToParking = parkingOwnerInfoPresenter.calculateDistanceToParking(usersCurrentLocation,
                parkingOwner.getLatOfHisParking(), parkingOwner.getLngOfHisParking());
        distanceFromParking.setText(distanceToParking);
        parkingPrice.setText(pricePerHour);
    }
}
