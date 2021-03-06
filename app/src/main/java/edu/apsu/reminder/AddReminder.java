package edu.apsu.reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddReminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Calendar calendar;
    DatePickerDialog datePickerDialog;


    TextView tv;

    private String reminder;
    private String remindDate;
    private String remindTime;
    public String position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Intent intent = getIntent();

        if (intent != null) {
            // getting the reminder information from ReminderView activity to be edited!
            reminder = intent.getStringExtra("reminder");
            remindDate = intent.getStringExtra("date");
            remindTime = intent.getStringExtra("time");
            position = intent.getStringExtra("index");
            Log.i("open item ADDReminder ", position + "***************");
        } else {

            reminder = intent.getStringExtra(MainActivity.REMINDER_KEY);
            remindDate = intent.getStringExtra(MainActivity.REMINDER_DATE_KEY);
            remindTime = intent.getStringExtra(MainActivity.REMINDER_TIME_KEY);
        }

        TextView reminderTV = findViewById(R.id.reminder_et);
        reminderTV.setText(reminder);

        TextView dateTV = findViewById(R.id.date_et);
        dateTV.setText(remindDate);

        TextView timeTV = findViewById(R.id.time_et);
        timeTV.setText(remindTime);

        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSave();
            }
        });

        Button buttonTimePicker = findViewById(R.id.choose_time_button);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        findViewById(R.id.choose_date_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date();
            }
        });
    }


    // displays dialog for date picker
    private void date() {
        calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        tv = findViewById(R.id.date_et);

        datePickerDialog = new DatePickerDialog(AddReminder.this,R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv.setText((month + 1) + "/" + dayOfMonth + "/" + year);
            }
        }, day, month, year);
        datePickerDialog.show();
    }


    // when the time is set it will start the alarm
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    // starts the alarm
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);


        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    // updates the text in the textview to show the time the user selects
    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        TextView timeTv = findViewById(R.id.time_et);
        timeTv.setText(timeText);
    }

    // called when the user presses the cancel button
    private void cancelSave() {
        setResult(RESULT_CANCELED);
        finish();
    }


    @Override
    public void onBackPressed() {

        //Adding new reminder
        TextView textView = findViewById(R.id.reminder_et);
        reminder = textView.getText().toString();

        textView = findViewById(R.id.date_et);
        remindDate = textView.getText().toString();


        textView = findViewById(R.id.time_et);
        remindTime = textView.getText().toString();
        if (reminder.length() != 0 && remindDate.length() != 0 && remindTime.length() != 0) {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.REMINDER_KEY, reminder);
            intent.putExtra(MainActivity.REMINDER_DATE_KEY, remindDate);
            intent.putExtra(MainActivity.REMINDER_TIME_KEY, remindTime);
            //if the reminder is for editing, then will need to put its index
            if (position != null) {
                intent.putExtra("index", position);
            }
            Log.i("PUT item ADDREMINDER", position + "***************");

            setResult(RESULT_OK, intent);

            super.onBackPressed(); //  will end up closing the activity
        }

    }
}