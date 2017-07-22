package vd.parkmeapp;


import android.test.mock.MockContext;
import android.widget.Toast;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ValidationTest  {
    private Validator validator;
    private String[] invalidEmails;
    private String[] validEmails;
    private String[] validPasswords;
    private String[] invalidPasswords;
    private String[] validNames;
    private String[] invalidNames;

    @Before
    public void beforeTest(){
        validator = new Validator(new MockContext());
        invalidEmails = new String[]{"InvalidEmail", ".Invalid@gmail.com", "InvalidEmail@.gmail.com"
        ,"InvalidEmail@gmail.c", "invalidEmail()@gmail.com", "InvalidEmail@#*.com",
                "Invalid..Email@gmail.com", "InvalidEmail.@gmail.com",
                "3Invalid@Email@gmail.com", "InvalidEmail@gmail.com.5u"
        ,"..InvalidEmail@gmail.com"};
        validEmails= new String[]{"validEmail@gmail.com", "valid_Email@gmail.com",
                "ValidEmail@gmail.com.uk", "valid-email@gmail.com", "valid123Email@gmail.com",
                "validEmail_@yahoo.com", "valid-123-email@gmail.com", "valid+email@gmail.com",
                "v@g.com"
        };

        validPasswords = new String[]{"123V123v", "Vasilis7", "vasiL7", "ParkMeApp15",
                "ValidPass0rd", "@vasI7", "@%#Ss7"};
        invalidPasswords = new String[]{"1", "vasilis7", "vasiliS", "VASILIS", "V1v", "@asS7",
                "aA@7"};
        validNames = new String[]{"Bill", "vAsiLis", "GeorGe"};
        invalidNames = new String[]{"Bi ll", "vasi3lis", "V@s1lis"};

    }

    @Test
    public void validEmailTest(){
        for(String email : validEmails){
            boolean isValid = validator.validateEmail(email);
            System.out.println("Email("+email+")" + " is valid : " + isValid);
            Assert.assertEquals(isValid, true);

        }
    }

  /*  @Test
    public void invalidEmailTest(){
        for(String email : invalidEmails){
            boolean isNotValid = validator.validateEmail(email);
            System.out.println("Email("+email +")"+ " is valid : "+ isNotValid);
            Assert.assertEquals(isNotValid, false);
        }
    }*/

    @Test
    public void validPasswordTest(){

        for(String password: validPasswords){
            boolean isValid = validator.validatePassword(password);
            System.out.println("Password("+password+") is valid :"+ isValid);
            Assert.assertEquals(isValid, true);
        }
    }

  /*  @Test
    public void invalidPasswords(){
        for(String password: invalidPasswords){
            boolean isNotValid = validator.validatePassword(password);
            System.out.println("Password("+password+") is valid :"+ isNotValid);
            Assert.assertEquals(isNotValid,false);
        }
    }*/

    @Test
    public void validNames(){
        for(String name: validNames){
            boolean isValid = validator.validateName(name);
            System.out.println("Name("+name+") is valid :"+ isValid);
            Assert.assertEquals(isValid, true);
        }
    }

  /*  @Test
    public void invalidNames(){
        for(String name: invalidNames){
            boolean isNotValid = validator.validateName(name);
            System.out.println("Name("+name+") is valid :"+ isNotValid);
            Assert.assertEquals(isNotValid, false);
        }
    }*/

    @After
    public void afterTest(){
        invalidEmails = null;
        validEmails = null;
        validPasswords = null;
        invalidPasswords = null;
        validNames = null;
        invalidNames = null;
        validator = null;

    }
}