package at.xtools.pwawrapper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HeadsUpNotificationActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra("CALL_RESPONSE_ACTION_KEY");

            if (action != null) {
                performClickAction(context, action);
            }

            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
            context.stopService(new Intent(context, CallService.class));
        }
    }

    private void performClickAction(Context context, String action) {
        if (action.equals("CALL_RECEIVE_ACTION")) {
            Intent openIntent = null;
            openIntent = new Intent(context, CallActivity.class);
            openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(openIntent);
        }  else if (action.equals("CALL_CANCEL_ACTION")) {
            context.stopService(new Intent(context, CallService.class));
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        }
    }
}