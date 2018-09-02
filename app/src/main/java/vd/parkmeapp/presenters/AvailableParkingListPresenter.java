package vd.parkmeapp.presenters;

import java.util.ArrayList;
import java.util.Arrays;

import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.views.AvailableParkingListActivity;

public class AvailableParkingListPresenter implements Presenter{

    private AvailableParkingListActivity mView;


    public AvailableParkingListPresenter(AvailableParkingListActivity view) {

        mView = view;
    }

    @Override
    public void passMessage(String message) {

        mView.showMessage(message);

    }


    public void getParkingList(){
        DbSingleton myDb = DbSingleton.getInstance();

         //return myDb.fetchData();
    }


}
