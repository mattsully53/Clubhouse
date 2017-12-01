package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText mNameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mBioView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public void onClickCreateAccount (View view) {
        mNameView = (EditText) findViewById(R.id.name);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mBioView = (EditText) findViewById(R.id.bio);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(validateEmailAndPassword(email, password)) {

            boolean check;

            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(CreateAccountActivity.this);
            SQLiteDatabase db = clubhouseDatabaseHelper.getWritableDatabase();
            check = ClubhouseDatabaseHelper.insertUser(db, mNameView.getText().toString(), mEmailView.getText().toString(),
                    mPasswordView.getText().toString(),
                    mBioView.getText().toString(),
                    R.drawable.blank_profile);

            if (check) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            } else {
                Toast.makeText(CreateAccountActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateEmailAndPassword(String email, String password){
        // Check for a valid password, if the user entered one.
        if ((TextUtils.isEmpty(password) || password.length() < 5)) {
            Toast.makeText(CreateAccountActivity.this, "The password must have more than 4 characters.", Toast.LENGTH_LONG).show();
            return false;
        }
        // Check for a valid email address.
        if (email.contains("@")) {
            return true;
        } else {
            Toast.makeText(CreateAccountActivity.this, "Please enter a valid email.", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
