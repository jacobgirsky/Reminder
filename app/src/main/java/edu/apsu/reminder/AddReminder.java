package edu.apsu.reminder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class AddReminder extends Activity {

    TextView tv;
    TextView tv2;

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String amPm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        tv = findViewById(R.id.date_tv);
        tv2 = findViewById(R.id.time_tv);


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

        datePickerDialog = new DatePickerDialog(AddReminder.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv2.setText((month + 1) + "/" + dayOfMonth + "/" + year);
            }
        }, day, month, year);
        datePickerDialog.show();
    }


    // displays dialog for time picker
    private void time() {
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(AddReminder.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                tv.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
            }
        }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }

}