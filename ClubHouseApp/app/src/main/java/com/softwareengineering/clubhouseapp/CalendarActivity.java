
package com.softwareengineering.clubhouseapp;

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


    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    private TextView thedate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mCalendarView=(CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Intent intent = new Intent(CalendarActivity.this, CalendarDayView.class);
                intent.putExtra( "date",    (month+1) + "/" + dayOfMonth + "/" + year);

                startActivity(intent);
            }
        });
    }
}
