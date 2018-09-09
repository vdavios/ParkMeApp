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
    private TextView parkingLocation;
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
        parkingLocation = findViewById(R.id.ParkingLocationText);
        mPresenter.setUsersInfo();
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
    public void setParkingInfo(String locationAddress) {
        parkingLocation.setText(locationAddress);

    }
    @Override
    public void enableListeners() {
       parkingLocation.setOnClickListener(this);
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
        Intent intent = new Intent(AddParkingActivity.this, EditParkingInfoActivity.class);
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
