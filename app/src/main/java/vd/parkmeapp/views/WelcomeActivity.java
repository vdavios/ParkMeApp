package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vd.parkmeapp.R;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.WelcomeActivityPresenter;

public class WelcomeActivity extends AppCompatActivity {
    private WelcomeActivityPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPresenter = new WelcomeActivityPresenter(this);
        mPresenter.fetchCurrentUserData();
    }




    public void mainActivity(User mCurrentUser){

        Intent intent = new Intent(WelcomeActivity.this, ParkMeAppActivity.class);
        intent.putExtra("User", mCurrentUser);
        startActivity(intent);

    }


}
