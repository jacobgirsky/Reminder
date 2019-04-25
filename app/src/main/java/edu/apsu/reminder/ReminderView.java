package edu.apsu.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReminderView extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminderview);

        Intent intent = getIntent();

        if (intent != null) {
            final String reminderText = (String) intent.getStringExtra("reminder");
            final String date = (String) intent.getStringExtra("date");
            final String time = (String) intent.getStringExtra("time");
            final String index = (String) intent.getStringExtra("index");
            String reminder = "reminder: " + reminderText + "\n" + " date: " + date + "\n " + time;
            TextView tv = findViewById(R.id.view_textView);
            tv.setText(reminder);
        }
    }
}
