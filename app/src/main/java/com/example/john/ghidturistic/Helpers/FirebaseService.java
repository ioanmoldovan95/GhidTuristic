package com.example.john.ghidturistic.Helpers;

import android.support.annotation.NonNull;

import com.example.john.ghidturistic.Models.Objective;
import com.example.john.ghidturistic.Models.User;
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
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;


public class FirebaseService {

    private static FirebaseAuth mAuth;
    private static FirebaseAuth.AuthStateListener mAuthStateListener;
    private static FirebaseUser user = null;
    private static FirebaseDatabase mDatabase;
    private static FirebaseService firebaseService = new FirebaseService();
    private static ArrayList<Objective> objectives;
    public static Bus bus = new Bus(ThreadEnforcer.ANY);

    public static FirebaseService getInstance() {
        return firebaseService;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void firebaseInit() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        getObjectivesFromFirebase();
        if (mAuth.getCurrentUser() != null) {
            //TODO set user logged in -done
            bus.post(Constants.BusCodes.LOGIN_USER_CODE);
        }
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //TODO set user logged in -done
                    bus.post(Constants.BusCodes.LOGIN_USER_CODE);
                    DatabaseReference userRef = mDatabase.getReference(Constants.FirebaseDBKeys.USERS_DB).child(user.getUid());
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User loggedUser = dataSnapshot.getValue(User.class);
                            //TODO set app user
                            bus.post(loggedUser);
                            bus.post(loggedUser);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //TODO close login activity -done
                    bus.post(Constants.BusCodes.CLOSE_LOGIN_ACTIVITY_CODE);
                }
            }
        };
    }

    public void addAuthStateListener() {
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    public void removeAuthStateListener() {
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    public void registerNewUser(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                user = mAuth.getCurrentUser();
                if (user != null) {
                    addUserToDB(email);
                    //TODO set user logged in -done
                    bus.post(Constants.BusCodes.LOGIN_USER_CODE);

                } else {
                    // TODO set user logged out
                    bus.post(Constants.BusCodes.LOGOUT_USER_CODE);
                    // TODO show login failed toast
                    bus.post(Constants.BusCodes.LOGIN_FAILED);
                }
            }
        });
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                user = mAuth.getCurrentUser();
                if (user != null) {
                    //TODO set user logged in
                    bus.post(Constants.BusCodes.LOGIN_USER_CODE);
                } else {
                    //TODO set user logged out
                    bus.post(Constants.BusCodes.LOGOUT_USER_CODE);
                }
            }
        });
    }

    public void logoutUser() {
        mAuth.signOut();
        //TODO set user logged out
        bus.post(Constants.BusCodes.LOGOUT_USER_CODE);


    }

    private void addUserToDB(String email) {
        String userID = user.getUid();
        DatabaseReference userRef = mDatabase.getReference(Constants.FirebaseDBKeys.USERS_DB).child(userID);
        User newUser = new User(email);
        //TODO set user logged in
        bus.post(Constants.BusCodes.LOGIN_USER_CODE);
        userRef.setValue(newUser);
    }

    public void getObjectivesFromFirebase() {
        objectives = new ArrayList<>();
        DatabaseReference objRef = mDatabase.getReference(Constants.FirebaseDBKeys.OBJECTIVES_DB);

        objRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    {
                        objectives.add(postSnapshot.getValue(Objective.class));
                    }
                    //TODO update objectives
                    bus.post(Constants.BusCodes.OBJECTIVES_UPDATED);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addNewObjective(Objective objective) {
        DatabaseReference objRef = mDatabase.getReference(Constants.FirebaseDBKeys.OBJECTIVES_DB);
        String objId = objRef.push().getKey();
        objRef.child(objId).setValue(objective);
        //TODO update UI on new objective added
        this.getObjectivesFromFirebase();
    }

    public ArrayList<Objective> getObjectives(){
        return objectives;
    }

    public Bus getBus(){
        return bus;
    }
}
