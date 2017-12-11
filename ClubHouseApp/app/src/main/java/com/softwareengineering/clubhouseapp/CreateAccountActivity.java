package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends Activity {

    private EditText mNameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mBioView;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void onClickCreateAccount (View view) {
        //Get references of each input field
        mNameView = findViewById(R.id.name);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mBioView = findViewById(R.id.bio);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(validateEmailAndPassword(email, password)) {
            boolean check;

            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(CreateAccountActivity.this);
            db = clubhouseDatabaseHelper.getWritableDatabase();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
