package vd.parkmeapp.views;

public interface AddParkingView extends View{
    void setParkingInfo(String streetName, String houseNumber, String postCode, String pph);
    void enableListeners();
}
