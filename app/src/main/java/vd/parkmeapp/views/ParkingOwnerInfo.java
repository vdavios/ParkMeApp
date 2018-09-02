package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vd.parkmeapp.R;
import vd.parkmeapp.models.User;

public class ParkingOwnerInfo extends AppCompatActivity {
    private User mCurrentUser, parkingOwner;
    private TextView parkingOwnerName, parkingAddress, parkingPrice, distanceFromParking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_owner_info);
        mCurrentUser = getIntent().getParcelableExtra("User");
        parkingOwner = getIntent().getParcelableExtra("ParkingOwner");

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

        Button rentButton = findViewById(R.id.RentParking);
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParkingOwnerInfo.this, ParkMeAppActivity.class);
                intent.putExtra("User",mCurrentUser);
                startActivity(intent);
            }
        });

    }
}
