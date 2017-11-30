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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewGroupsActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor groupCursor, bookmarkCursor;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_groups);
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
                        intent.putExtra(GroupMenuActivity.EXTRA_GROUPID, (int) id);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                };
        //Assign the listener to the list view
        listGroups.setOnItemClickListener(groupClickListener);

        //Create the bookmark list listener
        ListView listBookmarks = findViewById(R.id.list_bookmarks);
        AdapterView.OnItemClickListener bookmarkClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //Pass the group the user clicks on to GroupMenuActivity
                        Intent intent = new Intent(ViewGroupsActivity.this, GroupMenuActivity.class);
                        intent.putExtra(GroupMenuActivity.EXTRA_GROUPID, (int) id);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                };
        //Assign the listener to the list view
        listBookmarks.setOnItemClickListener(bookmarkClickListener);
    }

    private class UpdateGroupListTask extends AsyncTask<Integer,Void,Boolean> {
        ListView listGroups, listBookmarks;

        protected void onPreExecute() {
            listGroups = findViewById(R.id.list_groups);
            listBookmarks = findViewById(R.id.list_bookmarks);
        }

        protected Boolean doInBackground(Integer...table) {
            Integer userId = table[0];
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(ViewGroupsActivity.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                groupCursor = db.rawQuery("SELECT GROUPS._id, NAME FROM GROUPS JOIN USER_IN_GROUP ON GROUPS._id = USER_IN_GROUP.GROUP_ID WHERE USER_IN_GROUP.USER_ID = ?", new String[] {userId.toString()} );
                Log.d("TAG", "doInBackground: " + groupCursor.moveToFirst());
                bookmarkCursor = db.query("GROUPS",
                        new String[] {"_id", "NAME"},
                        "BOOKMARK = 1",
                        null,null,null,null);
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
                SimpleCursorAdapter groupListAdapter = new SimpleCursorAdapter(ViewGroupsActivity.this,
                        android.R.layout.simple_list_item_1,
                        groupCursor,
                        new String[] {"NAME"},
                        new int[] {android.R.id.text1},
                        0);
                listGroups.setAdapter(groupListAdapter);

                SimpleCursorAdapter bookmarkListAdapter = new SimpleCursorAdapter(ViewGroupsActivity.this,
                        android.R.layout.simple_list_item_1,
                        bookmarkCursor,
                        new String[] {"NAME"},
                        new int[] {android.R.id.text1},
                        0);
                listBookmarks.setAdapter(bookmarkListAdapter);

            }
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        new UpdateGroupListTask().execute(userId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        groupCursor.close();
//        bookmarkCursor.close();
        db.close();
    }
}
