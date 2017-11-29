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
