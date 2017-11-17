package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ViewUsersActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        //Populate the ListView with users of the chosen group
        new UpdateUserListTask().execute("GROUPS");

        //Create the group list listener
        ListView listGroups = (ListView)findViewById(R.id.list_groups);
        AdapterView.OnItemClickListener groupClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //Pass the group the user clicks on to GroupMenuActivity
                        Intent intent = new Intent(ViewGroupsActivity.this, GroupMenuActivity.class);
                        intent.putExtra(GroupMenuActivity.EXTRA_GROUPID, (int) id);
                        startActivity(intent);
                    }
                };
        //Assign the listener to the list view
        listGroups.setOnItemClickListener(groupClickListener);
    }


}
