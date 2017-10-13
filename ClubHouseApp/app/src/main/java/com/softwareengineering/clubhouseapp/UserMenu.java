package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String user = intent.getStringExtra("user");

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(user);

        Button mViewProfileButton = (Button) findViewById(R.id.view_profile_button);
        mViewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, UserProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}