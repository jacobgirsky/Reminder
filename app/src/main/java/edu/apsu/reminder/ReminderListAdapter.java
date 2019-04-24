package edu.apsu.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReminderListAdapter extends ArrayAdapter<Reminder> {

    private Context mContext;
    int mResource;


    public ReminderListAdapter(Context context, int resource, ArrayList<Reminder> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String reminder = getItem(position).getReminder();
        String remindDate = getItem(position).getDateToBeReminded();
        String remindTime = getItem(position).getTimeToBeReminded();

        Reminder reminder1 = new Reminder(reminder, remindDate, remindTime);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvReminder = convertView.findViewById(R.id.reminder_tv);
        tvReminder.setText(reminder);

        TextView tvTime = convertView.findViewById(R.id.remind_time_tv);
        tvTime.setText(remindTime);



        return convertView;

    }
}
