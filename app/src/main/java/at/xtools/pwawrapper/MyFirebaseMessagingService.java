package at.xtools.pwawrapper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        this.sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String number = remoteMessage.getData().get("number");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, CallService.class);
            intent.putExtra("title", title);
            intent.putExtra("number", number);
            context.startForegroundService(intent);
        } else {
            Intent intent = new Intent(this, CallService.class);
            intent.putExtra("title", title);
            intent.putExtra("number", number);
            startService(intent);
        }
    }
}