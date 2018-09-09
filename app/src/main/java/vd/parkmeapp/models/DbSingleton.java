package vd.parkmeapp.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
import vd.parkmeapp.views.WelcomeActivity;


import static android.content.ContentValues.TAG;


/**
 * Firebase methods
 */

public class DbSingleton {

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference connectivityReference;

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

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {

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
                     Log.d("Inside DataSnapshot: ", "passed");
                     count++;
                     Tenant tenant = user.getValue(Tenant.class);
                     String usrFn = tenant.getFirstName();
                     String usrLastName = tenant.getLastName();
                     Log.d("Compairing: ", "First Name "+usrFn+" with "+ currentUser.getFirstName()+"\n");
                     Log.d("Compairing: ", "Last Name "+usrLastName+" with "+ currentUser.getLastName()+"\n");
                     Boolean flag = tenant.getHasParking().equals("yes");
                     Boolean flag1 = tenant.getRented().equals("no");

                     Log.d("Returns : ",flag.toString()+"\n");
                     Log.d("Returns : ",flag1.toString()+"\n");

                     if(!currentUser.getFirstName().equals(usrFn)
                             && !currentUser.getLastName().equals(usrLastName)
                             && tenant.getHasParking().equals("yes")
                             && tenant.getRented().equals("no")){
                            Log.d("FirstCondition: ", "Passed"+"\n");
                         if(!myArrL.contains(tenant)){
                             Log.d("Added: ", tenant.toString()+"\n");
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
                lDPresenter.moveToInternetFailureActivity();
            }
        });
    }

    // Loading data completed passing the results to the presenter so we can move to the next activity
    private void loadingCompleted(ArrayList<Tenant> results, LoadingDataPresenter ldPresenter){
        ldPresenter.resultsLoaded(results);
    }

    public void signUpUser(final String firstName, final String lastName,
                           final String email, final String password,
                           final String creditCardNumber, final String cvvNumber,
                           final String streetName,final String houseNumber,
                           final String postCode,
                           final String pricePerHour,
                           final Presenter presenter) {
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
                                        streetName,
                                        houseNumber,
                                        postCode,
                                        pricePerHour,
                                        "no","no",userID,"no"
                                        ,"0",
                                        0d,0d,
                                        0d,
                                        0d);
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


                            if(task.getException()!=null){
                                Log.w(TAG, "Sign In fail reason: ", task.getException());
                                String reason = task.getException().getMessage();
                                ((LoginPresenter)presenter).
                                        logInFailReason(reason);
                            } else {
                                ((LoginPresenter)presenter).logInFailReason("Log in failed");

                            }




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

    public void getUser(final WelcomeActivityPresenter presenter) {
        Log.d("Updating Tenant", "Pulling for db");
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser != null) {
            final String uID = mUser.getUid();
            mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        Tenant tenant = data.child(uID).getValue(Tenant.class);
                        Log.d("Done: ", "Pulling for db");
                        presenter.loadingUsersDataCompleted(tenant);

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

    public void setIsHeRenting(String flag) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null) {
            String userId = mUser.getUid();
            setValue(userId, "isHeRenting", flag);
        }
    }

    public void setHasParking(String hasParking){
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null){
            String userId = mUser.getUid();
            setValue(userId,"hasParking", hasParking);
        }
    }
    public void setUsersIdParkingThatHeIsRenting(String usersIdParkingThatHeIsRenting){
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null){
            String userId = mUser.getUid();
            setValue(userId,"usersIdParkingThatHeIsRenting", usersIdParkingThatHeIsRenting);
        }
    }

    public void setLatOfUsersParking(double lat){
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null){
            String userId = mUser.getUid();

            setDoubleValue(userId,"latOfHisParking", lat);
        }
    }

    public void setLngOfUsersParking(double lng){
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null){
            String userId = mUser.getUid();
            setDoubleValue(userId,"lngOfHisParking",lng);
        }
    }

    public void setLatOfTheParkingThatHeIsCurrentlyRenting(double lat){
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null){
            String userId = mUser.getUid();
            setDoubleValue(userId,"latOfParkingThatHeIsCurrentlyRenting",lat);
        }
    }

    public void setLngOfTheParkingThatHeIsCurrentlyRenting(double lng){
        FirebaseUser mUser = mAuth.getCurrentUser();
        if(mUser!=null){
            String userId = mUser.getUid();
            setDoubleValue(userId,"lngOfParkingThatHeIsCurrentlyRenting",lng);
        }
    }

    public void setDoubleValue(String uId, String field, double value){
        mReference.child("Users").child(uId).child(field).setValue(value);

    }
    public void setParkingAvailableToRent(String uId, String rented){
        setValue(uId,"rented",rented);
    }

    private void setValue(String userId, String field, String value) {
        mReference.child("Users").child(userId).child(field).setValue(value);
    }




}