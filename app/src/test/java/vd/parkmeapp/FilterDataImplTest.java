package vd.parkmeapp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vd.parkmeapp.models.FilterData;
import vd.parkmeapp.models.FilterDataImpl;
import vd.parkmeapp.models.Tenant;
import vd.parkmeapp.models.User;

public class FilterDataImplTest {

    //public Tenant(String firstName, String lastName, String email,
    //                  String password, String creditCardNumber, String cVV,
    //                  String streetName, String houseNumber, String postCode, String pph,
    //                  String hasParking, String isRented, String uId, String isHeRenting,
    //                  String usersIdParkingThatHeIsRenting,
    //                  double latOfHisParking,
    //                  double lngOfHisParking, double latOfParkingThatHeIsCurrentlyRenting,
    //                  double lngOfParkingThatHeIsCurrentlyRenting)

    private User tenant;
    private User parkingOwner;
    private FilterData filterData;

    @Before
    public void beforeTest(){
        tenant = new Tenant("vas", "vas", "",
                "", "", "", "", "",
                "", "", "", "", "", "",
                "",0d,0d,
                0d,0d);
        parkingOwner = new Tenant();
        filterData = new FilterDataImpl();
    }

    @Test
    public void testingUsersWithDifferentNamesAndNotRentedParking1Success(){
        parkingOwner.setFirstName("George");
        parkingOwner.setLastName("George");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("no");
        Assert.assertTrue(filterData.filter(tenant, parkingOwner));

    }


    @Test
    public void testingUsersWithDifferentNamesAndNotRentedParking2Success(){
        parkingOwner.setFirstName("Kat");
        parkingOwner.setLastName("Kat");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("no");
        Assert.assertTrue(filterData.filter(tenant, parkingOwner));

    }

    @Test
    public void testingUsersWithDifferentNamesAndNotRentedParking3Success(){
        parkingOwner.setFirstName("Rob");
        parkingOwner.setLastName("Rob");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("no");
        Assert.assertTrue(filterData.filter(tenant, parkingOwner));

    }

    @Test
    public void testingUsersWithAlmostSameNamesAndNotRentedParkingSuccess(){
        parkingOwner.setFirstName("Vasi");
        parkingOwner.setLastName("Vasi");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("no");
        Assert.assertTrue(filterData.filter(tenant, parkingOwner));

    }

    @Test
    public void testingUsersWithAlmostSameNames2AndNotRentedParkingSuccess(){
        parkingOwner.setFirstName("Vas");
        parkingOwner.setLastName("Vas");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("no");
        Assert.assertTrue(filterData.filter(tenant, parkingOwner));

    }
    @Test
    public void testingUsersWithSameNamesAndNotRentedParkingFailure(){
        parkingOwner.setFirstName("Vas");
        parkingOwner.setLastName("Vas");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }

    @Test
    public void testingUsersWithDifferentNamesAndRentedParking1Failure(){
        parkingOwner.setFirstName("Vas");
        parkingOwner.setLastName("Vas");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }

    @Test
    public void testingUsersWithDifferentNamesAndRentedParking2Failure(){
        parkingOwner.setFirstName("george");
        parkingOwner.setLastName("george");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }

    @Test
    public void testingUsersWithDifferentNamesAndRentedParking3Failure(){
        parkingOwner.setFirstName("kat");
        parkingOwner.setLastName("kat");
        parkingOwner.setHasParking("yes");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }

    @Test
    public void testingUsersWithDifferentNamesWithoutParking1Failure(){
        parkingOwner.setFirstName("kos");
        parkingOwner.setLastName("kos");
        parkingOwner.setHasParking("no");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }

    @Test
    public void testingUsersWithDifferentNamesWithoutParking2Failure(){
        parkingOwner.setFirstName("George");
        parkingOwner.setLastName("George");
        parkingOwner.setHasParking("no");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }

    @Test
    public void testingUsersWithDifferentNamesWithoutParking3Failure(){
        parkingOwner.setFirstName("Kat");
        parkingOwner.setLastName("Kat");
        parkingOwner.setHasParking("no");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }


    @Test
    public void testingUsersWithAlmostSameNamesWithoutParking1Failure(){
        parkingOwner.setFirstName("Vas");
        parkingOwner.setLastName("Vas");
        parkingOwner.setHasParking("no");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }


    @Test
    public void testingUsersWithAlmostSameNamesWithoutParking2Failure(){
        parkingOwner.setFirstName("Vasi");
        parkingOwner.setLastName("Vasi");
        parkingOwner.setHasParking("no");
        parkingOwner.setRented("yes");
        Assert.assertFalse(filterData.filter(tenant, parkingOwner));
    }

    @After
    public void afterTest(){
        tenant = null;
        parkingOwner = null;
    }




}
