package com.derekbaumgartner.glooassessment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends ActionBarActivity {

    Button mSignUpButton, mLoginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class<? extends Activity> activityClass;
        User.initializeInstance(getApplicationContext());
        User user = User.getInstance();
        if(!user.isUserRegistered()) {
            Intent newActivity = new Intent(getApplicationContext(), SignUp.class);
            newActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(newActivity);
        }

        setContentView(R.layout.activity_login);

        mSignUpButton = (Button)findViewById(R.id.input_sign_up_button);
        mSignUpButton.setOnClickListener(signUpButtonClicked);

        mLoginButton = (Button)findViewById(R.id.input_login_button);
        mLoginButton.setOnClickListener(loginButtonClicked);
    }

    private View.OnClickListener signUpButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent newActivity = new Intent(getApplicationContext(), SignUp.class);
            newActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(newActivity);
        }
    };

    private View.OnClickListener loginButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean authenticated;
            EditText mUsername = (EditText)findViewById(R.id.input_username);
            EditText mPassword = (EditText)findViewById(R.id.input_password);


            User user = User.getInstance();
            authenticated = user.authenticateUser(mUsername.getText().toString(), mPassword.getText().toString());

            if(authenticated) {
                Intent newActivity = new Intent(getApplicationContext(), First.class);
                newActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(newActivity);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid Credentials, please try again.", Toast.LENGTH_LONG).show();
            }
        }
    };
}
