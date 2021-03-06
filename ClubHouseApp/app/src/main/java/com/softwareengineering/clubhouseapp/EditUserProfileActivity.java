package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditUserProfileActivity extends AppCompatActivity {

    private EditText mNameView;
    private EditText mEmailView;
    private EditText mBioView;
    private int mUserId;
    private int mImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String bio = intent.getStringExtra("bio");
        mImageId = intent.getIntExtra("image_id", 1);
        mUserId = intent.getIntExtra("user_id", 0);

        EditText userName = (EditText) findViewById(R.id.edit_name);
        userName.setText(name);

        EditText userEmail = (EditText) findViewById(R.id.edit_email);
        userEmail.setText(email);

        EditText userBio = (EditText) findViewById(R.id.edit_bio);
        userBio.setText(bio);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public void onClickEditAccount (View view) {
        mEmailView = (EditText) findViewById(R.id.edit_email);
        mBioView = (EditText) findViewById(R.id.edit_bio);
        mNameView = (EditText) findViewById(R.id.edit_name);

        boolean check;

        SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(EditUserProfileActivity.this);
        SQLiteDatabase db = clubhouseDatabaseHelper.getWritableDatabase();

        check = ClubhouseDatabaseHelper.updateUser(db,
                mUserId,
                mNameView.getText().toString(),
                mEmailView.getText().toString(),
                mBioView.getText().toString(),
                mImageId
        );

        if (check){
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        } else {
            Toast.makeText(EditUserProfileActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();
        }
    }
}
