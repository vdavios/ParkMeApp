package vd.parkmeapp.Presenters;



import vd.parkmeapp.Models.DbSingleton;
import vd.parkmeapp.Models.Validator;
import vd.parkmeapp.Models.ValidatorImpl;
import vd.parkmeapp.Views.SignUpView;

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
                           String password, String creditCardNumber, String cVV){

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
        myDb.signUpUser(firstName, lastName, email, password, creditCardNumber, cVV, this);


    }

    public void singUpSuccessfully() {
        mView.mainActivity();
    }

    public void failedToSignUp() {
        passMessage("Sign Up Failed");
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