package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import vd.parkmeapp.R;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.LoadingDataPresenter;

public class LoadingScreenActivity extends AppCompatActivity implements View {
    private LoadingDataPresenter mPresenter;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        currentUser = getIntent().getParcelableExtra("User");
        mPresenter = new LoadingDataPresenter(this,currentUser);
        mPresenter.getParkingList();
    }


    public void dataLoaded(ArrayList<Tenant> results){
        Intent intent = new Intent(LoadingScreenActivity.this, AvailableParkingListActivity.class);
        intent.putExtra("Results", results);
        intent.putExtra("User",currentUser);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }
}
