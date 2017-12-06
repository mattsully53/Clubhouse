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

public class ViewGroupsActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor groupCursor;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);

        //Get userId from UserMenu
        userId = (Integer) getIntent().getExtras().get("userId");

        //Populate the ListView with available groups
        new UpdateGroupListTask().execute(userId);

        //Create the group list listener
        ListView listGroups = findViewById(R.id.list_groups);
        AdapterView.OnItemClickListener groupClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //Pass the group the user clicks on to GroupMenuActivity
                        Intent intent = new Intent(ViewGroupsActivity.this, GroupMenuActivity.class);
                        intent.putExtra("groupId", (int) id);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                };

        //Assign the listener to the list view
        listGroups.setOnItemClickListener(groupClickListener);
    }

    private class UpdateGroupListTask extends AsyncTask<Integer,Void,Boolean> {
        ListView listGroups;

        protected void onPreExecute() {
            //Get reference of ListView list_groups
            listGroups = findViewById(R.id.list_groups);
        }

        protected Boolean doInBackground(Integer...table) {
            Integer userId = table[0];
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(ViewGroupsActivity.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                groupCursor = db.rawQuery("SELECT GROUPS._id, NAME FROM GROUPS JOIN USER_IN_GROUP ON GROUPS._id = USER_IN_GROUP.GROUP_ID WHERE USER_IN_GROUP.USER_ID = ?", new String[] {userId.toString()} );
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(ViewGroupsActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                //Display the NAME column of groupCursor through groupListAdapter
                SimpleCursorAdapter groupListAdapter = new SimpleCursorAdapter(ViewGroupsActivity.this,
                        android.R.layout.simple_list_item_1,
                        groupCursor,
                        new String[] {"NAME"},
                        new int[] {android.R.id.text1},
                        0);
                listGroups.setAdapter(groupListAdapter);
            }
        }
    }

    public void onClickDeleteGroup(View view) {
        Intent intent = new Intent(this, DeleteGroupActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        new UpdateGroupListTask().execute(userId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        groupCursor.close();
        db.close();
    }
}
