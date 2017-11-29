package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String email = intent.getStringExtra("user");

        mQueryTask = new getFullUserTask(email);
        mQueryTask.execute((Void) null);


        Button mEditProfileButton = (Button) findViewById(R.id.edit_button);
        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, EditUserProfileActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

    public class getFullUserTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;

        getFullUserTask(String email) {
            mEmail = email;
        }

        protected Boolean doInBackground(Void... params) {
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(UserProfileActivity.this);

            String[] whereArgs = new String[] {
                    mEmail
            };
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                userCursor = db.rawQuery("SELECT * FROM USERS WHERE EMAIL = ? ", whereArgs);
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

                    String emailText = userCursor.getString(userCursor.getColumnIndex("EMAIL"));
                    String bioText = userCursor.getString(userCursor.getColumnIndex("BIO"));
                    int photoId = userCursor.getInt(userCursor.getColumnIndex("IMAGE_RESOURCE_ID"));

                    //Populate User Icon
                    ImageView userIcon = (ImageView)findViewById(R.id.user_icon);
                    userIcon.setImageResource(photoId);
                    userIcon.setContentDescription(emailText);

                    //Populate User Bio
                    TextView userBio = (TextView) findViewById(R.id.user_bio);
                    userBio.setText(bioText);

                    //Populate User Email
                    TextView userEmail = (TextView) findViewById(R.id.user_email);
                    userEmail.setText(emailText);
                }
            }
        }
    }
}
