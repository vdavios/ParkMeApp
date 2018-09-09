package vd.parkmeapp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;

/**
 *
 */

public class UserTest {
    private User myUser;
    private User tenant;
    // Constructor
    //       String firstName, String lastName, String email,
    //                  String password, String creditCardNumber, String cVV,
    //                  String streetName, String houseNumber, String postCode, String pph,
    //                  String hasParking, String isRented, String uId, String isHeRenting,
    //                  String usersIdParkingThatHeIsRenting,
    //                  double latOfHisParking,
    //                  double lngOfHisParking, double latOfParkingThatHeIsCurrentlyRenting,
    //                  double lngOfParkingThatHeIsCurrentlyRentinggThatHeIsCurrentlyRenting

    @Before
    public void beforeTest(){
        myUser = new Tenant();
        tenant = new Tenant("Test","Test", "test@test.com",
                "123456bB", "1234567890123456",
                "123","ddd", "12", "12412",
                "12", "no", "no", "sdjaskenk1k123@",
                "no","",
                0d,0d,0d,0d);
    }


    @Test
    public void firstNameTestForNull(){
        Assert.assertEquals(null , myUser.getFirstName());
    }

    @Test
    public void firstNameTestSuccess(){
        Assert.assertEquals("Test", tenant.getFirstName());
    }

    @Test
    public void LastNameTestForNull(){
        Assert.assertEquals(null , myUser.getLastName());
    }

    @Test
    public void LastNameTestSuccess(){
        Assert.assertEquals("Test", tenant.getLastName());
    }

    @Test
    public void emailTestForNull(){
        Assert.assertEquals(null , myUser.getEmail());
    }

    @Test
    public void emailTestSuccess(){
        Assert.assertEquals("test@test.com", tenant.getEmail());
    }


    @Test
    public void passwordTestForNull(){
        Assert.assertEquals(null , myUser.getPassword());
    }

    @Test
    public void passwordTestSuccess(){
        Assert.assertEquals("123456bB", tenant.getPassword());
    }

    @Test
    public void creditCardNumberTestForNull(){
        Assert.assertEquals(null , myUser.getCreditCardNumber());
    }

    @Test
    public void creditCardNumberSuccess(){
        Assert.assertEquals("1234567890123456", tenant.getCreditCardNumber());
    }

    @Test
    public void cVVTestForNull(){
        Assert.assertEquals(null , myUser.getcVV());
    }

    @Test
    public void cVVTestSuccess(){
        Assert.assertEquals("123", tenant.getcVV());
    }

    @Test
    public void streetNameTestForNull(){
        Assert.assertEquals(null , myUser.getStreetName());
    }

    @Test
    public void streetNameTestSuccess(){
        Assert.assertEquals("ddd", tenant.getStreetName());
    }

    @Test
    public void houseNumberTestForNull(){
        Assert.assertEquals(null , myUser.getHouseNumber());
    }

    @Test
    public void houseNumberTestSuccess(){
        Assert.assertEquals("12", tenant.getHouseNumber());
    }

    @Test
    public void postCodeTestForNull(){
        Assert.assertEquals(null , myUser.getPostCode());
    }


    @Test
    public void postCodeTestSuccess(){
        Assert.assertEquals("12412", tenant.getPostCode());
    }

    @Test
    public void pphTestForNull(){
        Assert.assertEquals(null , myUser.getPph());
    }

    @Test
    public void pphTestSuccess(){
        Assert.assertEquals("12", tenant.getPph());
    }

    @Test
    public void isRentedTestForNull(){
        Assert.assertEquals(null , myUser.getRented());
    }

    @Test
    public void isRentedTestSuccess(){
        Assert.assertEquals("no", tenant.getRented());
    }

    @Test
    public void hasParkingTestForNull(){
        Assert.assertEquals(null , myUser.getHasParking());
    }

    @Test
    public void hasParkingTestSuccess(){
        Assert.assertEquals("no", tenant.getHasParking());
    }

    @Test
    public void uIdTestForNull(){
        Assert.assertEquals(null , myUser.getUid());
    }

    @Test
    public void uIdTestSuccess(){
        Assert.assertEquals("sdjaskenk1k123@", tenant.getUid());
    }

    @Test
    public void isHeRentingTestForNull(){
        Assert.assertEquals(null , myUser.getIsHeRenting());
    }

    @Test
    public void isHeRentingTestSuccess(){
        Assert.assertEquals("no" , tenant.getIsHeRenting());
    }

    @Test
    public void usersIdParkingThatHeIsRentingTestForNull(){
        Assert.assertEquals(null , myUser.getUsersIdParkingThatHeIsRenting());
    }

    @Test
    public void usersIdParkingThatHeIsRentingTestSuccess(){
        Assert.assertEquals("" , tenant.getUsersIdParkingThatHeIsRenting());
    }


    @After
    public void afterTest(){
        myUser = null;
        tenant = null;

    }
}
