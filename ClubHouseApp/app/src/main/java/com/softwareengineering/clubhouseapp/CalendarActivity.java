
package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.CalendarView;
import android.content.Intent;
import android.os.Bundle;

public class CalendarActivity extends Activity {

    private CalendarView mCalendarView;
    private int userId, groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Get groupId and userId from UserMenu or ViewMembersActivity
        groupId = getIntent().getIntExtra("groupId", 0);
        userId = getIntent().getIntExtra("userId", 0);

        mCalendarView= findViewById(R.id.calendarView);

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
