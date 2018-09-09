package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import vd.parkmeapp.R;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.AddParkingPresenter;


public class AddParkingActivity extends AppCompatActivity implements AddParkingView, View.OnClickListener{
    private TextView streetNameText;
    private TextView houseNumberText;
    private TextView postCodeText;
    private TextView pricePerHourText;
    private User myTenant;
    private AddParkingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking);


        if(savedInstanceState!=null){
           myTenant = savedInstanceState.getParcelable("User");
        } else {
            myTenant = getIntent().getParcelableExtra("User");
        }
        mPresenter = new AddParkingPresenter(this,myTenant);
        streetNameText = (findViewById(R.id.StreetNameText));
        houseNumberText = (findViewById(R.id.HouseNumberText));
        postCodeText = (findViewById(R.id.PostCodeText));
        pricePerHourText = findViewById(R.id.PricePerHourText);
        mPresenter.usersInfo();
        Button mParkingButton = findViewById(R.id.RentMyParking);
        if(mPresenter.hasSetParkingInfo()){

            //Use has set his parking info make button visible
            mParkingButton.setVisibility(View.VISIBLE);
            //Check if the user made it available to rent
            if(myTenant.getHasParking().equals("no")){
                mParkingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mPresenter.parkingAvailableToRent();
                        //Jumping to main activity
                        Intent intent = new Intent(AddParkingActivity.this
                                , ParkMeAppActivity.class);
                        intent.putExtra("User",myTenant);
                        startActivity(intent);


                    }
                });
            } else {
                mParkingButton.setBackgroundColor(getResources().getColor(R.color.red));
                mParkingButton.setText(R.string.RemoveUsersParkingFromAvailableParkingList);
                mParkingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Removes his parking: ", "true");
                        mPresenter.parkingNotAvailableToRent();
                        Intent intent = new Intent(AddParkingActivity.this, ParkMeAppActivity.class);
                        intent.putExtra("User", myTenant);
                        startActivity(intent);
                    }
                });
            }
        }


    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setParkingInfo(String streetName, String houseNumber, String postCode, String pph) {
        streetNameText.setText(streetName);
        houseNumberText.setText(houseNumber);
        postCodeText.setText(postCode);
        pricePerHourText.setText(pph);
    }
    @Override
    public void enableListeners() {
        streetNameText.setOnClickListener(this);
        houseNumberText.setOnClickListener(this);
        postCodeText.setOnClickListener(this);
        pricePerHourText.setOnClickListener(this);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddParkingActivity.this, ParkMeAppActivity.class);
        intent.putExtra("User", myTenant);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int rId = view.getId();
        Intent intent = new Intent(AddParkingActivity.this, EditUserInfoActivity.class);
        intent.putExtra("R.id", rId);
        intent.putExtra("User", myTenant);
        intent.putExtra("caller", "AddParkingActivity");
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("User",myTenant);
        super.onSaveInstanceState(outState);
    }
}
