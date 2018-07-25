package vd.parkmeapp.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vd.parkmeapp.Presenters.LoginPresenter;
import vd.parkmeapp.R;


public class LogInActivity extends AppCompatActivity implements LoginView {

    private EditText inputEmail, inputPassword;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Creating the presenter
        mPresenter = new LoginPresenter(this);

        //if the user is already logged in and authenticated from a previous session
        //then when start the main activity
        mPresenter.isLoggedIn();


        setContentView(R.layout.login);

        inputEmail = (EditText) (findViewById(R.id.email_input));
        inputPassword = (EditText) (findViewById(R.id.password_input));


        Button logInButton = (Button) findViewById(R.id.LogInButton);
        logInButton.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v) {

                userSignIn();

            }
        });

        Button newUserButton = (Button) findViewById(R.id.SingUpButton);
        newUserButton.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(android.view.View v){
                signUpActivity();

            }
        });

    }

    @Override
    public void userSignIn(){

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        mPresenter.logIn(email, password);

    }

    @Override
    public void onBackPressed(){
        //Sign in page do nothing
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



    @Override
    public void signUpActivity() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void mainActivity() {
        Intent intent = new Intent(getApplicationContext(), ParkMeAppActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }



}