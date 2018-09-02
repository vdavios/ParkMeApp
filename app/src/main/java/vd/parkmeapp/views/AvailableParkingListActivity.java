package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private ArrayList<String> aL;
    private User mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_parking);
        final ListView listView = findViewById(R.id.listView);
        //Creating the presenter
        mPresenter = new AvailableParkingListPresenter(this);
        mCurrentUser = getIntent().getParcelableExtra("User");





        //String firstName, String lastName, String email,
        //                  String password, String creditCardNumber, String cVV,
        //                  String streetName, String houseNumber, String postCode, String pph, String rented
        Tenant nT = new Tenant("Vasilis",
                "Davios",
                "vas.davios@gmail.com","123456bB",
                "1656495495949595","466",
                "Holliday Street","18","B11TH","2","no");

        Tenant nt1 = new Tenant("tst","tst","tst@tst.com",
                "123456bB","1356468676766867","469"
                , "Dios","2","15127","3","no");

        ArrayList<User> alUser = new ArrayList<>();
        alUser.add(nT);
        alUser.add(nt1);

        //Testing Custom Adapter
        CustomAdapter customAdapter = new CustomAdapter(alUser);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                User selected = (User)listView.getItemAtPosition(position);

                Log.d("Selected item: ", selected.toString());
                Intent intent = new Intent(
                        AvailableParkingListActivity.this, ParkingOwnerInfo.class
                );
                intent.putExtra("User",mCurrentUser);
                intent.putExtra("ParkingOwner", selected);
                startActivity(intent);
            }
        });
        //Getting the List with the available Parking
       /** aL = getIntent().getStringArrayListExtra("Results");

        ArrayAdapter listAdapter = new ArrayAdapter<>(this, R.layout.listitems,aL);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(
                        AvailableParkingListActivity.this, ParkMeAppActivity.class
                );
                intent.putExtra("User",mCurrentUser);
                startActivity(intent);
            }
        });**/


    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onBackPressed(){}

    class CustomAdapter extends BaseAdapter{

        private ArrayList<User> uList;
        public CustomAdapter(ArrayList<User> usersList){
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
            distance.setText("TODO");
            // add distance
            return view;
        }
    }
}
