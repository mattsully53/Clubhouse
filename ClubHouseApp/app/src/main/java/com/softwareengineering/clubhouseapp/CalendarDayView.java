package com.softwareengineering.clubhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//A way to show the date and show events for that date
public class CalendarDayView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day_view);
        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(date);

        Button mGotoEventCreation = (Button) findViewById(R.id.EventViewButton);
        mGotoEventCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarDayView.this, EventCreation.class);
                intent.putExtra("date", date);
                startActivityForResult(intent, 1);


            }
        });

    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == 1) {
//            if (resultCode == Activity.RESULT_CANCELED) {
//                mQueryTask = new getFullUserTask();
//                mQueryTask.execute((Void) null);
//            }
//        }
//    }

}
