package vd.parkmeapp.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import vd.parkmeapp.R;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.WelcomeActivityPresenter;

public class WelcomeActivity extends AppCompatActivity implements ActivitiesThatNeedInternetAccess{
    private WelcomeActivityPresenter mPresenter;
    private User mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPresenter = new WelcomeActivityPresenter(this);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Checking for active internet connection
        mPresenter.activeInternetConnection(connectivityManager);
        //When we pass from sign-up to welcome activity User object is already created
        //and saved to the database. This is not true when we pass from log-in activity to welcome activity
        //We create the user object and we check (after we assert that we have an internet connection)
        //if the object is null
        mCurrentUser = getIntent().getParcelableExtra("User");


    }

    public void mainActivity(User mCurrentUser){

        Intent intent = new Intent(WelcomeActivity.this, ParkMeAppActivity.class);
        intent.putExtra("User", mCurrentUser);
        startActivity(intent);

    }

    public void loadingFinishedWithSuccess(User mCurrentUser){
        mPresenter.setLatLng(mCurrentUser);
    }

    @Override
    public void moveToInternetFailureActivity(){
        Intent intent = new Intent(WelcomeActivity.this,
                InternetFailureActivity.class);
        startActivity(intent);
    }

    @Override
    public void hasConnection(Boolean result) {
        if(result){
            //Check user object
            if(mCurrentUser == null){
                Log.d("current user: ", "null");
                mPresenter.fetchCurrentUserData();
            } else {
                //if it's not null then this is the first time that the user sign up
                //and we need to find the latlng location of his parking
                loadingFinishedWithSuccess(mCurrentUser);
            }

        } else {
            moveToInternetFailureActivity();
        }
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }


}
