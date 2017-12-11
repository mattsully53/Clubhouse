package com.softwareengineering.clubhouseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Matthew Sullivan on 11/3/17.
 * Added to by Zac on 11/16/17.
 */

public class ClubhouseDatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "clubhouse"; //Name of our database
    private static final int DB_VERSION = 1; //Version of our database

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

    public static boolean insertUser(SQLiteDatabase db, String name, String email, String password, String bio, int resourceId) {
        Long res;
        ContentValues userValues = new ContentValues();
        userValues.put("NAME", name);
        userValues.put("EMAIL", email);
        userValues.put("PASSWORD", password);
        userValues.put("BIO", bio);
        userValues.put("IMAGE_RESOURCE_ID", resourceId);
        res = db.insert("USERS", null, userValues);
        return !(res == -1);
    }

    public static void insertUserInGroup(SQLiteDatabase db, int groupId, int userId) {
        ContentValues userGroupValues = new ContentValues();
        userGroupValues.put("GROUP_ID", groupId);
        userGroupValues.put("USER_ID", userId);
        db.insert("USER_IN_GROUP", null, userGroupValues);
    }

    public static boolean updateUser(SQLiteDatabase db, int id, String name, String email, String bio, int resourceId) {
        int res;
        ContentValues userValues = new ContentValues();
        userValues.put("NAME", name);
        userValues.put("EMAIL", email);
        userValues.put("BIO", bio);
        userValues.put("IMAGE_RESOURCE_ID", resourceId);
        res = db.update("USERS", userValues, "_id = ?", new String[] {String.valueOf(id)});
        return (res == 1);
    }

    public static boolean insertEvent(SQLiteDatabase db, String description, int group_id, String date) {
        long res;
        ContentValues EventValues = new ContentValues();
        EventValues.put("DESCRIPTION", description);
        EventValues.put("GROUP_ID", group_id);
        EventValues.put("DATE", date);
        res = db.insert("EVENT", null, EventValues);
        return !(res == -1);
    }

    /*
    *  Method to allow easy updates to the user's database. This way, the user does not have
    *  to reinstall the app in order to update the database.
    */
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE USERS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "EMAIL TEXT, "
                    + "PASSWORD TEXT, "
                    + "BIO TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            db.execSQL("CREATE TABLE GROUPS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER, "
                    + "OWNER_ID INTEGER, "
                    + "FOREIGN KEY(OWNER_ID) REFERENCES USERS(_id));");
            db.execSQL("CREATE TABLE USER_IN_GROUP (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "GROUP_ID INTEGER, "
                    + "USER_ID INTEGER, "
                    + "FOREIGN KEY(GROUP_ID) REFERENCES GROUPS(_id), "
                    + "FOREIGN KEY(USER_ID) REFERENCES USERS(_id));");
            insertUser(db, "Bobby Joe", "test@test.com", "testpass", "This is a test user.", R.drawable.blank_profile);
            db.execSQL("CREATE TABLE EVENT (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DESCRIPTION TEXT, "
                    + "GROUP_ID INTEGER, "
                    + "DATE TEXT, "
                    + "FOREIGN KEY(GROUP_ID) REFERENCES GROUPS(_id));");
        }
    }
}
