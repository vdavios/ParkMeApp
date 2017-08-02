package vd.parkmeapp.Models;

/**
 * Validator Interface
 */

public interface Validator {
    boolean validateEmail(String inputEmail);

    boolean validatePassword(String inputPass);

    boolean validateCreditCardNumber(String inputCreditCard);

    boolean validateCVV(String inputCVV);

    boolean validateName(String name);

    boolean sameValues(String newValue, String oldValue);
}
