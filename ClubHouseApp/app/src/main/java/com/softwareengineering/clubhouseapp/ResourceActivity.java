package com.softwareengineering.clubhouseapp;

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

import java.net.URI;
import java.net.URL;

public class ResourceActivity extends AppCompatActivity {

    public static final String Resources = "Resources";
    private Cursor cursor;
    private SQLiteDatabase db;
    private Object Downloader;

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
    }

    public void onClickDownloadResources(View view) {
        int groupId = (Integer) getIntent().getExtras().get(Resources);
        new DownloadResourcesTask().execute(groupId);
    }

    private class DownloadResourcesTask extends AsyncTask<URL, Integer, Long> {
        private Integer progressPercent;

        protected Long doInBackground(URL... urls) {
                int count = urls.length;
                long totalSize = 0;
                for (int i = 0; i < count; i++) {
                    totalSize = totalSize + Downloader(urls[i]);
                    publishProgress((int) ((i / (float) count) * 100));
                    // Escape early if cancel() is called
                    if (isCancelled()) break;
                }
                return totalSize;
            }

        private long Downloader(URL url) {
        }

        protected void onProgressUpdate(Integer... progress) {
                setProgressPercent(progress[0]);
            }

            protected void onPostExecute(Long result) {
                showDialog("Downloaded " + result + " bytes");
            }

        private void showDialog(String s) {
        }

        public void setProgressPercent(Integer progressPercent) {
            this.progressPercent = progressPercent;
        }

        public void execute(int groupId) {
        }
    }

    }

   public void onClickUploadResources(View view) {
        int groupId = (Integer) getIntent().getExtras().get(Resources);
        new UploadResourcesTask().execute(groupId);



    }}

    private class UploadResourcesTask extends AsyncTask<Integer, Void, Void> {

        private static final Object TaskSnapshot = null;

        @Override
        protected Void doInBackground(Integer... integers) {
            return null;
        }
        URI file = URI.fromFile(new file("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadResourcesTask= riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
UploadResourcesTask.addOnFailureListener(new UploadResourcesTask() {
            public onFailure; boolean Null;
            (!Null Exception){
                // Handle unsuccessful uploads
            }
        }

        public UploadResourcesTask OnSuccessListener;

        {
            class onSuccess {
                {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    URI downloadUrl;
                    assert TaskSnapshot != null;
                    downloadUrl = TaskSnapshot.getDownloadUrl();
                }

                file =URI.fromFile(new

                onSuccess("path/to/images/rivers.jpg"))
            }
        } }