package com.derekbaumgartner.glooassessment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Second extends ActionBarActivity {

    private TextView mField1, mField2, mField3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button mSaveButton = (Button)findViewById(R.id.second_save_button);
        mSaveButton.setOnClickListener(saveButtonClicked);

        mField1 = (TextView)findViewById(R.id.textview_field1);
        mField2 = (TextView)findViewById(R.id.textview_field2);
        mField3 = (TextView)findViewById(R.id.textview_field3);

        Intent intent = getIntent();
        mField1.setText(intent.getStringExtra("field1"));
        mField2.setText(intent.getStringExtra("field2"));
        mField3.setText(intent.getStringExtra("field3"));

        mField1.setOnClickListener(field1Clicked);
        mField2.setOnClickListener(field2Clicked);
        mField3.setOnClickListener(field3Clicked);
    }

    private View.OnClickListener saveButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            returnData();
        }
    };

    private View.OnClickListener field1Clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialog(mField1, "Field 1");
        }
    };

    private View.OnClickListener field2Clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialog(mField2, "Field 2");
        }
    };

    private View.OnClickListener field3Clicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialog(mField3, "Field 3");
        }
    };

    // Create a dialog that allows a user to change the current field value
    private void createDialog(TextView field, String fieldName) {
        final TextView fieldTextView = field;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fieldName);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(fieldTextView.getText().toString());
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If the prompt field value is not null, set the field to the new value
                if(input.getText().toString().trim().length() != 0) {
                    fieldTextView.setText(input.getText().toString());
                }
                // Otherwise alert the user that
                else {
                    Toast.makeText(getApplicationContext(), "Field cannot be blank", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void returnData () {
        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("field1", mField1.getText().toString());
        bundle.putString("field2", mField2.getText().toString());
        bundle.putString("field3", mField3.getText().toString());
        returnIntent.putExtras(bundle);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
