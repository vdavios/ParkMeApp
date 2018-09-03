package vd.parkmeapp.models;

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

import java.util.ArrayList;

import vd.parkmeapp.presenters.LoadingDataPresenter;
import vd.parkmeapp.presenters.LoginPresenter;
import vd.parkmeapp.presenters.Presenter;
import vd.parkmeapp.presenters.SignUpPresenter;
import vd.parkmeapp.presenters.WelcomeActivityPresenter;


import static android.content.ContentValues.TAG;


/**
 * Firebase methods
 */

public class DbSingleton {

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static DbSingleton ourInstance;

    public static DbSingleton getInstance() {
        if(ourInstance == null){
            ourInstance = new DbSingleton();
        }
        return ourInstance;
    }

    private DbSingleton() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference();
    }


    public void fetchData(final LoadingDataPresenter lDPresenter, final User currentUser){

        mReference.addValueEventListener(new ValueEventListener() {
            int count=0;
            ArrayList<Tenant> myArrL = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 DataSnapshot usersSnapShot = dataSnapshot.child("Users");

                 //Finds the number of children in my database
                 Long chCount = usersSnapShot.getChildrenCount();
                 Log.d("Children Count: ", chCount.toString());
                 Iterable<DataSnapshot> usersChildren = usersSnapShot.getChildren();
                 for(DataSnapshot user: usersChildren){
                     count++;
                     Tenant tenant = user.getValue(Tenant.class);
                     String usrFn = tenant.getFirstName();
                     String usrLastName = tenant.getLastName();

                     if(!currentUser.getFirstName().equals(usrFn)
                             && !currentUser.getLastName().equals(usrLastName)){
                         Log.d("Added: ", tenant.toString());
                         if(!myArrL.contains(tenant)){
                             //Log.d("Added: ", tenant.toString());
                             myArrL.add(tenant);
                         }
                     }

                     if(count>= chCount-1){
                         loadingCompleted(myArrL, lDPresenter);
                     }
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Loading data completed passing the results to the presenter so we can move to the next activity
    private void loadingCompleted(ArrayList<Tenant> results, LoadingDataPresenter ldPresenter){
        ldPresenter.resultsLoaded(results);
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
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null){
                                String userID = mAuth.getCurrentUser().getUid();
                                Log.d(TAG, "onComplete: Authstate changed: " + userID);
                                User newUser = new Tenant(firstName, lastName, email, password,
                                        creditCardNumber, cvvNumber,
                                        "Please add the Street Name",
                                        "Please add the House Number",
                                        "Please add the Post Code",
                                        "Please add Price per Hour",
                                        "no","no");
                                mReference.child("Users").child(userID).setValue(newUser);
                                ((SignUpPresenter)presenter).singUpSuccessfully();
                            }
                        } else {

                            if(task.getException()!= null){
                                Log.i("error is:", task.getException().getMessage());
                                ((SignUpPresenter)presenter).failedToSignUp(task.getException().getMessage());
                            } else {
                                ((SignUpPresenter)presenter).failedToSignUp("Sign Up failed");
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

                            Log.w(TAG, "Sign In fail reason: ", task.getException());

                            ((LoginPresenter)presenter).
                                    logInFailReason("Authentication failed");



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

    //Get users data
    public void getUser(WelcomeActivityPresenter presenter) {

        getUser(new Tenant(),presenter);


    }

    private Boolean flag = false;
    private void getUser(final User tenant, final WelcomeActivityPresenter presenter) {
        Log.d("Updating Tenant", "Pulling for db");
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser != null) {
            final String uID = mUser.getUid();
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        Tenant tenant = data.child(uID).getValue(Tenant.class);
                        //Required field for our users
                        String firstName = tenant.getFirstName();
                        String lastName = tenant.getLastName();
                        String creditCardNumber = tenant.getCreditCardNumber();
                        String cVV = tenant.getcVV();
                        String email = tenant.getEmail();
                        String password = tenant.getPassword();
                        String streetName = tenant.getStreetName();
                        String houseNumber = tenant.getHouseNumber();
                        String postCode = tenant.getPostCode();
                        String pph = tenant.getPph();
                        String rented = tenant.getRented();
                        String hasParking = tenant.getHasParking();

                        tenant.setFirstName(firstName);
                        tenant.setLastName(lastName);
                        tenant.setEmail(email);
                        tenant.setPassword(password);
                        tenant.setCreditCardNumber(creditCardNumber);
                        tenant.setcVV(cVV);
                        tenant.setStreetName(streetName);
                        tenant.setHouseNumber(houseNumber);
                        tenant.setPostCode(postCode);
                        tenant.setPph(pph);
                        tenant.setHasParking(hasParking);
                        tenant.setRented(rented);
                        Log.d("Flag value: ", flag.toString());
                        //Avoid going to ParkMeApp activity whenever a value is change
                        //It will go to ParkMeAppActivity only when loads data for the first time
                        if(!flag){
                            flag = true;
                            Log.d("Going to: ", "ParkMeAppActivity");
                            presenter.loadingUsersDataCompleted(tenant);

                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    String reason  = databaseError.getMessage();
                    Log.w("Error: ", reason );
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
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "lastName", lastName);
        }


    }


    public void setEmail (String email) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "email", email);
        }

    }

    public void setPassword (String password) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "password", password);
        }

    }

    public void setStreetName(String streetName) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "streetName", streetName);
        }
    }

    public void setHouseNumber(String houseNumber) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "houseNumber", houseNumber);
        }
    }

    public void setPostCode(String postCode) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "postCode", postCode);
        }
    }

    public void setPph(String pph) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "pph", pph);
        }
    }

    public void setRented(String rented) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "rented", rented);
        }
    }

    public void setHasParking(String hasParking){
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null){
            String userId = mUser.getUid();
            setValue(userId,"hasParking", hasParking);
        }
    }
}