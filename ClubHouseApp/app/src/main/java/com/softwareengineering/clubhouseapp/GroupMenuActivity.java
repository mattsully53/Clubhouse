package com.softwareengineering.clubhouseapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;

public class GroupMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClickViewCalendar (View view) {
        Intent intent = new Intent(this, ViewCalendarActivity.class);
        startActivity(intent);
    }

    public void onClickViewMembers (View view) {
        Intent intent = new Intent(this, ViewMembersActivity.class);
        startActivity(intent);
    }

    public void onClickViewResources (View view) {
        Intent intent = new Intent(this, ViewResourcesActivity.class);
        startActivity(intent);
    }

    public void onClickJoinMeeting (View view) {
        Intent intent = new Intent(this, JoinMeetingActivity.class);
        startActivity(intent);
    }
}
