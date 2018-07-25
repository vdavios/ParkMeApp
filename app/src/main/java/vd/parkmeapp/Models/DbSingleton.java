package vd.parkmeapp.Models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vd.parkmeapp.Presenters.LoginPresenter;
import vd.parkmeapp.Presenters.Presenter;
import vd.parkmeapp.Presenters.SignUpPresenter;


import static android.content.ContentValues.TAG;


/**
 * Firebase methods
 */

public class DbSingleton {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private String userID;

    private static final DbSingleton ourInstance = new DbSingleton();

    public static DbSingleton getInstance() {
        return ourInstance;
    }

    private DbSingleton() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference();
        mUser = mAuth.getCurrentUser();
        //userID = mUser.getUid();
    }

    
    public void signUpUser(final String firstName, final String lastName,
                           final String email, final String password,
                           final String creditCardNumber, final String cvvNumber, final Presenter presenter) {
        authorizeUser();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            ((SignUpPresenter)presenter).failedToSignUp();

                        }
                        else if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null){
                                String userID = mAuth.getCurrentUser().getUid();
                                Log.d(TAG, "onComplete: Authstate changed: " + userID);
                                User newUser = new Tenant(firstName, lastName, email, password,
                                        creditCardNumber, cvvNumber);
                                mReference.child("Users").child(userID).setValue(newUser);
                                ((SignUpPresenter)presenter).singUpSuccessfully();
                            }

                        }

                    }
                });
    }


    public void signInUser(String email, String password, final Presenter presenter) {
        authorizeUser();
        final String TAG = "LogIn";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){

                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            ((LoginPresenter)presenter).logInFailReason("Authentication failed");

                        } else {
                            ((LoginPresenter) presenter).userLoggedIn();
                            Log.d(TAG, "signInWithEmail: Succeed");
                        }
                    }

                });

    }

    public boolean isUserLoggedIn() {
        //Set authentication Listener
        authorizeUser();
        return mAuth.getCurrentUser()!=null;
    }


    public void setAuthListener() {

        mAuth.addAuthStateListener(mAuthListener);
    }

    public void cancelAuthListener() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void authorizeUser() {

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    Log.i(TAG, "User Logged In with id " + user.getUid());

                } else {

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }


    public User getUser() {
        User tenant = new Tenant();
        getUser(tenant);
        return tenant;
    }
    private void getUser(final User tenant) {

        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser != null) {
            final String uID = mUser.getUid();
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        String firstName = data.child(uID).getValue(Tenant.class).getFirstName();
                        String lastName = data.child(uID).getValue(Tenant.class).getLastName();
                        String creditCardNumber = data.child(uID)
                                .getValue(Tenant.class).getCreditCardNumber();
                        String cVV = data.child(uID).getValue(Tenant.class).getcVV();
                        String email = data.child(uID).getValue(Tenant.class).getEmail();
                        String password = data.child(uID).getValue(Tenant.class).getPassword();
                        tenant.setFirstName(firstName);
                        tenant.setLastName(lastName);
                        tenant.setEmail(email);
                        tenant.setPassword(password);
                        tenant.setCreditCardNumber(creditCardNumber);
                        tenant.setcVV(cVV);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void setValue(String userId, String field, String value) {
        mReference.child("Users").child(userId).child(field).setValue(value);
    }


    public void signOut() {
        mAuth.signOut();
    }

    public void setFirstName(String firstName) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "firstName", firstName);
        }
    }

    public void setLastName(String lastName) {

        setValue(userID, "lastName", lastName);

    }


    public void setEmail (String email) {
        setValue(userID, "email", email);
    }

    public void setPassword (String password) {
        setValue(userID, "password", password);
    }
}