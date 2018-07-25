package vd.parkmeapp.Presenters;

import android.os.Bundle;



import vd.parkmeapp.Models.DbSingleton;
import vd.parkmeapp.Models.User;
import vd.parkmeapp.Models.Validator;
import vd.parkmeapp.Models.ValidatorImpl;
import vd.parkmeapp.R;
import vd.parkmeapp.Views.EditUserInfoView;


/**
 * Presenter class for editing users info
 */

public class EditUserInfoPresenter implements Presenter{


    private EditUserInfoView mView;
    private User tenant;
    private int id;
    private String field;


    public EditUserInfoPresenter(EditUserInfoView mView, Bundle bundle){

        this.mView = mView;
        tenant = bundle.getParcelable("User");
        id = bundle.getInt("R.id", 1);

    }

    public void setEditableInfo() {
        if(id == R.id.firstNameEditProfile) {
            field = "First Name";
            mView.setFields("First Name", tenant.getFirstName());
        } else if (id == R.id.lastNameEditProfile) {
            field = "Last Name";
            mView.setFields("Last Name", tenant.getLastName());
        } else if (id == R.id.emailEditProfile) {
            field = "Email";
            mView.setFields("Email", tenant.getEmail());
        } else if (id == R.id.passwordEditProfile) {
            field = "Password";
            mView.setFields("Password", tenant.getPassword());
        }
    }



    @Override
    public void passMessage(String message) {
        mView.showMessage(message);
    }

    private void updateUser() {
        mView.updateUser(tenant);
    }




    public boolean newText(String newValue) {

        Validator validator = new ValidatorImpl();
        DbSingleton myDb = DbSingleton.getInstance();

        switch (field) {
            case "First Name":

                if(validator.sameValues(newValue,
                        tenant.getFirstName())){

                    passMessage("You made no changes");
                    return false;

                } else if(validator.validateName(newValue)) {

                    myDb.setFirstName(newValue);
                    tenant.setFirstName(newValue);
                    updateUser();
                    passMessage("First Name successfully changed");
                    return true;

                } else {

                    passMessage("Invalid First Name");
                    return false;
                }

            case "Last Name":

                if(validator.sameValues(newValue,
                        tenant.getLastName())){

                    passMessage("You made no changes");

                } else if(validator.validateName(newValue)){

                    myDb.setLastName(newValue);
                    tenant.setLastName(newValue);
                    updateUser();
                    passMessage("Last Name successfully changed");
                    return true;

                } else {

                    passMessage("Invalid Last Name");
                    return false;
                }

            case "Email":

                if(validator.sameValues(newValue,
                        tenant.getEmail())){

                    passMessage("You made no changes");
                    return false;

                } else if(validator.validateEmail(newValue)) {

                    myDb.setEmail(newValue);
                    tenant.setEmail(newValue);
                    updateUser();
                    passMessage("Email successfully changed");
                    return true;

                } else {

                    passMessage("Invalid Email");
                    return false;
                }
            case "Password":

                if(validator.sameValues(newValue,
                        tenant.getPassword())){
                    passMessage("You made no changes");
                    return false;

                } else if(validator.validatePassword(newValue)) {

                    myDb.setPassword(newValue);
                    tenant.setPassword(newValue);
                    updateUser();
                    passMessage("Password successfully changed");
                    return true;

                } else {

                    passMessage("Invalid Password");
                    return false;
                }

        }
        return false;
    }
}