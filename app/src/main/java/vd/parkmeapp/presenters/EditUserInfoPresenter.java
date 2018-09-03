package vd.parkmeapp.presenters;

import android.os.Bundle;



import vd.parkmeapp.models.DbSingleton;
import vd.parkmeapp.models.User;
import vd.parkmeapp.models.Validator;
import vd.parkmeapp.models.ValidatorImpl;
import vd.parkmeapp.R;
import vd.parkmeapp.views.EditUserInfoView;


/**
 * Presenter class for editing users info
 */

public class EditUserInfoPresenter implements Presenter{


    private EditUserInfoView mView;
    private User tenant;
    private int id;
    private String field;


    public EditUserInfoPresenter(EditUserInfoView mView, User currentUser, int rID){

        this.mView = mView;
        tenant = currentUser;
        id = rID;

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
        } else if (id == R.id.StreetNameText){
            field = "Street Name";
            mView.setFields("Street Name", tenant.getStreetName());
        } else if (id == R.id.HouseNumberText){
            field = "House Number";
            mView.setFields("House Number", tenant.getHouseNumber());
        } else if (id == R.id.PostCodeText){
            field = "Post Code";
            mView.setFields("Post Code", tenant.getPostCode());
        } else if (id == R.id.PricePerHourText){
            field = "Price Per Hour";
            mView.setFields("Price Per Hour", tenant.getPph());
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
            case "Street Name":
                if(validator.sameValues(newValue, tenant.getStreetName())){
                    passMessage("You made no changes");
                    return false;
                }
                myDb.setStreetName(newValue);
                tenant.setStreetName(newValue);
                updateUser();
                passMessage("Street Name successfully changed");
                return true;
            case "House Number":
                if(validator.sameValues(newValue, tenant.getHouseNumber())){
                    passMessage("You made no changes");
                    return false;
                }
                myDb.setHouseNumber(newValue);
                tenant.setHouseNumber(newValue);
                updateUser();
                passMessage("House Number successfully changed");
                return true;
            case "Post Code":
                if(validator.sameValues(newValue, tenant.getPostCode())){
                    passMessage("You made no changes");
                    return false;
                }
                myDb.setPostCode(newValue);
                tenant.setPostCode(newValue);
                updateUser();
                passMessage("Post Code successfully changed");
                return true;
            case "Price Per Hour":
                if(validator.sameValues(newValue, tenant.getPph())){
                    passMessage("You made no changes");
                    return false;
                }
                myDb.setPph(newValue);
                tenant.setPph(newValue);
                updateUser();
                passMessage("Price Per Hour successfully changed");
                return true;

        }
        return false;
    }
}