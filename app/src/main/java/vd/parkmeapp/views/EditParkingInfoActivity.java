package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import vd.parkmeapp.R;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.EditParkingInfoPresenter;

public class EditParkingInfoActivity extends AppCompatActivity implements View.OnClickListener, vd.parkmeapp.views.View{
    private TextView streetNameText;
    private TextView houseNumberText;
    private TextView postCodeText;
    private TextView pricePerHourText;
    private User myTenant;
    private Button addParkingButton;
    private EditParkingInfoPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parking_info);
        myTenant = getIntent().getParcelableExtra("User");
        streetNameText = (findViewById(R.id.StreetNameText));
        houseNumberText = (findViewById(R.id.HouseNumberText));
        postCodeText = (findViewById(R.id.PostCodeText));
        pricePerHourText = findViewById(R.id.PricePerHourText);
        addParkingButton = findViewById(R.id.AddParking);
        mPresenter = new EditParkingInfoPresenter(myTenant,this);
        mPresenter.setUsersInfo();


        addParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingActivity();
            }
        });
    }


    public void loadingActivity(){
        Intent intent = new Intent(EditParkingInfoActivity.this, LoadingParkingActivity.class);
        intent.putExtra("User", myTenant);
        startActivity(intent);
    }

    public void setParkingInfo(String streetName, String houseNumber, String postCode, String pph) {
        streetNameText.setText(streetName);
        houseNumberText.setText(houseNumber);
        postCodeText.setText(postCode);
        pricePerHourText.setText(pph);
    }

    public void enableListeners(){
        streetNameText.setOnClickListener(this);
        houseNumberText.setOnClickListener(this);
        postCodeText.setOnClickListener(this);
        pricePerHourText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int rId = view.getId();
        Intent intent = new Intent(EditParkingInfoActivity.this, EditUserInfoActivity.class);
        intent.putExtra("R.id", rId);
        intent.putExtra("User", myTenant);
        intent.putExtra("caller", "EditParkingInfoActivity");
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        loadingActivity();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message,Toast.LENGTH_LONG)
                .show();
    }
}
