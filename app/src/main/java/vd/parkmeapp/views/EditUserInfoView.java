package vd.parkmeapp.views;

import vd.parkmeapp.models.User;

/**
 * Created by vasileiosdavios on 8/5/17.
 */

public interface EditUserInfoView extends View{


    void setFields(String labelName, String firstName);

    void updateUser(User user);
}