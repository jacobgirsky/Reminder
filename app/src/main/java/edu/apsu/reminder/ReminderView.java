package edu.apsu.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static edu.apsu.reminder.MainActivity.REMINDER_DATE_KEY;
import static edu.apsu.reminder.MainActivity.REMINDER_KEY;
import static edu.apsu.reminder.MainActivity.REMINDER_REQUEST_CODE1;
import static edu.apsu.reminder.MainActivity.REMINDER_REQUEST_CODE2;
import static edu.apsu.reminder.MainActivity.REMINDER_TIME_KEY;
import static edu.apsu.reminder.MainActivity.reminders;

public class ReminderView extends Activity {
    public String position = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminderview);

        Intent intent = getIntent();

        if (intent != null) {
            // getting the reminder information
            final String reminderText = (String) intent.getStringExtra(REMINDER_KEY);
            final String date = (String) intent.getStringExtra(REMINDER_DATE_KEY);
            final String time = (String) intent.getStringExtra(REMINDER_TIME_KEY);
            final String index = (String) intent.getStringExtra("index");
            // display the reminder in reminder textview
            TextView r_tv = findViewById(R.id.reminderText_textView);
            r_tv.setText(reminderText);
            // display the reminder date in date textview
            TextView d_tv = findViewById(R.id.dateText_textView);
            d_tv.setText(date);
            // display the reminder time in time textview
            TextView t_tv = findViewById(R.id.timeText_textView);
            t_tv.setText(time);

            //when click edit button it will take the reminder information and open AddReminder activity
            Button edit_button;
            edit_button = (Button) findViewById(R.id.editt_button);
            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AddReminder.class);
                    intent.putExtra("reminder", reminderText);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    intent.putExtra("index", index + "");
                    startActivityForResult(intent, REMINDER_REQUEST_CODE2);
                }
            });
        }

    }

    //this method gets the updated reminder after being edited
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String reminderText = "", date = "", time = "";
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == REMINDER_REQUEST_CODE2) {
                reminderText = data.getStringExtra(REMINDER_KEY);
                date = data.getStringExtra(REMINDER_DATE_KEY);
                time = data.getStringExtra(REMINDER_TIME_KEY);
                position = data.getStringExtra("index");

                int index = Integer.parseInt(position);
                TextView r_tv = findViewById(R.id.reminderText_textView);
                r_tv.setText(reminderText);

                TextView d_tv = findViewById(R.id.dateText_textView);
                d_tv.setText(date);

                TextView t_tv = findViewById(R.id.timeText_textView);
                t_tv.setText(time);

                Reminder reminder = new Reminder(reminderText, date, time);
                reminders.set(index, reminder);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //moves the updated reminder information to main activity in order to replace
    //the old reminder in list view with the new one!
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();

        TextView r_tv = findViewById(R.id.reminderText_textView);
        String reminderTX = r_tv.getText().toString();

        TextView d_tv = findViewById(R.id.dateText_textView);
        String remindDate = d_tv.getText().toString();


        TextView t_tv = findViewById(R.id.timeText_textView);
        String remindTime = t_tv.getText().toString();

        intent.putExtra(MainActivity.REMINDER_KEY, reminderTX);
        intent.putExtra(MainActivity.REMINDER_DATE_KEY, remindDate);
        intent.putExtra(MainActivity.REMINDER_TIME_KEY, remindTime);
        intent.putExtra("index", position);

        setResult(RESULT_OK, intent);
        super.onBackPressed(); //  will end up closing the activity
    }
}
