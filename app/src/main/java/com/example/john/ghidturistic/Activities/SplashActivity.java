package com.example.john.ghidturistic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Helpers.FirebaseService;
import com.example.john.ghidturistic.Models.Objective;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import static com.example.john.ghidturistic.Helpers.FirebaseService.bus;


public class SplashActivity extends AppCompatActivity {

    FirebaseService firebaseService;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseService=FirebaseService.getInstance();
        firebaseService.firebaseInit();
        bus.register(this);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);


    }

    @Subscribe
    public void getObjectives(ArrayList<Objective> objectives){
        Toast.makeText(this, "objectives received", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent=new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseService.addAuthStateListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseService.removeAuthStateListener();
    }
}
