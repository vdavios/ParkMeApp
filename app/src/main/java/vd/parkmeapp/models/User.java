package vd.parkmeapp.models;

import android.os.Parcelable;

/**
 * User Interface
 */

public interface User extends Parcelable {
    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();

    String getCreditCardNumber();

    String getcVV();

    String getStreetName();

    String getHouseNumber();

    String getAddressOfTheParkingThatHeIsCurrentlyRenting();

    String getPostCode();
    String getPph();
    String getRented();
    String getHasParking();
    String getUid();
    String getIsHeRenting();
    String getUsersIdParkingThatHeIsRenting();

    void setUsersIdParkingThatHeIsRenting(String usersIdParkingThatHeIsRenting);
    void setIsHeRenting(String isHeRenting);

    void setUid(String uid);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setEmail(String email);

    void setPassword(String password);

    void setCreditCardNumber(String creditCardNumber);

    void setcVV(String cVV);

    void setStreetName(String streetName);

    void setHouseNumber(String houseNumber);

    void setPostCode(String postCode);

    void setPph(String pph);

    void setRented(String rented);

    void setHasParking(String hasParking);

    void setAddressOfTheParkingThatHeIsCurrentlyRenting(String addressOfTheParkingThatHeIsCurrentlyRenting);
}
