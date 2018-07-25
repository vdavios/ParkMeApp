package vd.parkmeapp.Views;

import vd.parkmeapp.Models.User;

/**
 * Created by vasileiosdavios on 8/5/17.
 */

public interface EditUserInfoView {
    void showMessage(String message);

    void setFields(String labelName, String firstName);

    void updateUser(User user);
}