package vd.parkmeapp;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vd.parkmeapp.models.Validator;
import vd.parkmeapp.models.ValidatorImpl;

public class ValidationTest  {
    private Validator validator;
    private String[] invalidEmails;
    private String[] validEmails;
    private String[] validPasswords;
    private String[] invalidPasswords;
    private String[] validNames;
    private String[] invalidNames;
    private String[] validCreditCardNumbers;
    private String[] invalidCreditCardNumbers;
    private String[] validCvvNumbers;
    private String[] invalidCvvNumbers;

    @Before
    public void beforeTest(){
        validator = new ValidatorImpl();
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

        validCreditCardNumbers = new String[]{
                "4892683405112577", "3608924468231477", "6828044450130178",
                "8709422104837261", "5238383903790177", "1015935953643788", "0374987926096258",
                "0498252197828928", "2268562154834472", "0719503284273796"
        };

        invalidCreditCardNumbers = new String[]{
                "56036", "30362", "36624", "70136", "14249", "69062", "30180", "80429",
                "02432", "33687"
        };

        validCvvNumbers = new String[] {
                "369",
                "034",
                "592",
                "942",
                "017",
                "822",
                "320",
                "747",
                "668",
                "231"
        };

        invalidCvvNumbers = new String[]{
                "1234567891123456", "4892683405112577", "3608924468231477", "6828044450130178",
                "8709422104837261", "5238383903790177", "1015935953643788", "0374987926096258",
                "0498252197828928", "2268562154834472", "0719503284273796"
        };


    }

    @Test
    public void validEmailTest(){
        for(String email : validEmails){
            boolean isValid = validator.validateEmail(email);
            Assert.assertEquals(isValid, true);

        }
    }

    @Test
    public void invalidEmailTest(){
        for(String email : invalidEmails){
            boolean isNotValid = validator.validateEmail(email);
            Assert.assertEquals(isNotValid, false);
        }
    }

    @Test
    public void validPasswordTest(){

        for(String password: validPasswords){
            boolean isValid = validator.validatePassword(password);
            Assert.assertEquals(isValid, true);
        }
    }

    @Test
    public void invalidPasswords(){
        for(String password: invalidPasswords){
            boolean isNotValid = validator.validatePassword(password);
            Assert.assertEquals(isNotValid,false);
        }
    }

    @Test
    public void validNames(){
        for(String name: validNames){
            boolean isValid = validator.validateName(name);
            Assert.assertEquals(isValid, true);
        }
    }

    @Test
    public void invalidNames(){
        for(String name: invalidNames){
            boolean isNotValid = validator.validateName(name);
            Assert.assertEquals(isNotValid, false);
        }
    }

    @Test
    public void validCreditCardNumbers(){
        for(String creditCardNumber: validCreditCardNumbers){
            boolean isValid = validator.validateCreditCardNumber(creditCardNumber);
            Assert.assertEquals(isValid, true);
        }
    }

    @Test
    public void invalidCreditCardNumbers(){
        for(String creditCardNumber: invalidCreditCardNumbers){
            boolean isNotValid = validator.validateCreditCardNumber(creditCardNumber);
            Assert.assertEquals(isNotValid, false);
        }
    }

    @Test
    public void validCvvNumbers(){
        for(String cvvNumber: validCvvNumbers){
            boolean isValid = validator.validateCVV(cvvNumber);
            Assert.assertEquals(isValid, true);
        }
    }

    @Test
    public void invalidCvvNumbers(){
        for(String cvvNumber: invalidCvvNumbers){
            boolean isValid = validator.validateCVV(cvvNumber);
            Assert.assertEquals(isValid, false);
        }
    }

    @Test
    public void checkingForSameValuesSuccess(){
        for(String creditCardNumber: validCreditCardNumbers){
            boolean sameValue = validator.sameValues(creditCardNumber,creditCardNumber);
            Assert.assertEquals(sameValue, true);
        }
    }

    @Test
    public void checkingForSameValuesFailure(){
        for(int i = 0; i< validCreditCardNumbers.length ; i ++){
            boolean sameValue = validator.sameValues(validCreditCardNumbers[i], invalidCreditCardNumbers[i]);
            Assert.assertEquals(sameValue, false);
        }
    }

    @After
    public void afterTest(){
        invalidEmails = null;
        validEmails = null;
        validPasswords = null;
        invalidPasswords = null;
        validNames = null;
        invalidNames = null;
        validCreditCardNumbers = null;
        invalidCreditCardNumbers = null;
        validCvvNumbers = null;
        invalidCvvNumbers = null;
        validator = null;

    }
}
