package vd.parkmeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText creditCardNumberText;
    private EditText cvvText;
    private EditText firstNameText;
    private EditText lastNameText;
    private Validator validator;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        emailText = (EditText)(findViewById(R.id.SingUpEmail));
        passwordText = (EditText)(findViewById(R.id.SignUpPassword));
        creditCardNumberText = (EditText) (findViewById(R.id.creditCard_input));
        cvvText = (EditText) (findViewById(R.id.CVV));
        firstNameText = (EditText) (findViewById(R.id.FirstName));
        lastNameText = (EditText) (findViewById(R.id.LastName));
        validator = new Validator();
        mAuth = FirebaseAuth.getInstance();


        Button singUpButton = (Button)(findViewById(R.id.SignUp));
        singUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!validateForm()){
                    return;
                }
                registerUser();
                //parkMeAppMainActivity();

            }
        });
    }


    private void parkMeAppMainActivity(){
        Intent intent = new Intent(this, ParkMeAppMainActivity.class);
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String creditCardNumber = creditCardNumberText.getText().toString().trim();
        String cvvNumber =  cvvText.getText().toString().trim();
        String firstName = firstNameText.getText().toString().trim();
        String lastName = lastNameText.getText().toString().trim();

        if (!validator.validateEmail(email)) {
            Toast.makeText(getApplicationContext(), R.string.invalidEmail,
                    Toast.LENGTH_SHORT)
                    .show();
            valid = false;
        } else if (!validator.validatePassword(password)) {
            Toast.makeText(getApplicationContext(), R.string.passwordInstructions,
                    Toast.LENGTH_SHORT)
                    .show();
            valid = false;
        } else if (!validator.validateCreditCardNumber(creditCardNumber)) {
            Toast.makeText(getApplicationContext(), R.string.invalidCreditCard,
                    Toast.LENGTH_SHORT)
                    .show();
            valid = false;
        } else if (!validator.validateCVV(cvvNumber)) {
            Toast.makeText(getApplicationContext(), R.string.invalidCVV,
                    Toast.LENGTH_SHORT)
                    .show();
            valid = false;
        } else if (!validator.validateName(firstName)) {
            Toast.makeText(getApplicationContext(), R.string.invalidFirstName,
                    Toast.LENGTH_SHORT)
                    .show();
            valid = false;
        } else if (!validator.validateName(lastName)) {
            Toast.makeText(getApplicationContext(), R.string.invalidLastName,
                    Toast.LENGTH_SHORT)
                    .show();
            valid = false;
        }

        return valid;
    }

    private void createAccount(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password);


    }

    private void registerUser(){
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." ,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            parkMeAppMainActivity();
                            Toast.makeText(SignUpActivity.this, "Success!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}