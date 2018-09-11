package vd.parkmeapp.views;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.ParkMeAppPresenter;
import vd.parkmeapp.R;


public class ParkMeAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleMap.OnCameraMoveStartedListener, ParkMeAppView {

    private GoogleMap mMap;
    private static final int CODE = 1;
    private User tenant;
    private ParkMeAppPresenter mPresenter;
    private ConnectivityManager connectivityManager;
    private String address;
    private boolean accessToLocation = false;
    private double latOfTheParkingThatHeIsRenting;
    private double lngOfTheParkingThatHeIsRenting;
    private ArrayList<Marker> markerList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_me_app);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(savedInstanceState !=null){
            tenant = savedInstanceState.getParcelable("User");
        } else {
            tenant  = getIntent().getParcelableExtra("User");
        }

        // Creating ParkMeApp presenter-controller
        mPresenter = new ParkMeAppPresenter(this, getApplicationContext(),tenant,connectivityManager);

        //request permission for location (must be done inside an activity or a fragment)
        accessUsersLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        //----------------- Drawer -----------------//

        final DrawerLayout drawer =  findViewById(R.id.drawer_layout);

        Button menuButton = (findViewById(R.id.menuButton));
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                drawer.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //---------Search Box for location---------//
        mSearchText = findViewById(R.id.input_search);


        //---------- Parking Results Button ----------//
        Button parkMeAppButton = findViewById(R.id.parkMeAppResultsButton);


        //Checking if the user is currently renting a parking
        if(tenant.getIsHeRenting().equals("yes")){

            Log.d("Renting a parking: ", "true");
            //find the parking location
            latOfTheParkingThatHeIsRenting = tenant.getLatOfParkingThatHeIsCurrentlyRenting();
            lngOfTheParkingThatHeIsRenting = tenant.getLngOfParkingThatHeIsCurrentlyRenting();
            //check connection and add route to parking
            //mPresenter.activeInternetConnection(connectivityManager);

            //If he is renting a parking, change the settings of the button
            parkMeAppButton.setBackgroundColor(getResources().getColor(R.color.red));
            parkMeAppButton.setText(R.string.UserIsCurrentlyRentingParking);
            parkMeAppButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Reset button -> Welcome page
                    mPresenter.leaveParking(tenant.getUsersIdParkingThatHeIsRenting(),tenant);
                   // polyline.remove();
                    Intent intent = new Intent(ParkMeAppActivity.this, WelcomeActivity.class);
                    intent.putExtra("User", tenant);
                    startActivity(intent);
                }
            });
        } else {
            //User is not renting any parking
            parkMeAppButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Check if we have access to the location of the user
                    if(doWeHaveAccessToUserLocation()){

                        Log.d("access user location: ", "true");

                        //Users last known location
                        LatLng userLocation = mPresenter.usersLocation();

                        //We have access, ParkMeApp fully functional
                        Intent intent = new Intent(ParkMeAppActivity.this, LoadingScreenActivity.class);
                        intent.putExtra("User",tenant);
                        intent.putExtra("UsersLocation", userLocation);
                        startActivity(intent);
                    } else {
                        //We don't have access to location. Deny access to user with a toast message
                        Toast.makeText(ParkMeAppActivity.this,R.string.access_location, Toast.LENGTH_LONG)
                                .show();
                        }

                    }
                });


        }



    }



    //---------------Google Maps and permissions Request ---------------------//

    @Override
    public void onMapReady( GoogleMap googleMap) {
        mMap = googleMap;

        if(!(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            accessUsersLocation();
        }else {
            mMap.setMyLocationEnabled(true);
            setUpMap();
            init();

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
                Log.d("Permission granted: ", "here");
                mPresenter.connectApiClient();
                accessToLocation = true;
                if(mMap !=null){
                    mMap.setMyLocationEnabled(true);
                    setUpMap();
                }

            }
        }
    }

    @Override
    public void onCameraMoveStarted(int i) {
        if(i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE){
            mPresenter.pauseRequests();

        }

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if(markerList !=null){
                    for(Marker marker: markerList){
                        marker.remove();
                    }
                }
                mPresenter.resumeRequests();
                return true;
            }
        });

    }

    @Override
    public void setCamera(LatLng mLatLng) {
        String lat = Double.toString(mLatLng.latitude);
        String longitude = Double.toString(mLatLng.longitude);
        Log.d("LatLong: ", lat + " "+ longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 17));

    }


    //Geolocation
    private static String TAG = "Geolocation";
    private EditText mSearchText;

    @Override
    public void moveCamera(LatLng latLng, float zoom, ArrayList<LatLng> locations, ArrayList<Tenant> owners){
        mPresenter.pauseRequests();
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options = new MarkerOptions();
        for(int i = 0 ; i < locations.size(); i++){

            options.position(locations.get(i));
            Tenant parkingOwner = owners.get(i);
            String title = parkingOwner.getHouseNumber() + " " + parkingOwner.getStreetName() + ", "
                    + parkingOwner.getPostCode();
            options.title(title);
            Marker marker = mMap.addMarker(options);
            markerList.add(marker);
        }

    }

    private void init(){
        Log.d(TAG, " initializing");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                Log.d(TAG, "init: inside the listener");
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE){

                    address = mSearchText.getText().toString();

                    //Check if the address is not empty
                    if(!address.isEmpty()){
                        //Check if user has active internet connection
                        mPresenter.activeInternetConnection(connectivityManager);
                    }



                }

                return false;
            }
        });

    }


    private void routeToParkingThatUserIsRenting(double latOfTheParkingThatHeIsRenting, double lngOfTheParkingThatHeIsRenting){
        Log.d(TAG, "Calling presenter method: routeToParking");

        mPresenter.getRouteToLocation(latOfTheParkingThatHeIsRenting, lngOfTheParkingThatHeIsRenting);
    }

    public void addPolyline(ArrayList<LatLng> points){

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(points);
        polylineOptions.width(7);
        polylineOptions.color(Color.RED);
        mMap.addPolyline(polylineOptions);

    }



    //----------------- Drawer/Burger Menu -----------------//

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            // History
            Intent intent = new Intent(ParkMeAppActivity.this, HistoryActivity.class);
            intent.putExtra("User", tenant);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {

            mPresenter.pauseRequests();
            Intent intent =
                    new Intent(ParkMeAppActivity.this, SettingsActivity.class);
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
            mPresenter.pauseRequests();
            Intent intent =
                    new Intent(ParkMeAppActivity.this, AddParkingActivity.class);
            intent.putExtra("User",tenant);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //Creating toast/message to display
    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    //Updating User in Local Memory
    public void updateUser(User tenant){
        this.tenant = tenant;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("User",tenant);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void moveToInternetFailureActivity() {
        Intent intent = new Intent(ParkMeAppActivity.this,
                InternetFailureActivity.class);
        startActivity(intent);
    }

    @Override
    public void hasConnection(Boolean result) {
        if(result){
            //Check if the user is looking for a random location
            if(address==null || address.isEmpty()){
                if(doWeHaveAccessToUserLocation()){
                    //He is not create root to the parking he is renting
                    routeToParkingThatUserIsRenting(latOfTheParkingThatHeIsRenting, lngOfTheParkingThatHeIsRenting);
                } else {
                    //In the rare case that the user rents a parking space then decides to remove the application
                    //from his device. And then re-install it this message will be shown.
                    showMessage("Please restart your application to see the route to the parking");
                }

            } else {

                //He is looking for parking spaces in a random location
                //Move camera to that location
                moveToAddress(address);
            }
            //Reset the address value
            address = "";

        } else {
            moveToInternetFailureActivity();
        }
    }

    public void moveToAddress(String address){
        mPresenter.findLocation(this,address);
    }

    public boolean doWeHaveAccessToUserLocation(){
        return accessToLocation;
    }
}