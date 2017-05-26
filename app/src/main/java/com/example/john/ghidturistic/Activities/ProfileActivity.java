package com.example.john.ghidturistic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Helpers.Constants;
import com.example.john.ghidturistic.Helpers.FirebaseService;


public class ProfileActivity extends AppCompatActivity {

    TextView accountTypeTextView, emailTextView;
    ImageView accountImageView;
    Button logoutButton;
    ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent=getIntent();
        String email=intent.getStringExtra(Constants.Keys.EMAIL_KEY);
        accountImageView=(ImageView)findViewById(R.id.account_image_view);
        accountTypeTextView=(TextView)findViewById(R.id.account_type_text_view);
        emailTextView=(TextView)findViewById(R.id.email_text_view);
        logoutButton=(Button)findViewById(R.id.logout_button);
        backButton=(ImageButton)findViewById(R.id.back_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseService.getInstance().logoutUser();
                Intent intent =new Intent(ProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ProfileActivity.this.finish();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.finish();
            }
        });

        emailTextView.setText(email);

    }
}
