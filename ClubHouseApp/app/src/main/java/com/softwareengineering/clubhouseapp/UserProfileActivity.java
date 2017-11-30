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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends AppCompatActivity {

    private getFullUserTask mQueryTask = null;

    private SQLiteDatabase db;
    private Cursor userCursor;

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

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        userId = (Integer) getIntent().getExtras().get("userId");

        mQueryTask = new getFullUserTask();
        mQueryTask.execute((Void) null);

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

        protected Boolean doInBackground(Void... params) {
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(UserProfileActivity.this);

            String[] whereArgs = new String[] {
                    String.valueOf(userId)
            };
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                userCursor = db.rawQuery("SELECT * FROM USERS WHERE _id = ? ", whereArgs);
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
            }
        }
    }
}
