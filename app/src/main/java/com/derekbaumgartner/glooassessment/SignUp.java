package com.derekbaumgartner.glooassessment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends ActionBarActivity {

    private EditText mFirstName, mLastName, mUsername, mPass, mPassConf;
    private Button mSignUpButton;
    private boolean passwordsMatch;
    private boolean submitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Get button, set disabled, and register on click listener
        mSignUpButton = (Button)findViewById(R.id.input_sign_up_button);
        mSignUpButton.setEnabled(false);
        mSignUpButton.setOnClickListener(signUpButtonClicked);

        // Get all EditText Fields
        mFirstName = (EditText)findViewById(R.id.input_first_name);
        mLastName = (EditText)findViewById(R.id.input_last_name);
        mUsername = (EditText)findViewById(R.id.input_username);
        mPass = (EditText)findViewById(R.id.input_password);
        mPassConf = (EditText)findViewById(R.id.input_password_confirmation);

        // Register listener on all fields
        mFirstName.addTextChangedListener(formFieldChanged);
        mLastName.addTextChangedListener(formFieldChanged);
        mUsername.addTextChangedListener(formFieldChanged);
        mPass.addTextChangedListener(passwordFieldChanged);
        mPassConf.addTextChangedListener(passwordFieldChanged);
    }

    private View.OnClickListener signUpButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitted = true;
            passwordsMatch = doPasswordsMatch();

            // If the passwords match then save the user
            if(passwordsMatch) {
                saveUser();
            }

            // Otherwise, let the user know the passwords don't match
            else {
                mPass.setError("Passwords must match.");
                mPassConf.setError("Passwords must match.");
            }
        }
    };

    private TextWatcher formFieldChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validateFields();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher passwordFieldChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // If the user has clicked sign up and the passwords did not match or still do not match
            if(submitted && !passwordsMatch) {
                // Make sure they are both filled
                if(isNotEmpty(mPass) && isNotEmpty(mPassConf)) {
                    passwordsMatch = doPasswordsMatch();

                    // If the passwords match, remove error
                    if(passwordsMatch) {
                        mPass.setError(null);
                        mPass.setError(null);
                    }
                }
            }

            validateFields();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void validateFields() {
        // Check if all fields are filled out
        if(isNotEmpty(mFirstName) && isNotEmpty(mLastName) &&
                isNotEmpty(mUsername) &&  isNotEmpty(mPass) && isNotEmpty(mPassConf)) {
                // If they are, enable the sign up button
                mSignUpButton.setEnabled(true);
        }
        // Otherwise, disable the sign up button
        else {
            mSignUpButton.setEnabled(false);
        }
    }

    private boolean isNotEmpty(EditText editText) {
        return editText.getText().toString().trim().length() != 0;
    }

    // Get text value from EditText
    private String editTextValue(EditText editText) {
        return editText.getText().toString();
    }

    // Check if passwords are identical
    private boolean doPasswordsMatch() {
        return mPass.getText().toString().equals(mPassConf.getText().toString());
    }

    // Save the user and end the intent
    private void saveUser() {
        User.initializeInstance(getApplicationContext());
        User user = User.getInstance();
        user.setUserCredentials(editTextValue(mFirstName), editTextValue(mLastName), editTextValue(mUsername), editTextValue(mPass));
        finish();
    }
}
