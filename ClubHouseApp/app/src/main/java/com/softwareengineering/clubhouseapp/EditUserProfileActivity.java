package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditUserProfileActivity extends AppCompatActivity {

    // UI references
    private EditText mNameView;
    private EditText mEmailView;
    private EditText mBioView;

    // User data
    private int mUserId;
    private int mImageId;

    /*
     * Display layout on page load
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        // Get the passed user information
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String bio = intent.getStringExtra("bio");
        mImageId = intent.getIntExtra("image_id", 1);
        mUserId = intent.getIntExtra("user_id", 0);

        // Populate UI with user data
        EditText userName = (EditText) findViewById(R.id.edit_name);
        userName.setText(name);
        EditText userEmail = (EditText) findViewById(R.id.edit_email);
        userEmail.setText(email);
        EditText userBio = (EditText) findViewById(R.id.edit_bio);
        userBio.setText(bio);

        // Remove the action bar back arrow
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public void onClickEditAccount (View view) {
        //Populate UI references
        mEmailView = (EditText) findViewById(R.id.edit_email);
        mBioView = (EditText) findViewById(R.id.edit_bio);
        mNameView = (EditText) findViewById(R.id.edit_name);

        // Used to verify that edit was successful
        boolean check;

        // Get the database
        SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(EditUserProfileActivity.this);
        SQLiteDatabase db = clubhouseDatabaseHelper.getWritableDatabase();

        // Update the user data
        check = ClubhouseDatabaseHelper.updateUser(db,
                mUserId,
                mNameView.getText().toString(),
                mEmailView.getText().toString(),
                mBioView.getText().toString(),
                mImageId
        );

        // If successful, finish
        if (check){
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        // If not successful, tell the user
        } else {
            Toast.makeText(EditUserProfileActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();
        }
    }
}
