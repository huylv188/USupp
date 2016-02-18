package com.unbelievable.uetsupport.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.adapter.NotificationAdapter;
import com.unbelievable.uetsupport.common.Constant;
import com.unbelievable.uetsupport.objects.Notification;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nam on 11/20/2015.
 */
public class NotificationFragment extends Fragment {
    ListView listNotification;
    NotificationAdapter adapter;
    ArrayList<Notification> notificationArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu,container,false);
        listNotification = (ListView) v.findViewById(R.id.listNotification);
        notificationArrayList = new ArrayList<>();
        IntentFilter filter = new IntentFilter(Constant.GCM_RECEIVED_ACTION);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                notificationArrayList.add(new Notification("Thông báo",intent.getExtras().getString("msg", ""),false,new Date(System.currentTimeMillis())));
                adapter = new NotificationAdapter(getContext(),notificationArrayList);
                listNotification.setAdapter(adapter);
            }
        };
        getActivity().registerReceiver(broadcastReceiver, filter);
        adapter = new NotificationAdapter(getContext(),notificationArrayList);
        listNotification.setAdapter(adapter);
        return v;
    }
}
