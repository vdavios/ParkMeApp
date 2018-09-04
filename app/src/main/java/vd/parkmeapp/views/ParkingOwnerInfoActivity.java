package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vd.parkmeapp.R;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.presenters.ParkingOwnerInfoPresenter;

public class ParkingOwnerInfoActivity extends AppCompatActivity implements vd.parkmeapp.views.View{
    private Tenant mCurrentUser, parkingOwner;
    private TextView parkingOwnerName, parkingAddress, parkingPrice, distanceFromParking;
    private ArrayList<Tenant> owners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_owner_info);
        mCurrentUser = getIntent().getParcelableExtra("User");
        parkingOwner = getIntent().getParcelableExtra("ParkingOwner");
        owners = getIntent().getParcelableArrayListExtra("Results");
        final ParkingOwnerInfoPresenter parkingOwnerInfoPresenter = new ParkingOwnerInfoPresenter(this, mCurrentUser);
        parkingOwnerName = findViewById(R.id.ParkingOwnerNameText);
        parkingAddress = findViewById(R.id.AddressText);
        parkingPrice = findViewById(R.id.PricePerHourParkingOwnerText);
        distanceFromParking = findViewById(R.id.DistanceText);

        //Testing View
        String name = parkingOwner.getFirstName() + " " + parkingOwner.getLastName();
        parkingOwnerName.setText(name);
        String address = parkingOwner.getStreetName() + " "+ parkingOwner.getHouseNumber();
        parkingAddress.setText(address);
        String price = parkingOwner.getPph() + " "+ "P/h";
        parkingPrice.setText(price);
        String distance = "15 min";
        distanceFromParking.setText(distance);

        Button rentButton = findViewById(R.id.RentParking);
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parkingOwnerInfoPresenter.isNowRented(parkingOwner.getUid());
                Log.d("Going to: ", "ParkMeAppActivity");
                Intent intent = new Intent(ParkingOwnerInfoActivity.this, ParkMeAppActivity.class);
                intent.putExtra("User",mCurrentUser);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ParkingOwnerInfoActivity.this, AvailableParkingListActivity.class);
        intent.putExtra("User", mCurrentUser);
        intent.putExtra("Results", owners);
        startActivity(intent);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();

    }

    public void updateUser(Tenant tenant){
        mCurrentUser = tenant;
    }
}
