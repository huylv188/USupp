package uetsupport.dtui.uet.edu.uetsupport.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.models.AlarmSchedule;
import uetsupport.dtui.uet.edu.uetsupport.models.Course;

/**
 * Created by huylv on 27-Dec-15.
 */
public class AlarmScheduleAdapter extends ArrayAdapter<Course> {
    Context context;
    ArrayList<Course> alarmScheduleArrayList;


    public AlarmScheduleAdapter(Context context,ArrayList<Course> al) {
        super(context,-1, al);
        this.context = context;
        alarmScheduleArrayList=al;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_alarm_schedule,parent,false);
        Switch switchAlarm = (Switch)rowView.findViewById(R.id.switchAlarmSchedule);
        Course c = alarmScheduleArrayList.get(position);
        String name = c.getTenMonHoc() +" - "+ c.getThu()+" - Tiết "+c.getTiet();
        switchAlarm.setText(name);
        switchAlarm.setChecked(c.isAlarmOn());

        return rowView;
    }

    @Override
    public int getCount() {
        return alarmScheduleArrayList.size();
    }
}
