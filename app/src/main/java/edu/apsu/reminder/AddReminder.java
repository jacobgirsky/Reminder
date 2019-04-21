package edu.apsu.reminder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class AddReminder extends Activity {

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    EditText et;
    EditText et2;

    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String amPm;

    private String reminder;
    private String remindDate;
    private String remindTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        Intent intent = getIntent();

        reminder = intent.getStringExtra(MainActivity.REMINDER_KEY);
        remindDate = intent.getStringExtra(MainActivity.REMINDER_DATE_KEY);
        remindTime = intent.getStringExtra(MainActivity.REMINDER_TIME_KEY);


        et = findViewById(R.id.reminder_et);
        et.setText(reminder);




        findViewById(R.id.choose_date_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date();
            }
        });

        findViewById(R.id.choose_time_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time();
            }
        });
  }

    // displays dialog for date picker
    private void date() {
        calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        et = findViewById(R.id.date_et);

        datePickerDialog = new DatePickerDialog(AddReminder.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                et.setText((month + 1) + "/" + dayOfMonth + "/" + year);
            }
        }, day, month, year);
        datePickerDialog.show();
    }


    // displays dialog for time picker
    private void time() {
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);
        et2 = findViewById(R.id.time_et);

        timePickerDialog = new TimePickerDialog(AddReminder.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                et2.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
            }
        }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        EditText editText = findViewById(R.id.reminder_et);
        reminder = editText.getText().toString();

        editText = findViewById(R.id.date_et);
        remindDate = editText.getText().toString();


        editText = findViewById(R.id.time_et);
        remindTime = editText.getText().toString();

            Intent intent = new Intent();
            intent.putExtra(MainActivity.REMINDER_KEY, reminder);
            intent.putExtra(MainActivity.REMINDER_DATE_KEY, remindDate);
            intent.putExtra(MainActivity.REMINDER_TIME_KEY, remindTime);

            setResult(RESULT_OK, intent);

        super.onBackPressed(); //  will end up closing the activity
    }

}