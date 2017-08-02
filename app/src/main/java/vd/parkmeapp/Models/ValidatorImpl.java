package vd.parkmeapp.Models;


import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vd.parkmeapp.R;

public class ValidatorImpl implements Validator {

    private Pattern emailPattern;
    private Pattern passPattern;
    private Pattern namePattern;
    private Matcher matcher;
    private static final int PASSWORD_SIZE = 5;
    private static final int CREDIT_CARD_SIZE = 16;
    private static final int CVV_SIZE = 3;

    public ValidatorImpl(){

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

    @Override
    public boolean validateEmail(String inputEmail){

        matcher = emailPattern.matcher(inputEmail);
        return matcher.matches();


    }

    @Override
    public boolean validatePassword(String inputPass){
        matcher = passPattern.matcher(inputPass);
        return inputPass.length() > PASSWORD_SIZE && matcher.matches();

    }

    @Override
    public boolean validateCreditCardNumber(String inputCreditCard) {
        return inputCreditCard.length() == CREDIT_CARD_SIZE;
    }

    @Override
    public boolean validateCVV(String inputCVV){
        return inputCVV.length() == CVV_SIZE;
    }

    @Override
    public boolean validateName(String name){
        matcher = namePattern.matcher(name);
        return matcher.matches();
    }


    @Override
    public boolean sameValues(String newValue, String oldValue) {
        return !newValue.equals(oldValue);
    }
}
