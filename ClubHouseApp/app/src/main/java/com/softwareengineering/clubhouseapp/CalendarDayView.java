package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

//A way to show the date and show events for that date
public class CalendarDayView extends Activity {
     private String date;
     private SQLiteDatabase db;
     private Cursor eventCursor;
     private int userId, groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day_view);

        //Get userId and groupId from CalendarActivity
        userId = getIntent().getIntExtra("userId", 0);
        groupId = getIntent().getIntExtra("groupId",0);
        date = getIntent().getExtras().get("date").toString();

        TextView dateView = findViewById(R.id.date);
        dateView.setText(date);

        //User cannot create an event when Calendar is accessed through user menu
        if(groupId > 0) {
            new UpdateGroupDayListTask().execute();
        } else {
            new UpdateUserDayListTask().execute();
            findViewById(R.id.EventViewButton).setVisibility(View.INVISIBLE);
        }

        Button mGotoEventCreation = findViewById(R.id.EventViewButton);
        mGotoEventCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarDayView.this, EventCreation.class);
                intent.putExtra("date", date);
                intent.putExtra("groupId", groupId);
                intent.putExtra("userId", userId);
                startActivityForResult(intent, 1);
            }
        });
    }

    private class UpdateGroupDayListTask extends AsyncTask<Void,Void,Boolean> {
        ListView listEvents;

        protected void onPreExecute() {
            listEvents = findViewById(R.id.list_Events);
        }

        protected Boolean doInBackground(Void...params) {
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(CalendarDayView.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                eventCursor = db.rawQuery("SELECT _id, DESCRIPTION FROM EVENT WHERE DATE = ? AND GROUP_ID = ?", new String[] {date, String.valueOf(groupId)} );
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(CalendarDayView.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                SimpleCursorAdapter groupListAdapter = new SimpleCursorAdapter(CalendarDayView.this,
                        android.R.layout.simple_list_item_1,
                        eventCursor,
                        new String[] {"DESCRIPTION"},
                        new int[] {android.R.id.text1},
                        0);
                listEvents.setAdapter(groupListAdapter);
            }
        }
    }

    private class UpdateUserDayListTask extends AsyncTask<Void,Void,Boolean> {
        ListView listEvents;

        protected void onPreExecute() {
            //Get reference of ListView list_events
            listEvents = findViewById(R.id.list_Events);
        }

        protected Boolean doInBackground(Void...params) {
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(CalendarDayView.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                eventCursor = db.rawQuery("SELECT EVENT._id, DESCRIPTION FROM EVENT JOIN USER_IN_GROUP ON EVENT.GROUP_ID = USER_IN_GROUP.GROUP_ID WHERE USER_IN_GROUP.USER_ID = ? AND DATE = ?", new String[] {String.valueOf(userId), date} );
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(CalendarDayView.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                SimpleCursorAdapter groupListAdapter = new SimpleCursorAdapter(CalendarDayView.this,
                        android.R.layout.simple_list_item_1,
                        eventCursor,
                        new String[] {"DESCRIPTION"},
                        new int[] {android.R.id.text1},
                        0);
                listEvents.setAdapter(groupListAdapter);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {

                new UpdateGroupDayListTask().execute();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventCursor.close();
        db.close();
    }
}
