package vd.parkmeapp.models;

import android.os.Parcel;

/**
 * T
 */

public class Tenant implements User {

    private String firstName, lastName, email, password, creditCardNumber, cVV,
            streetName, houseNumber, postCode, pph, rented;


    public Tenant(){ }

    public Tenant(String firstName, String lastName, String email,
                  String password, String creditCardNumber, String cVV,
                  String streetName, String houseNumber, String postCode, String pph, String rented) {
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
        this.rented = rented;
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
        rented = in.readString();
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
    public String getPph() {
        return pph;
    }


    @Override
    public String getRented() {
        return rented;
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
        this.rented = rented;
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
        dest.writeString(rented);
    }
}
