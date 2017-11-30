package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        Button mCreateAccountButton = (Button) findViewById(R.id.create_account_button);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNameView = (EditText) findViewById(R.id.name);
                mEmailView = (EditText) findViewById(R.id.email);
                mPasswordView = (EditText) findViewById(R.id.password);
                mBioView = (EditText) findViewById(R.id.bio);

                boolean check;

                SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(CreateAccountActivity.this);
                SQLiteDatabase db = clubhouseDatabaseHelper.getWritableDatabase();
                check = ClubhouseDatabaseHelper.insertUser(db, mNameView.getText().toString(), mEmailView.getText().toString(),
                        mPasswordView.getText().toString(),
                        mBioView.getText().toString(),
                        R.drawable.blank_profile);

                if (check){
                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
