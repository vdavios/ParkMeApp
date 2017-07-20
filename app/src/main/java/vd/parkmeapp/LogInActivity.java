package vd.parkmeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText inputEmail, inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            parkMeAppMainActivity();
        }

        setContentView(R.layout.login);
        inputEmail = (EditText) (findViewById(R.id.email_input));
        inputPassword = (EditText) (findViewById(R.id.password_input));

        Button logInButton = (Button) findViewById(R.id.LogInButton);
        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                userAuthentication();
            }
        });

        Button newUserButton = (Button) findViewById(R.id.SingUpButton);
        newUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpActivity();

            }
        });

    }


    private void userAuthentication(){

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please enter your password",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    R.string.authenticationFailed, Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            parkMeAppMainActivity();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed(){
        //Sign in page do nothing
    }

    private void parkMeAppMainActivity() {
        Intent intent = new Intent(getApplicationContext(), ParkMeAppMainActivity.class);
        startActivity(intent);
    }

    private void signUpActivity() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }
}


