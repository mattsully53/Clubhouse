package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserMenu extends AppCompatActivity {

    public static final String EXTRA_USERID = "userId";
    //int userId = (Integer) getIntent().getExtras().get(EXTRA_USERID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
    }


//    public void onClickViewProfile (View view) {
//        Intent intent = new Intent(this, ViewProfileActivity.class);
//        intent.putExtra(ViewProfileActivity.class, userId);
//        startActivity(intent);
//    }
//
//    public void onClickCreateGroup (View view) {
//        Intent intent = new Intent(this, CreateGroupActivity.class);
//        startActivity(intent);
//    }

    public void onClickJoinGroup (View view) {
        Intent intent = new Intent(this, JoinGroupActivity.class);
        startActivity(intent);
    }

    public void onClickViewGroups (View view) {
        Intent intent = new Intent(this, ViewGroupsActivity.class);
       // intent.putExtra(ViewGroupsActivity.EXTRA_USERID, userId);
        startActivity(intent);
    }

//    public void onClickViewCalendar (View view) {
//        Intent intent = new Intent(this, ViewCalendarActivity.class);
//        intent.putExtra(ViewCalendarActivity.EXTRA_USERID, userId);
//        startActivity(intent);
//    }


}
