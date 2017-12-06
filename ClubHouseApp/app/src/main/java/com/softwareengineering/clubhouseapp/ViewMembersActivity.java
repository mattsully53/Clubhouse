package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ViewMembersActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor memberCursor;
    private int groupId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_members);

        //Get groupId and userId from UserProfileActivity
        groupId = getIntent().getIntExtra("groupId", 0);
        userId = getIntent().getIntExtra("userId",0);

        //Populate the ListView with members of the chosen group
        new UpdateMemberListTask().execute(groupId);

        //Create the member list listener
        ListView listMembers = findViewById(R.id.list_members);
        AdapterView.OnItemClickListener memberClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //Pass the member the user clicks on to UserProfileActivity
                        Intent intent = new Intent(ViewMembersActivity.this, UserProfileActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                };

        //Assign the listener to the list view
        listMembers.setOnItemClickListener(memberClickListener);
    }

    private class UpdateMemberListTask extends AsyncTask<Integer,Void,Boolean> {

        ListView listMembers;

        protected void onPreExecute() {
            //Get reference of ListView list_members
            listMembers = findViewById(R.id.list_members);
        }

        protected Boolean doInBackground(Integer... groups) {
            Integer groupId = groups[0];
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(ViewMembersActivity.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                memberCursor = db.rawQuery("SELECT USERS._id, NAME FROM USERS JOIN USER_IN_GROUP ON USERS._id = USER_IN_GROUP.USER_ID WHERE USER_IN_GROUP.GROUP_ID = ?", new String[] {groupId.toString()} );
                return true;
            } catch (SQLException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(ViewMembersActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                //Display the NAME column of memberCursor through memberListAdapter
                SimpleCursorAdapter memberListAdapter = new SimpleCursorAdapter(ViewMembersActivity.this,
                        android.R.layout.simple_list_item_1,
                        memberCursor,
                        new String[] {"NAME"},
                        new int[] {android.R.id.text1},
                        0);
                listMembers.setAdapter(memberListAdapter);
            }
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        new ViewMembersActivity.UpdateMemberListTask().execute(groupId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        memberCursor.close();
        db.close();
    }
}
