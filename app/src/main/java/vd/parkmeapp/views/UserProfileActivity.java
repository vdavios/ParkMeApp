package vd.parkmeapp.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.UserProfilePresenter;
import vd.parkmeapp.R;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener, UserProfileView {

    private User myTenant;
    private TextView firstNameView, lastNameView, emailView, passwordView;
    private UserProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        firstNameView =  (findViewById(R.id.firstNameEditProfile));
        lastNameView =  (findViewById(R.id.lastNameEditProfile));
        emailView = (findViewById(R.id.emailEditProfile));
        passwordView = (findViewById(R.id.passwordEditProfile));
        myTenant = getIntent().getParcelableExtra("User");
        //Creating presenter
        mPresenter = new UserProfilePresenter(this);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mPresenter.activeInternetConnection(connectivityManager);


    }


    @Override
    public void onClick(View v){
        int rId = v.getId();
        Intent intent = new Intent(UserProfileActivity.this, EditUserInfoActivity.class);
        intent.putExtra("R.id", rId);
        intent.putExtra("User", myTenant);
        intent.putExtra("caller", "UserProfileActivity");
        startActivity(intent);
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(UserProfileActivity.this, SettingsActivity.class);
        intent.putExtra("User", myTenant);
        startActivity(intent);


    }

    @Override
    public void emptyUserInfo() {

        firstNameView.setText("");
        lastNameView.setText("");
        emailView.setText("");
        passwordView.setText("");
    }

    @Override
    public void setUserInfo(String firstName, String lastName, String email, String password) {

        firstNameView.setText(firstName);
        lastNameView.setText(lastName);
        emailView.setText(email);
        passwordView.setText(password);

    }

    @Override
    public void enableListeners() {

        firstNameView.setOnClickListener(this);
        lastNameView.setOnClickListener(this);
        emailView.setOnClickListener(this);
        passwordView.setOnClickListener(this);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void moveToInternetFailureActivity() {
        Intent intent = new Intent(UserProfileActivity.this,
                InternetFailureActivity.class);
        startActivity(intent);
    }

    @Override
    public void hasConnection(Boolean result) {
        if(result){

            //passing user object to presenter and requesting for user info
            mPresenter.usersInfo(myTenant);
        } else {
            emptyUserInfo();
            moveToInternetFailureActivity();
        }
    }
}