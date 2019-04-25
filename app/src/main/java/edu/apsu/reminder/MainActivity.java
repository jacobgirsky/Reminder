package edu.apsu.reminder;

import android.app.NotificationManager;
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
import android.widget.AdapterView;
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

    ArrayAdapter<Reminder> adapter;

    private final String DATA_FILE_NAME = "remindersss.dat";

    private static final int REMINDER_REQUEST_CODE1 = 42;
    public static final String REMINDER_KEY = "reminder_key";
    public static final String REMINDER_DATE_KEY = "reminder_date_key";
    public static final String REMINDER_TIME_KEY = "reminder_time_key";

    boolean delete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ArrayList<Reminder> reminders = readData();


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, reminders);


        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

       // ReminderListAdapter adapter = new ReminderListAdapter(this, R.layout.row, reminders);
        //listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reminder reminder = (Reminder) parent.getItemAtPosition(position);
                if (delete) {
                    deleteReminder(reminder);
                }
                delete = false;

                    final ListView listView = findViewById(R.id.listview);
                    final String reminderText = reminder.getReminder();
                    final String date = reminder.getDateToBeReminded();
                    final String time = reminder.getTimeToBeReminded();
                    final int index = position;

                    Intent intent = new Intent(getApplicationContext(), ReminderView.class);
                    intent.putExtra("reminder", reminderText);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    intent.putExtra("index", index + "");
                    Log.i("open item ", index + "***************");
                    startActivityForResult(intent, REMINDER_REQUEST_CODE1);


                }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_action_add) {
            Intent intent = new Intent(getApplicationContext(), AddReminder.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REMINDER_REQUEST_CODE1);
        } else if (item.getItemId() == R.id.menu_delete) {
            delete = true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteReminder(Reminder reminder) {
        adapter.remove(reminder);
        adapter.notifyDataSetChanged();
    }

    // reads the data from the array list into the file
    private ArrayList<Reminder> readData() {
        ArrayList<Reminder> reminders = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput(DATA_FILE_NAME);
            Scanner scanner = new Scanner(fis);

            while(scanner.hasNext()) {
                String remind = scanner.nextLine();
                String dateStr = scanner.nextLine();
                String strTime = scanner.nextLine();

                Reminder reminder = new Reminder(remind, dateStr, strTime);
                reminders.add(reminder);
            }
            scanner.close();
        } catch (FileNotFoundException e) {

        }
        return reminders;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REMINDER_REQUEST_CODE1 || requestCode == RESULT_OK) {
            String reminder = data.getStringExtra(REMINDER_KEY);
            String dateStr = data.getStringExtra(REMINDER_DATE_KEY);
            String timeStr = data.getStringExtra(REMINDER_TIME_KEY);

            addReminder(reminder, dateStr, timeStr);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    // adds a new Reminder object to the adapter
    private void addReminder(String reminder, String dateToBeReminded, String timeToBeReminded) {
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
