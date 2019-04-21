package edu.apsu.reminder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Reminder> adapter;
    private final String DATA_FILE_NAME = "reminders.txt";
    private ListView listView;

    private static final int REMINDER_REQUEST_CODE1 = 42;
    public static final String REMINDER_KEY = "reminder_key";
    public static final String REMINDER_DATE_KEY = "reminder_date_key";
    public static final String REMINDER_TIME_KEY = "reminder_time_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String arr[] = {"Hry", "There"};

        ArrayList<Reminder> reminders = readData();


        ArrayAdapter<Reminder> adapters = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, reminders);

        listView = findViewById(R.id.listview);
        listView.setAdapter(adapters);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_action_add) {
            Intent intent = new Intent(this, AddReminder.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REMINDER_REQUEST_CODE1);
        }
        return super.onOptionsItemSelected(item);
    }

    // reads the data from the array list into the file
    private ArrayList<Reminder> readData() {
        ArrayList<Reminder> reminders = new ArrayList<>();

        Date dateCreated;
        Date d;

        try {
            FileInputStream fis = openFileInput(DATA_FILE_NAME);
            Scanner scanner = new Scanner(fis);

            while(scanner.hasNext()) {
                String remind = scanner.nextLine();
                String dateStr = scanner.nextLine();
                String strTime = scanner.nextLine();

                DateFormat dateFormats = new SimpleDateFormat("hh:mm:ss");

                DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

                try {
                    dateCreated = dateFormat.parse(dateStr);
                    d = dateFormats.parse(strTime);

                } catch (ParseException e) {
                    // this is not supposed to happen
                    // date value is in unexpected format
                    throw new RuntimeException(e);
                }

                Reminder reminder = new Reminder(remind, dateCreated, d);
                reminders.add(reminder);
            }
            scanner.close();
        } catch (FileNotFoundException e) {

        }
        return reminders;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REMINDER_REQUEST_CODE1 && requestCode == RESULT_OK) {
            String reminder = data.getStringExtra(REMINDER_KEY);
            String dateStr = data.getStringExtra(REMINDER_DATE_KEY);
            String timeStr = data.getStringExtra(REMINDER_TIME_KEY);

            Date remindDate;
            Date remindTime;

            DateFormat dateFormats = new SimpleDateFormat("hh:mm:ss");

            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

            try {
                remindDate = dateFormat.parse(dateStr);
                remindTime = dateFormats.parse(timeStr);

            } catch (ParseException e) {
                // this is not supposed to happen
                // date value is in unexpected format
                throw new RuntimeException(e);
            }

            addReminder(reminder, remindDate, remindTime);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // adds a new Reminder object to the adapter
    private void addReminder(String reminder, Date dateToBeReminded, Date timeToBeReminded) {
        Reminder myReminder = new Reminder(reminder, dateToBeReminded, timeToBeReminded);
        adapter.add(myReminder);
        writeData();
    }

    // writes the data to the text file
    private void writeData() {
        try {
            FileOutputStream fos = openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);

            for (int i = 0; i < adapter.getCount(); i++) {
                Reminder reminder = adapter.getItem(i);
                pw.println(reminder.getReminder() + "\n" + reminder.getDateToBeReminded() + "\n" + reminder.getTimeToBeReminded());
            }

            pw.close();
        } catch (FileNotFoundException e) {
            Log.e("WRITE ERROR", "Cannot save data: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }

}
