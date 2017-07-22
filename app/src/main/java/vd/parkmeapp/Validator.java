package vd.parkmeapp;


import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Pattern emailPattern;
    private Pattern passPattern;
    private Pattern namePattern;
    private Matcher matcher;
    private Context reference;
    private static final int PASSWORD_SIZE = 5;
    private static final int CREDIT_CARD_SIZE = 16;
    private static final int CVV_SIZE = 3;

    public Validator(Context reference){
        this.reference = reference;
        emailPattern = Pattern.compile(EMAIL_PATTERN);
        passPattern = Pattern.compile(PASSWORD_PATTERN);
        namePattern = Pattern.compile(NAME_PATTERN);
    }

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //At least 6 characters, one upper Case char, one lowerCase and one number
    private static final String PASSWORD_PATTERN =
            "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$";

    private static final String NAME_PATTERN = "[a-zA-Z]+";

    public boolean validateEmail(String inputEmail){

        matcher = emailPattern.matcher(inputEmail);
        if(!matcher.matches()){
            Toast.makeText(reference, R.string.invalidEmail, Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;


    }

    public boolean validatePassword(String inputPass){
        matcher = passPattern.matcher(inputPass);
        if(!(inputPass.length() > PASSWORD_SIZE && matcher.matches())) {
            Toast.makeText(reference, R.string.passwordInstructions, Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;

    }

    public boolean validateCreditCardNumber(String inputCreditCard) {
        if(!(inputCreditCard.length() == CREDIT_CARD_SIZE)) {
            Toast.makeText(reference, R.string.invalidCreditCard, Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    public boolean validateCVV(String inputCVV){
        if(!(inputCVV.length() == CVV_SIZE)){
            Toast.makeText(reference, R.string.invalidCVV, Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    public boolean validateName(String name){
        matcher = namePattern.matcher(name);
        if(!(matcher.matches())){
         Toast.makeText(reference, R.string.invalidName, Toast.LENGTH_SHORT)
                 .show();
            return false;
        }
        return true;
    }
}
