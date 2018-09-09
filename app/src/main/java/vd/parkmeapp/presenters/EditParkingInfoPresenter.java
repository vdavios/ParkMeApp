package vd.parkmeapp.presenters;

import android.util.Log;

import vd.parkmeapp.models.User;
import vd.parkmeapp.views.EditParkingInfoActivity;

public class EditParkingInfoPresenter {

    private User currentUser;
    private EditParkingInfoActivity mView;
    public EditParkingInfoPresenter(User currentUser, EditParkingInfoActivity mView){
        this.currentUser = currentUser;
        this.mView = mView;
    }
    public void setUsersInfo() {

        String strN = currentUser.getStreetName();
        Log.d("Street Name", "[" + strN + "]");
        mView.setParkingInfo(currentUser.getStreetName(), currentUser.getHouseNumber(),
                currentUser.getPostCode(), currentUser.getPph());
        mView.enableListeners();

    }
}
