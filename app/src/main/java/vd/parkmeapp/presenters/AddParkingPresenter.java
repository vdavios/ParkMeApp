package vd.parkmeapp.presenters;

import android.util.Log;

import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;
import vd.parkmeapp.views.AddParkingActivity;

public class AddParkingPresenter implements Presenter{
    private AddParkingActivity mView;
    private User tenant;

    public AddParkingPresenter(AddParkingActivity view, User tenant){
        mView = view;
        this.tenant = tenant;
        Log.d("Presenter", "Created Presenter");
    }

    public void usersInfo() {
        if(tenant.getFirstName() == null || tenant.getLastName() == null
                || tenant.getEmail() == null || tenant.getPassword() == null) {
            Log.d("Error: ", "Something is wrong");
            passMessage("Please check your internet connection");
        } else {
            String strN = tenant.getStreetName();
            Log.d("Street Name", "[" + strN + "]");
            mView.setParkingInfo(tenant.getStreetName(), tenant.getHouseNumber(),
                    tenant.getPostCode(), tenant.getPph());
            mView.enableListeners();
        }
    }



    public boolean hasSetParkingInfo(){

            return !tenant.getStreetName().startsWith("Please")
                    && !tenant.getHouseNumber().startsWith("Please")
                    && !tenant.getPostCode().startsWith("Please")
                    && !tenant.getPph().startsWith("Please");

    }


    public void parkingAvailableToRent(){
        DbSingleton.getInstance().setHasParking("yes");
    }
    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }
}
