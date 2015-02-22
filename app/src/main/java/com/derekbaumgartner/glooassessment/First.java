package com.derekbaumgartner.glooassessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class First extends ActionBarActivity {

    Button mProceedButton;
    EditText mField1, mField2, mField3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Get proceed button, disable it, and add on click listener
        mProceedButton = (Button)findViewById(R.id.first_proceed_button);
        mProceedButton.setEnabled(false);
        mProceedButton.setOnClickListener(proceedButtonClicked);

        mField1 = (EditText)findViewById(R.id.input_field1);
        mField2 = (EditText)findViewById(R.id.input_field2);
        mField3 = (EditText)findViewById(R.id.input_field3);

        // Set Text changed listners on the fields
        mField1.addTextChangedListener(textChanged);
        mField2.addTextChangedListener(textChanged);
        mField3.addTextChangedListener(textChanged);
    }

    private TextWatcher textChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkFields();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private View.OnClickListener proceedButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Create new intent
            Intent newActivity = new Intent(getApplicationContext(), Second.class);

            // Add field string values to bundle
            Bundle extras = new Bundle();
            extras.putString("field1", mField1.getText().toString());
            extras.putString("field2", mField2.getText().toString());
            extras.putString("field3", mField3.getText().toString());

            // Add bundle to intent
            newActivity.putExtras(extras);
            startActivityForResult(newActivity, 1);
        }
    };

    private void checkFields() {
        // Make sure all the fields are filled out
        if(isNotEmpty(mField1) && isNotEmpty(mField2) && isNotEmpty(mField3)) {
            // If all the fields are filled out, enable the proceed button
            mProceedButton.setEnabled(true);
        }
        else {
            // If the fields aren't all filled out, disable the button
            mProceedButton.setEnabled(false);
        }
    }

    // Check if the EditText is null or not
    private boolean isNotEmpty(EditText editText) {
        return editText.getText().toString().trim().length() != 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the intent returned new data, set the text fields to the new values
        if(resultCode == RESULT_OK){
            mField1.setText(data.getStringExtra("field1"));
            mField2.setText(data.getStringExtra("field2"));
            mField3.setText(data.getStringExtra("field3"));
        }
    }
}
