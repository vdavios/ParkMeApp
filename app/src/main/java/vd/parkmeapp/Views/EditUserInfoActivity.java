package vd.parkmeapp.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vd.parkmeapp.Models.Tenant;
import vd.parkmeapp.Models.Validator;
import vd.parkmeapp.R;


public class EditUserInfoActivity extends AppCompatActivity {
    private String value, newValue, child, property;
    private Validator validator;
    private int rId;
    private Tenant tenant;
    private static final String TAG ="Fail to update email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        final EditText valueText = (EditText) (findViewById(R.id.value_Input));
        final TextView label = (TextView) (findViewById(R.id.Label));
        // Setting up validator
       // validator = new ValidatorImpl(this);
        //Get value from intent
        getValues();


        //Checking to find out what view was clicked from the user
        if(rId == R.id.firstNameEditProfile) {
            property = "First Name";
            value = tenant.getFirstName();
            child = "firstName";
            label.setText(R.string.firstName);

        } else if (rId == R.id.lastNameEditProfile){
            property = "Last Name";
            value = tenant.getLastName();
            child = "lastName";
            label.setText(R.string.lastName);

        } else if (rId == R.id.emailEditProfile) {
            property = "Email";
            value = tenant.getEmail();
            child = "email";
            label.setText(R.string.email);

        } else if (rId == R.id.passwordEditProfile) {
            property = "Password";
            value = tenant.getPassword();
            child = "password";
            label.setText(R.string.password);
        }

        valueText.setText(value);
        FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        final DatabaseReference myRef= mDb.getReference();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser fUser = mAuth.getCurrentUser();
        final String userId = fUser.getUid();



        valueText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    newValue = valueText.getText().toString().trim();
                    if(isNewValueValid()){
                        myRef.child("Users").child(userId).child(child).setValue(newValue);
                        userProfileActivity();
                        return true;
                    }

                }
                return false;
            }
        });


        Button saveButton = (Button) (findViewById(R.id.saveButton));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newValue = valueText.getText().toString().trim();
                Toast.makeText(getApplicationContext(), "I am inside button", Toast.LENGTH_SHORT)
                        .show();
                if (isNewValueValid()) {
                    Toast.makeText(getApplicationContext(), "Valid", Toast.LENGTH_SHORT)
                            .show();
                    //myDb.updateValues("Users", userId, child, newValue);

                    if (child.equals("email")) {
                        AuthCredential credential = EmailAuthProvider.getCredential(tenant.getEmail()
                                , tenant.getPassword());
                        fUser.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            fUser.updateEmail(value)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                myRef.child("Users")
                                                                        .child(userId).child(child)
                                                                        .setValue(newValue);
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Task is succesfull", Toast.LENGTH_SHORT)
                                                                        .show();
                                                                myRef.child("Users").child(userId).child(child).setValue(newValue);
                                                            } else {
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Skato", Toast.LENGTH_SHORT)
                                                                        .show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Log.d(TAG, "Error auth failed");
                                        }
                                    }
                                });





                        // myRef.child("Users").child(userId).child(child).setValue(newValue);
                        userProfileActivity();
                    }
                }

            }

        });

    }

    private boolean isNewValueValid() {

        /*if(validator.sameValues(newValue, value)) {
            return  false;
        } else {*/
            switch (property) {
                case "First Name":
                    if (!validator.validateName(newValue)) {
                        return false;
                    } else {
                        tenant.setFirstName(newValue);
                        return true;
                    }
                case "Last Name":
                    if (!validator.validateName(newValue)) {
                        return false;
                    } else {
                        tenant.setLastName(newValue);
                        return true;
                    }
                case "Email":
                    if (!validator.validateEmail(newValue)) {
                        return false;
                    } else {
                        tenant.setEmail(newValue);
                        return true;
                    }
                case "Password":
                    if (!validator.validatePassword(newValue)) {
                        return false;
                    } else {
                        tenant.setPassword(newValue);
                        return true;
                    }
            //}
        }



        return false;
    }

    private void getValues(){
        rId = getIntent().getIntExtra("R.id", 1);
        tenant = getIntent().getParcelableExtra("User");


    }


    private void userProfileActivity(){
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("User", tenant);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        userProfileActivity();
    }
}
