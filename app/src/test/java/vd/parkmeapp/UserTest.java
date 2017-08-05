package vd.parkmeapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vd.parkmeapp.Models.Tenant;
import vd.parkmeapp.Models.User;

/**
 *
 */

public class UserTest {
    private User myUser;

    @Before
    public void beforeTest(){
        myUser = new Tenant();
    }


    @Test
    public void testForNull(){
        Assert.assertEquals(null , myUser.getFirstName());
    }
}
