package vd.parkmeapp.Views;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;


import vd.parkmeapp.Models.User;
import vd.parkmeapp.Presenters.ParkMeAppPresenter;
import vd.parkmeapp.R;


public class ParkMeAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleMap.OnCameraMoveStartedListener, ParkMeAppView {

    private GoogleMap mMap;
    private static final int CODE = 1;
    private User tenant;
    private ParkMeAppPresenter mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_me_app);


        // Creating ParkMeApp presenter
        mPresenter = new ParkMeAppPresenter(this, getApplicationContext());


        tenant  = mPresenter.getUser((User) getIntent().getParcelableExtra("User"));

        //request permission for location (must be done inside an activity or a fragment)
        accessUsersLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //----------------- Drawer -----------------//

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Button menuButton = (Button)(findViewById(R.id.menuButton));
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onMapReady( GoogleMap googleMap) {
        mMap = googleMap;

        if(!(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            accessUsersLocation();
        }else {
            mMap.setMyLocationEnabled(true);
            setUpMap();
        }


    }

    private void setUpMap(){
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setOnCameraMoveStartedListener(this);
    }



    private void accessUsersLocation(){
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, R.string.access_location, Toast.LENGTH_LONG)
                        .show();
            }else {
                Toast.makeText(this, R.string.access_location, Toast.LENGTH_LONG)
                        .show();
            }
        } else {

            if((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)){
                mPresenter.connectApiClient();
                mMap.setMyLocationEnabled(true);
                setUpMap();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            // History
        } else if (id == R.id.nav_settings) {
            showMessage("in settings");

            mPresenter.pauseRequests();
            Intent intent = new Intent(ParkMeAppActivity.this, SettingsActivity.class);
            intent.putExtra("User",tenant);
            startActivity(intent);


        } else if (id == R.id.nav_payment) {
            // payment
        } else if (id == R.id.nav_signOut) {
            //Cancel Location Requests
            mPresenter.pauseRequests();

            //Sign Out user
            mPresenter.signOut();

            // move to sign in activity
            startActivity(new Intent(ParkMeAppActivity.this, LogInActivity.class));
        } else if (id == R.id.nav_help) {
            // help
        } else if (id == R.id.nav_parkingOwner) {
            // do something
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCameraMoveStarted(int i) {
        if(i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE){
            mPresenter.pauseRequests();

        }

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mPresenter.resumeRequests();
                return true;
            }
        });

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void setCamera(LatLng mLatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 17));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            mPresenter.resumeRequests();
        }
    }


    @Override
    public void onStart(){
        super.onStart();
        mPresenter.connectApiClient();
    }


    @Override
    public void onStop(){
        super.onStop();
        mPresenter.disconnectApiClient();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resumeRequests();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pauseRequests();
    }


}
