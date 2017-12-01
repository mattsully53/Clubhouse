package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

//A way to show the date and show events for that date
public class CalendarDayView extends AppCompatActivity {
     int groupId;
     private String date;
     private SQLiteDatabase db;
     private Cursor eventCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day_view);
//        userId = (Integer) getIntent().getExtras().get("userId");
        date = getIntent().getExtras().get("date").toString();


        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.setText(date);


        new UpdateDayListTask().execute(groupId);


        Button mGotoEventCreation = (Button) findViewById(R.id.EventViewButton);
        mGotoEventCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarDayView.this, EventCreation.class);
                intent.putExtra("date", date);
                intent.putExtra("groupId", groupId);
                startActivityForResult(intent, 1);


            }
        });

    }


    private class UpdateDayListTask extends AsyncTask<Integer,Void,Boolean> {
        ListView listEvents;

        protected void onPreExecute() {
            listEvents =(ListView) findViewById(R.id.list_Events);

        }

        protected Boolean doInBackground(Integer...table) {
            Integer userId = table[0];
            SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(CalendarDayView.this);
            try {
                db = clubhouseDatabaseHelper.getReadableDatabase();
                eventCursor = db.rawQuery("SELECT _id, DESCRIPTION FROM EVENT WHERE DATE = ?", new String[] {date} );
                Log.d("TAG", "doInBackground: " + eventCursor.moveToFirst());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {

                new UpdateDayListTask().execute(groupId);
            }
        }
    }

}
