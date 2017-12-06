package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGroupActivity extends Activity {

    private EditText name, description;

    private SQLiteDatabase db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        //Get userId from UserMenu
        userId = (Integer)getIntent().getExtras().get("userId");

        //Get reference of input fields
        name = findViewById(R.id.group_name);
        description = findViewById(R.id.group_description);
    }

    public void onClickCreateGroup(View view) {
        //Capture user input
        String groupName = name.getText().toString();
        String groupDescription = description.getText().toString();

        //Create a new group in the database table GROUPS
        SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(this);
        ContentValues groupValues = new ContentValues();
        groupValues.put("NAME", groupName);
        groupValues.put("DESCRIPTION", groupDescription);
        groupValues.put("IMAGE_RESOURCE_ID", R.drawable.blank_profile);
        groupValues.put("OWNER_ID", userId);
        try {
            db = clubhouseDatabaseHelper.getWritableDatabase();
            db.insert("GROUPS", null, groupValues);
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT).show();
        }

        //Start UserMenu
        Intent intent = new Intent(this, UserMenu.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
