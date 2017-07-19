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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        Button logInButton = (Button) findViewById(R.id.LogInButton);
        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                parkMeAppMainActivity(v);
            }
        });

        Button newUserButton = (Button) findViewById(R.id.SingUpButton);
        newUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpActivity(v);

            }
        });

    }

    private void parkMeAppMainActivity(View v) {
        Intent intent = new Intent(getApplicationContext(), ParkMeAppMainActivity.class);
        startActivity(intent);
    }

    private void signUpActivity(View v) {
        Intent intent = new Intent(getApplicationContext(), SingUpActivity.class);
        startActivity(intent);
    }
}


