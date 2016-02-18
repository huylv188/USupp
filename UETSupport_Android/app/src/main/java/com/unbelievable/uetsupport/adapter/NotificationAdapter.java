package com.unbelievable.uetsupport.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.objects.Notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Nam on 11/22/2015.
 */
public class NotificationAdapter extends ArrayAdapter<Notification>{
    Context context;
    ArrayList<Notification> notificationArrayList;
    TextView tvNotificationTitle;
    TextView tvNotificationContent;
    TextView tvCreateTime;
    SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");

    public NotificationAdapter( Context context, ArrayList<Notification> notificationArrayList) {
        super(context, -1, notificationArrayList);
        this.context = context;
        this.notificationArrayList = notificationArrayList;
    }

    @Override
    public int getCount() {
        return notificationArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.list_notification_item, parent, false);
        tvNotificationTitle = (TextView) rowView.findViewById(R.id.tvNotificationTitle);
        tvCreateTime = (TextView) rowView.findViewById(R.id.tvCreateTime);
        tvNotificationContent = (TextView) rowView.findViewById(R.id.tvNotificationContent);
        tvNotificationTitle.setText(notificationArrayList.get(position).title);
        tvNotificationContent.setText(notificationArrayList.get(position).content);
        tvCreateTime.setText(format.format(notificationArrayList.get(position).createTime));
        if (notificationArrayList.get(position).important) tvNotificationTitle.setTextColor(Color.RED);
        return rowView;
    }
}
