package vd.parkmeapp.presenters;



import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.views.ParkingOwnerInfoActivity;

public class ParkingOwnerInfoPresenter implements Presenter{
    private ParkingOwnerInfoActivity mView;
    private Tenant currentUser;


    public ParkingOwnerInfoPresenter(ParkingOwnerInfoActivity view, Tenant currentUser){
        mView = view;
        this.currentUser = currentUser;
    }

    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }

    public void isNowRented(String ownerId){
        currentUser.setIsHeRenting("yes");
        currentUser.setUsersIdParkingThatHeIsRenting(ownerId);
        mView.updateUser(currentUser);
        DbSingleton.getInstance().setUsersIdParkingThatHeIsRenting(ownerId);
        DbSingleton.getInstance().setValue(ownerId,"yes");
        DbSingleton.getInstance().setIsHeRenting("yes");
        mView.showMessage("Is rented now");
    }
}
