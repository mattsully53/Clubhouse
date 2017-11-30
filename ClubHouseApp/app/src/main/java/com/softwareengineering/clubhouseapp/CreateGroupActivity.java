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
import android.widget.EditText;
import android.widget.Toast;

public abstract class CreateGroupActivity extends Activity {

    private EditText name, description;

    private SQLiteDatabase db;
    private Cursor userCursor;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        name = findViewById(R.id.group_name);
        description = findViewById(R.id.group_description);
        userId = (Integer)getIntent().getExtras().get("userId");

    }

    public void CreateGroup(View view) {
        String groupName = name.getText().toString();
        String groupDescription = description.getText().toString();
        SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(this);
        ContentValues groupValues = new ContentValues();
        groupValues.put("NAME", groupName);
        groupValues.put("DESCRIPTION", groupDescription);
        groupValues.put("IMAGE_RESOURCE_ID", R.drawable.blank_profile);
        try {
            db = clubhouseDatabaseHelper.getWritableDatabase();
            db.insert("GROUPS", null, groupValues);
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, UserMenu.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    //Adding Picture Functionalities
    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

    CreateGroupActivity(intent, 0) {

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
Intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
    startActivityForResult(intent, 0);

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            setPic();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        ClubhouseDatabaseHelper.Options bmOptions = new ClubhouseDatabaseHelper().Options();
        bmOptions.inJustDecodeBounds = true;
        ClubhouseDatabaseHelper.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}
