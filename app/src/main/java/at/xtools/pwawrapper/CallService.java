package at.xtools.pwawrapper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class CallService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = buildNotification(intent);
        startForeground(1, notification);
        sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification buildNotification(Intent intent) {
        String title = intent.getStringExtra("title");
        String number = intent.getStringExtra("number");

        Intent receiveCallAction = new Intent(this, HeadsUpNotificationActionReceiver.class);
        receiveCallAction.putExtra("CALL_RESPONSE_ACTION_KEY", "CALL_RECEIVE_ACTION");
        receiveCallAction.setAction("RECEIVE_CALL");

        Intent cancelCallAction = new Intent(this, HeadsUpNotificationActionReceiver.class);
        cancelCallAction.putExtra("CALL_RESPONSE_ACTION_KEY", "CALL_CANCEL_ACTION");
        cancelCallAction.setAction("CANCEL_CALL");

        PendingIntent receiveCallPendingIntent = PendingIntent.getBroadcast(this, 1200, receiveCallAction, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent cancelCallPendingIntent = PendingIntent.getBroadcast(this, 1201, cancelCallAction, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent fullScreenIntent = new Intent(this, CallActivity.class);
        fullScreenIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_ONE_SHOT);


        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel("incoming_call", "Incoming call");
        }

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_appbar)
                        .setContentTitle(title)
                        .setContentText(number)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .addAction(R.drawable.acpt_btn, "Answer", receiveCallPendingIntent)
                        .addAction(R.drawable.rjt_btn, "Decline", cancelCallPendingIntent)
                        // Use a full-screen intent only for the highest-priority alerts where you
                        // have an associated activity that you would like to launch after the user
                        // interacts with the notification. Also, if your app targets Android 10
                        // or higher, you need to request the USE_FULL_SCREEN_INTENT permission in
                        // order for the platform to invoke this notification.
                        .setFullScreenIntent(receiveCallPendingIntent, true);
        notificationBuilder.setAutoCancel(true);
        Notification incomingCallNotification = notificationBuilder.build();
        return incomingCallNotification;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel chan =  new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(chan);
        return channelId;
    }
}