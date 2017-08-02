package vd.parkmeapp.Models;

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

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setEmail(String email);

    void setPassword(String password);

    void setCreditCardNumber(String creditCardNumber);

    void setcVV(String cVV);
}
