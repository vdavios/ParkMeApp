package vd.parkmeapp;

/**
 * User Information
 */

public class UserInformation {
    private String firstName;
    private String lastName;
    private String creditCard;
    private String cVV;

    public UserInformation(String firstName, String lastName, String creditCard, String cVV){
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCard = creditCard;
        this.cVV = cVV;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public String getcVV() {
        return cVV;
    }
}
