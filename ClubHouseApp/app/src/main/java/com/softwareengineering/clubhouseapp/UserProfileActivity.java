package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    private getFullUserTask mQueryTask = null;

    private SQLiteDatabase db;
    private Cursor userCursor;
    private Cursor groupCursor;

    private String mName;
    private String mEmail;
    private String mBio;
    private int mUserId;
    private int mImageId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userId = (Integer) getIntent().getExtras().get("userId");

        mQueryTask = new getFullUserTask();
        mQueryTask.execute((Void) null);

        //Create the group list listener
        ListView listGroups = (ListView) findViewById(R.id.list_user_groups);
        AdapterView.OnItemClickListener groupClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //Pass the group the user clicks on to GroupMenuActivity
                        Intent intent = new Intent(UserProfileActivity.this, GroupMenuActivity.class);
                        intent.putExtra(GroupMenuActivity.EXTRA_GROUPID, (int) id);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                };
        //Assign the listener to the list view
        listGroups.setOnItemClickListener(groupClickListener);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public void onClickEditProfile (View view) {
        Intent intent = new Intent(UserProfileActivity.this, EditUserProfileActivity.class);
        intent.putExtra("name", mName);
        intent.putExtra("email", mEmail);
        intent.putExtra("bio", mBio);
        intent.putExtra("user_id", mUserId);
        intent.putExtra("image_id", mImageId);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {
                mQueryTask = new getFullUserTask();
                mQueryTask.execute((Void) null);
            }
        }
    }

    private class getFullUserTask extends AsyncTask<Void, Void, Boolean> {

        ListView listUserGroups;

        protected void onPreExecute() {
            listUserGroups = (ListView) findViewById(R.id.list_user_groups);
        }

        protected Boolean doInBackground(Void... params) {
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(UserProfileActivity.this);

            String[] whereArgs = new String[] {
                    String.valueOf(userId)
            };
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                userCursor = db.rawQuery("SELECT * FROM USERS WHERE _id = ? ", whereArgs);
                groupCursor = db.rawQuery("SELECT GROUPS._id, NAME FROM GROUPS JOIN USER_IN_GROUP ON GROUPS._id = USER_IN_GROUP.GROUP_ID WHERE USER_IN_GROUP.USER_ID = ?", new String[] {String.valueOf(userId)} );
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(UserProfileActivity.this, "Database Unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                //Move to the first record in the cursor
                if (userCursor.moveToFirst()) {

                    mName = userCursor.getString(userCursor.getColumnIndex("NAME"));
                    mEmail = userCursor.getString(userCursor.getColumnIndex("EMAIL"));
                    mBio = userCursor.getString(userCursor.getColumnIndex("BIO"));
                    mImageId = userCursor.getInt(userCursor.getColumnIndex("IMAGE_RESOURCE_ID"));
                    mUserId = userCursor.getInt(userCursor.getColumnIndex("_id"));

                    //Populate User Icon
                    ImageView userIcon = (ImageView)findViewById(R.id.user_icon);
                    userIcon.setImageResource(mImageId);
                    userIcon.setContentDescription(mEmail);

                    //Populate User Bio
                    TextView userBio = (TextView) findViewById(R.id.user_bio);
                    userBio.setText(mBio);

                    //Populate User Email
                    TextView userEmail = (TextView) findViewById(R.id.user_email);
                    userEmail.setText(mEmail);

                    //Populate User Name
                    TextView userName = (TextView) findViewById(R.id.user_name);
                    userName.setText(mName);
                }
                SimpleCursorAdapter groupListAdapter = new SimpleCursorAdapter(UserProfileActivity.this,
                        android.R.layout.simple_list_item_1,
                        groupCursor,
                        new String[] {"NAME"},
                        new int[] {android.R.id.text1},
                        0);
                listUserGroups.setAdapter(groupListAdapter);
            }
        }
    }
}
