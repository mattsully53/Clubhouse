
package com.softwareengineering.clubhouseapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.Month;


public class CalendarActivity extends AppCompatActivity {


    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    private int userId, groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mCalendarView=(CalendarView) findViewById(R.id.calendarView);
        Log.d(TAG, "onCreate: made it this far");
        groupId = (Integer) getIntent().getExtras().get("groupId");
        Log.d(TAG, "onCreate: got group ID successfully");
        userId = (Integer) getIntent().getExtras().get("userId");
        Log.d(TAG, "onCreate: got user ID successfully");
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Intent intent = new Intent(CalendarActivity.this, CalendarDayView.class);
                intent.putExtra( "date",    (month+1) + "/" + dayOfMonth + "/" + year);
                intent.putExtra("groupId", groupId);
                intent.putExtra("userId", userId);

                startActivity(intent);
            }
        });
    }
}
