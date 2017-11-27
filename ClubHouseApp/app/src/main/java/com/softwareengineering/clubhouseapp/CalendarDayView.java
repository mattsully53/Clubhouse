package com.softwareengineering.clubhouseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CalendarDayView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day_view);
        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(date);
    }

}
