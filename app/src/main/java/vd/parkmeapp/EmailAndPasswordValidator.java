package vd.parkmeapp;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAndPasswordValidator {

    private Pattern emailPattern;
    private Pattern passPattern;
    private Matcher matcher;
    static final int size = 5;

    public EmailAndPasswordValidator(){
        emailPattern = Pattern.compile(EMAIL_PATTERN);
        passPattern = Pattern.compile(PASSWORD_PATTERN);
    }

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //At least 6 characters, one upper Case char, one lowerCase and one number
    private static final String PASSWORD_PATTERN =
            "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$";

    public boolean emailValidator(String inputEmail){

        matcher = emailPattern.matcher(inputEmail);
        return matcher.matches();

    }

    public boolean passwordValidator(String inputPass){
        matcher = passPattern.matcher(inputPass);
        if(inputPass.length() > size && matcher.matches()){

            return true;
        }

        return false;

    }
}
