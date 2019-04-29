package edu.apsu.reminder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class MyAdapter extends ArrayAdapter<Reminder> {

    private ArrayList<Reminder> reminders1;
    private Context mContext;



    public MyAdapter(Context context, ArrayList<Reminder> reminders) {
        super(context, R.layout.row, reminders);
        this.reminders1 = reminders;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(R.layout.row, parent, false);

        TextView tvReminder = convertView.findViewById(R.id.reminder_tv);
        tvReminder.setText(reminders1.get(position).getReminder());

        TextView tvTime = convertView.findViewById(R.id.remind_time_tv);
        tvTime.setText(reminders1.get(position).getTimeToBeReminded());


        return convertView;
    }
}
