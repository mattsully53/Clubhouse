package com.softwareengineering.clubhouseapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ResourceActivity extends AppCompatActivity {

    public static final String Resources = "Resources";
    private Cursor cursor;
    private SQLiteDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        //Get the group from the intent
        int groupId = (Integer) getIntent().getExtras().get(Resources);

        //PopulateViews
        new PopulateResourcesTask().execute(groupId);
    }



    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class PopulateResourcesTask extends AsyncTask<Integer, Void, Boolean> {

        protected Boolean doInBackground(Integer... groups) {
            int groupId = groups[0];
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(ResourceActivity.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                cursor = db.query("RESOURCES",
                        new String[]{"NAME"},
                        "_id = ?",
                        new String[]{Integer.toString(groupId)},
                        null, null, null);
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        public void onClickDeleteResources (View view) {
            int groupId = (Integer) getIntent().getExtras().get(Resources);
            new ResourceActivity.DeleteResourcesTask().execute(groupId);
        }

        private class DeleteResourcesTask {
        
        }


    public void onClickAddResources (View view) {
        int groupId = (Integer) getIntent().getExtras().get(Resources);
        new ResourceActivity.AddResourcesTask().execute(groupId);
    }
    private class AddResourcesTask {


    }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(ResourceActivity.this, "Resource Unavailable", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //Move to the first record in the cursor
                if (cursor.moveToFirst()) {
                    //Get the group details from the cursor
                    String nameText = cursor.getString(0);
                    String descriptionText = cursor.getString(1);
                    int photoId = cursor.getInt(2);
                    boolean isBookmarked = (cursor.getInt(3) == 1);
                }
            }
        }
    }
}



       @Override
        public void onRestart() {
            super.onRestart();
            new ViewGroupsActivity.UpdateResourcesTask().execute("RESOURCES");
        }

    @Override
    public void onDestroy () {
        super.onDestroy();
        cursor.close();
        db.close();}}

**/

