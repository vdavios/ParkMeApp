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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private EditText creditCardNumberText;
    private EditText cvvText;
    private EditText firstNameText;
    private EditText lastNameText;
    private Validator validator;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private UserInformation mUserInformation;
    private String email, password, creditCardNumber, cvvNumber, firstName, lastName, userId;


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
        validator = new Validator(SignUpActivity.this);
        mAuth = FirebaseAuth.getInstance();

        Button singUpButton = (Button)(findViewById(R.id.SignUp));
        singUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Convert text to string
                email = emailText.getText().toString().trim();
                password = passwordText.getText().toString().trim();
                creditCardNumber = creditCardNumberText.getText().toString().trim();
                cvvNumber =  cvvText.getText().toString().trim();
                firstName = firstNameText.getText().toString().trim();
                lastName = lastNameText.getText().toString().trim();
                if(!validateForm()){
                    return;
                }
                //Creating object for user Information after we checking that the user input
                //is valid
                mUserInformation =
                        new UserInformation(firstName, lastName, creditCardNumber, cvvNumber);
                registerUser();


            }
        });
    }


    private void parkMeAppActivity(){
        Intent intent = new Intent(this, ParkMeAppActivity.class);
        startActivity(intent);
    }

    private boolean validateForm() {


        String email = emailText.getText().toString().trim();
        return validator.validateEmail(email) && validator.validatePassword(password) &&
                validator.validateCreditCardNumber(creditCardNumber) &&
                validator.validateCVV(cvvNumber) && validator.validateName(firstName)
                && validator.validateName(lastName);
    }


    private void registerUser(){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, R.string.signUpFailed,
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            mFirebaseDatabase = FirebaseDatabase.getInstance();
                            mReference = mFirebaseDatabase.getReference();
                            FirebaseUser user = mAuth.getCurrentUser();
                            userId = user.getUid();
                            mReference.child("Users").child(userId).setValue(mUserInformation);
                            parkMeAppActivity();
                            Toast.makeText(SignUpActivity.this, R.string.welcomeMessage,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




}