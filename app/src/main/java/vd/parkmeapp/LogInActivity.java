package vd.parkmeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    private Button newUserButton;
    private Button logInButton;
    private EditText email;
    private EditText password;
    private EmailAndPasswordValidator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = (EditText) findViewById(R.id.email_input);
        password = (EditText) findViewById(R.id.password_input);
        validator = new EmailAndPasswordValidator();

        logInButton = (Button) findViewById(R.id.LogInButton);
        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String emailSt = email.getText().toString().trim();
                String passSt = password.getText().toString().trim();
                if(!validator.emailValidator(emailSt)){
                    Toast.makeText(getApplicationContext(), R.string.invalidEmail, Toast.LENGTH_SHORT)
                            .show();

                } else if (!validator.passwordValidator(passSt)) {
                    Toast.makeText(getApplicationContext(), R.string.passwordSize, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    parkMeAppMainActivity(v);
                }
            }
        });

        newUserButton = (Button) findViewById(R.id.SingUpButton);
        newUserButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Toast.makeText(getApplicationContext(),R.string.email, Toast.LENGTH_LONG)
                            .show();
                }
        });

    }

    private void parkMeAppMainActivity(View v) {
        Intent intent = new Intent(this, ParkMeAppMainActivity.class);
        startActivity(intent);
    }
}
