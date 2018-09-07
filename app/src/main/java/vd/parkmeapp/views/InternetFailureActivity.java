package vd.parkmeapp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vd.parkmeapp.R;

public class InternetFailureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_failure);
    }


    @Override
    public void onBackPressed(){ //Do nothing
         }
}
