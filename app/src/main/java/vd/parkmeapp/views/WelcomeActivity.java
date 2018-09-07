package vd.parkmeapp.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import vd.parkmeapp.R;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.WelcomeActivityPresenter;

public class WelcomeActivity extends AppCompatActivity implements ActivitiesThatNeedInternetAccess{
    private WelcomeActivityPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPresenter = new WelcomeActivityPresenter(this);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //Checking for active internet connection
        mPresenter.activeInternetConnection(connectivityManager);

    }

    public void mainActivity(User mCurrentUser){

        Intent intent = new Intent(WelcomeActivity.this, ParkMeAppActivity.class);
        intent.putExtra("User", mCurrentUser);
        startActivity(intent);

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
            mPresenter.fetchCurrentUserData();
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
