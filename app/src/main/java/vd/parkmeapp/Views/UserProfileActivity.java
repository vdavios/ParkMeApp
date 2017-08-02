package vd.parkmeapp.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import vd.parkmeapp.Models.User;
import vd.parkmeapp.R;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView firstNameView, lastNameView, emailView, passwordView;
    private TextView firstNameLabelView;
    private User myTenant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        firstNameView = (TextView)(findViewById(R.id.firstNameEditProfile));
        lastNameView = (TextView)(findViewById(R.id.lastNameEditProfile));
        emailView = (TextView)(findViewById(R.id.emailEditProfile));
        passwordView = (TextView)(findViewById(R.id.passwordEditProfile));
        firstNameLabelView = (TextView)(findViewById(R.id.firstNameLabel));

        /*firstName = getIntent().getStringExtra("First Name");
        lastName = getIntent().getStringExtra("Last Name");
        email = getIntent().getStringExtra("Email");
        password = getIntent().getStringExtra("Password");*/
        myTenant = getIntent().getParcelableExtra("User");
        //
        if(myTenant == null) {
            firstNameView.setText("");
            lastNameView.setText("");
            emailView.setText("");
            passwordView.setText("");
            Toast.makeText(this, "Please check you internet connection", Toast.LENGTH_SHORT)
                    .show();


        } else {
            firstNameView.setText(myTenant.getFirstName());
            firstNameView.setOnClickListener(this);
            lastNameView.setText(myTenant.getLastName());
            lastNameView.setOnClickListener(this);
            emailView.setText(myTenant.getEmail());
            emailView.setOnClickListener(this);
            passwordView.setText(myTenant.getPassword());
            passwordView.setOnClickListener(this);
        }



    }


    @Override
    public void onClick(View v){
        int rId = v.getId();
        Intent intent = new Intent(UserProfileActivity.this, EditUserInfoActivity.class);
        intent.putExtra("R.id", rId);
        intent.putExtra("User", myTenant);
        startActivity(intent);

    }


    @Override
    public void onBackPressed(){

        if(myTenant == null) {
            Intent intent = new Intent(UserProfileActivity.this, ParkMeAppActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(UserProfileActivity.this, SettingsActivity.class);
            intent.putExtra("User", myTenant);
            startActivity(intent);
        }

    }
}
