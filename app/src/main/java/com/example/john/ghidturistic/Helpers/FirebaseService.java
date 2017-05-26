package com.example.john.ghidturistic.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Activities.LoginActivity;
import com.example.john.ghidturistic.Activities.MainActivity;
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

import java.util.ArrayList;


public class FirebaseService {

    private static FirebaseAuth mAuth;
    private static FirebaseAuth.AuthStateListener mAuthStateListener;
    private static FirebaseUser user = null;
    private static FirebaseDatabase mDatabase;
    private static FirebaseService firebaseService = new FirebaseService();
    private static ArrayList<Objective> objectives;
    private DataSnapshot savedDataSnapshot;
    MainActivity mainActivity;

    public static FirebaseService getInstance() {
        return firebaseService;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void firebaseInit(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mainActivity = (MainActivity) context;
        getObjectives();
        if(mAuth.getCurrentUser()!=null){
            MainActivity.setUserLoggedIn(true);
        }
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    MainActivity.setUserLoggedIn(true);
                    DatabaseReference userRef = mDatabase.getReference(Constants.FirebaseDBKeys.USERS_DB).child(user.getUid());
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User loggedUser = dataSnapshot.getValue(User.class);
                            MainActivity.setAppUser(loggedUser);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if (LoginActivity.getActivity() != null) {
                        LoginActivity.getActivity().finish();
                    }
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
                    MainActivity.setUserLoggedIn(true);
                    Intent intent = new Intent(LoginActivity.getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    LoginActivity.getActivity().startActivity(intent);
                } else {
                    MainActivity.setUserLoggedIn(false);
                    MainActivity.setAppUser(null);
                    Toast.makeText(LoginActivity.getActivity(), LoginActivity.getActivity().getString(R.string.login_failed_string), Toast.LENGTH_SHORT).show();
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
                    MainActivity.setUserLoggedIn(true);
                    Intent intent = new Intent(LoginActivity.getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    LoginActivity.getActivity().startActivity(intent);
                } else {
                    MainActivity.setUserLoggedIn(false);
                    MainActivity.setAppUser(null);
                    Toast.makeText(LoginActivity.getActivity(), LoginActivity.getActivity().getString(R.string.login_failed_string), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logoutUser() {
        mAuth.signOut();
        MainActivity.setAppUser(null);
        MainActivity.setUserLoggedIn(false);
        mainActivity.fab.setVisibility(View.GONE);

    }

    private void addUserToDB(String email) {
        String userID = user.getUid();
        DatabaseReference userRef = mDatabase.getReference(Constants.FirebaseDBKeys.USERS_DB).child(userID);
        User newUser = new User(email);
        MainActivity.setUserLoggedIn(true);
        MainActivity.setAppUser(newUser);
        userRef.setValue(newUser);
    }

    public void getObjectives() {
        objectives = new ArrayList<>();
        DatabaseReference objRef = mDatabase.getReference(Constants.FirebaseDBKeys.OBJECTIVES_DB);

        objRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    {
                        objectives.add(postSnapshot.getValue(Objective.class));
                    }
                    mainActivity.updateObjectives(objectives);

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
        this.getObjectives();
    }
}
