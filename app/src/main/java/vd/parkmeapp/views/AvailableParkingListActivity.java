package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vd.parkmeapp.R;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.presenters.AvailableParkingListPresenter;

public class AvailableParkingListActivity  extends AppCompatActivity implements AvailableParkingListView {


    private AvailableParkingListPresenter mPresenter;
    private ArrayList<Tenant> aL;
    private User mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_parking);
        final ListView listView = findViewById(R.id.listView);
        //Creating the presenter
        mPresenter = new AvailableParkingListPresenter(this);

        //Current User
        mCurrentUser = getIntent().getParcelableExtra("User");

        //Array List with parking owners
        aL = getIntent().getParcelableArrayListExtra("Results");


        //Setting List View with Custom Adapter
        CustomAdapter customAdapter = new CustomAdapter(aL);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                User selected = (User)listView.getItemAtPosition(position);

                Log.d("Selected item: ", selected.toString());
                Intent intent = new Intent(
                        AvailableParkingListActivity.this, ParkingOwnerInfoActivity.class
                );
                intent.putExtra("User",mCurrentUser);
                intent.putExtra("ParkingOwner", selected);
                intent.putParcelableArrayListExtra("Results",aL);
                startActivity(intent);
            }
        });



    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onBackPressed(){}




    private class CustomAdapter extends BaseAdapter{

        private ArrayList<Tenant> uList;
        public CustomAdapter(ArrayList<Tenant> usersList){
            uList = usersList;


        }

        @Override
        public int getCount() {
            return uList.size();
        }

        @Override
        public Object getItem(int i) {
            return uList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            TextView parkingAddress = view.findViewById(R.id.parkingAddress);
            TextView pricePerHour = view.findViewById(R.id.PricePerHour);
            TextView distance = view.findViewById(R.id.Distance);
            User parkingOwner = uList.get(i);
            String street = parkingOwner.getStreetName() + " " + parkingOwner.getHouseNumber();
            parkingAddress.setText(street);
            pricePerHour.setText(parkingOwner.getPph());
            distance.setText("15min");
            // add distance
            return view;
        }
    }
}
