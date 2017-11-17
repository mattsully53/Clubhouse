package com.softwareengineering.clubhouseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matt on 11/3/17.
 * Added to by Zac on 11/16/17.
 */

public class ClubhouseDatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "clubhouse"; //Name of our database
    private static final int DB_VERSION = 4; //Version of our database

    ClubhouseDatabaseHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    public static void insertGroup(SQLiteDatabase db, String name, String description, int resourceId) {
        ContentValues groupValues = new ContentValues();
        groupValues.put("NAME", name);
        groupValues.put("DESCRIPTION", description);
        groupValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("GROUPS", null, groupValues);
    }

    private static void insertUser(SQLiteDatabase db, String email, String password, String bio, int resourceId) {
        ContentValues userValues = new ContentValues();
        userValues.put("EMAIL", email);
        userValues.put("PASSWORD", password);
        userValues.put("BIO", bio);
        userValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("USERS", null, userValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE GROUPS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "NAME TEXT, "
                        + "DESCRIPTION TEXT, "
                        + "IMAGE_RESOURCE_ID INTEGER);");
            insertGroup(db, "Computer Science Club", "Where nerds do cool stuff", R.drawable.blank_profile);
        }
        if (oldVersion < 2) {
            insertGroup(db, "Archery Club", "We like shooting stuff the old fashioned way", R.drawable.blank_profile);
        }

        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE GROUPS ADD COLUMN BOOKMARK NUMERIC;");
        }

        if (oldVersion < 4) {
            db.execSQL("CREATE TABLE USERS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "EMAIL TEXT, "
                    + "PASSWORD TEXT, "
                    + "BIO TEXT, "
                    + "IMAGE_ID INTEGER);");

            insertUser(db, "test@test.com", "testpass", "This is a test user.", R.drawable.blank_profile);
        }
    }


}
