package vd.parkmeapp.models;

import android.os.Parcel;

/**
 * Tenant class implements user interface
 * objects of this class are saved locally and passed through the activities and in our database
 */

public class Tenant implements User {

    private String firstName, lastName, email, password, creditCardNumber, cVV,
            streetName, houseNumber, postCode, pph, isRented, hasParking, uId, isHeRenting,
            usersIdParkingThatHeIsRenting;
    private double latOfHisParking, lngOfHisParking, latOfParkingThatHeIsCurrentlyRenting,
            lngOfParkingThatHeIsCurrentlyRenting;

    public Tenant(){ }

    public Tenant(String firstName, String lastName, String email,
                  String password, String creditCardNumber, String cVV,
                  String streetName, String houseNumber, String postCode, String pph,
                  String hasParking, String isRented, String uId, String isHeRenting,
                  String usersIdParkingThatHeIsRenting,
                  double latOfHisParking,
                  double lngOfHisParking, double latOfParkingThatHeIsCurrentlyRenting,
                  double lngOfParkingThatHeIsCurrentlyRenting) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.creditCardNumber = creditCardNumber;
        this.cVV = cVV;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.pph = pph;
        this.hasParking = hasParking;
        this.isRented = isRented;
        this.isHeRenting = isHeRenting;
        this.uId = uId;
        this.usersIdParkingThatHeIsRenting = usersIdParkingThatHeIsRenting;
        this.latOfParkingThatHeIsCurrentlyRenting = latOfParkingThatHeIsCurrentlyRenting;
        this.lngOfParkingThatHeIsCurrentlyRenting = lngOfParkingThatHeIsCurrentlyRenting;
        this.latOfHisParking = latOfHisParking;
        this.lngOfHisParking = lngOfHisParking;

    }

    public Tenant(Parcel in) {

        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
        creditCardNumber = in.readString();
        cVV = in.readString();
        streetName = in.readString();
        houseNumber = in.readString();
        postCode = in.readString();
        pph = in.readString();
        isRented = in.readString();
        hasParking = in.readString();
        isHeRenting = in.readString();
        uId = in.readString();
        usersIdParkingThatHeIsRenting = in.readString();
        latOfHisParking = in.readDouble();
        lngOfHisParking = in.readDouble();
        latOfParkingThatHeIsCurrentlyRenting = in.readDouble();
        lngOfParkingThatHeIsCurrentlyRenting = in.readDouble();

    }

    public static final Creator<Tenant> CREATOR = new Creator<Tenant>() {
        @Override
        public Tenant createFromParcel(Parcel in) {
            return new Tenant(in);
        }

        @Override
        public Tenant[] newArray(int size) {
            return new Tenant[size];
        }
    };

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    @Override
    public String getcVV() {
        return cVV;
    }

    @Override
    public String getStreetName() {
        return streetName;
    }

    @Override
    public String getHouseNumber() {
        return houseNumber;
    }

    @Override
    public String getPostCode() {
        return postCode;
    }

    @Override
    public String getPph() {
        return pph;
    }


    @Override
    public String getRented() {
        return isRented;
    }

    @Override
    public String getHasParking() {
        return hasParking;
    }

    @Override
    public String getUid() {
        return uId;
    }

    @Override
    public String getIsHeRenting() {
        return isHeRenting;
    }

    @Override
    public String getUsersIdParkingThatHeIsRenting() {
        return usersIdParkingThatHeIsRenting;
    }

    @Override
    public double getLatOfParkingThatHeIsCurrentlyRenting() {
        return latOfParkingThatHeIsCurrentlyRenting;
    }

    @Override
    public double getLngOfParkingThatHeIsCurrentlyRenting() {
        return lngOfParkingThatHeIsCurrentlyRenting;
    }

    @Override
    public double getLatOfHisParking() {
        return latOfHisParking;
    }

    @Override
    public double getLngOfHisParking() {
        return lngOfHisParking;
    }



    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public void setUsersIdParkingThatHeIsRenting(String usersIdParkingThatHeIsRenting) {
        this.usersIdParkingThatHeIsRenting = usersIdParkingThatHeIsRenting;
    }

    @Override
    public void setIsHeRenting(String isHeRenting) {
        this.isHeRenting = isHeRenting;
    }

    @Override
    public void setUid(String uid) {
        this.uId = uid;
    }


    @Override
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public void setPph(String pph) {
        this.pph = pph;
    }
    @Override
    public void setcVV(String cVV) {
        this.cVV = cVV;
    }

    @Override
    public void setRented(String rented) {
        this.isRented = rented;
    }

    @Override
    public void setHasParking(String hasParking) {
        this.hasParking = hasParking;
    }


    @Override
    public void setLatOfHisParking(double lat) {
        latOfHisParking = lat;
    }

    @Override
    public void setLngOfHisParking(double lng) {
        lngOfHisParking = lng;
    }

    @Override
    public void setLatOfParkingThatHeIsCurrentlyRenting(double lat) {
        latOfParkingThatHeIsCurrentlyRenting = lat;
    }

    @Override
    public void setLngOfParkingThatHeIsCurrentlyRenting(double lng) {
        lngOfParkingThatHeIsCurrentlyRenting = lng;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(creditCardNumber);
        dest.writeString(cVV);
        dest.writeString(streetName);
        dest.writeString(houseNumber);
        dest.writeString(postCode);
        dest.writeString(pph);
        dest.writeString(isRented);
        dest.writeString(hasParking);
        dest.writeString(isHeRenting);
        dest.writeString(uId);
        dest.writeString(usersIdParkingThatHeIsRenting);
        dest.writeDouble(latOfHisParking);
        dest.writeDouble(lngOfHisParking);
        dest.writeDouble(latOfParkingThatHeIsCurrentlyRenting);
        dest.writeDouble(lngOfParkingThatHeIsCurrentlyRenting);

    }
}
