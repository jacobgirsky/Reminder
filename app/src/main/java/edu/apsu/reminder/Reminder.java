package edu.apsu.reminder;


import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class Reminder {

    private String reminder;
    private String  dateToBeReminded;
    private String timeToBeReminded;

    public Reminder(String reminder, String dateToBeReminded, String timeToBeReminded) {
        this.reminder = reminder;
        this.dateToBeReminded = dateToBeReminded;
        this.timeToBeReminded = timeToBeReminded;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getDateToBeReminded() {
        return dateToBeReminded;
    }

    public void setDateToBeReminded(String dateToBeReminded) {
        this.dateToBeReminded = dateToBeReminded;
    }

    public String getTimeToBeReminded() {
        return timeToBeReminded;
    }

    public void setTimeToBeReminder(String timeToBeReminder) {
        this.timeToBeReminded = timeToBeReminder;
    }

    @Override
    public String toString() {
        return reminder;
    }
}