package vd.parkmeapp.views;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import vd.parkmeapp.models.User;
import vd.parkmeapp.R;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView accountView, paymentView;
    private User myTenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        accountView = (findViewById(R.id.account));

        myTenant = getIntent().getParcelableExtra("User");

        accountView.setOnClickListener(this);



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
        }
    }
}