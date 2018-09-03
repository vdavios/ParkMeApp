package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vd.parkmeapp.R;
import vd.parkmeapp.models.Tenant;

public class HistoryActivity extends AppCompatActivity {
    private  Tenant tenant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        tenant = getIntent().getParcelableExtra("User");


    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(HistoryActivity.this, ParkMeAppActivity.class);
        intent.putExtra("User", tenant);
        startActivity(intent);
    }
}
