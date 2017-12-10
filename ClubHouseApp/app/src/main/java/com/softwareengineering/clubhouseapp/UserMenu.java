package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserMenu extends AppCompatActivity {

    // User's ID
    private int userId;

    /*
     * Display layout on page load
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        userId = (Integer) getIntent().getExtras().get("userId");

        // Remove the action bar back arrow
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    /*
     * When View Profile button is clicked
     */
    public void onClickViewProfile (View view) {
        goToIntent(UserProfileActivity.class);
    }

    /*
     * When Login button is clicked
     */
    public void onClickCreateGroup (View view) {
        goToIntent(CreateGroupActivity.class);
    }

    /*
     * When Join Group button is clicked
     */
    public void onClickJoinGroup (View view) {
        goToIntent(JoinGroupActivity.class);
    }

    /*
     * When View Groups button is clicked
     */
    public void onClickViewGroups (View view) {
        goToIntent(ViewGroupsActivity.class);
    }

    /*
     * When View Calendar button is clicked
     */
    public void onClickViewCalendar (View view) {
        goToIntent(CalendarActivity.class);
    }

    /*
     * Goes to the given page
     */
    private void goToIntent(Class location){
        Intent intent = new Intent(UserMenu.this, location);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

}
