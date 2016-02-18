package com.unbelievable.uetsupport;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.unbelievable.uetsupport.dialog.ReminderDialog;
import com.unbelievable.uetsupport.objects.Reminder;

import java.util.ArrayList;

/**
 * Created by huylv on 21/11/2015.
 */
public class ReminderActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView reminderListView;
    ArrayList<Reminder> reminderArrayList;
    ReminderAdapter reminderAdapter;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remider);
        reminderArrayList = new ArrayList<>();
        reminderArrayList.add(new Reminder(new Long(1), 1000l, 1000l, 3, "Cầu lông 1", "note"));
        reminderArrayList.add(new Reminder(new Long(2), 1000l, 1000l, 3, "Học thêm tiếng anh", "note"));
        reminderArrayList.add(new Reminder(new Long(3), 1000l, 1000l, 3, "Giải trí cuối tuần", "note"));

        reminderListView = (ListView) findViewById(R.id.reminderListView);
        reminderAdapter = new ReminderAdapter(this,R.layout.list_reminder_item,reminderArrayList);
        reminderListView.setAdapter(reminderAdapter);
        reminderListView.setOnItemClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Nhắc nhở");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class ReminderAdapter extends BaseAdapter {
        private Context context;
        private int layoutId;
        private ArrayList<Reminder> reminders;

        public ReminderAdapter(Context context, int layoutId, ArrayList<Reminder> reminders) {
            this.context = context;
            this.layoutId = layoutId;
            this.reminders = reminders;
        }

        @Override
        public int getCount() {
            return reminders.size();
        }

        @Override
        public Object getItem(int position) {
            return reminders.get(position);
        }

        @Override
        public long getItemId(int position) {
            return reminders.get(position).getReminderId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = getLayoutInflater().inflate(layoutId,parent,false);
            }
            ImageView imgReminderItem = (ImageView) convertView.findViewById(R.id.ivReminderItem);
            TextView tvReminderTitle = (TextView) convertView.findViewById(R.id.tvReminderTitle);
            TextView tvReminderTime = (TextView) convertView.findViewById(R.id.tvReminderTime);
            Switch btnActivate = (Switch) convertView.findViewById(R.id.btnActivate);

            tvReminderTitle.setText(reminders.get(position).getTitle());
            tvReminderTime.setText(reminders.get(position).getTimeReminder() + "");
            Reminder reminder = reminders.get(position);
            btnActivate.setChecked(reminder.isActivated);
            reminder.isActivated = btnActivate.isChecked();
            return convertView;
        }
        //        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View rowView = inflater.inflate(R.layout.list_reminder_item, parent, false);
////            ImageView ivReminderItem = (ImageView)rowView.findViewById(R.id.ivProfileItem);
//            TextView tvReminderTitle = (TextView) rowView.findViewById(R.id.tvReminderTitle);
//            TextView tvReminderTime = (TextView) rowView.findViewById(R.id.tvReminderTime);
//
//            tvReminderTitle.setText(reminderArrayList.get(position).getTitle());
//            tvReminderTime.setText(reminderArrayList.get(position).getTimeReminder() + "");
//
//            return rowView;
//        }
//
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Reminder reminder = reminderArrayList.get(position);
        ReminderDialog dialog = new ReminderDialog(ReminderActivity.this,reminder);
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
