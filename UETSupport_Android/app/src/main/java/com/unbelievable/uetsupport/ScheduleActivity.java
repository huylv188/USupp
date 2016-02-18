package com.unbelievable.uetsupport;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.unbelievable.uetsupport.objects.Course;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by DucAnhZ on 21/11/2015.
 */
public class ScheduleActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView scheduleListView;
    ArrayList<Course> scheduleArrayList;
    ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Thời khóa biểu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prepareList();
        scheduleListView = (ListView)findViewById(R.id.scheduleListView);
        scheduleAdapter = new ScheduleAdapter(this);
        scheduleListView.setAdapter(scheduleAdapter);



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


    private class ScheduleAdapter extends ArrayAdapter<Course>{

        public ScheduleAdapter(Context context) {
            super(context, -1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_schedule_item,parent,false);
            TextView tvScheduleStt = (TextView)rowView.findViewById(R.id.tvScheduleStt);
            TextView tvScheduleCourseName = (TextView)rowView.findViewById(R.id.tvScheduleCourseName);
            Switch btScheduleSwitch = (Switch)rowView.findViewById(R.id.btScheduleSwitch);

            tvScheduleStt.setText(position+1+"");
            tvScheduleCourseName.setText(scheduleArrayList.get(position).getName());
            btScheduleSwitch.setChecked(scheduleArrayList.get(position).isTurnedOnRemind());
            return rowView;
        }

        @Override
        public int getCount() {
            return scheduleArrayList.size();
        }
    }

    void prepareList(){
        scheduleArrayList = new ArrayList<>();
        scheduleArrayList.add(new Course("Giải tích 1 - 301-G2",true));
        scheduleArrayList.add(new Course("Cầu lông",true));
        scheduleArrayList.add(new Course("Tiếng Anh B1 - 304-GĐ2",true));
        scheduleArrayList.add(new Course("Đại số - 309-GĐ2",true));
        scheduleArrayList.add(new Course("Tiếng Anh B1 - 304-GĐ2",true));
        scheduleArrayList.add(new Course("Đại số BT - 309-GĐ2",true));
        scheduleArrayList.add(new Course("Cơ sở dữ liệu B1 - 309-G2",true));
        scheduleArrayList.add(new Course("Cơ sở dữ liệu TH - 201-G2",true));
        scheduleArrayList.add(new Course("Mạng máy tính - 304-GĐ2",true));

    }
}
