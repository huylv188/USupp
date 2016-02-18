package uetsupport.dtui.uet.edu.uetsupport.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.dialog.ReminderDialog;
import uetsupport.dtui.uet.edu.uetsupport.models.Reminder;

/**
 * Created by huylv on 30-Dec-15.
 */
public class ReminderAdapter extends ArrayAdapter<Reminder> {

    ArrayList<Reminder> reminderArrayList;
    Context context;

    public ReminderAdapter(Context context, ArrayList<Reminder> rl) {
        super(context, -1, rl);
        this.context = context;
        reminderArrayList=rl;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_reminder,parent,false);
        TextView tvReminderTitle = (TextView)rowView.findViewById(R.id.tvReminderTitle);
        final ImageView ivReminderON = (ImageView)rowView.findViewById(R.id.ivReminderON);
        LinearLayout llReminderIcon = (LinearLayout)rowView.findViewById(R.id.llReminderIcon);
        TextView tvReminderTime = (TextView)rowView.findViewById(R.id.tvReminderTime);
        RelativeLayout rlReminder = (RelativeLayout)rowView.findViewById(R.id.rlReminder);

        tvReminderTitle.setText(reminderArrayList.get(position).getTitle());
        tvReminderTime.setText(reminderArrayList.get(position).getTime());
        if(reminderArrayList.get(position).isON()){
            ivReminderON.setImageResource(R.mipmap.nhacnho_bat);
        }else{
            ivReminderON.setImageResource(R.mipmap.nhacnho_tat);
        }

        rlReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderDialog rd = new ReminderDialog(context, reminderArrayList.get(position));
                rd.show();
            }
        });
        llReminderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderArrayList.get(position).setON(!reminderArrayList.get(position).isON());
                if(reminderArrayList.get(position).isON()){
                    ivReminderON.setImageResource(R.mipmap.nhacnho_bat);
                }else{
                    ivReminderON.setImageResource(R.mipmap.nhacnho_tat);
                }
            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        return reminderArrayList.size();
    }
}
