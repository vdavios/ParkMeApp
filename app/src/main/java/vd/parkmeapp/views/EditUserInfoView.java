package vd.parkmeapp.views;

import vd.parkmeapp.models.User;


public interface EditUserInfoView extends View{


    void setFields(String labelName, String firstName);

    void updateUser(User user);
}