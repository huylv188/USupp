package uetsupport.dtui.uet.edu.uetsupport.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import org.jsoup.Jsoup;

import java.util.concurrent.ExecutionException;

import uetsupport.dtui.uet.edu.uetsupport.NewsActivity;
import uetsupport.dtui.uet.edu.uetsupport.R;
import uetsupport.dtui.uet.edu.uetsupport.asynctask.CheckNewAnnouncement;
import uetsupport.dtui.uet.edu.uetsupport.models.Announcement;

public class NotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                checkForNewAnnouncement();
//                processStartNotification();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void checkForNewAnnouncement() throws ExecutionException, InterruptedException {
        CheckNewAnnouncement cna = new CheckNewAnnouncement(getApplicationContext());
        cna.execute();
        if(cna.get()!=null){
//            Log.e("cxz","khac nulll"+cna.get());
            processStartNotification(cna.get());
        }else{
//            Log.e("cxz","bang null");
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    //tao notification tren status bar
    private void processStartNotification(Announcement announcement) {
        // Do something. For example, fetch fresh data from backend to create a rich notification?

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(announcement.getAnnouncementTitle())
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_500))
                        .setContentText(announcement.getAnnouncementDesc())
                        .setSmallIcon(R.mipmap.logo_uet);

        //start newsactivity khi click vao noti
        Intent mainIntent = new Intent(this, NewsActivity.class);
        mainIntent.putExtra("title",announcement.getAnnouncementTitle());
        mainIntent.putExtra("url",announcement.getAnnouncementLink());
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
//        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
