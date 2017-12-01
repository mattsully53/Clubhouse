package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DeleteGroupActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor groupCursor;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_group);
        userId = (Integer)getIntent().getExtras().get("userId");

        //Populate the ListView with available groups
        new UpdateGroupListTask().execute();

        //Create the group list listener
        ListView listGroups = findViewById(R.id.list_groups);
        AdapterView.OnItemClickListener groupClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //Delete the group the user clicks on
                        new DeleteGroupTask().execute((int) id);
                        Intent intent = new Intent(DeleteGroupActivity.this, UserMenu.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                };
        //Assign the listener to the list view
        listGroups.setOnItemClickListener(groupClickListener);
    }

    private class DeleteGroupTask extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            Integer groupId = integers[0];
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(DeleteGroupActivity.this);
            try {
                db = clubhouseDatabaseHelper.getWritableDatabase();
                db.delete("GROUPS", "_id = ?", new String[] {groupId.toString()});
                db.delete("USER_IN_GROUP", "GROUP_ID = ?", new String[] {groupId.toString()});
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(DeleteGroupActivity.this, "Database Unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private class UpdateGroupListTask extends AsyncTask<Void,Void,Boolean> {
        ListView listGroups;

        protected void onPreExecute() {
            listGroups = findViewById(R.id.list_groups);
        }

        protected Boolean doInBackground(Void...params) {
            Integer id = userId;
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(DeleteGroupActivity.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                groupCursor = db.rawQuery("SELECT _id, NAME FROM GROUPS WHERE OWNER_ID = ?", new String[] {id.toString()});
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(DeleteGroupActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                SimpleCursorAdapter groupListAdapter = new SimpleCursorAdapter(DeleteGroupActivity.this,
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
        new UpdateGroupListTask().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        groupCursor.close();
        db.close();
    }
}
