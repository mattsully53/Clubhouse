package com.softwareengineering.clubhouseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EventCreation extends AppCompatActivity {

    private EditText Event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        Event=(EditText) findViewById(R.id.Event);
    }

}
