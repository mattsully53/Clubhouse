package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserMenu extends Activity {

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        //Get userId from Login Activity
        userId = getIntent().getIntExtra("userId",0);
    }

    //All onClick methods pass the userId with the created intent

    public void onClickViewProfile (View view) {
        Intent intent = new Intent(UserMenu.this, UserProfileActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

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

    public void onClickViewCalendar (View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
