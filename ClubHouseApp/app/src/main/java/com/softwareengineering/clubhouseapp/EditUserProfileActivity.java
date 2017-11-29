package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditUserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("user");
        final String bio = intent.getStringExtra("bio");


        EditText userEmail = (EditText) findViewById(R.id.edit_email);
        userEmail.setText(email);

        EditText userBio = (EditText) findViewById(R.id.edit_bio);
        userBio.setText(bio);

        Button mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditUserProfileActivity.this, UserProfileActivity.class);
                intent.putExtra("user", email);
                startActivity(intent);
            }
        });
    }
}
