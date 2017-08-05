package vd.parkmeapp.Views;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import vd.parkmeapp.Models.User;
import vd.parkmeapp.R;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView accountView, paymentView;
    private User myTenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        accountView = (TextView)(findViewById(R.id.account));
        paymentView = (TextView)(findViewById(R.id.payment));
        myTenant = getIntent().getParcelableExtra("User");

        accountView.setOnClickListener(this);
        paymentView.setOnClickListener(this);


    }

    private void userProfileActivity() {

        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("User", myTenant);
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ParkMeAppActivity.class);
        intent.putExtra("User", myTenant);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.account) {
            userProfileActivity();
        } else if(v.getId() == R.id.payment) {
            //// TODO: 7/26/17
        }
    }
}
