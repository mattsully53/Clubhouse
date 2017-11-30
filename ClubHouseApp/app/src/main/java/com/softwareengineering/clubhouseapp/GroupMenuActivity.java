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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;

public class GroupMenuActivity extends Activity {

    public static final String EXTRA_GROUPID = "groupId";
    private Cursor cursor;
    private SQLiteDatabase db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_menu);

        //Get the group and user from the intent
        int groupId = (Integer) getIntent().getExtras().get(EXTRA_GROUPID);
        userId = (Integer) getIntent().getExtras().get("userId");

        //PopulateViews
        new PopulateGroupMenuTask().execute(groupId);
    }

        private class PopulateGroupMenuTask extends AsyncTask<Integer, Void, Boolean> {

            protected Boolean doInBackground(Integer... groups) {
                int groupId = groups[0];
                SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(GroupMenuActivity.this);
                try {
                    db = clubhouseDatabaseHelper.getReadableDatabase();
                    cursor = db.query("GROUPS",
                            new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "BOOKMARK"},
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
                        boolean isBookmarked = (cursor.getInt(3) == 1);

                        //Populate Group Icon
                        ImageView groupIcon = (ImageView)findViewById(R.id.group_icon);
                        groupIcon.setImageResource(photoId);
                        groupIcon.setContentDescription(nameText);

                        //Populate Group Description
                        TextView groupDescription = (TextView) findViewById(R.id.group_description);
                        groupDescription.setText(descriptionText);

                        //Populate Bookmark Checkbox
                        CheckBox checkbox = (CheckBox)findViewById(R.id.bookmark);
                        checkbox.setChecked(isBookmarked);
                    }
                }
            }
        }

        public void onClickBookmark (View view) {
            int groupId = (Integer) getIntent().getExtras().get(EXTRA_GROUPID);
            new UpdateBookmarkTask().execute(groupId);
        }

        private class UpdateBookmarkTask extends AsyncTask<Integer, Void, Boolean> {
            ContentValues groupValues;

            protected void onPreExecute() {
                CheckBox checkbox = (CheckBox)findViewById(R.id.bookmark);
                groupValues = new ContentValues();
                groupValues.put("BOOKMARK", checkbox.isChecked());
            }

            protected Boolean doInBackground(Integer... groups){
                int groupId = groups[0];
                SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(GroupMenuActivity.this);
                try {
                    db = clubhouseDatabaseHelper.getWritableDatabase();
                    db.update("GROUPS", groupValues, "_id = ?", new String[] {Integer.toString(groupId)});
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
            }
        }

        @Override
        public void onDestroy () {
            super.onDestroy();
            cursor.close();
            db.close();
        }

//    public void onClickViewCalendar (View view) {
//        Intent intent = new Intent(this, ViewCalendarActivity.class);
//        startActivity(intent);
//    }

    public void onClickViewMembers (View view) {
        Intent intent = new Intent(this, ViewMembersActivity.class);
        int groupId = (Integer) getIntent().getExtras().get(EXTRA_GROUPID);
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
