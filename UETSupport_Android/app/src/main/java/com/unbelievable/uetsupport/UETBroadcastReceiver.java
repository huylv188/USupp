package com.unbelievable.uetsupport;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.SoundEffectConstants;

import com.unbelievable.uetsupport.common.Constant;

/**
 * Created by DucAnhZ on 22/11/2015.
 */
public class UETBroadcastReceiver extends BroadcastReceiver {
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction().equals(Constant.GCM_RECEIVED_ACTION)) {
            // Handle notification
            notificationManager.notify(0,
                    notificationFromServer(context, intent.getExtras().getString("msg", "") ));

        }
    }

    private Notification notificationFromServer(Context context, String message) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        android.app.Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("UETSupport Thông Báo")
                .setVibrate(new long[]{1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true).setContentIntent(pendingIntent)
                .setPriority(Integer.MAX_VALUE)
                .build();

        return notification;
    }
}
