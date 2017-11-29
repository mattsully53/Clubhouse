package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserMenu extends AppCompatActivity {

    private int userId;

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
                intent.putExtra("email", user);
                startActivity(intent);
            }
        });

        Button mViewGroupsButton = (Button) findViewById(R.id.view_groups_button);
        mViewGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, ViewGroupsActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        userId = (Integer) getIntent().getExtras().get("userId");
    }


//    public void onClickViewProfile (View view) {
//        Intent intent = new Intent(this, ViewProfileActivity.class);
//        intent.putExtra("userId", userId);
//        startActivity(intent);
//    }
//
    public void onClickCreateGroup (View view) {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void onClickJoinGroup (View view) {
        Intent intent = new Intent(this, JoinGroupActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void onClickViewGroups (View view) {
        Intent intent = new Intent(this, ViewGroupsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

//    public void onClickViewCalendar (View view) {
//        Intent intent = new Intent(this, ViewCalendarActivity.class);
//        intent.putExtra("userId", userId);
//        startActivity(intent);
//    }


}
