package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class JoinGroupActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor groupCursor, userGroupCursor;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        //Get the userId from UserMenu
        userId = (Integer) getIntent().getExtras().get("userId");

        //Populate the ListView with available groups
        new UpdateGroupListTask().execute("GROUPS");

        //Create the group list listener
        ListView listGroups = findViewById(R.id.list_groups);
        AdapterView.OnItemClickListener groupClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //Pass the group the user clicks on to update database and return to UserMenu
                            new UpdateGroupDatabaseTask().execute((int) id);
                            Intent intent = new Intent(JoinGroupActivity.this, UserMenu.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                    }
                };

        //Assign the listener to the list view
        listGroups.setOnItemClickListener(groupClickListener);
    }

    private class UpdateGroupDatabaseTask extends AsyncTask<Integer,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            Integer groupId = integers[0];
            Integer thisUserId = userId;
            String[] whereArgs = new String[] {groupId.toString(),thisUserId.toString()};
            ClubhouseDatabaseHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(JoinGroupActivity.this);
            try {
                db = clubhouseDatabaseHelper.getWritableDatabase();
                userGroupCursor = db.rawQuery("SELECT GROUP_ID, USER_ID FROM USER_IN_GROUP WHERE GROUP_ID = ? AND USER_ID = ?",
                        whereArgs);
                //Only update the database if userGroupCursor does not contain any results.
                //This insures the user does not join a group if they are already a part of it.
                if (!userGroupCursor.moveToFirst()) {
                    ClubhouseDatabaseHelper.insertUserInGroup(db, groupId, userId);
                }
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(JoinGroupActivity.this, "Database Unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private class UpdateGroupListTask extends AsyncTask<String,Void,Boolean> {
        ListView listGroups;

        protected void onPreExecute() {
            //Get reference of ListView list_groups
            listGroups = findViewById(R.id.list_groups);
        }

        protected Boolean doInBackground(String...table) {
            String tableName = table[0];
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(JoinGroupActivity.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                groupCursor = db.query(tableName,
                        new String[] {"_id", "NAME"},
                        null,null,null,null,null);
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(JoinGroupActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                //Display the NAME column of groupCursor through groupListAdapter
                SimpleCursorAdapter groupListAdapter = new SimpleCursorAdapter(JoinGroupActivity.this,
                        android.R.layout.simple_list_item_1,
                        groupCursor,
                        new String[] {"NAME"},
                        new int[] {android.R.id.text1},
                        0);
                listGroups.setAdapter(groupListAdapter);
            }
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        new UpdateGroupListTask().execute("GROUPS");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        groupCursor.close();
        userGroupCursor.close();
        db.close();
    }
}
