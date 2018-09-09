package vd.parkmeapp.presenters;



import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.Validator;
import vd.parkmeapp.models.ValidatorImpl;
import vd.parkmeapp.views.SignUpView;

/**
 * Presenter for SignUp View
 */

public class SignUpPresenter implements Presenter{

    private final DbSingleton myDb;
    private SignUpView mView;

    public SignUpPresenter(SignUpView mView){

        myDb = DbSingleton.getInstance();
        this.mView = mView;

    }


    public void signUpUser(String firstName, String lastName, String email,
                           String password, String creditCardNumber, String cVV, String streetName,
                           String houseNumber, String postCode, String pricePerHour){

        Validator validator = new ValidatorImpl();
        if(!validator.validateName(firstName) || !validator.validateName(lastName)) {
            passMessage("Invalid Name");
            return;
        } else if (!validator.validateEmail(email)) {
            passMessage("Invalid Email");
            return;
        } else if (!validator.validatePassword(password)) {
            passMessage("Your password must be at least 6 characters long and contain at least 1 uppercase\n" +
                    "and 1 number");
            return;
        } else if (!validator.validateCreditCardNumber(creditCardNumber)) {
            passMessage("Invalid Credit Card Number");
            return;
        } else if (!validator.validateCVV(cVV)) {
            passMessage("Invalid CVV number");
            return;
        }
        myDb.signUpUser(firstName, lastName, email, password, creditCardNumber,
                cVV, streetName, houseNumber, postCode, pricePerHour,this);


    }

    public void singUpSuccessfully() {
        mView.welcomeActivity();
    }

    public void failedToSignUp(String errorMessage) {
        passMessage(errorMessage);
    }


    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }

    public void resumeAuthListener() {
        myDb.setAuthListener();
    }

    public void stopAuthListener() {
        myDb.cancelAuthListener();
    }
}