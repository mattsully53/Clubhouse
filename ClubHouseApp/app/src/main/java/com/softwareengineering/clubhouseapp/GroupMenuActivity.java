package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;

public class GroupMenuActivity extends Activity {

    public static final String EXTRA_GROUPID = "groupId";
    private Cursor cursor;
    private SQLiteDatabase db;
    private int userId, groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_menu);

        //Get the group and user from the intent
        groupId = (Integer) getIntent().getExtras().get("groupId");
        userId = (Integer) getIntent().getExtras().get("userId");

        //PopulateViews
        new PopulateGroupMenuTask().execute(groupId);

        Button mViewCalendarButton = (Button) findViewById(R.id.view_calendar_button);
        mViewCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupMenuActivity.this, CalendarActivity.class);
                intent.putExtra("groupId",groupId);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
    }

    private class PopulateGroupMenuTask extends AsyncTask<Integer, Void, Boolean> {

        protected Boolean doInBackground(Integer... groups) {
            int groupId = groups[0];
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(GroupMenuActivity.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                cursor = db.query("GROUPS",
                        new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                        "_id = ?",
                        new String[]{Integer.toString(groupId)},
                        null, null, null);
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(GroupMenuActivity.this, "Database Unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                //Move to the first record in the cursor
                if (cursor.moveToFirst()) {
                    //Get the group details from the cursor
                    String nameText = cursor.getString(0);
                    String descriptionText = cursor.getString(1);
                    int photoId = cursor.getInt(2);

                    //Populate Group Icon
                    ImageView groupIcon = (ImageView)findViewById(R.id.group_icon);
                    groupIcon.setImageResource(photoId);
                    groupIcon.setContentDescription(nameText);

                    //Populate Group Description
                    TextView groupDescription = (TextView) findViewById(R.id.group_description);
                    groupDescription.setText(descriptionText);
                }
            }
        }
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
//        cursor.close();
        db.close();
    }

    public void onClickViewCalendar (View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("groupId", groupId);
        startActivity(intent);
    }

    public void onClickViewMembers (View view) {
        Intent intent = new Intent(this, ViewMembersActivity.class);
        intent.putExtra("groupId", groupId);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
//
//    public void onClickViewResources (View view) {
//        Intent intent = new Intent(this, ViewResourcesActivity.class);
//        startActivity(intent);
//    }
//
//    public void onClickJoinMeeting (View view) {
//        Intent intent = new Intent(this, JoinMeetingActivity.class);
//        startActivity(intent);
//    }
}
