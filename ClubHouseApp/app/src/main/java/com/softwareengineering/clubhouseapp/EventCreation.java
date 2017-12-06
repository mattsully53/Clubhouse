package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.softwareengineering.clubhouseapp.ClubhouseDatabaseHelper.insertEvent;

public class EventCreation extends AppCompatActivity {

    private EditText mDescription;
    private String mDate;
    private SQLiteDatabase db;
    private Cursor eventCursor;
    private int userId, groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        Intent intent = getIntent();
        mDate = intent.getStringExtra("date");
        mDescription = (EditText) findViewById(R.id.Event);
        userId = (Integer) intent.getExtras().get("userId");
        groupId = (Integer) getIntent().getExtras().get("groupId");

        Button mCreateEventButton = (Button) findViewById(R.id.create_event_button);
        mCreateEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SQLiteOpenHelper clubhouseDatabaseHelper = new ClubhouseDatabaseHelper(EventCreation.this);
                SQLiteDatabase db = clubhouseDatabaseHelper.getWritableDatabase();

                boolean check = ClubhouseDatabaseHelper.insertEvent(db, mDescription.getText().toString(), groupId, mDate);
                if (check) {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();

                } else {
                    Toast.makeText(EventCreation.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                }
            }


        });

    }

}