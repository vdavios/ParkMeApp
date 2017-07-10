package vd.parkmeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    Button newUserButton;
    Button logInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        logInButton = (Button) findViewById(R.id.LogInButton);
        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                parkMeAppMainAct(v);
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


    private void parkMeAppMainAct(View v) {
        Intent intent = new Intent(this, ParkMeAppMainActivity.class);
        startActivity(intent);
    }
}
