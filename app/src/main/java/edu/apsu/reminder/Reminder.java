package edu.apsu.reminder;


import java.sql.Time;
import java.util.Date;
import java.util.Timer;

public class Reminder {

    private String reminder;
    private Date dateToBeReminded;
    private int timeToBeReminded;

    public Reminder(String reminder, Date dateToBeReminded, int timeToBeReminded) {
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

    public Date getDateToBeReminded() {
        return dateToBeReminded;
    }

    public void setDateToBeReminded(Date dateToBeReminded) {
        this.dateToBeReminded = dateToBeReminded;
    }

    public int getTimeToBeReminded() {
        return timeToBeReminded;
    }

    public void setTimeToBeReminder(int timeToBeReminder) {
        this.timeToBeReminded = timeToBeReminder;
    }

    @Override
    public String toString() {
        return reminder;
    }
}