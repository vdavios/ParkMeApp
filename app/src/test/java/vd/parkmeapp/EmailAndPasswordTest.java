package vd.parkmeapp;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailAndPasswordTest {
    private EmailAndPasswordValidator validator;
    private String[] invalidEmails;
    private String[] validEmails;
    private String[] validPasswords;
    private String[] invalidPasswords;

    @Before
    public void beforeTest(){
        validator = new EmailAndPasswordValidator();
        invalidEmails = new String[]{"InvalidEmail", ".Invalid@gmail.com", "InvalidEmail@.gmail.com"
        ,"InvalidEmail@gmail.c", "invalidEmail()@gmail.com", "InvalidEmail@#*.com",
                "Invalid..Email@gmail.com", "InvalidEmail.@gmail.com",
                "Invalid@Email@gmail.com", "InvalidEmail@gmail.com.5u"
        ,"..InvalidEmail@gmail.com"};
        validEmails= new String[]{"validEmail@gmail.com", "valid_Email@gmail.com",
                "ValidEmail@gmail.com.uk", "valid-email@gmail.com", "valid123Email@gmail.com",
                "validEmail_@yahoo.com", "valid-123-email@gmail.com", "valid+email@gmail.com"
        };

        validPasswords = new String[]{"123V123v", "Vasilis7", "vasiL7", "ParkMeApp15",
                "ValidPass0rd", "@vasI7", "@%#Ss7"};
        invalidPasswords = new String[]{"1", "vasilis7", "vasiliS", "VASILIS", "V1v", "@asS7",
                "aA@7"};
    }

    @Test
    public void validEmailTest(){
        for(String email : validEmails){
            boolean isValid = validator.emailValidator(email);
            System.out.println("Email("+email+")" + " is valid : " + isValid);
            Assert.assertEquals(isValid, true);

        }
    }

    @Test
    public void invalidEmailTest(){
        for(String email : invalidEmails){
            boolean isNotValid = validator.emailValidator(email);
            System.out.println("Email("+email +")"+ " is valid : "+ isNotValid);
            Assert.assertEquals(isNotValid, false);
        }
    }

    @Test
    public void validPasswordTest(){
        for(String password: validPasswords){
            boolean isValid = validator.passwordValidator(password);
            System.out.println("Password("+password+") is valid :"+ isValid);
            Assert.assertEquals(isValid, true);
        }
    }

    @Test
    public void setInvalidPasswords(){
        for(String password: invalidPasswords){
            boolean isNotValid = validator.passwordValidator(password);
            System.out.println("Password("+password+") is valid :"+ isNotValid);
            Assert.assertEquals(isNotValid,false);
        }
    }

    @After
    public void afterTest(){
        invalidEmails = null;
        validEmails = null;
        validPasswords = null;
        invalidPasswords = null;
        validator = null;

    }
}
