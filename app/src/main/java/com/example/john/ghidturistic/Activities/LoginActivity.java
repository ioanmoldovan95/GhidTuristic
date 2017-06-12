package com.example.john.ghidturistic.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.ghid_turistic_cluj.R;
import com.example.john.ghidturistic.Helpers.Constants;
import com.example.john.ghidturistic.Helpers.FirebaseService;
import com.squareup.otto.Subscribe;


public class LoginActivity extends AppCompatActivity {

    EditText emailText, pass1Text, pass2Text;
    Button loginButton;
    FirebaseService firebaseService;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseService=FirebaseService.getInstance();
        emailText=(EditText)findViewById(R.id.email_edit_text);
        pass1Text=(EditText)findViewById(R.id.password_edit_text);
        pass2Text=(EditText)findViewById(R.id.password_repeat_edit_text);
        loginButton=(Button)findViewById(R.id.login_button);
        backButton=(ImageButton)findViewById(R.id.back_button);
        final TextView registerButton=(TextView)findViewById(R.id.register_text);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass2Text.setVisibility(View.VISIBLE);
                loginButton.setText(R.string.register_string);
                registerButton.setVisibility(View.GONE);
                backButton.setVisibility(View.VISIBLE);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginButton.getText().equals(getString(R.string.register_string))){
                    loginButton.setText(R.string.login_string);
                    registerButton.setVisibility(View.VISIBLE);
                    pass2Text.setVisibility(View.GONE);
                }else{
                    LoginActivity.this.finish();
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailText.getText().toString();
                String password=pass1Text.getText().toString();
                if(checkEmpty(email, R.string.empty_email_string)){
                    return;
                }else{
                    if(checkEmpty(password, R.string.empty_password_string)){
                        return;
                    }else{
                        if(!checkPassword(password,R.string.wrong_password_string)){
                            return;
                        }else{
                            if(!checkEmail(email, R.string.wrong_email_string)){
                                return;
                            }
                        }
                    }
                }
                if(loginButton.getText().equals(getString(R.string.register_string))){
                    String password2=pass2Text.getText().toString();
                    if(password2.equals(password)){
                        firebaseService.registerNewUser(email, password);
                    }
                }else{
                    firebaseService.loginUser(email, password);
                }
            }
        });
    }

    private boolean checkEmpty(String string, int resID){
        if(TextUtils.isEmpty(string)){
            Toast.makeText(this, resID, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean checkPassword(String pass, int resID){
        if(pass.length()<6 || pass.length()>20){
            Toast.makeText(this,resID,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkEmail(String email, int resID){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, resID, Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    @Subscribe
    public void closeActivity(int code){
        if(code== Constants.BusCodes.CLOSE_LOGIN_ACTIVITY_CODE){
            this.finish();
        }
    }

}
