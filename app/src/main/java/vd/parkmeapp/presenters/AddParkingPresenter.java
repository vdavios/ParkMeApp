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

    public void setUsersInfo() {

            String locationAddress = tenant.getHouseNumber() + " "
                    + tenant.getStreetName() + " " + tenant.getPostCode();
            mView.setParkingInfo(locationAddress);
            mView.enableListeners();

    }



    public boolean hasSetParkingInfo(){

            return !tenant.getStreetName().startsWith("Please")
                    && !tenant.getHouseNumber().startsWith("Please")
                    && !tenant.getPostCode().startsWith("Please")
                    && !tenant.getPph().startsWith("Please");

    }


    public void parkingAvailableToRent(){
        tenant.setHasParking("yes");
        DbSingleton.getInstance().setHasParking("yes");
    }
    public void parkingNotAvailableToRent() {
        tenant.setHasParking("no");
        DbSingleton.getInstance().setHasParking("no");
    }
    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }
}
