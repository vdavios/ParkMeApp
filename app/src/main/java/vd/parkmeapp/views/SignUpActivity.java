package vd.parkmeapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vd.parkmeapp.presenters.SignUpPresenter;
import vd.parkmeapp.R;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    private EditText emailText;
    private EditText passwordText;
    private EditText creditCardNumberText;
    private EditText cvvText;
    private EditText firstNameText;
    private EditText lastNameText;
    private SignUpPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        mPresenter = new SignUpPresenter(this);
        initWidgets();
        initSignUpButton();
    }

    @Override
    public void initWidgets(){
        emailText = findViewById(R.id.SingUpEmail);
        passwordText = (findViewById(R.id.SignUpPassword));
        creditCardNumberText =  (findViewById(R.id.creditCard_input));
        cvvText =  (findViewById(R.id.CVV));
        firstNameText = (findViewById(R.id.FirstName));
        lastNameText = (findViewById(R.id.LastName));
    }

    @Override
    public void initSignUpButton(){
        Button singUpButton = (findViewById(R.id.SignUp));
        singUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Convert text to string
                String firstName = firstNameText.getText().toString().trim();
                String lastName = lastNameText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String creditCardNumber = creditCardNumberText.getText().toString().trim();
                String cvvNumber =  cvvText.getText().toString().trim();

                mPresenter.signUpUser(firstName, lastName, email,
                        password, creditCardNumber, cvvNumber);
            }
        });
    }

    @Override
    public void welcomeActivity(){
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessage(String errorMessage) {
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.resumeAuthListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stopAuthListener();
    }



}