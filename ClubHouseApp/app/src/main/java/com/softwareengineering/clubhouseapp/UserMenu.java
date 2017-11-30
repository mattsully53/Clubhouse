package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserMenu extends AppCompatActivity {

    private int userId;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        userId = (Integer) getIntent().getExtras().get("userId");
        Integer user = userId;
        SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(this);
        db = clubhouseDatabaseHelper.getWritableDatabase();
        cursor = db.rawQuery("SELECT NAME FROM USERS WHERE _id = ?", new String[] {user.toString()});
        String userName = cursor.getString(0);
        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.user_name);
        textView.setText(userName);

        Button mViewProfileButton = (Button) findViewById(R.id.view_profile_button);
        mViewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, UserProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        Button mViewGroupsButton = (Button) findViewById(R.id.view_groups_button);
        mViewGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, ViewGroupsActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        Button mViewCalendarButton = (Button) findViewById(R.id.view_calendar_button);
        mViewCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenu.this, CalendarActivity.class);
                intent.putExtra("date", "12/17/2012");
                startActivity(intent);
            }
        });
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
