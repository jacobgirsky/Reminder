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
            TextView r_tv = findViewById(R.id.reminderText_textView);
            r_tv.setText(reminderText);

            TextView d_tv = findViewById(R.id.dateText_textView);
            d_tv.setText(date);

            TextView t_tv = findViewById(R.id.timeText_textView);
            t_tv.setText(time);
        }
    }
}
